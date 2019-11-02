package com.dingguan.cheHengShi.product.service;

import com.dingguan.cheHengShi.common.exception.CustomException;
import com.dingguan.cheHengShi.common.resp.ApiResult;
import com.dingguan.cheHengShi.common.utils.DateUtil;
import com.dingguan.cheHengShi.common.utils.Sequences;
import com.dingguan.cheHengShi.common.utils.UpdateTool;
import com.dingguan.cheHengShi.job.AsynchronousJob;
import com.dingguan.cheHengShi.product.entity.Product;
import com.dingguan.cheHengShi.product.entity.ProductParameter;
import com.dingguan.cheHengShi.product.entity.Sku;
import com.dingguan.cheHengShi.product.repository.ProductRepository;
import com.dingguan.cheHengShi.user.entity.BrowseRecord;
import com.dingguan.cheHengShi.user.entity.Manufacturer;
import com.dingguan.cheHengShi.user.service.BrowseRecordService;
import com.dingguan.cheHengShi.user.service.ManufacturerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zyc on 2018/12/3.
 */
@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductParameterService productParameterService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private AsynchronousJob asynchronousJob;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private BrowseRecordService browseRecordService;


    @Override
    public Product findByPrimaryKey(String id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product findByPrimaryKey(String id, String openId) throws CustomException {
        Product product = productRepository.findOne(id);
        if (product != null) {
            updateClicks(id, 1);
            if (StringUtils.isNotBlank(openId)) {
                BrowseRecord build = BrowseRecord.builder()
                        .id(Sequences.get())
                        .openId(openId)
                        .banner(product.getBanner())
                        .introduce(product.getIntroduce())
                        .productId(id)
                        .productType((2 + Integer.parseInt(product.getType())) + "")
                        .time(new Date())
                        .Title(product.getName())
                        .price(product.getMinPrice())
                        .sales(product.getSales())
                        .clicks(product.getClicks())
                        .build();

                browseRecordService.insertSelective(build);
            }

        }

        asynchronousJob.synchronousProduct(null, product.getId());


        return product;
    }

    @Override
    public ApiResult<List<Product>> findList(Product product, PageRequest pageRequest) {

        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Product> of = Example.of(product, matcher);
        Page<Product> page = productRepository.findAll(of, pageRequest);
        ApiResult apiResult = ApiResult.pageToApiResult(page);
        return apiResult;
    }

    @Override
    public List<Product> findList(Product product, Sort sort) {
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Product> of = Example.of(product, matcher);
        List<Product> productList = productRepository.findAll(of, sort);
        return productList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product insertSelective(Product product) throws CustomException {
        if (StringUtils.isBlank(product.getId())) {
            product.setId(Sequences.get());
        }
        if (product.getSort() == null) {
            Integer sort = findMaxSort();
            sort = sort == null ? 1 : ++sort;
            product.setSort(sort);
        }
        if (product.getSales() == null) {
            product.setSales(0);
        }
        if (product.getMinPrice() == null) {
            product.setMinPrice(new BigDecimal(0.00));
        }
        if (product.getClicks() == null) {
            product.setClicks(0);
        }
        if (product.getPostage() == null) {
            product.setPostage(new BigDecimal(0));
        }
        if(StringUtils.isBlank(product.getTime())){
            product.setTime(DateUtil.curDateStr());
        }
        if(StringUtils.isBlank(product.getPoster())){
            product.setPoster("1");
        }

        //
        if (StringUtils.isBlank(product.getStatus())) {
            product.setStatus("1");
        }
        if (product.getRecommend() == null) {
            product.setRecommend("1");
        }

        String manufacturerId = product.getManufacturerId();
        if (StringUtils.isBlank(manufacturerId)) {
            Manufacturer query = Manufacturer.builder()
                    .pattern("2")
                    .build();
            Sort sort = new Sort(Sort.Direction.ASC, "sort");
            List<Manufacturer> manufacturerList = manufacturerService.findList(query, sort);
            if (CollectionUtils.isNotEmpty(manufacturerList)) {
                Manufacturer manufacturer = manufacturerList.get(0);
                product.setManufacturerId(manufacturer.getId());


            }
        }
        Manufacturer manufacturer = manufacturerService.findByPrimaryKey(manufacturerId);
        product.setManufacturerBanner(manufacturer.getBanner());
        product.setManufacturerName(manufacturer.getCompanyName());


        List<ProductParameter> productParameterList = product.getProductParameterList();
        if (CollectionUtils.isNotEmpty(productParameterList)) {
            productParameterList.stream().forEach(
                    param -> {
                        param.setId(Sequences.get());
                        param.setProductId(product.getId());
                        productParameterService.insertSelective(param);
                    }
            );
        }


        List<Sku> skuList = product.getSkuList();
        if (CollectionUtils.isNotEmpty(skuList)) {



            if (CollectionUtils.isNotEmpty(skuList)) {
                //最小价格
                BigDecimal price = new BigDecimal(0);
                for (int i = 0; i < skuList.size(); i++) {
                    Sku sku = skuList.get(i);
                    if (i == 0) {
                        price = sku.getPrice();
                    } else {
                        if (price.compareTo(sku.getPrice()) > 0) {
                            price = sku.getPrice();
                        }
                    }
                }
                if (product.getMinPrice().compareTo(price) != 0) {
                    product.setMinPrice(price);

                }


                //库存
                Integer stock = 0;
                for (int i = 0; i < skuList.size(); i++) {
                    Sku sku = skuList.get(i);
                    stock = stock + sku.getStock();
                }
                if (!(product.getStock() + "").equals(stock)) {
                    product.setStock(stock);
                }

                //最小积分
                if ("2".equals(product.getType())) {
                    Integer minIntegral = 0;
                    for (int i = 0; i < skuList.size(); i++) {
                        Sku sku = skuList.get(i);
                        if (i == 0) {
                            minIntegral = sku.getIntegral();
                        } else {
                            if (minIntegral > sku.getIntegral()) {
                                minIntegral = sku.getIntegral();
                            }
                        }
                    }

                    Integer minIntegral1 = product.getMinIntegral();
                    if (minIntegral1 == null) {
                        minIntegral1 = 0;
                    }
                    if (minIntegral.intValue() != minIntegral1.intValue()) {
                        product.setMinIntegral(minIntegral);
                    }
                }
            }



        }

        productRepository.save(product);
        if(CollectionUtils.isNotEmpty(skuList)){
            for (int i = 0; i < skuList.size(); i++) {
                Sku param = skuList.get(i);
                param.setId(Sequences.get());
                param.setProductId(product.getId());
                skuService.insertSelective(param);
            }
        }



         return product;

    }

    @Override
    public void deleteByPrimaryKey(String id) {
        productRepository.delete(id);
    }

    @Override
    public Product updateByPrimaryKeySelective(Product product) throws CustomException {
        Product source = productRepository.findOne(product.getId());
        UpdateTool.copyNullProperties(source, product);
        product.setTime(DateUtil.curDateStr());
        asynchronousJob.synchronousShopCart(product, null);
        return productRepository.save(product);
    }

    @Override
    public Integer findMaxSort() {
        return productRepository.findMaxSort();
    }


    @Override
    public Page<Product> findByPoductTypeId(String productTypeId, Integer pageNo, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        Pageable pageRequest = new PageRequest(pageNo, pageSize, sort);
        return productRepository.findByProductTypeId(productTypeId, pageRequest);
    }

    @Override
    public List<Product> findByProductTypeId(String productTypeId) {
        return productRepository.findByProductTypeIdOrderBySort(productTypeId);
    }


    @Override
    public void updateStockAndSales(String id, Integer stock, Integer sales) {
        productRepository.updateProductStockAndSales(id, stock, sales);
    }

    @Override
    public void updateClicks(String id, Integer num) {
        productRepository.updateClicks(id, num);
    }

}
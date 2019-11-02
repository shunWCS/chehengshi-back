package com.dingguan.cheHengShi.trade.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zyc on 2018/12/26.
 */

@Data
@Entity
@ApiModel(description = "批量下单中间表")
@Table(name = "[batch_pay]")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchPay {

    @Id
    @Column(columnDefinition = "varchar(30) comment '批量下单中间表'")
    private String id;


    @ApiModelProperty(value = " 所属产品id")
    @Column(columnDefinition = "varchar(30) comment 'spuId'",name = "[order_id_str]")
    private String orderIdStr;

    @ApiModelProperty(value = " 下单时间")
    @Column(name = "[time]", columnDefinition = "TIMESTAMP  comment '下单时间'  ")
    private Date time;

}
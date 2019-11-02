package com.dingguan.cheHengShi.home.mapper;

import com.dingguan.cheHengShi.common.resp.MyMapper;
import com.dingguan.cheHengShi.home.dto.CommonDetail;
import com.dingguan.cheHengShi.home.dto.CourseVo;
import com.dingguan.cheHengShi.home.dto.FileSearchVo;
import com.dingguan.cheHengShi.home.dto.HomePage;
import com.dingguan.cheHengShi.home.entity.CourseApply;
import com.dingguan.cheHengShi.material.entity.Journalism;
import com.dingguan.cheHengShi.product.entity.Course;
import com.dingguan.cheHengShi.user.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @author: czh
 * @Date: 2019/9/18 21:41
 */
@Mapper
public interface CourseApplyMapper extends MyMapper<CourseApply> {

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "findCourseNews")
    List<HomePage> findCourseNews(FileSearchVo fileSearchVo);

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "findCourseByTypeId")
    List<Course> findCourseByTypeId(String typeId);

    @Select("select ca.*,c.title courseName from course_apply ca LEFT JOIN course c on ca.course_id = c.id order by ca.create_time desc")
    List<CourseApply> findApplyPersonList();

    @Select("SELECT ca.id,ca.apply_name applyName,ca.phone,ca.remark,c.id courseId,c.banner,c.price,c.begin_time beginTime,c.end_time endTime,c.title,c.address \n" +
            "from course_apply ca LEFT JOIN course c on ca.course_id = c.id where ca.id= #{id}")
    Map<String, Object> getCourseApplyById(Integer id);

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "searchCourse")
    List<Course> searchCourse(CourseVo courseVo);

    @Select("SELECT *FROM journalism WHERE id = #{id}")
    CommonDetail findJournalismById(String id);

    @Select("SELECT c.*,(SELECT if((c.end_time > NOW()),'报名中','报名截止') state) state, (SELECT if((c.end_time > NOW()),2,1) stateNo) stateNo FROM course c WHERE c.id = #{id}")
    Course findByPrimaryKey(String id);

    @Select(" select count(*) from course_apply where course_id=#{courseId} and open_id = #{openId}")
    Integer findCourseByCourseIdAndOpenId(@Param("courseId") String courseId, @Param("openId") String openId);

    @Select(" select count(*) from favorite where type=#{type} and open_id = #{openId} and id = #{courseId}")
    Integer findIsFavorite(@Param("courseId") String courseId, @Param("openId") String openId,@Param("type") String type);

    @Select("select *from address where 1=1 and open_id = #{openId} order by made_by_imperial_order desc ")
    List<Address> findAddress(String openId);

    @SelectProvider(type = CourseApplyMapperProvider.class,method = "findListAll")
    List<Journalism> findListAll(String type);
}

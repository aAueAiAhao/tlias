package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import io.micrometer.common.KeyValues;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
* 操作员工基础信息
* */
@Mapper
public interface EmpMapper {
    //原理版
    /*
    //查询总记录数
    @Select("select count(0) from emp e left join dept d on e.dept_id=d.id")
    public long count();
    //查询页内数据
    @Select("select e.* , d.name from emp as e  left join dept as d on e.dept_id=d.id limit #{start},#{pageSize}")
    public List<Emp> list(Integer start,Integer pageSize);*/

    //pageHelper插件版
    public List<Emp> list(EmpQueryParam empQueryParam);

    //添加员工信息
    /**
     * 新增员工数据
     */
    //主键回填,把数据库生成的主键返回到对象的属性
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);


}

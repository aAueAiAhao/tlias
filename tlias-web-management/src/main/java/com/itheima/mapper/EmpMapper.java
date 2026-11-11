package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
* 操作员工基础信息
* */
@Mapper
public interface EmpMapper {

    //查询总记录数
    @Select("select count(0) from emp e left join dept d on e.dept_id=d.id")
    public long count();
    //查询页内数据
    @Select("select e.* , d.name from emp as e  left join dept as d on e.dept_id=d.id limit #{start},#{pageSize}")
    public List<Emp> list(Integer start,Integer pageSize);
}

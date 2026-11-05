package com.itheima.mapper;

import com.itheima.pojo.Dept;
import jakarta.annotation.PostConstruct;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    //查询部门
 //方法1:手动结果映射
    /*@Results({
            @Result(column="create_time",property = "createTime"),
            @Result(column="update_time",property = "updateTime")
    })*/
    //select会自动把查询到的值赋给被调用的方法
 //方法2:起别名在create_time后面加createTime
    @Select("select id,name,create_time, update_time from dept order by update_time desc ")
    //接口不需要方法体
    public List<Dept> findAll();

    //方法3:驼峰命名,需打开yml文件查看

    //删除部门
    @Delete("delete from dept where id= #{id}")
    public void deleteById(Integer id);

    //添加部门
    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    public void add(Dept dept);

    //查询回显
    @Select("select name from dept where id=#{id}")
    Dept findById(Integer id);
    //修改数据
    @Update("update dept set name=#{name} ,update_time=#{updateTime} where id=#{id}")
    public void update(Dept dept);
}

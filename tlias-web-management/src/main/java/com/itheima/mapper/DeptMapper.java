package com.itheima.mapper;

import com.itheima.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {
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
}
 //方法3:驼峰命名,需打开yml文件查看
package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
* 分页结果封装类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    //返回总共多少数据
    private long total;
    //返回页面数据
    private List<T> rows;
}

package com.itheima.controller;

import com.itheima.pojo.JobOption;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@RestController()
@RequestMapping("/report")
public class reportController {

    @Autowired
    private EmpService empService;

    /*
    * 统计各个部门人数
    * */
    @GetMapping("/empJobData")
    public Result reportJob() {
        log.info("部门人数统计");
        return Result.success((empService.reportCountByDept()));
    }

    @GetMapping("/empGenderData")
    public Result reportGender() {
        log.info("性别统计");
        return Result.success(empService.reportCountByGender());
    }
}

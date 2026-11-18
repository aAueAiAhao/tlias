package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@RestController
public class UpdateController {


    /*  @PostMapping("/upload")
      public Result Update(String username,
                           Integer age,
              //如果接参与实参名字不一样
              *//*@RequestParam("file") MultipartFile image*//*
                         MultipartFile file
    ) throws Exception {
        log.info("上传文件:{},{},{}", username, age, file);
        //.transferTo方法是把文件下载到本地,Spring 会通过 InputStream 从源（临时文件或内存）读取数据，再通过 OutputStream 写入到 dest 指定的目标路径。
        file.transferTo(new File("D:/javaweb-image/" + file.getOriginalFilename()));
        return Result.success();
    }*/
    private static final String Update_Dir = "D:/javaweb-image/";

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /*
     *文件上传
     * */
    @PostMapping("/upload")
    public Result Update(MultipartFile file) throws Exception {
        log.info("上传文件:{}", file);
      /*  if (!file.isEmpty()) {
            //生成唯一文件名
            //获取原始名字
            String fileOriginalFilename = file.getOriginalFilename();
            //获取后缀名字,subString是截取方法,lastIndexOf是获取分割后的最后索引的元素
            String fileExtName = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
            //获取不重复的文件前缀名.replace方法把元素中所有的一个字符串变另一个字符串
            String uniqueName = UUID.randomUUID().toString().replace("-", "");
            //拼接完整得文件路径
            File targetFile = new File(Update_Dir + uniqueName + fileExtName);
            // 如果目标目录不存在，则创建它
               //targetFile 是“文件本身”（一张具体的照片）。
               //getParentFile() 是“装照片的那个文件袋”（D:/images）。
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            //保存到本地
            file.transferTo(targetFile);
        }*/
        if (!file.isEmpty()) {
            //生成唯一文件名
            String fileOriginalFilename = file.getOriginalFilename();
            String fileExtname=fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
            String finalFileName=UUID.randomUUID().toString().replace("-","")+fileExtname;
            String filePath = Update_Dir + finalFileName;
            //上传文件
            String url=aliyunOSSOperator.upload(file.getBytes(), fileOriginalFilename);
            return Result.success(url);
        }

        return Result.error("上传失败");

    }
}

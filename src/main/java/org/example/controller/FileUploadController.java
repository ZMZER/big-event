package org.example.controller;

import org.example.pojo.Result;
import org.example.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file) throws Exception {
        //把文件的内容存储到本地磁盘
        String originalFilename = file.getOriginalFilename();
        //保证文件名字唯一性 防止文件覆盖
        String fileName = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));

//        file.transferTo(new File("C:\\Users\\ASUS\\Desktop\\files\\"+fileName));
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}

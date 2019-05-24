package com.yetx.controller;

import com.yetx.pojo.User;
import com.yetx.utils.ResultVOUtils;
import com.yetx.vo.ResultVO;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageController {

    @Value("${img.space}")
    private String imgSpace;

    @PostMapping("/util/imgs")
    public ResultVO recieveImg(@RequestParam("file") MultipartFile[] files) {
        System.out.println("已经进入多文件接收");
        //定义一个要存储到磁盘的位置
        String fileSpace = imgSpace;

//        String PathToDB = "/data";
        //接下来将文件写入服务器磁盘，并将文件的相对路径存入数据库中
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        List<String> urls = new ArrayList<>();
        try {
            if (files != null && files.length > 0) {//简单判断是否有文件传输进来
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    if (!StringUtils.isEmpty(fileName)) {
                        String finalPath = fileSpace + "/" + fileName;
                        File finalFile = new File(finalPath);
                        if (finalFile.getParentFile() == null || !finalFile.getParentFile().isDirectory()) {
                            finalFile.getParentFile().mkdirs();//如第一次上传，需要创建一个文件夹
                        }
                        fileOutputStream = new FileOutputStream(finalFile);
                        inputStream = file.getInputStream();
                        IOUtils.copy(inputStream, fileOutputStream);
                        urls.add("/imgsave/" + fileName);//   "/imgsave/"+fileName==> "/usr/local/imgsave"+"/"+fileName";
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtils.success(urls);
    }
    @PostMapping("/util/imgone")
    public ResultVO recieveImgOne(@RequestParam("file") MultipartFile[] files) {
        System.out.println("已经进入单文件接收");
        //定义一个要存储到磁盘的位置
        String fileSpace = imgSpace;

//        String PathToDB = "/data";
        //接下来将文件写入服务器磁盘，并将文件的相对路径存入数据库中
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String url = "";
        try {
            if (files != null && files.length > 0) {//简单判断是否有文件传输进来
                MultipartFile file = files[0];
                    String fileName = file.getOriginalFilename();
                    if (!StringUtils.isEmpty(fileName)) {
                        String finalPath = fileSpace + "/" + fileName;
                        File finalFile = new File(finalPath);
//                        if (finalFile.getParentFile() == null || !finalFile.getParentFile().isDirectory()) {
//                            finalFile.getParentFile().mkdirs();//如第一次上传，需要创建一个文件夹
//                        }
                        fileOutputStream = new FileOutputStream(finalFile);
                        inputStream = file.getInputStream();
                        IOUtils.copy(inputStream, fileOutputStream);
                        url = "/imgsave/"+fileName;
                        //urls.add("/imgsave/" + fileName);//   "/imgsave/"+fileName==> "/usr/local/imgsave"+"/"+fileName";
                    }
                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtils.success(url);
    }
}

package cn.wbnull.springbootdemo.service;

import cn.wbnull.springbootdemo.boot.GlobalException;
import cn.wbnull.springbootdemo.model.ReturnMessage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

/**
 * 日志接口服务类
 *
 * @author dukunbiao(null)  2019-03-27
 * https://github.com/dkbnull/SpringBootDemo
 */
@Service
public class LogService {

    public void logDownload(String name, HttpServletResponse response) throws Exception {
        String fileName = safeFileName(name);
        File file = new File("logs", fileName);

        if (!file.exists()) {
            throw new GlobalException(fileName + "文件不存在");
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setContentLength((int) file.length());

        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            OutputStream os = response.getOutputStream();

            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        }
    }

    public JSONObject logUpload(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new GlobalException("未选择需上传的日志文件");
        }

        String filePath = new File("logs_app").getAbsolutePath();
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }

        fileUpload = new File(filePath, safeFileName(file.getOriginalFilename()));
        if (fileUpload.exists()) {
            throw new GlobalException("上传的日志文件已存在");
        }

        try {
            file.transferTo(fileUpload);

            return ReturnMessage.success();
        } catch (IOException e) {
            throw new GlobalException("上传日志文件到服务器失败：" + e.toString());
        }
    }

    public JSONObject logUploads(HttpServletRequest request) throws Exception {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");

        for (MultipartFile file : files) {
            logUpload(file);
        }

        return ReturnMessage.success();
    }

    private String safeFileName(String name) throws GlobalException {
        if (name == null || name.trim().isEmpty()) {
            throw new GlobalException("文件名不能为空");
        }

        String fileName;
        try {
            fileName = Paths.get(name).getFileName().toString();
        } catch (InvalidPathException e) {
            throw new GlobalException("文件名非法：" + name);
        }

        if (".".equals(fileName) || "..".equals(fileName) || fileName.trim().isEmpty()) {
            throw new GlobalException("文件名非法：" + name);
        }

        return fileName;
    }
}

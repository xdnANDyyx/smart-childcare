package com.zhyyt.nursery.module.system.controller;

import com.zhyyt.nursery.common.api.Result;
import com.zhyyt.nursery.utils.MinioUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "文件管理")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final MinioUtil minioUtil;

    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        String url = minioUtil.uploadFile(file);
        Map<String, String> result = new HashMap<>();
        result.put("url", url);
        result.put("fileName", file.getOriginalFilename());
        result.put("size", String.valueOf(file.getSize()));
        return Result.ok(result);
    }

    @Operation(summary = "多文件上传")
    @PostMapping("/uploads")
    public Result<Map<String, String>> uploads(@RequestParam("files") MultipartFile[] files) {
        Map<String, String> result = new HashMap<>();
        for (MultipartFile file : files) {
            String url = minioUtil.uploadFile(file);
            result.put(file.getOriginalFilename(), url);
        }
        return Result.ok(result);
    }
}

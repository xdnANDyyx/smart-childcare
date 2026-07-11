package com.zhyyt.nursery.module.iot.controller;

import com.zhyyt.nursery.common.api.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "IoT设备集成")
@RestController
@RequestMapping("/iot")
@RequiredArgsConstructor
public class IotDeviceController {

    private final RabbitTemplate rabbitTemplate;

    @Operation(summary = "人脸识别打卡回调(门禁设备Webhook)")
    @PostMapping("/face/callback")
    public Result<Map<String, Object>> faceCallback(@RequestBody FaceCallbackDTO dto) {
        // 将打卡数据发送到消息队列，异步处理
        Map<String, Object> message = new HashMap<>();
        message.put("studentId", dto.getStudentId());
        message.put("deviceId", dto.getDeviceId());
        message.put("faceScore", dto.getFaceScore());
        message.put("direction", dto.getDirection()); // in/out
        message.put("timestamp", LocalDateTime.now().toString());
        message.put("imageUrl", dto.getImageUrl());

        rabbitTemplate.convertAndSend(
                "nursery.direct",
                "nursery.routing.attendance",
                message
        );

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "success");
        return Result.ok(result);
    }

    @Operation(summary = "穿戴设备数据上报")
    @PostMapping("/wearable/data")
    public Result<Map<String, Object>> wearableData(@RequestBody WearableDataDTO dto) {
        Map<String, Object> message = new HashMap<>();
        message.put("studentId", dto.getStudentId());
        message.put("deviceId", dto.getDeviceId());
        message.put("heartRate", dto.getHeartRate());
        message.put("steps", dto.getSteps());
        message.put("temperature", dto.getTemperature());
        message.put("location", dto.getLocation());
        message.put("sleepStatus", dto.getSleepStatus());
        message.put("timestamp", LocalDateTime.now().toString());

        rabbitTemplate.convertAndSend(
                "nursery.direct",
                "nursery.routing.iot.data",
                message
        );

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "success");
        return Result.ok(result);
    }

    @Operation(summary = "体测设备数据上报")
    @PostMapping("/bodytest/data")
    public Result<Map<String, Object>> bodyTestData(@RequestBody BodyTestDataDTO dto) {
        Map<String, Object> message = new HashMap<>();
        message.put("studentId", dto.getStudentId());
        message.put("deviceId", dto.getDeviceId());
        message.put("height", dto.getHeight());
        message.put("weight", dto.getWeight());
        message.put("vitalCapacity", dto.getVitalCapacity());
        message.put("gripStrength", dto.getGripStrength());
        message.put("standingJump", dto.getStandingJump());
        message.put("timestamp", LocalDateTime.now().toString());

        rabbitTemplate.convertAndSend(
                "nursery.direct",
                "nursery.routing.iot.data",
                message
        );

        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "success");
        return Result.ok(result);
    }

    @Data
    public static class FaceCallbackDTO {
        private String studentId;
        private String deviceId;
        private String faceScore;
        private String direction;
        private String imageUrl;
    }

    @Data
    public static class WearableDataDTO {
        private String studentId;
        private String deviceId;
        private Integer heartRate;
        private Integer steps;
        private String temperature;
        private String location;
        private String sleepStatus;
    }

    @Data
    public static class BodyTestDataDTO {
        private String studentId;
        private String deviceId;
        private String height;
        private String weight;
        private String vitalCapacity;
        private String gripStrength;
        private String standingJump;
    }
}

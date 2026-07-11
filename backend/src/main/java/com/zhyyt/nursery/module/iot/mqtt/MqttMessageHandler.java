package com.zhyyt.nursery.module.iot.mqtt;

import com.zhyyt.nursery.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * MQTT消息处理器
 * 用于处理来自穿戴设备、体测设备等的MQTT消息
 * 实际使用时需配合EMQX MQTT Broker
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MqttMessageHandler {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 处理收到的MQTT设备消息
     */
    public void handleDeviceMessage(String topic, String payload) {
        log.info("收到MQTT消息: topic={}, payload={}", topic, payload);

        Map<String, Object> message = Map.of(
                "topic", topic,
                "payload", payload,
                "timestamp", LocalDateTime.now().toString()
        );

        // 转发到RabbitMQ进行异步处理
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_DIRECT,
                RabbitMQConfig.RK_IOT_DATA,
                message
        );
    }
}

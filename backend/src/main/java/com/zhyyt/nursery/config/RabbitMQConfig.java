package com.zhyyt.nursery.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ 配置
 */
@Configuration
public class RabbitMQConfig {

    // ======== 交换机 ========
    public static final String EXCHANGE_DIRECT = "nursery.direct";
    public static final String EXCHANGE_TOPIC = "nursery.topic";
    public static final String EXCHANGE_DELAY = "nursery.delay";

    // ======== 队列 ========
    public static final String QUEUE_ATTENDANCE = "nursery.queue.attendance";       // 出勤
    public static final String QUEUE_NURSERY_LOG = "nursery.queue.nursery.log";     // 保育日志
    public static final String QUEUE_IOT_DATA = "nursery.queue.iot.data";           // IoT设备数据
    public static final String QUEUE_NOTIFICATION = "nursery.queue.notification";   // 消息通知
    public static final String QUEUE_PAYMENT = "nursery.queue.payment";             // 缴费
    public static final String QUEUE_DELAY = "nursery.queue.delay";                 // 延迟队列

    // ======== 路由键 ========
    public static final String RK_ATTENDANCE = "nursery.routing.attendance";
    public static final String RK_NURSERY_LOG = "nursery.routing.nursery.log";
    public static final String RK_IOT_DATA = "nursery.routing.iot.data";
    public static final String RK_NOTIFICATION = "nursery.routing.notification";
    public static final String RK_PAYMENT = "nursery.routing.payment";

    // ======== 交换机声明 ========
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_DIRECT, true, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_TOPIC, true, false);
    }

    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(EXCHANGE_DELAY, "x-delayed-message", true, false, args);
    }

    // ======== 队列声明 ========
    @Bean
    public Queue queueAttendance() {
        return new Queue(QUEUE_ATTENDANCE, true);
    }

    @Bean
    public Queue queueNurseryLog() {
        return new Queue(QUEUE_NURSERY_LOG, true);
    }

    @Bean
    public Queue queueIotData() {
        return new Queue(QUEUE_IOT_DATA, true);
    }

    @Bean
    public Queue queueNotification() {
        return new Queue(QUEUE_NOTIFICATION, true);
    }

    @Bean
    public Queue queuePayment() {
        return new Queue(QUEUE_PAYMENT, true);
    }

    @Bean
    public Queue queueDelay() {
        return new Queue(QUEUE_DELAY, true);
    }

    // ======== 绑定 ========
    @Bean
    public Binding bindingAttendance() {
        return BindingBuilder.bind(queueAttendance()).to(directExchange()).with(RK_ATTENDANCE);
    }

    @Bean
    public Binding bindingNurseryLog() {
        return BindingBuilder.bind(queueNurseryLog()).to(directExchange()).with(RK_NURSERY_LOG);
    }

    @Bean
    public Binding bindingIotData() {
        return BindingBuilder.bind(queueIotData()).to(directExchange()).with(RK_IOT_DATA);
    }

    @Bean
    public Binding bindingNotification() {
        return BindingBuilder.bind(queueNotification()).to(directExchange()).with(RK_NOTIFICATION);
    }

    @Bean
    public Binding bindingPayment() {
        return BindingBuilder.bind(queuePayment()).to(directExchange()).with(RK_PAYMENT);
    }

    // ======== 序列化 ========
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }
}

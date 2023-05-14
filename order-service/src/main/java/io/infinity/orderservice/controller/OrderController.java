package io.infinity.orderservice.controller;

import io.infinity.basedomains.dto.Order;
import io.infinity.basedomains.dto.OrderEvent;
import io.infinity.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer producer;

    public OrderController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping("order")
    public String placeOrder(@RequestBody Order order) {

        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent event = new OrderEvent();

        event.setStatus("PENDING");
        event.setMessage("Order is in PENDING state");
        event.setOrder(order);

        producer.sendMessage(event);
        return "Order placed successfully!";
    }
}

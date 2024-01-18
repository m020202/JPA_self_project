package study.project.controller;

import jakarta.persistence.EntityManager;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.project.domain.*;
import study.project.dto.OrderDtoForController;
import study.project.dto.OrderDtoForRepository;
import study.project.repository.OrderRepository;
import study.project.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping("/orders")
    public List<OrderDtoForController> orders(@RequestParam(value = "name",required = false) String name, @RequestParam(value = "status",required = false) OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findAll(new OrderSearch(name, orderStatus));
        List<OrderDtoForController> result = orders.stream().map(o -> new OrderDtoForController(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/orders/V2")
    public Result orders(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        OrderStatus orderStatus;
        switch(status) {
            case "READY":
                orderStatus = OrderStatus.ORDER;
                break;
            case "COMP":
                orderStatus = OrderStatus.CANCEL;
                break;
            default:
                orderStatus = null;
                break;
        }

        List<OrderDtoForRepository> result = orderRepository.findAllByDto(new OrderSearch(name, orderStatus));
        return new Result(result, result.size());
    }

    @Data
    static class Result<T> {
        private T data;
        private int cnt;

        Result(T result, int size) {
            data = result;
            cnt = size;
        }
    }
}

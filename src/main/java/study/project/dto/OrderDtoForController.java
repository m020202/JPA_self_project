package study.project.dto;

import lombok.Data;
import study.project.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDtoForController {
     private Long id;
     private String name;
     private Address address;
     private DeliveryStatus deliveryStatus;
     private List<OrderItemDto> orderItems;

     public OrderDtoForController(Order order) {
          id = order.getId();
          name = order.getMember().getName();
          address = order.getDelivery().getAddress();
          deliveryStatus = order.getDelivery().getStatus();
          List<OrderItem> items = order.getOrderItems();
          orderItems = items.stream().map(i -> new OrderItemDto(i)).collect(Collectors.toList());
     }
}

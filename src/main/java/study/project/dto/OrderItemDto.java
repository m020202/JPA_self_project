package study.project.dto;

import lombok.Data;
import study.project.domain.OrderItem;

@Data
public class OrderItemDto {
    private String name;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        name = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}

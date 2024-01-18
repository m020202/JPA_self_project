package study.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
public class OrderSearch {
    private String name;
    private OrderStatus status;
}

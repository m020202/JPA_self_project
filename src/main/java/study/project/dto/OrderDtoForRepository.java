package study.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.project.domain.Address;
import study.project.domain.DeliveryStatus;

@Data
@AllArgsConstructor
public class OrderDtoForRepository {
    private Long id;
    private String name;
    private Address address;
    private DeliveryStatus status;
}

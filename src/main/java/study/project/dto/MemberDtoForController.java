package study.project.dto;

import lombok.Data;
import study.project.domain.Address;
import study.project.domain.Member;

@Data
public class MemberDtoForController {
    private Long id;
    private String name;
    private Address address;

    public MemberDtoForController(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.address = member.getAddress();
    }
}

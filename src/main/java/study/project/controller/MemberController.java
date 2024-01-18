package study.project.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.project.domain.Address;
import study.project.domain.Member;
import study.project.dto.MemberDtoForController;
import study.project.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/create")
    public CreateMemberResponse createMember(@RequestBody CreateMemberRequest request) {
        Address address = new Address();
        address.setCity(request.getCity());
        address.setZipcode(request.getZipcode());
        address.setStreet(request.getStreet());

        Member member = Member.createMember(request.getName(), address);
        member.setName(request.getName());
        memberService.join(member);
        return new CreateMemberResponse(member);
    }

    @PutMapping("/update/{id}/{category}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id, @PathVariable("category") String category, @RequestBody UpdateMemberRequest request) {
        Long updateMemberId = 1L;
        if (category.equals("address")) {
            Address address = new Address();
            address.setCity(request.getCity());
            address.setZipcode(request.getZipcode());
            address.setStreet(request.getStreet());
            updateMemberId = memberService.updateMemberAddress(id, address);
        }
        else {
            updateMemberId = memberService.updateMemberName(id, request.getName());
        }
        Member member = memberService.findOne(updateMemberId);
        return new UpdateMemberResponse(member);
    }

    @GetMapping("/members")
    public List<MemberDtoForController> members() {
        List<MemberDtoForController> collect = memberService.findMembers().stream()
                .map(m -> new MemberDtoForController(m))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/members/v2")
    public Result membersV2() {
        List<Member> members = memberService.findMembers();
        List<MemberDtoForController> collect = members.stream()
                .map(m -> new MemberDtoForController(m))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @AllArgsConstructor
    @Data
    static class Result<T> {
        private T data;
    }











//    @GetMapping("/members/v2")
//    public Result memberV2() {
//        List<Member> members = memberService.findMembers();
//        List<MemberDtoForController> collect = members.stream()
//                .map(m -> new MemberDtoForController(m))
//                .collect(Collectors.toList());
//
//        return new Result(collect, collect.size());
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private T data;
//        private int size;
//    }

    @Data
    static class UpdateMemberResponse {
        private Long id;
        private String name;
        private Address address;

        UpdateMemberResponse(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.address = member.getAddress();
        }
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
        private String city;
        private String street;
        private String zipcode;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;
        private String name;
        private Address address;

        CreateMemberResponse(Member member) {
            this.id = member.getId();
            this.name = member.getName();
            this.address = member.getAddress();
        }
    }

    @Data
    static class CreateMemberRequest{
        private String name;
        private String city;
        private String street;
        private String zipcode;
    }
}

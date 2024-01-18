package study.project.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import study.project.domain.Member;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);

        Member findMember = memberService.findOne(saveId);
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test()
    public void 회원_중복_예외() throws Exception {
        Member member = new Member();
        member.setName("kim");
        memberService.join(member);
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member);
        });
    }

    @Test
    public void 회원조회() throws Exception {
        Member member1 = new Member();
        member1.setName("member1");
        Member member2 = new Member();
        member2.setName("member2");

        memberService.join(member1);
        memberService.join(member2);

        List<Member> findMembers = memberService.findMembers();
        assertThat(findMembers.size()).isEqualTo(2);
    }
}
package study.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.project.domain.Address;
import study.project.domain.Member;
import study.project.repository.MemberRepository;

import java.util.List;

@Service
@Transactional()
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (findMembers.size() > 0) throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    public Long updateMemberName(Long id, String name) {
        Member updateMember = this.findOne(id);
        updateMember.setName(name);
        return updateMember.getId();
    }

    public Long updateMemberAddress(Long id, Address address) {
        Member updateMember = this.findOne(id);
        updateMember.setAddress(address);
        return updateMember.getId();
    }

}

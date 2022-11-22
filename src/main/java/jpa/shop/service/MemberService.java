package jpa.shop.service;

import jpa.shop.domain.Member;
import jpa.shop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional : 이 어노테이션이 붙어있는 클래스나 멤버에 트랜잭션 적용.
// 메서드 호출시 트랜잭션 시작 - 메서드 종료시 트랜잭션 커밋.
//예외 발생시 트랜잭션 롤백.(Unchecked Exception만.
//  설정으로 변경 가능(Transcational.rollbackFor = Exception.class))
@Transactional
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    public List<Member> findMembers(){ return memberRepository.findAll(); }
    public Member findOne(Long memberId){ return memberRepository.findOne(memberId); }
}

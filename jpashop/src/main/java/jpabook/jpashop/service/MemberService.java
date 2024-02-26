package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자를 만들어서 주입시킨다
// @AllArgsConstructor 모든 필드를 다 생성자를 만들어 준다
// @NoArgsConstructor 디폴트 생성자를 생성한다.
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


    // Transactional 은 스프링 꺼를 쓴다 꼭
    //JPA는 트랜잭션 안에서 실행이 되어야한다. 데이터가 변경이 되면, 트랜잭션을 해야한다. public 메소드는 다 트랜잭션에 걸린다.
    //ReadOnlu = true 는 조회에 꼭 사용을 해라

    // 회원 가입
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return null;
    }

    // 중복 회원 검증하는 비즈니스 로직
    private void validateDuplicateMember(Member member) {
        // Exception
        List<Member> findMembers =  memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}

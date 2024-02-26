package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest  //JUnit5 에는 @RunWith(SpringRunner.class) 포함 ,스프링부트 컨테이너 안에서 테스트를 돌리기 위함이다
@Transactional //롤백이 되게 해야함, 테스트를 다 하고 롤백을 한다.
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;


    // 스프링 트랙잭션은 커밋이 아니라, 항상 롤백을 한다.
    // 그러므로 롤백이 아닌걸 보고싶다면 @Rollback(false)를 진행해야 한다. 그러면 롤백안하고 커밋을 진행한다.
    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("jin");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush(); // 영속성 컨텍스트 변경 등록 내용을 DB에 반영하는 것
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("jin1");

        Member member2 = new Member();
        member2.setName("jin2");

        //when
        memberService.join(member1);
        memberService.join(member2);
        // 같은 이름을 넣었을 때 예외가 나와야 한다.

        //then
        //Junit5 예외처리 방법.
       assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}


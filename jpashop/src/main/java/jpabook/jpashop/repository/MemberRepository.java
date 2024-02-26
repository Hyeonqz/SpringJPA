package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    // 단건 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 다중 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)  // JPQL 을 사용한다.
                .getResultList();
        // SQL은 테이블 대상으로 쿼리를 날림, JPQL은 객체를 대상으로 쿼리를 날림
    }

    //이름에 따른 조회
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}

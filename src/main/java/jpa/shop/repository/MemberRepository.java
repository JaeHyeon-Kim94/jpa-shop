package jpa.shop.repository;

import jpa.shop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/*
@Repository
componentscan에 의한 spring bean register 이외에
JPA 예외 발생시 스프링이 추상화한 예외로 변환하여 서비스 계층으로 반환하는 기능도 있다.
*/
@Repository
public class MemberRepository {
    /*
    * 스프링 컨테이너에서 제공(DI)하는 엔티티 매니저 사용.
    * 엔티티매니저팩토리를 주입받으려 한다면 @PersistenceUnit 사용.
    */
    @PersistenceContext
    EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("SELECT m FROM Member m WHERE m.name= :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}

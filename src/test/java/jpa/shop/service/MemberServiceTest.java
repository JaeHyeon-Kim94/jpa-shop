package jpa.shop.service;


import jpa.shop.domain.Member;
import jpa.shop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//jUnit으로 작성한 테스트 케이스를 스프링과 통합.
//테스트가 스프링 컨테이너에서 실행됨.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:appConfig.xml")
@Transactional
public class MemberServiceTest {

    @Autowired MemberService service;
    @Autowired MemberRepository repository;

    @Test
    public void 회원가입() throws Exception{
        //Given
        Member m = new Member();
        m.setName("재현");

        //When
        Long saveId = service.join(m);

        //Then
        assertEquals(m, repository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //Given
        Member m1 = new Member();
        m1.setName("재현");

        Member m2 = new Member();
        m2.setName("재현");

        //When
        service.join(m1);
        service.join(m2);

        //Then
        fail("예외가 발생해야 함.");
    }

}

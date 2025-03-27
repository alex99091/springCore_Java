package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository1" + memberRepository1);
        System.out.println("orderService -> memberRepository2" + memberRepository2);

        /* 두 서비스(MemberServiceImpl, OrderServiceImpl)가 동일한 MemoryMemberRepository 인스턴스를 공유
            스프링은 @Configuration 클래스 (AppConfig) 안에서 @Bean 메서드를 호출할 때,
            직접 new로 객체를 만드는 게 아니라 스프링 컨테이너가 관리하는 싱글톤 객체를 반환합니다.
            memberRepository()를 각각 두 번 호출해도,
            실제로는 같은 객체를 반환하게 만듭니다.
         */

    }
}

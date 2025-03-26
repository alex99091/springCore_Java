package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

public class StatefulServiceTest {

    @Test
    void testStatefulService() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A사용자가 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadB: B사용자가 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        // 싱글톤: 같은 객체를 공유하기 때문에 A사용자 금액에 B사용자 금액이 할당됨
        // 특정 클라이언트가 해당 값을 변경할 수 있는 필드가 존재하면 안됨. (공유필드 존재)
        // 무상태로 설계해야함.

        // 아래는 지역변수를 이용
        int userAPrice = statefulService1.fixOrder("userA", 10000);
        int userBPrice = statefulService1.fixOrder("userA", 20000);
        System.out.println("userAPrice = " + userAPrice + ", userBPrice = " + userBPrice);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}

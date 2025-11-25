package jpabook.jpashop1;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Jpashop1Application {


	public static void main(String[] args) {

        SpringApplication.run(Jpashop1Application.class, args);
    }

    @Bean
    Hibernate5JakartaModule hibernate5JakartaModule() {
        return new Hibernate5JakartaModule();
    }
    // 기본적으로 초기화 된 프록시 객체만 노출! 가짜가 아닌 진짜 객체가 된 경우에만!
    // 초기화 되지 않은 프록시 객체는 노출하지 않음!

}

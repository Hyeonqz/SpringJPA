package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // JPA 내장 타입을 사용한다는 의미
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;



    // JPA 기본 스펙이 리플레쉬, 프록시 기술을 써야한다
    // 임베디드 타입은 자바 기본 생성자를 필요로 한다
    // public, protected 로 생성 가능, private는 안됌
    // new 로 함부로 생성하면 안됌
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

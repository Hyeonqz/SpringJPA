package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;

@Embeddable // JPA 내장 타입을 사용한다는 의미
public class Address {

    private String city;
    private String street;
    private String zipcode;
}

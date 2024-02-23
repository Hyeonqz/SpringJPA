package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장 타입을 포함했다는 뜻임 -> 클래스에 있거나 변수에 있거나 둘 중 하나만 있어두 된다
    private Address address;

    @OneToMany(mappedBy = "member") //나는 연관관계의 주인이 아니에요 -> member 필드에 의해 맵핑 된 것이다 즉 readOnly
    private List<Order> orders = new ArrayList<>();
}

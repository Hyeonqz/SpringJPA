package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch =FetchType.LAZY, mappedBy = "delivery") //1:1 매핑
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //ORDINAL 안됌 장애가 생길 수 있음
    private DeliveryStatus status; // READY,COMPLETE ->
}

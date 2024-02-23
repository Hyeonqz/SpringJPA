package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Table(name="orders")
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //연관 관계의 주인은 -> FK키가 있는 곳을 기준으로 한다.
    @ManyToOne // N : 1 관계 이다 -> 주문은 여러개 할 수 있지만 회원은 하나다. Member : 1(one), Order : N (Many)
    @JoinColumn(name="member_id") // foreign key 이름을 뭘로 할 것인지 설정한다
    private Member member;

    @OneToMany(mappedBy = "order") //OrderItems 클래스에 Order가 변수로 선언되어 있는데 그것에 대해서 구현되는 것이다 라는 뜻
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    private OrderStatus status; // 주문상태 (즉 불변임)
}

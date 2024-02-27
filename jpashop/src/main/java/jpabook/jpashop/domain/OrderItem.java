package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Item.Item;
import lombok.*;

import static jakarta.persistence.FetchType.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice; //주문가격
    private int count; //주문 수량

    /* 도메인 모델 패턴
    *  1) 엔티티가 비즈니스 로직을 가지고,객체지향 특성 적극 활용
    *  2) 서비스 계층은 위임만 한다.
    * */

    // ==생성 메소드== //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }


    // 비즈니스 로직 //
    public void cancel() {
        getItem().addStock(count); // 재고수량을 원복해준다.
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}

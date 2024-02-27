package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    @Autowired EntityManager entityManager;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = new Member();
        member.setName("진현규");
        member.setAddress(new Address("서울", "압구정로","123'456"));
        entityManager.persist(member);

        Item item = new Book();
        item.setName("클린코드");
        item.setPrice(10000);
        item.setStockQuantity(10);
        entityManager.persist(item);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //thenx
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        Assertions.assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        Assertions.assertEquals(10000*orderCount,getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        Assertions.assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다." );
    }

    @Test
    void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createItem("시골JPA",10000,10);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL");
        Assertions.assertEquals(item.getStockQuantity(),10, "주문 취소시 상태는 CANCEL");


    }

    private Item createItem(String name, int price, int stockQuantity) {
        Item Item = new Book();
        Item.setName(name);
        Item.setPrice(price);
        Item.setStockQuantity(stockQuantity);
        entityManager.persist(Item);
        return Item;
    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createItem("시골",1000,10);

        int orderCount = 11;

        //when
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

        //then

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("진현규");
        member.setAddress(new Address("서울", "압구정로","123'456"));
        entityManager.persist(member);
        return member;
    }

}
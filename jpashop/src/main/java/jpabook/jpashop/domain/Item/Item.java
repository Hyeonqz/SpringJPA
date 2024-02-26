package jpabook.jpashop.domain.Item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Joined=정규화 스타일, SingleTable 한 테이블에 다 떄려박기, Table per class각 테이블이 나오는거
@DiscriminatorColumn(name = "dtype") //구분할 때 -> book이면 어떻게 할거야, 뭐면 어떡할꺼야
@Entity
public abstract class Item { //추상 클래스로 선언을 함

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 비즈니스 로직
    // 엔티티 자체에서 해결할 수 있는 것들은
    // 엔티티안에 비즈니스 로직을 넣어서 해결을 한다 ( DDD )
    /* 재고수량 증가시키는 로직*/
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /* 재고수량 감소시키는 로직*/
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    // 객체지향적 으로 보면, 데이터를 가진 쪽에서 비즈니스 로직이 있어야 응집력이 있다. -> 관리하기가 편하다.
}


package jpabook.jpashop.domain.Item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
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
}


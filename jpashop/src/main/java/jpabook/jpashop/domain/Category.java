package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany // N : N 관계
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    ) // 연관 관계가 둘다 List 이기 때문에 두개를 둘다 List로 담기 위해서는 Join을 해줘야 한다.

    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id") //부모 역할
    private Category parent;

    @OneToMany(mappedBy = "parent") //자식 역할 -> 자식은 여러개가 있을 수 있다. (보통 부모는 1, 자식은 N개를 할 수 있음을 의미
    private List<Category> child = new ArrayList<>();
}

package jpa.shop.domain;

import jpa.shop.domain.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id @GeneratedValue
    @Column( name="CATEGORY_ID")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable( name="CATEGORY_ITEM",
                joinColumns = @JoinColumn(name="CATEGORY_ID"),
                inverseJoinColumns = @JoinColumn(name="ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();
    //계층형 테이블 설계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany( mappedBy="parent")
    private List<Category> childs = new ArrayList<>();

    public void addChildCategory(Category child){
        this.childs.add(child);
        child.setParent(this);
    }

    public void addItem(Item item){ items.add(item); }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    public Category getParent() {
        return parent;
    }

    public List<Category> getChilds() {
        return childs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setChilds(List<Category> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

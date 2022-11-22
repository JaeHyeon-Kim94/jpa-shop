package jpa.shop.domain;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ORDERS")
public class Order {
    @Id @GeneratedValue
    @Column( name="ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn( name="DELIVERY_ID")
    private Delivery delivery;

    @OneToMany( mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList();
    //@Temporal 생략시 dialect에 따라
        // 오라클 : TemporalType.TIMESTAMP
        // MySQL : datetime
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem item : orderItems){
            order.addOrderItem(item);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }



    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP)
            throw new RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다.");

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem item : orderItems){
            item.cancel();
        }
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem item : orderItems){
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    private void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", status=" + status +
                '}';
    }
}

package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    @Column(name = "sub_price")
    private Double subPrice;

    @Column(name = "set_price")
    private Double setPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderDetails() 
    {
    }

    public OrderDetails(Integer id, Integer quantity, Double subPrice, Double setPrice, Orders order, Product product) 
    {
        this.id = id;
        this.quantity = quantity;
        this.subPrice = subPrice;
        this.setPrice = setPrice;
        this.order = order;
        this.product = product;
    }

    public Integer getId() 
    {
        return id;
    }

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getQuantity() 
    {
        return quantity;
    }

    public void setQuantity(Integer quantity) 
    {
        this.quantity = quantity;
    }

    public Double getSubPrice() 
    {
        return subPrice;
    }

    public void setSubPrice(Double subPrice) 
    {
        this.subPrice = subPrice;
    }

    public Double getSetPrice()
    {
        return setPrice;
    }

    public void setSetPrice(Double setPrice) 
    {
        this.setPrice = setPrice;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

package com.pulsar.model;


import java.util.Set;

public record Customer(Long id,
                       String name,
                       Integer tier,
                       Set<Order> orders) {
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}

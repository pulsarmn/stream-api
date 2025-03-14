package com.pulsar.model;


import java.math.BigDecimal;

public record Product(Long id,
                      String name,
                      String category,
                      BigDecimal price) {

    @Override
    public String toString() {
        return "Product{" +
                "category='" + category + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

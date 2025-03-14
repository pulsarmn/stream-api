package com.pulsar.model;

import java.time.LocalDate;
import java.util.Set;

public record Order(Long id,
                    Status status,
                    LocalDate orderDate,
                    LocalDate deliveryDate,
                    Set<Product> products,
                    Customer customer) {

    public enum Status {
        ASSEMBLY("В сборке"),
        ON_THE_WAY("В пути"),
        DELIVERED("Доставлен"),
        CANCELLED("Отменён");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                ", id=" + id +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}

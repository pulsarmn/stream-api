package com.pulsar;

import com.pulsar.model.Customer;
import com.pulsar.model.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String FOOD = "Продукты питания";
    private static final String VITAMINS = "Витамины/БАДы";
    private static final String SPORT_NUTRITION = "Спортивное питание";
    private static final String ELECTRONICS = "Электроника";
    private static final String CHANCELLERY = "Канцелярия";
    private static final String HOUSEHOLD_GOODS = "Товары для дома";
    private static final String BOOKS = "Книги";

    private static final String DELIMITER = "---------------";

    private static Customer getRandomCustomer() {
        List<Customer> customers = customers();
        Collections.shuffle(customers);
        return customers.getFirst();
    }

    private static List<Customer> customers() {
        Customer customer1 = new Customer(1L, "Александр", 10, new HashSet<>());
        Customer customer2 = new Customer(2L, "Иван", 5, new HashSet<>());
        Customer customer3 = new Customer(3L, "Петя", 4, new HashSet<>());
        Customer customer4 = new Customer(4L, "Максим", 7, new HashSet<>());
        Customer customer5 = new Customer(5L, "Катя", 3, new HashSet<>());

        return new ArrayList<>(List.of(customer1, customer2, customer3, customer4, customer5));
    }

    private static Set<Product> getRandomProducts() {
        List<Product> products = products();
        Collections.shuffle(products);
        return products.stream()
                .limit(6)
                .collect(Collectors.toSet());
    }

    private static List<Product> products() {
        Product product1 = new Product(1L, "Геркулес", FOOD, BigDecimal.valueOf(546));
        Product product2 = new Product(2L, "Творог", FOOD, BigDecimal.valueOf(53));
        Product product3 = new Product(3L, "Гречка", FOOD, BigDecimal.valueOf(50));
        Product product4 = new Product(4L, "Витамин Д3", VITAMINS, BigDecimal.valueOf(250));
        Product product5 = new Product(5L, "Глицин", VITAMINS, BigDecimal.valueOf(235));
        Product product6 = new Product(6L, "Креатин", SPORT_NUTRITION, BigDecimal.valueOf(658));
        Product product7 = new Product(7L, "Протеин", SPORT_NUTRITION, BigDecimal.valueOf(1200));
        Product product8 = new Product(8L, "Изолят белка", SPORT_NUTRITION, BigDecimal.valueOf(1650));
        Product product9 = new Product(9L, "Mi Band", ELECTRONICS, BigDecimal.valueOf(2400));
        Product product10 = new Product(10L, "Кастрюля", HOUSEHOLD_GOODS, BigDecimal.valueOf(990));
        Product product11 = new Product(11L, "Сковорода антипригарная", HOUSEHOLD_GOODS, BigDecimal.valueOf(854));
        Product product12 = new Product(12L, "Логика", BOOKS, BigDecimal.valueOf(650));
        Product product13 = new Product(13L, "Преступление и наказание", BOOKS, BigDecimal.valueOf(350));
        Product product14 = new Product(14L, "Чай", FOOD, BigDecimal.valueOf(89));
        Product product15 = new Product(15L, "Кофе", FOOD, BigDecimal.valueOf(238));
        Product product16 = new Product(16L, "Ручка", CHANCELLERY, BigDecimal.valueOf(45));
        Product product17 = new Product(17L, "Карандаш простой", CHANCELLERY, BigDecimal.valueOf(30));
        Product product18 = new Product(18L, "Ноутбук", ELECTRONICS, BigDecimal.valueOf(47000));
        Product product19 = new Product(19L, "Тетрадь", CHANCELLERY, BigDecimal.valueOf(90));
        Product product20 = new Product(20L, "Ножницы", CHANCELLERY, BigDecimal.valueOf(100));
        Product product21 = new Product(21L, "Цинк пиколинат", VITAMINS, BigDecimal.valueOf(405));

        return new ArrayList<>(List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                product10, product11, product12, product13, product14, product15, product16, product17, product18,
                product19, product20, product21));
    }
}

package com.pulsar;

import com.pulsar.model.Customer;
import com.pulsar.model.Order;
import com.pulsar.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
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

    public static void main(String[] args) {
        // 1
        System.out.println("1)");
        products().stream()
                .filter(product -> product.category().equals(BOOKS))
                .filter(product -> product.price().compareTo(BigDecimal.valueOf(300)) > 0)
                .forEach(System.out::println);
        System.out.println(DELIMITER);

        // 2 - Children's products заменил на категорию FOOD
        System.out.println("2)");
        orders().stream()
                .filter(order -> order.products().stream()
                        .anyMatch(product -> product.category().equals(FOOD)))
                .forEach(System.out::println);

        // 3 - Toys заменил на категорию FOOD
        System.out.println("3)");
        BigDecimal sum = products().stream()
                .filter(product -> product.category().equals(FOOD))
                .map(Product::price)
                .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(sum);
        System.out.println(DELIMITER);

        // 4
        System.out.println("4)");
        orders().stream()
                .filter(order -> order.customer().tier() == 2)
                .filter(order -> order.orderDate().isAfter(LocalDate.of(2021, 2, 1)))
                .filter(order -> order.deliveryDate().isBefore(LocalDate.of(2021, 4, 1)))
                .map(Order::products)
                .forEach(System.out::println);
        System.out.println(DELIMITER);

        // 5
        System.out.println("5)");
        products().stream()
                .filter(product -> product.category().equals(BOOKS))
                .sorted(Comparator.comparing(Product::price))
                .limit(2)
                .forEach(System.out::println);
        System.out.println(DELIMITER);

        // 6
        System.out.println("6)");
        orders().stream()
                .sorted(Comparator.comparing(Order::orderDate).reversed())
                .limit(3)
                .forEach(System.out::println);
        System.out.println(DELIMITER);

        // 7 - 15 марта заменил на сегодняшнюю дату(LocalDate.now())
        System.out.println("7)");
        orders().stream()
                .filter(order -> order.orderDate().equals(LocalDate.now()))
                .peek(order -> System.out.println(order.id()))
                .flatMap(order -> order.products().stream())
                .forEach(System.out::println);
        System.out.println(DELIMITER);

        // 8 - февраль заменил на текущий месяц(март)
        System.out.println("8)");
        BigDecimal sumByMarch = orders().stream()
                .filter(order -> order.orderDate().isAfter(LocalDate.of(2025, 3, 1)))
                .filter(order -> order.orderDate().isBefore(LocalDate.of(2025, 4, 1)))
                .flatMap(order -> order.products().stream())
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(sumByMarch);
        System.out.println(DELIMITER);

        // 9 - 14 марта 2021 заменил на LocalDate.now()
        System.out.println("9)");
        OptionalDouble average = orders().stream()
                .filter(order -> order.deliveryDate().equals(LocalDate.now()))
                .flatMap(order -> order.products().stream())
                .mapToInt(product -> product.price().intValue())
                .average();
        average.ifPresent(System.out::println);
        System.out.println(DELIMITER);

        // 10 - я так понял, что нужно занести значения в отдельные переменные
        System.out.println("10)");
        int bookSum = products().stream()
                .filter(product -> product.category().equals(BOOKS))
                .map(Product::price)
                .map(BigDecimal::intValue)
                .reduce(0, Integer::sum);
        OptionalDouble bookAverage = products().stream()
                .filter(product -> product.category().equals(BOOKS))
                .mapToInt(product -> product.price().intValue())
                .average();
        Optional<Product> bookMax = products().stream()
                .filter(product -> product.category().equals(BOOKS))
                .max(Comparator.comparing(Product::price));
        Optional<Product> bookMin = products().stream()
                .filter(product -> product.category().equals(BOOKS))
                .min(Comparator.comparing(Product::price));
        long bookCount = products().stream()
                .filter(product -> product.category().equals(BOOKS))
                .count();

        // 11
        System.out.println("11)");
        Map<Long, Integer> orderMap = orders().stream()
                .collect(Collectors.toMap(Order::id, order -> order.products().size()));
        orderMap.forEach((k, v) -> System.out.println("Key: %s | Value: %s".formatted(k, v)));
        System.out.println(DELIMITER);

        // 12
        System.out.println("12)");
        Map<Customer, List<Order>> customerMap = customers().stream()
                .collect(Collectors.toMap(Function.identity(), customer -> new ArrayList<>(customer.orders())));
        customerMap.forEach((k, v) -> System.out.println("Key: %s | Value: %s".formatted(k, v)));
        System.out.println(DELIMITER);

        // 13 - так и не понял как решить проблему циклической зависимости между классами
//        System.out.println("13)");
//        Map<Order, Double> orderDoubleMap = orders().stream()
//                .collect(Collectors.toMap(Function.identity(), order -> order.products().stream()
//                        .map(product -> product.price().doubleValue())
//                        .reduce(0.0, Double::sum))
//                );
//        orderDoubleMap.forEach((k, v) -> System.out.println("Key: %s | Value: %s".formatted(k, v)));
//        System.out.println(DELIMITER);

        // 14
        System.out.println("14)");
        Map<String, List<Product>> productMap = products().stream()
                .map(Product::category)
                .distinct()
                .collect(Collectors.toMap(Function.identity(), category -> products().stream()
                        .filter(product -> product.category().equals(category))
                        .collect(Collectors.toList()))
                );
        productMap.forEach((k, v) -> System.out.println("Key: %s | Value: %s".formatted(k, v)));
        System.out.println(DELIMITER);

        // 15
        System.out.println("15)");
        Map<String, Product> stringProductMap = products().stream()
                .map(Product::category)
                .distinct()
                .collect(Collectors.toMap(Function.identity(), category -> products().stream()
                        .limit(1)
                        .max(Comparator.comparing(Product::price))
                        .orElseThrow())
                );
        stringProductMap.forEach((k, v) -> System.out.println("Key: %s | Value: %s".formatted(k, v)));

    }

    private static List<Order> orders() {
        Order order1 = new Order(1L, Order.Status.DELIVERED,
                LocalDate.of(2025, 10, 11),
                LocalDate.of(2025, 10, 15), getRandomProducts(), getRandomCustomer());
        order1.customer().orders().add(order1);

        Order order2 = new Order(2L, Order.Status.ASSEMBLY,
                LocalDate.now(), LocalDate.of(2025, 3, 20), getRandomProducts(), getRandomCustomer());
        order2.customer().orders().add(order2);

        Order order3 = new Order(3L, Order.Status.CANCELLED,
                LocalDate.now(), LocalDate.now(),
                getRandomProducts(), getRandomCustomer());
        order3.customer().orders().add(order3);

        Order order4 = new Order(4L, Order.Status.DELIVERED,
                LocalDate.of(2025, 3, 10), LocalDate.now(),
                getRandomProducts(), getRandomCustomer());
        order4.customer().orders().add(order4);

        Order order5 = new Order(5L, Order.Status.ON_THE_WAY,
                LocalDate.of(2025, 3, 11), LocalDate.now(),
                getRandomProducts(), getRandomCustomer());
        order5.customer().orders().add(order5);

        return new ArrayList<>(List.of(order1, order2, order3, order4, order5));
    }

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

package org.leon.mall.repo;

import org.leon.mall.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class OrderRepo {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();


    public Order findById(Long id) {
        return orders.get(id);
    }

    public void save(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public List<Order> findAll(Integer userId) {
        return orders.values().stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}

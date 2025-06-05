package org.leon.mall.ctrl;

import com.alibaba.cloud.nacos.annotation.NacosConfig;
import org.leon.mall.entity.Order;
import org.leon.mall.repo.OrderRepo;
import org.leon.mall.service.UserService;
import org.leon.mall.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderCtrl {

    Logger logger = LoggerFactory.getLogger(OrderCtrl.class);

    @NacosConfig(dataId = "mall-order",group = "online-mall",key = "mall.order.thread.pool.size")
    private String poolSize;

    private final OrderRepo orderRepo;
    private final DiscoveryClient discoveryClient;
//    private final RestTemplate restTemplate;  // 使用了 OpenFeign 就不需要直接调用 @LoadBalance RestTemplate 了

    private final UserService userService;

    public OrderCtrl(OrderRepo orderRepo, DiscoveryClient discoveryClient, UserService userService) {
        this.orderRepo = orderRepo;
        this.discoveryClient = discoveryClient;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long orderId) {
        logger.info("config from nacos: {}", poolSize);
        return ResponseEntity.ok(orderRepo.findById(orderId));
    }

    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody Order order) {

        Integer userId = order.getUserId();
        if (userId == null) {
            return ResponseEntity.badRequest().body("userId is empty");
        }

        /*
        String url = "http://" + USER_SERVICE_NAME + "/user/" + userId;
        ResponseEntity<User> userEntity = restTemplate.getForEntity(url, User.class);
        */

        User user = userService.getByUserId(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("user is not existed");
        }
        logger.info("user info {}", user);

        order.setOrderId(new Date().getTime());
        order.setStatus(0);
        order.setCreateTime(new Date());
        orderRepo.save(order);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getAll(@PathVariable("userId") Integer userId) {
        List<User> allUsers = userService.getAllUsers();
        logger.info("all user info {}", allUsers);
        return ResponseEntity.ok().body( orderRepo.findAll(userId));
    }

    @GetMapping("user-services")
    public ResponseEntity<?> getUserServices() {
        return ResponseEntity.ok().body(discoveryClient.getInstances("user-service"));
    }
}

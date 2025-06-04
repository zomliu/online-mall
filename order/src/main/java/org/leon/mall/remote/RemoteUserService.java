package org.leon.mall.remote;

import org.leon.mall.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", fallback = RemoteUserServiceFallback.class)
public interface RemoteUserService {

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") Integer userId);

    @GetMapping("/user/users")
    List<User> getAllUsers();
}

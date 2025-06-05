package org.leon.mall.remote;

import org.leon.mall.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 使用 OpenFeign 中的注解实现调用过程.
 * 启动过程中, 会把 @FeignClient 修饰的接口生成一个代理类.
 * 如果希望调用服务出错时, 可以实现这个接口并通过配置 fallback 实现降级逻辑
 *
 */
@FeignClient(name = "user-service", fallback = RemoteUserServiceFallback.class)
public interface RemoteUserService {

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") Integer userId);

    @GetMapping("/user/users")
    List<User> getAllUsers();
}

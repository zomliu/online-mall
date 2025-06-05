package org.leon.mall.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.leon.mall.remote.RemoteUserService;
import org.leon.mall.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final RemoteUserService remoteUserService;

    public UserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    /** 使用 @SentinelResource 注解使一个方法称为 Sentinel 的流控节点
     *  blockHandler 主要针对以下场景
     *      - Sentinel 规则触发的阻塞异常（如流量控制、熔断降级等）
     *      - 参数必须接受 BlockException 类型的参数
     * <p>
     *  fallback 主要针对以下场景
     *      - 业务逻辑中的非阻塞异常（如业务异常、运行时异常等）
     *      - 接受 Throwable 类型的参数，代表任意类型的异常
     * </p/
     * <p>
     *  注意：如果同时满足条件，优先执行 blockHandler，在没有 BlockException 发生时才会考虑 fallback
     * </p>
     */

    @SentinelResource(value = "userServer.getByUserId", blockHandler = "handleBlockForGetByUserId", fallback = "getByUserIdFallback")
    public User getByUserId(Integer userId) {
        return remoteUserService.getUserById(userId);
    }

    @SentinelResource(value = "userServer.getAllUsers", blockHandler = "handleBlockForGetAllUsers", fallback = "getAllUsersByIdsFallback")
    public List<User> getAllUsers() {
        return remoteUserService.getAllUsers();
    }


    public String handleBlockForGetByUserId(BlockException ex) {
        return "服务A暂时不可用，请稍后再试";
    }

    public String handleBlockForGetAllUsers(BlockException ex) {
        return "服务B暂时不可用，请稍后再试";
    }

    public User getByUserIdFallback(Throwable t) {
        logger.warn("getByUserIdFallback");
        User user = new User();
        user.setId(-1);
        return user;
    }

    public List<User> getAllUsersByIdsFallback(Throwable t) {
        logger.warn("getAllUsersByIdsFallback");
        return null;
    }

}

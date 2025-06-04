package org.leon.mall.remote;

import org.leon.mall.user.entity.User;

import java.util.List;

public class RemoteUserServiceFallback implements RemoteUserService {
    @Override
    public User getUserById(Integer userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}

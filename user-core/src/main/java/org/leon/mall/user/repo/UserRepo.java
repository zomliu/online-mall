package org.leon.mall.user.repo;

import org.leon.mall.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepo {

    private final Map<Integer, User> userMap = new HashMap<>();

    public UserRepo() {
        userMap.put(1, new User(1, "Alex", "Alex-password"));
        userMap.put(2, new User(2, "Tom", "Tom-password"));
        userMap.put(3, new User(3, "Ethen", "Ethen-password"));
        userMap.put(4, new User(4, "Elon", "Elon-password"));
        userMap.put(5, new User(5, "Jimmy", "Jimmy-password"));
    }

    public List<User> findAll() {
        return userMap.values().stream().toList();
    }

    public User findById(Integer id) {
        return userMap.get(id);
    }
}

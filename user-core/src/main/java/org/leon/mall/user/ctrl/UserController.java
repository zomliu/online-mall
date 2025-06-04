package org.leon.mall.user.ctrl;

import org.leon.mall.user.entity.User;
import org.leon.mall.user.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserName(@PathVariable(name = "id") Integer id) {
        User user = userRepo.findById(id);
        logger.info("user :{}", user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(userRepo.findAll());
    }
}

package kr.salm.controller;

import kr.salm.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam("value") String username) {
        boolean exists = userRepository.existsByUsername(username);
        Map<String, Boolean> result = new HashMap<>();
        result.put("duplicate", exists);
        return result;
    }

    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam("value") String email) {
        boolean exists = userRepository.existsByEmail(email);
        Map<String, Boolean> result = new HashMap<>();
        result.put("duplicate", exists);
        return result;
    }
}

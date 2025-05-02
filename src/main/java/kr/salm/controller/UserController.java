package kr.salm.controller;

import kr.salm.entity.User;
import kr.salm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/mypage")
    public String mypageForm(Model model) {
        // 현재 로그인한 사용자 정보 불러오기 등 추가 가능
        return "mypage";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, Model model) {
        // 사용자 정보 수정 로직
        userRepository.save(user);
        model.addAttribute("updateSuccess", true);
        return "mypage";
    }

    // ❌ 중복 원인 제거: /login 매핑 메서드는 AuthController에만 남김
}

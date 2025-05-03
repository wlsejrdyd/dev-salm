package kr.salm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WriteRedirectController {

    @GetMapping("/write")
    public String redirectWrite() {
        return "redirect:/post/write";
    }
}

package kr.salm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index"; // src/main/resources/templates/index.html 렌더링
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // src/main/resources/templates/dashboard.html 렌더링
    }

    @GetMapping("/product")
    public String product() {
        return "product"; // src/main/resources/templates/product.html 렌더링
    }
}


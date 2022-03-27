package cn.zeroclian.sharehub.controller;

import cn.zeroclian.sharehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: ZeroClian
 * @date: 2022-03-26 5:50 下午
 */
@Controller
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public String test(HttpServletRequest request){
        request.setAttribute("user",userRepository.getById(1L));
        return "logout";
    }
}

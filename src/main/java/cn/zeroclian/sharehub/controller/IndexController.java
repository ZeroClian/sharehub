package cn.zeroclian.sharehub.controller;

import cn.zeroclian.sharehub.base.dto.DatelineDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ZeroClian
 * @date 2022-03-27 2:47 上午
 */
@Controller
public class IndexController extends BaseController{

    @Value("${server.domain}")
    private String domain;

    @RequestMapping(value = {"", "/"})
    public String index(){
        List<DatelineDto> datelineDtos = collectService.getDatelineByUserId(getCurrentUserId());
        req.setAttribute("datelines", datelineDtos);
        req.setAttribute("userId", getCurrentUserId());

        return "index";
    }
}

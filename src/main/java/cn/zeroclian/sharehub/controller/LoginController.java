package cn.zeroclian.sharehub.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.zeroclian.sharehub.base.dto.UserDto;
import cn.zeroclian.sharehub.base.lang.Consts;
import cn.zeroclian.sharehub.base.lang.Result;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author ZeroClian
 * @date 2022-03-26 6:32 下午
 */
@Slf4j
@Controller
public class LoginController extends BaseController {

    @Autowired
    WxMpService wxMpService;

    @Autowired
    WxMpMessageRouter wxMpMessageRouter;


    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        String code = "ZC" + RandomUtil.randomNumbers(4);
        while (redisUtil.hasKey("ticket-" + code)) {
            code = "ZC" + RandomUtil.randomNumbers(4);
        }
        String ticket = RandomUtil.randomString(32);
        // 5 min
        redisUtil.set("ticket-" + code, ticket, 5 * 60);

        request.setAttribute("code", code);
        request.setAttribute("ticket", ticket);
        log.info(code + ":" + ticket);
        return "login";
    }

    @ResponseBody
    @GetMapping("/login-check")
    public Result loginCheck(String code, String ticket) {
        if (!redisUtil.hasKey("Info-" + code)) {
            return Result.failure("未登录");
        }
        String ticketBak = redisUtil.get("ticket-"+code).toString();
        if (!ticketBak.equals(ticket)) {
            return Result.failure("登陆失败");
        }
        String userJson = String.valueOf(redisUtil.get("Info-" + code));
        UserDto user = JSONUtil.toBean(userJson, UserDto.class);
        req.getSession().setAttribute(Consts.CURRENT_USER, user);
        return Result.success();
    }

    @ResponseBody
    @RequestMapping("/wx/back")
    public String wxCallBack(String signature, String timestamp, String nonce, String echostr) throws IOException {

        if (StrUtil.isNotBlank(echostr)) {
            log.info("正在配置回调接口:{}", echostr);
            return echostr;
        }

        boolean checkSignature = wxMpService.checkSignature(timestamp, nonce, signature);
        if (!checkSignature) {
            log.error("签名不合法");
            return null;
        }

        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(req.getInputStream());
        WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(wxMpXmlMessage);

        return outMessage == null ? "" : outMessage.toXml();
    }

    @GetMapping("/autologin")
    public String autologin(String token) {

        Object userObj = redisUtil.get("autologin-" + token);
        if (userObj != null) {
            UserDto userDto = JSONUtil.toBean(userObj.toString(), UserDto.class);
            req.getSession().setAttribute(Consts.CURRENT_USER, userDto);
            return "redirect:/";
        }

        return "redirect:/login";
    }
}

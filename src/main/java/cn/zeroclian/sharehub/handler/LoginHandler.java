package cn.zeroclian.sharehub.handler;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import cn.zeroclian.sharehub.base.dto.UserDto;
import cn.zeroclian.sharehub.mapstruct.UserMapper;
import cn.zeroclian.sharehub.service.UserService;
import cn.zeroclian.sharehub.util.RedisUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ZeroClian
 * @date 2022-03-26 8:27 下午
 */
@Component
public class LoginHandler {

    @Value("${server.domain}")
    private String serverDomain;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    public String handle(String openId, String content, WxMpService wxMpService) {
        String result;
        //校验content的合法性
        if (content.length() != 6 || !redisUtil.hasKey("ticket-" + content)) {
            return "登陆验证码过期或不正确";
        }

        UserDto userDto = userService.register(openId);

        redisUtil.set("Info-" + content, JSONUtil.toJsonStr(userDto), 5 * 60);

        String token = UUID.randomUUID().toString(true);
        String url = serverDomain + "/autologin?token=" + token;

        redisUtil.set("autologin-" + token, JSONUtil.toJsonStr(userDto), 48 * 60 * 60);

        return "欢迎你!\n\n" + "<a href='" + url + "'>点击这里完成登陆</a>";
    }

}

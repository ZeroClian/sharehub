package cn.zeroclian.sharehub.controller;

import cn.zeroclian.sharehub.base.dto.UserDto;
import cn.zeroclian.sharehub.base.lang.Consts;
import cn.zeroclian.sharehub.mapstruct.CollectMapper;
import cn.zeroclian.sharehub.service.CollectService;
import cn.zeroclian.sharehub.service.UserService;
import cn.zeroclian.sharehub.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ZeroClian
 * @date 2022-03-26 6:48 下午
 */
public class BaseController {

    @Resource
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @Autowired
    CollectService collectService;

    @Autowired
    CollectMapper collectMapper;

    UserDto getCurrentUser() {
        UserDto userDto = (UserDto) req.getSession().getAttribute(Consts.CURRENT_USER);
        if (userDto == null) {
            userDto = new UserDto();
            userDto.setId(-1L);
        }
        return userDto;
    }

    long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    Pageable getPage() {
        int page = ServletRequestUtils.getIntParameter(req, "page", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 10);

        return PageRequest.of(page - 1, size,
                Sort.by(
                        Sort.Order.desc("collected"),
                        Sort.Order.desc("created")
                ));
    }
}

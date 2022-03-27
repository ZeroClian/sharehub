package cn.zeroclian.sharehub.service;

import cn.zeroclian.sharehub.base.dto.UserDto;

/**
 * @author ZeroClian
 * @date 2022-03-26 8:30 下午
 */
public interface UserService {

    UserDto register(String openid);
}

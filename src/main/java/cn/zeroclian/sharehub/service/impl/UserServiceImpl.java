package cn.zeroclian.sharehub.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.zeroclian.sharehub.base.dto.UserDto;
import cn.zeroclian.sharehub.entity.User;
import cn.zeroclian.sharehub.mapstruct.UserMapper;
import cn.zeroclian.sharehub.repository.UserRepository;
import cn.zeroclian.sharehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * @author ZeroClian
 * @date 2022-03-26 8:30 下午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional
    public UserDto register(String openid) {
        Assert.isTrue(openid != null, "不合法注册条件");

        User user = userRepository.findByOpenId(openid);

        if (user == null) {
            user = new User();
            user.setUserName("Hub-" + RandomUtil.randomString(5));
            user.setAvatar("http://localhost:8080/images/logo.jpeg");

            user.setCreated(LocalDateTime.now());
            user.setOpenId(openid);

        } else {
            user.setLasted(LocalDateTime.now());
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }
}

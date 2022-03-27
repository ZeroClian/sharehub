package cn.zeroclian.sharehub.repository;

import cn.zeroclian.sharehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: ZeroClian
 * @date: 2022-03-26 5:47 下午
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByOpenId(String openId);
}

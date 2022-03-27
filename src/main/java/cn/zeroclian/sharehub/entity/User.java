package cn.zeroclian.sharehub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : ZeroClian
 * @date : 2022-03-26 5:39 下午
 */
@Data
@Entity
@Table(name = "s_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 微信用户身份ID
     */
    @JsonIgnore
    private String openId;

    /**
     * 上次登陆
     */
    private LocalDateTime lasted;
    private LocalDateTime created;
}

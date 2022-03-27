package cn.zeroclian.sharehub.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDto implements Serializable {

	private Long id;

	private String userName;
	private String avatar;

	private LocalDateTime lasted;
	private LocalDateTime created;
}

package cn.zeroclian.sharehub.mapstruct;

import cn.zeroclian.sharehub.base.dto.UserDto;
import cn.zeroclian.sharehub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author lian
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserDto toDto(User user);

}

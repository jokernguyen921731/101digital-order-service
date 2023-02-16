package com.digital.assessment.order.service.mapper;

import com.digital.assessment.order.domain.User;
import com.digital.assessment.order.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends ModelMapper<UserEntity, User>{
}

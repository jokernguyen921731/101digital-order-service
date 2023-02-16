package com.digital.assessment.order.service.mapper;

import com.digital.assessment.order.domain.Menu;
import com.digital.assessment.order.entity.MenuEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper extends ModelMapper<MenuEntity, Menu>{
}

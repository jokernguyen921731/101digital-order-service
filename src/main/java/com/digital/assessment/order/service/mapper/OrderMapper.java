package com.digital.assessment.order.service.mapper;

import com.digital.assessment.order.domain.Order;
import com.digital.assessment.order.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends ModelMapper<OrderEntity, Order>{
}

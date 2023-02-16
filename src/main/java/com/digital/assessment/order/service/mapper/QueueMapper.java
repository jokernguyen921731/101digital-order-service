package com.digital.assessment.order.service.mapper;

import com.digital.assessment.order.domain.Queue;
import com.digital.assessment.order.entity.QueueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueueMapper extends ModelMapper<QueueEntity, Queue>{
}

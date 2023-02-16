package com.digital.assessment.order.service.mapper;

import com.digital.assessment.order.domain.Branch;
import com.digital.assessment.order.entity.BranchEntity;
import com.digital.assessment.order.util.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface BranchMapper extends ModelMapper<BranchEntity, Branch>{
    @Override
    @Mapping(target = "openTimeInLong",expression = "java(this.convertDateToLong(source.getOpenTime()))")
    @Mapping(target = "closeTimeInLong",expression = "java(this.convertDateToLong(source.getCloseTime()))")
    Branch toTarget(BranchEntity source);

    default Long convertDateToLong(LocalDateTime time) {
        return DateUtils.convertLocalDateTimeToLong(time);
    }
}

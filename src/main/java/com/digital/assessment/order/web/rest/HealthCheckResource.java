package com.digital.assessment.order.web.rest;

import com.digital.assessment.order.web.rest.response.ServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Health Check Resource")
@RequestMapping("/health-check")
@Validated
public interface HealthCheckResource {
    @ApiOperation(value = "Health check api")
    @GetMapping()
    ServiceResponse search();
}

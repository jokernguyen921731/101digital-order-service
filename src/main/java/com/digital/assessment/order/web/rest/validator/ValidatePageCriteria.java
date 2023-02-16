package com.digital.assessment.order.web.rest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PageCriteriaValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ValidatePageCriteria {
    String message() default "pageCriteria is not valid";

    String[] allowSorts() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

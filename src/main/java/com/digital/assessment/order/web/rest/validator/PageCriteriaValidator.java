package com.digital.assessment.order.web.rest.validator;

import com.vsm.vin.common.model.PageCriteria;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.stream.Collectors;

public class PageCriteriaValidator
        implements ConstraintValidator<ValidatePageCriteria, PageCriteria> {

    private List<String> allowSorts;

    @Override
    public void initialize(ValidatePageCriteria constraintAnnotation) {
        initAllowSorts(constraintAnnotation);
    }

    @Override
    public boolean isValid(PageCriteria criteria, ConstraintValidatorContext context) {
        List<String> sort = criteria.getSort();
        if (allowSorts.isEmpty() || CollectionUtils.isEmpty(sort)) {
            return true;
        }
        Set<String> notAllowedSorts = new HashSet<>(sort);
        notAllowedSorts.removeAll(allowSorts);
        if (!notAllowedSorts.isEmpty()) {
            String notAllowedSortsMsg = String.join(", ", notAllowedSorts);
            ValidatorUtils.createErrorField(
                    context, "sort", "Not allow sorts: " + notAllowedSortsMsg, true);
            return false;
        }
        return true;
    }

    private void initAllowSorts(ValidatePageCriteria constraintAnnotation) {
        List<String> baseAllowedSorts = Arrays.stream(constraintAnnotation.allowSorts())
                .map(String::trim)
                .collect(Collectors.toList());
        List<String> ascAllowedSorts = baseAllowedSorts.stream()
                .map(sort -> PageCriteria.ASC_SYMBOL + sort)
                .collect(Collectors.toList());
        List<String> descAllowedSorts = baseAllowedSorts.stream()
                .map(sort -> PageCriteria.DESC_SYMBOL + sort)
                .collect(Collectors.toList());

        allowSorts = new ArrayList<>();
        allowSorts.addAll(baseAllowedSorts);
        allowSorts.addAll(ascAllowedSorts);
        allowSorts.addAll(descAllowedSorts);
    }
}

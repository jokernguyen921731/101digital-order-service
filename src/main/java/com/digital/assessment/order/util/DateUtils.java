package com.digital.assessment.order.util;

import lombok.extern.slf4j.Slf4j;
import java.time.*;
import java.util.*;

@Slf4j
public class DateUtils {
    public static Long convertLocalDateTimeToLong(LocalDateTime time) {
        Long result = null;
        if (Objects.nonNull(time)) {
            ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());
            result =zdt.toInstant().toEpochMilli();
        }
        return result;
    }
}

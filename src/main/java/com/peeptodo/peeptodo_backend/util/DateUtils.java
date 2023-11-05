package com.peeptodo.peeptodo_backend.util;

import com.peeptodo.peeptodo_backend.config.DateConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private final static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 나중에 해외판을 대비해서 지역 설정
     * @return 지역에 맞는 현재 시간
     */
    public static LocalDateTime getNowDateTime() {
        return LocalDateTime.now(DateConfig.CURRENT_ZONE);
    }

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return localDateTime.format(formatter);
    }
}

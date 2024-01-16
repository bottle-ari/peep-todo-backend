package com.peeptodo.peeptodo_backend.util;

import com.peeptodo.peeptodo_backend.config.DateConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public final static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"; // ex) 2021-10-31T23:59:59

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

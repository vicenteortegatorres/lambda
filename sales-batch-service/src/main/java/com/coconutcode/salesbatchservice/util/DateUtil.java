package com.coconutcode.salesbatchservice.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String getDay(long timestamp) {
        return new SimpleDateFormat(DATE_FORMAT).format(Date.from(Instant.ofEpochMilli(timestamp)));
    }
}

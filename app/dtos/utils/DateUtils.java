package dtos.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.*;

/**
 * 日付クラスの変換ユーティリティ
 *
 * @auhtor arapiku
 */
public class DateUtils {

    /**
     * 文字列からLocalDate型に変換
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static LocalDate toLocalDate(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(format)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        dtf = dtf.withResolverStyle(ResolverStyle.STRICT).withZone(ZoneId.systemDefault());
        return LocalDate.parse(dateStr, dtf);
    }

    /**
     * 文字列からLocalTime型に変換
     *
     * @param timeStr
     * @param format
     * @return
     */
    public static LocalTime toLocalTime(String timeStr, String format) {
        if (StringUtils.isEmpty(timeStr) || StringUtils.isEmpty(format)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        dtf = dtf.withResolverStyle(ResolverStyle.STRICT).withZone(ZoneId.systemDefault());
        return LocalTime.parse(timeStr, dtf);
    }

    /**
     * 文字列からLocalDateTime型に変換
     *
     * @param dateTimeStr
     * @param format
     * @return
     */
    public static LocalDateTime toLocalDateTime(String dateTimeStr, String format) {
        if (StringUtils.isEmpty(dateTimeStr) || StringUtils.isEmpty(format)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        dtf = dtf.withResolverStyle(ResolverStyle.STRICT).withZone(ZoneId.systemDefault());
        return LocalDateTime.parse(dateTimeStr, dtf);
    }

    /**
     * 文字列からZonedDateTime型に変換
     *
     * @param dateTimeStr
     * @param format
     * @return
     */
    public static ZonedDateTime toZonedDateTime(String dateTimeStr, String format) {
        if (StringUtils.isEmpty(dateTimeStr) || StringUtils.isEmpty(format)) {
            return null;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        dtf = dtf.withResolverStyle(ResolverStyle.STRICT).withZone(ZoneId.systemDefault());
        return ZonedDateTime.parse(dateTimeStr, dtf);
    }
}

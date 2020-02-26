package utils;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 时间处理工具类
 */
public class DateTimeUtil {
    /**
     * 根据时间获取时间戳
     *
     * @param dateTime 日期时间
     * @return 时间戳
     */
    public static Long dateTimeToTimestamp(@NotNull LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.ofHours(8)).getEpochSecond();
    }

    /**
     * 根据时间戳获取日期时间
     *
     * @param timestamp 时间戳
     * @return 日期时间
     */
    public static LocalDateTime timestampToDateTime(@NotNull Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
    }

    /**
     * 过去指定格式的日期字符串
     *
     * @param dateTime 日期
     * @param format   为null或者空时默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateTimeToString(@NotNull LocalDateTime dateTime, String format) {
        // 时间戳字符串格式化
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.SIMPLIFIED_CHINESE);
        return dateTime.format(formatter);
    }

    /**
     * 根据日期时间字符串获取日期时间
     *
     * @param dateTime 日期时间字符串
     * @param format   为null或者空时默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LocalDateTime stringToDateTime(@NotNull String dateTime, String format) {
        // 时间戳字符串格式化
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTime, formatter);
    }

    /**
     * 根据日期字符串获取两个日期相差秒数,毫秒数
     * @param startDateTime 开始日期
     * @param endDateTime 结束日期
     * @param format 为null或者空时默认为yyyy-MM-dd HH:mm:ss
     */
    public static Duration durationBetweenByDateString(@NotNull String startDateTime,@NotNull String endDateTime, String format) {
        return Duration.between(stringToDateTime(startDateTime, format), stringToDateTime(endDateTime, format));
    }

    /**
     * 根据日期字符串获取两个日期相差秒数,毫秒数
     * @param startDateTime 开始日期
     * @param endDateTime 结束日期
     * @param format 为null或者空时默认为yyyy-MM-dd HH:mm:ss
     */
    public static Period periodBetweenByDateString(@NotNull String startDateTime,@NotNull String endDateTime, String format) {
        return Period.between(stringToDateTime(startDateTime, format).toLocalDate(), stringToDateTime(endDateTime, format).toLocalDate());
    }

    public static void main(String[] args) {
//        System.out.println(dateTimeToString(LocalDateTime.now().plusDays(2), null));
//        System.out.println(dateTimeToString(LocalDateTime.now().minusDays(1), null));
//        System.out.println(LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE));
        LocalDateTime localDateTime = stringToDateTime("2017-03-25 03:22:10", null);
//        System.out.println(dateTimeToString(localDateTime,  null));
        // 计算两个日期之间相差多少年，多少月，多少日
//        Period period = Period.between(stringToDateTime("2017-03-25 03:22:10", null).toLocalDate(), LocalDateTime.now().toLocalDate());
//        System.out.println(period.getYears() + "年" + period.getMonths() + "月" + period.getDays() + "天");
//         计算两个日期之间相差多少秒，多少毫秒
//        Duration duration = Duration.between(stringToDateTime("2017-03-25 19:00:00", null), LocalDateTime.now());
//        System.out.println(duration.getSeconds() / (60 * 60 * 24));
    }
}
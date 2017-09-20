package id.aseprojali.friendmanagement.domain.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by avew on 9/21/17.
 */
public final class JSR310DateConverters {
    private JSR310DateConverters() {
    }

    public static class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {
        public static final JSR310DateConverters.DateToLocalDateTimeConverter INSTANCE = new JSR310DateConverters.DateToLocalDateTimeConverter();

        private DateToLocalDateTimeConverter() {
        }

        public LocalDateTime convert(Date source) {
            return source == null?null:LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
        }
    }

    public static class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {
        public static final JSR310DateConverters.LocalDateTimeToDateConverter INSTANCE = new JSR310DateConverters.LocalDateTimeToDateConverter();

        private LocalDateTimeToDateConverter() {
        }

        public Date convert(LocalDateTime source) {
            return source == null?null:Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    public static class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
        public static final JSR310DateConverters.DateToZonedDateTimeConverter INSTANCE = new JSR310DateConverters.DateToZonedDateTimeConverter();

        private DateToZonedDateTimeConverter() {
        }

        public ZonedDateTime convert(Date source) {
            return source == null?null:ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
        }
    }

    public static class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {
        public static final JSR310DateConverters.ZonedDateTimeToDateConverter INSTANCE = new JSR310DateConverters.ZonedDateTimeToDateConverter();

        private ZonedDateTimeToDateConverter() {
        }

        public Date convert(ZonedDateTime source) {
            return source == null?null:Date.from(source.toInstant());
        }
    }

    public static class DateToLocalDateConverter implements Converter<Date, LocalDate> {
        public static final JSR310DateConverters.DateToLocalDateConverter INSTANCE = new JSR310DateConverters.DateToLocalDateConverter();

        private DateToLocalDateConverter() {
        }

        public LocalDate convert(Date source) {
            return source == null?null:ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault()).toLocalDate();
        }
    }

    public static class LocalDateToDateConverter implements Converter<LocalDate, Date> {
        public static final JSR310DateConverters.LocalDateToDateConverter INSTANCE = new JSR310DateConverters.LocalDateToDateConverter();

        private LocalDateToDateConverter() {
        }

        public Date convert(LocalDate source) {
            return source == null?null:Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }
}

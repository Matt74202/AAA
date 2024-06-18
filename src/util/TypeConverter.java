package util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class TypeConverter {
    private static final Map<Class<?>, Function<String, Object>> converters = new HashMap<>();

    static {
        converters.put(int.class, Integer::parseInt);
        converters.put(Integer.class, Integer::parseInt);
        converters.put(long.class, Long::parseLong);
        converters.put(Long.class, Long::parseLong);
        converters.put(double.class, Double::parseDouble);
        converters.put(Double.class, Double::parseDouble);
        converters.put(float.class, Float::parseFloat);
        converters.put(Float.class, Float::parseFloat);
        converters.put(boolean.class, TypeConverter::parseBoolean);
        converters.put(Boolean.class, TypeConverter::parseBoolean);
        converters.put(String.class, s -> s);
        converters.put(short.class, Short::parseShort);
        converters.put(Short.class, Short::parseShort);
        converters.put(byte.class, Byte::parseByte);
        converters.put(Byte.class, Byte::parseByte);
        converters.put(char.class, s -> s.charAt(0));
        converters.put(Character.class, s -> s.charAt(0));
        converters.put(LocalDate.class, s -> LocalDate.parse(s, DateTimeFormatter.ISO_DATE));
        converters.put(LocalDateTime.class, s -> LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME));
        converters.put(Date.class, TypeConverter::convertToDate);
        converters.put(Timestamp.class, Timestamp::valueOf);
    }

    private static boolean parseBoolean(String value) {
        if ("on".equalsIgnoreCase(value) || "t".equalsIgnoreCase(value)) {
            return true;
        } else if ("off".equalsIgnoreCase(value) || "f".equalsIgnoreCase(value)) {
            return false;
        } else {
            return Boolean.parseBoolean(value);
        }
    }

    private static Date convertToDate(String s) {
        Date parsedDate = null;
        String[] formats = {
                "yyyy-MM-dd HH:mm:ss",
                "dd/MM/yyyy",
                "yyyy/MM/dd",
                "MM/dd/yyyy",
                "yyyy-MM-dd",
                "dd-MM-yyyy",
                "MM-dd-yyyy",
                "yyyyMMdd",
                "MM/dd/yyyy HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                "EEE MMM dd HH:mm:ss zzz yyyy"
        };

        for (String format : formats) {
            try {
                DateFormat dateFormat = new SimpleDateFormat(format);
                dateFormat.setLenient(false);
                parsedDate = dateFormat.parse(s);
                break;
            } catch (ParseException e) { continue; }
        }

        if (parsedDate == null) {
            throw new IllegalArgumentException("Format de date non pris en charge: " + s);
        }

        return parsedDate;
    }

    public static Object convert(String value, Class<?> targetType) {
        Function<String, Object> converter = converters.get(targetType);
        if (converter != null) {
            return converter.apply(value);
        } else {
            throw new IllegalArgumentException("No converter found for type: " + targetType);
        }
    }
}

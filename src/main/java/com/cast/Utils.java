package com.cast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

final class Utils {

    private Utils() {
    }

    private static final String DELIMITER = ",";
    private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);
    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

    /**
     * 判断指定的对象是否是数组
     *
     * @param obj 要检查的对象
     * @return 如果是数组类型返回 {@code true}，否则返回 {@code false}
     */
    static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    /**
     * 判断指定的对象是否是枚举类型
     *
     * @param obj 要检查的对象
     * @return 如果是返回 {@code true}，否则返回 {@code false}
     */
    static boolean isEnum(Object obj) {
        return obj != null && obj.getClass().isEnum();
    }

    /**
     * 判断指定的对象是否为空
     * <p>该方法遵循以下规则</p>
     * <ul>
     *     <li>如果指定对象为 {@code null}，返回 {@code true}</li>
     *     <li>{@code CharSequence}: 如果长度为 0，返回 {@code true}</li>
     *     <li>{@code Array}: 如果长度为 0，返回 {@code true}</li>
     *     <li>{@code Collection}: 调用 {@link Collection#isEmpty()} 判断是否为空</li>
     *     <li>{@code Map}: 调用 {@link Collection#isEmpty()} 判断是否为空</li>
     *     <li>{@code Optional}: 调用 {@link Optional#isPresent()} 判断是否为空</li>
     *     <li>其他情况直接返回 {@code false}</li>
     * </ul>
     *
     * @param obj 要检查的对象
     * @return 如果指定对象为 {@code null} 或空返回 {@code true}
     */
    static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof Optional) {
            return ((Optional) obj).isPresent();
        }
        return false;
    }

    /**
     * 将 {@code Number} 转换为指定的数字类型
     *
     * @param value       要转换的原始 {@code Number} 值
     * @param targetClass 目标数字类型
     * @param <T>         目标类型泛型，继承自 {@code Number}
     * @return 转换后的目标类型的值
     */
    @SuppressWarnings("unchecked")
    static <T extends Number> T numberToTarget(Number value, Class<T> targetClass) {
        if (targetClass.isInstance(value)) {
            return (T) value;
        }
        if (targetClass == Byte.class) {
            return (T) Byte.valueOf(byteValue(value));
        }
        if (targetClass == Short.class) {
            return (T) Short.valueOf(shortValue(value));
        }
        if (targetClass == Integer.class) {
            return (T) Integer.valueOf(intValue(value));
        }
        if (targetClass == Long.class) {
            return (T) Long.valueOf(longValue(value));
        }
        if (targetClass == Float.class) {
            return (T) Float.valueOf(value.floatValue());
        }
        if (targetClass == Double.class) {
            return (T) Double.valueOf(value.doubleValue());
        }
        if (targetClass == BigInteger.class) {
            if (value instanceof BigDecimal) {
                return (T) ((BigDecimal) value).toBigInteger();
            }
            return (T) BigInteger.valueOf(value.longValue());
        }
        if (targetClass == BigDecimal.class) {
            return (T) new BigDecimal(value.toString());
        }
        throw new CastException(Number.class, targetClass);
    }

    /**
     * 将指定的字符串值转换为指定的数字类型
     *
     * @param value       字符串类型的源值
     * @param targetClass 目标数字类型
     * @param <T>         目标数字类型泛型
     * @return
     */
    @SuppressWarnings("unchecked")
    static <T extends Number> T stringToTargetNumber(String value, Class<T> targetClass) {
        if (Byte.class == targetClass) {
            return (T) (isHexNumber(value) ? Byte.decode(value) : Byte.valueOf(value));
        }
        if (Short.class == targetClass) {
            return (T) (isHexNumber(value) ? Short.decode(value) : Short.valueOf(value));
        }
        if (Integer.class == targetClass) {
            return (T) (isHexNumber(value) ? Integer.decode(value) : Integer.valueOf(value));
        }
        if (Long.class == targetClass) {
            return (T) (isHexNumber(value) ? Long.decode(value) : Long.valueOf(value));
        }
        if (Float.class == targetClass) {
            return (T) Float.valueOf(value);
        }
        if (Double.class == targetClass) {
            return (T) Double.valueOf(value);
        }
        if (BigInteger.class == targetClass) {
            int radix = 10;
            int index = 0;
            boolean negative = false;
            if (value.indexOf(0) == '-') {
                index++;
                negative = true;
            }
            if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
                index += 2;
                radix = 16;
            } else if (value.startsWith("#", index)) {
                index++;
                radix = 16;
            } else if (value.startsWith("0", index) && value.length() > 1 + index) {
                index++;
                radix = 9;
            }
            BigInteger bi = new BigInteger(value.substring(index), radix);
            return (T) (negative ? bi.negate() : bi);
        }
        if (BigDecimal.class == targetClass) {
            return (T) new BigDecimal(value);
        }
        return null;
    }

    private static boolean isHexNumber(String value) {
        int index = value.charAt(0) == '-' ? 1 : 0;
        return value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index);
    }

    static long checkAndToLong(Number number) {
        BigInteger res = null;
        if (number instanceof BigInteger) {
            res = (BigInteger) number;
        } else if (number instanceof BigDecimal) {
            res = ((BigDecimal) number).toBigInteger();
        }
        if (res != null && (res.compareTo(LONG_MIN) < 0 || res.compareTo(LONG_MAX) > 0)) {

        }
        return number.longValue();
    }

    /**
     * 将数组转换为字符串，直接调用元素对象的{@code toString}方法， 如果数组中包含 {@code null} 元素或 {@code toString} 后
     * 字符串为空，将会忽略。
     * <p>
     * 例：int[]{1, 2, 3} => "1,2,3"
     * </p>
     *
     * @param values 要转换的数组
     * @return 转换后的字符串，如果指定的数据为 {@code null}，或长度为 0，返回空字符串
     */
    static String arrayToString(Object values) {
        if (values == null) {
            return "";
        }
        int length = Array.getLength(values);
        if(length == 0) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(DELIMITER);
        for (int i = 0, len = length; i < len; i++) {
            Object item = Array.get(values, i);
            if(item == null) {
                continue;
            }
            String str = String.valueOf(item);
            if (str.isEmpty()) {
                continue;
            }
            joiner.add(str);
        }
        return joiner.toString();
    }

    /**
     * 将指定的 {@code Collection} 转换为字符串.
     *
     * @param value 被转换的集合
     * @return 转换后的 {@code String} 值，如果指定的集合为 {@code null} 或空，则返回空字符串
     */
    static String collectionToString(Collection value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(DELIMITER);
        Iterator iterator = value.iterator();
        Object item;
        while (iterator.hasNext()) {
            item = iterator.next();
            if (item == null) {
                continue;
            }
            String str = item.toString();
            if (str.isEmpty()) {
                continue;
            }
            joiner.add(str);
        }
        return joiner.toString();
    }

    static byte[] getBytesFromInputStream(InputStream in) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(in.available());
            byte[] buffer = new byte[1024 * 2];
            int len;
            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new CastException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    static Instant toInstant(TemporalAccessor accessor) {
        if (accessor instanceof Instant) {
            return (Instant) accessor;
        }
        if (accessor instanceof LocalDateTime) {
            return ((LocalDateTime) accessor).atZone(ZoneId.systemDefault()).toInstant();
        }
        if (accessor instanceof OffsetDateTime) {
            return ((OffsetDateTime) accessor).toInstant();
        }
        if (accessor instanceof LocalDate) {
            return ((LocalDate) accessor).atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        if (accessor instanceof LocalTime) {
            return ((LocalTime) accessor).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        }
        if (accessor instanceof OffsetTime) {
            return ((OffsetTime) accessor).atDate(LocalDate.now()).toInstant();
        }
        if (accessor instanceof ZonedDateTime) {
            return ((ZonedDateTime) accessor).toInstant();
        }
        return Instant.from(accessor);
    }

    static String clobToString(Clob clob) {
        try (Reader reader = clob.getCharacterStream()) {
            StringBuilder sb = new StringBuilder((int) clob.length());
            char[] buffer = new char[1024];
            int length = 0;
            while ((length = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, length);
            }
            return sb.toString();
        } catch (SQLException | IOException e) {
            throw new CastException(e);
        }
    }

    static byte[] blobToBytesArray(Blob blob) {
        try (InputStream in = blob.getBinaryStream()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream((int) blob.length());
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (SQLException | IOException e) {
            throw new CastException(e);
        }
    }

    static byte[] numberToBytes(Number number) {
        if(number instanceof Byte) {
            return new byte[]{number.byteValue()};
        }
        if(number instanceof Short) {
            short value = number.shortValue();
            return new byte[]{
                (byte) ((value >> 8) & 0xff),
                (byte) (value & 0xff)
            };
        }
        if(number instanceof Integer) {
            int value = number.intValue();
            return new byte[]{
                (byte) ((value >> 24) & 0xff),
                (byte) ((value >> 16) & 0xff),
                (byte) ((value >> 8) & 0xff),
                (byte) (value & 0xff)
            };
        }
        if(number instanceof Long) {
            long value = number.longValue();
            byte[] result = new byte[8];
            for(int i = 7; i >= 0; i--){
                result[i] = (byte) (value & 0xff);
                value >>= 8;
            }
            return result;
        }
        if(number instanceof BigInteger) {
            return ((BigInteger) number).toByteArray();
        }
        return null;
    }

    private static byte byteValue(Number value) {
        if(value instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) value;
            int scale = bigDecimal.scale();
            return scale >= -100 && scale <= 100 ? bigDecimal.byteValue() : bigDecimal.byteValueExact();
        }
        return value.byteValue();
    }

    private static short shortValue(Number value) {
        if(value instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) value;
            int scale = bigDecimal.scale();
            return scale >= -100 && scale <= 100 ? bigDecimal.shortValue() : bigDecimal.shortValueExact();
        }
        return value.shortValue();
    }

    private static int intValue(Number value) {
        if(value instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) value;
            int scale = bigDecimal.scale();
            return scale >= -100 && scale <= 100 ? bigDecimal.intValue() : bigDecimal.intValueExact();
        }
        return value.intValue();
    }

    private static long longValue(Number value) {
        if(value instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) value;
            int scale = bigDecimal.scale();
            return scale >= -100 && scale <= 100 ? bigDecimal.longValue() : bigDecimal.longValueExact();
        }
        return value.longValue();
    }

}

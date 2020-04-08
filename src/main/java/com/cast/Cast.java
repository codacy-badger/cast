package com.cast;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 类型转换帮助类
 * <p>将指定的值转换为目标类型</p>
 *
 * @author Jon
 */
public final class Cast {

    private Cast() {}

    /**
     * 将指定对象转换为 {@code Character}，如果不能转换为 {@code Character}，则返回指定的默认值
     * <p>遵循如下规则：</p>
     * <ul>
     *     <li>如果要转化的对象为 {@code null}，则返回提供的默认值</li>
     *     <li>如果要转化的对象为 {@code Character} 的实例，则直接返回</li>
     *     <li>如果要转化的对象为 {@code CharSequence} 的实例，如果为空字符串或包含多个字符，返回提供的默认值，否则转化为字符并返回</li>
     *     <li>如果要转化的对象为 {@code Number}，则取得其 short 类型并返回</li>
     *     <li>其他类型不能转化为 {@code Character}，则直接返回默认值</li>
     * </ul>
     *
     * @param obj 要转化的对象
     * @param defaultValue 默认值
     * @return 转化后的 {@code Character} 或指定的默认值
     */
    public static Character toChar(Object obj, Character defaultValue) {
        try {
            Character result = Converter.TO_CHAR_CONVERTER.convert(obj);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code Character}，如果不能转换为 {@code Character}，会抛出异常
     * <p>该方法的转换规则与 {@link Cast#toChar(Object, Character)} 一致，只不过在无法转换时，将会抛出异常</p>
     *
     * @param obj 要转化的对象
     * @return 转化后的 {@code Character}
     * @throws CastException 指定对象不能转换为 {@code Character} 时
     * @see Cast#toChar(Object, Character)
     */
    public static Character toChar(Object obj) {
        return Converter.TO_CHAR_CONVERTER.convert(obj);
    }

    /**
     * 将制定对象转换为 {@code char}，如果不能转换，将返回 char 类型的零值。
     * <p>该方法的转换规则与 {@link Cast#toChar(Object, Character)} 一致，只不过在无法转换时，将会抛出异常</p>
     *
     * @param obj 要转化的对象
     * @return 转换后的 char 或 其零值
     */
    public static char toCharValue(Object obj) {
        try {
            Character result = Converter.TO_CHAR_CONVERTER.convert(obj);
            return result == null ? 0 : result;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将指定对象转换为 {@code String}，如果不能转换为 {@code String}，则返回指定的默认值
     * <p>遵循如下规则：</p>
     * <ul>
     *     <li>如果要转换的对象为 {@code null}，返回提供的默认值</li>
     *     <li>如果要转化的对象为 {@code CharSequence}，调用 toString 方法后返回</li>
     *     <li>如果要转化的对象为数组或集合，则会直接调用元素的 {@code String.valueOf} 方法，并且将其连接起来，用英文逗号分隔。例：[1,2,3] - "1,2,3"</li>
     *     <li>如果要转化的对象为 {@code Clob}，则将其转换为 {@code String}，{@link Clob#getCharacterStream()}</li>
     *     <li>如果要转化的对象为 {@code Enum}，则会调动枚举对象的 {@code name} 方法</li>
     *     <li>其他类型，则会直接调用 {@code toString} 方法</li>
     * </ul>
     *
     * @param obj 要转化的对象
     * @param defaultValue 如果不能转换，提供的默认值
     * @return 转化后的 {@code String} 或默认值
     */
    public static String toStr(Object obj, String defaultValue) {
        try {
            String result = Converter.TO_STRING_CONVERTER.convert(obj);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code String}，如果不能转换为 {@code String}，会抛出异常
     * <p>该方法的转换规则与 {@link Cast#toStr(Object, String)} 一致，只不过在无法转换时，将会抛出异常</p>
     *
     * @param obj 要转化的对象
     * @return 转化后的 {@code String}
     * @throws CastException 指定对象不能转换为 {@code String} 时
     * @see Cast#toStr(Object, String)
     */
    public static String toStr(Object obj) {
        return Converter.TO_STRING_CONVERTER.convert(obj);
    }

    /**
     * 将指定对象转换为 {@code Boolean}，如果不能转换，则返回提供的默认值
     * <p>遵循如下规则：</p>
     * <ul>
     *     <li>如果为 {@code null}，直接返回默认值</li>
     *     <li>如果为 {@code Boolean} 实例，直接返回</li>
     *     <li>
     *         如果为 {@code CharSequence} 或 {@code Character} 的实例，首先会将其转换为 {@code String}，如果其值为 "true"、"on"、"yes"、"1"，将返回 true；
     *         如果值为 "false"、"off"、"no"、"0"，将返回 false
     *     </li>
     *     <li>其他情况则直接返回提供的默认值</li>
     * </ul>
     *
     * @param obj 要转化的对象
     * @param defaultValue 提供的默认值，如果不能转换为 {@code Boolean} 则会返回该值
     * @return 转化后的 {@code Boolean} 值
     */
    public static Boolean toBool(Object obj, Boolean defaultValue) {
        try {
            Boolean result = Converter.TO_BOOLEAN_CONVERTER.convert(obj);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code Boolean}，如果不能转换将抛出异常
     * <p>该方法的转换规则与 {@link Cast#toBool(Object, Boolean)} 一致，只不过当无法转换时，会抛出异常</p>
     *
     * @param obj 要转化的对象
     * @return 转化后的 {@code Boolean} 值
     * @throws CastException 指定对象不能转化为 {@code Boolean} 时抛出异常
     * @see Cast#toBool(Object, Boolean)
     */
    public static Boolean toBool(Object obj) {
        return Converter.TO_BOOLEAN_CONVERTER.convert(obj);
    }

    /**
     * 将制定对象转换为 {@code boolean}，如果不能转换，将返回 {@code false}。
     * <p>该方法的转换规则与 {@link Cast#toBool(Object, Boolean)} 一致，只不过在无法转换时，将会抛出异常</p>
     *
     * @param obj 要转化的对象
     * @return 转换后的 boolean 或 {@code false}
     * @see Cast#toBool(Object, Boolean)
     */
    public static boolean toBoolValue(Object obj) {
        try {
            Boolean result = Converter.TO_BOOLEAN_CONVERTER.convert(obj);
            return result == null ? false : result;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将指定对象转换为 {@code Byte}，如果不能转换则返回指定的默认值
     * <p>遵循如下规则：</p>
     * <ul>
     *     <li>如果指定的对象为 {@code null}，直接返回指定的默认值</li>
     *     <li>如果指定的对象为 {@code Number}，则会调用 {@code byteValue()}方法将其转换为 {@code Byte}，有可能会损失精度</li>
     *     <li>如果指定的对象为 {@code CharSequence}，则会调用 {@link Byte#valueOf(byte)} 或 {@link Byte#decode(String)} 方法将其转换为 {@code Byte}</li>
     *     <li>如果指定的对象为 {@code Boolean}，如果其值为{@code true}，则返回 1，否则返回 0</li>
     *     <li>如果指定对象为 Enum，则会调用枚举的 {@link Enum#ordinal()} 方法返回 {@code Byte} 值</li>
     * </ul>
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的默认值
     * @return 转换后的 {@code Byte} 实例或指定的默认值
     */
    public static Byte toByte(Object obj, Byte defaultValue) {
        try {
            Byte result = Converter.TO_NUMBER_CONVERTER.convert(obj, Byte.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code Byte}，如果不能完成转换将抛出异常。转换规则与 {@link Cast#toByte(Object, Byte)} 一致。
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code Byte} 实例
     * @throws CastException 不能完成转换时将抛出该异常
     * @see Cast#toByte(Object, Byte)
     */
    public static Byte toByte(Object obj) {
        return Converter.TO_NUMBER_CONVERTER.convert(obj, Byte.class);
    }

    /**
     * 将制定对象转换为 {@code byte}，如果不能转换，将返回 {@code 0}。
     * <p>该方法的转换规则与 {@link Cast#toByte(Object, Byte)} 一致，只不过在无法转换时，将会抛出异常</p>
     *
     * @param obj 要转化的对象
     * @return 转换后的 boolean 或 {@code 0}
     * @see Cast#toByte(Object, Byte)
     */
    public static byte toByteValue(Object obj) {
        try {
            Byte result = Converter.TO_NUMBER_CONVERTER.convert(obj, Byte.class);
            return result == null ? 0 : result;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将指定对象转换为 {@code Short}，如果不能完成转换返回指定的默认值，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的默认值
     * @return 转换后的 {@code Short} 实例或提供的默认值
     * @see Cast#toByte(Object, Byte)
     */
    public static Short toShort(Object obj, Short defaultValue) {
        try {
            Short result = Converter.TO_NUMBER_CONVERTER.convert(obj, Short.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code Short}，如果不能完成转换将会抛出异常，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code Short} 实例
     * @throws CastException 不能完成转换时抛出该异常信息
     * @see Cast#toByte(Object, Byte)
     */
    public static Short toShort(Object obj) {
        return Converter.TO_NUMBER_CONVERTER.convert(obj, Short.class);
    }

    /**
     * 将指定对象转换为 {@code short}，如果不能完成转换则返回 {@code 0}，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code short} 值或 {@code 0}
     * @see Cast#toByte(Object, Byte)
     */
    public static short toShortValue(Object obj) {
        try {
            Short result = Converter.TO_NUMBER_CONVERTER.convert(obj, Short.class);
            return result == null ? 0 : result;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将指定对象转换为 {@code Integer}，如果不能完成转换返回指定的默认值，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的默认值
     * @return 转换后的 {@code Integer} 实例或提供的默认值
     * @see Cast#toByte(Object, Byte)
     */
    public static Integer toInteger(Object obj, Integer defaultValue) {
        try {
            Integer result = Converter.TO_NUMBER_CONVERTER.convert(obj, Integer.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code Integer}，如果不能完成转换将会抛出异常，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code Integer} 实例
     * @throws CastException 不能完成转换时抛出该异常信息
     * @see Cast#toByte(Object, Byte)
     */
    public static Integer toInteger(Object obj) {
        return Converter.TO_NUMBER_CONVERTER.convert(obj, Integer.class);
    }

    /**
     * 将指定对象转换为 {@code int}，如果不能完成转换将会返回 {@code 0}，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code int} 值或 {@code 0}
     * @see Cast#toByte(Object, Byte)
     */
    public static int toIntValue(Object obj) {
        try {
            Integer result = Converter.TO_NUMBER_CONVERTER.convert(obj, Integer.class);
            return result == null ? 0 : result;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将指定对象转换为 {@code Long}，如果不能完成转换返回指定的默认值，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的默认值
     * @return 转换后的 {@code Long} 实例或提供的默认值
     * @see Cast#toByte(Object, Byte)
     */
    public static Long toLong(Object obj, Long defaultValue) {
        try {
            Long result = Converter.TO_NUMBER_CONVERTER.convert(obj, Long.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code Long}，如果不能完成转换将会抛出异常，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code Long} 实例
     * @throws CastException 不能完成转换时抛出该异常信息
     * @see Cast#toByte(Object, Byte)
     */
    public static Long toLong(Object obj) {
        return Converter.TO_NUMBER_CONVERTER.convert(obj, Long.class);
    }

    /**
     * 将指定对象转换为 {@code long}，如果不能完成转换将返回 {@code 0}，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code long} 值或 {@code 0}
     * @see Cast#toByte(Object, Byte)
     */
    public static long toLongValue(Object obj) {
        try {
            Long result = Converter.TO_NUMBER_CONVERTER.convert(obj, Long.class);
            return result == null ? 0 : result;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将指定对象转换为 {@code Float}，如果不能完成转换返回指定的默认值，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的默认值
     * @return 转换后的 {@code Float} 实例或提供的默认值
     * @see Cast#toByte(Object, Byte)
     */
    public static Float toFloat(Object obj, Float defaultValue) {
        try {
            Float result = Converter.TO_NUMBER_CONVERTER.convert(obj, Float.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code Float}，如果不能完成转换将会抛出异常，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code Float} 实例或提供的默认值
     * @throws CastException 不能完成转换时抛出该异常信息
     * @see Cast#toByte(Object, Byte)
     */
    public static Float toFloat(Object obj) {
        return Converter.TO_NUMBER_CONVERTER.convert(obj, Float.class);
    }

    /**
     * 将指定对象转换为 {@code float}，如果不能完成转换将返回 {@code 0.0}，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code float} 值或 {@code 0.0}
     * @see Cast#toByte(Object, Byte)
     */
    public static float toFloatValue(Object obj) {
        try {
            Float result = Converter.TO_NUMBER_CONVERTER.convert(obj, Float.class);
            return result == null ? 0.0F : result;
        } catch (Exception e) {
            return 0.0F;
        }
    }

    /**
     * 将指定对象转换为 {@code Double}，如果不能完成转换返回指定的默认值，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的默认值
     * @return 转换后的 {@code Double} 实例或提供的默认值
     * @see Cast#toByte(Object, Byte)
     */
    public static Double toDouble(Object obj, Double defaultValue) {
        try {
            Double result = Converter.TO_NUMBER_CONVERTER.convert(obj, Double.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code Double}，如果不能完成转换将会抛出异常，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code Double} 实例或提供的默认值
     * @throws CastException 不能完成转换时抛出该异常信息
     * @see Cast#toByte(Object, Byte)
     */
    public static Double toDouble(Object obj) {
        return Converter.TO_NUMBER_CONVERTER.convert(obj, Double.class);
    }

    /**
     * 将指定对象转换为 {@code double}，如果不能完成转换将返回 {@code 0.0}，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code double} 值或 {@code 0.0}
     * @see Cast#toByte(Object, Byte)
     */
    public static double toDoubleValue(Object obj) {
        try {
            Double result = Converter.TO_NUMBER_CONVERTER.convert(obj, Double.class);
            return result == null ? 0.0 : result;
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * 将指定对象转换为 {@code BigInteger}，如果不能完成转换返回指定的默认值，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的默认值
     * @return 转换后的 {@code BigInteger} 实例或提供的默认值
     * @see Cast#toByte(Object, Byte)
     */
    public static BigInteger toBigInteger(Object obj, BigInteger defaultValue) {
        try {
            BigInteger result = Converter.TO_NUMBER_CONVERTER.convert(obj, BigInteger.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code BigInteger}，如果不能完成转换将会抛出异常，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code BigInteger} 实例或提供的默认值
     * @throws CastException 不能完成转换时抛出该异常信息
     * @see Cast#toByte(Object, Byte)
     */
    public static BigInteger toBigInteger(Object obj) {
        return Converter.TO_NUMBER_CONVERTER.convert(obj, BigInteger.class);
    }

    /**
     * 将指定对象转换为 {@code BigDecimal}，如果不能完成转换返回指定的默认值，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的默认值
     * @return 转换后的 {@code BigDecimal} 实例或提供的默认值
     * @see Cast#toByte(Object, Byte)
     */
    public static BigDecimal toBigDecimal(Object obj, BigDecimal defaultValue) {
        try {
            BigDecimal result = Converter.TO_NUMBER_CONVERTER.convert(obj, BigDecimal.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定对象转换为 {@code BigDecimal}，如果不能完成转换将会抛出异常，转换规则与 {@link Cast#toByte(Object, Byte)} 类似.
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code BigDecimal} 实例或提供的默认值
     * @throws CastException 不能完成转换时抛出该异常信息
     * @see Cast#toByte(Object, Byte)
     */
    public static BigDecimal toBigDecimal(Object obj) {
        return Converter.TO_NUMBER_CONVERTER.convert(obj, BigDecimal.class);
    }

    /**
     * 将指定的对象转换为字节数组，如果不能转换返回提供的默认值
     *
     * @param obj 要转换的对象
     * @param defaultValue 无法完成转换时的的默认值
     * @return 转换后的字节数组或指定的默认值
     */
    public static byte[] toBytes(Object obj, byte[] defaultValue) {
        try {
            byte[] result = Converter.TO_BYTES_ARRAY_CONVERTER.convert(obj);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定的对象转换为字节数组，如果不能转换将会抛出异常
     *
     * @param obj 要转换的对象
     * @return 转换后的字节数组或指定的默认值
     * @throws CastException 如果不能转换将会抛出该异常
     */
    public static byte[] toBytes(Object obj) {
        return Converter.TO_BYTES_ARRAY_CONVERTER.convert(obj);
    }

    /**
     * 将指定的转换为 {@code Date} 类型，如果不能发生转换则返回指定的默认值
     *
     * @param obj 要转换的对象
     * @param defaultValue 提供的无法转换时的默认值
     * @return 转换后的 {@code Date} 值
     */
    public static Date toDate(Object obj, Date defaultValue) {
        try {
            Date result = Converter.TO_DATE_CONVERTER.convert(obj);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定的转换为 {@code Date} 类型，如果不能发生转换将会抛出异常
     *
     * @param obj 要转换的对象
     * @return 转换后的 {@code Date} 值
     * @throws CastException 不能发生转换时抛出该异常
     */
    public static Date toDate(Object obj) {
        return Converter.TO_DATE_CONVERTER.convert(obj);
    }

    public static Date toDate(Object obj, String format, Date defaultValue) {
        try {
            Date result = new ToDateConverter(format).convert(obj);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Date toDate(Object obj, String format) {
        return new ToDateConverter(format).convert(obj);
    }

    public static java.sql.Date toSqlDate(Object obj, java.sql.Date defaultValue) {
        try {
            java.sql.Date result = Converter.TO_DATE_CONVERTER.convert(obj, java.sql.Date.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static java.sql.Date toSqlDate(Object obj, String format, java.sql.Date defaultValue) {
        try {
            java.sql.Date result = new ToDateConverter(format).convert(obj, java.sql.Date.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Timestamp toSqlTimestamp(Object obj, Timestamp defaultValue) {
        try {
            Timestamp result = Converter.TO_DATE_CONVERTER.convert(obj, Timestamp.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Timestamp toSqlTimestamp(Object obj, String format, Timestamp defaultValue) {
        try {
            Timestamp result = new ToDateConverter(format).convert(obj, Timestamp.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Time toSqlTime(Object obj, Time defaultValue) {
        try {
            Time result = Converter.TO_DATE_CONVERTER.convert(obj, Time.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Time toSqlTime(Object obj, String format, Time defaultValue) {
        try {
            Time result = new ToDateConverter(format).convert(obj, Time.class);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定的对象转换为枚举类型实例，根据指定的 {@code targetClass} 去转换，如果不能转换，返回指定的默认值
     *
     * @param obj 要转化的对象
     * @param targetClass 目标枚举类型
     * @param defaultValue 提供的默认值
     * @param <T> 枚举类型
     * @return 转换后的指定类型的枚举值
     */
    public static <T extends Enum<T>> T toEnum(Object obj, Class<T> targetClass, T defaultValue) {
        try {
            T result = Converter.TO_ENUM_CONVERTER.convert(obj, targetClass);
            return result == null ? defaultValue : result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将指定的对象转换为枚举类型实例，根据指定的 {@code targetClass} 去转换，如果不能转换，将会抛出异常
     *
     * @param obj 要转化的对象
     * @param targetClass 目标枚举类型
     * @param <T> 枚举类型
     * @return 转换后的指定类型的枚举值
     * @throws CastException 如果不能完成转换将会抛出此异常
     */
    public static <T extends Enum<T>> T toEnum(Object obj, Class<T> targetClass) {
        return Converter.TO_ENUM_CONVERTER.convert(obj, targetClass);
    }

    /**
     * 将指定的集合转换为数组，如果指定的集合为 {@code null}，返回指定的默认值
     *
     * @param obj 要转化的集合
     * @param elementTargetClass 集合元素的类型
     * @param defaultValue 提供的默认值
     * @param <T> 元素类型的泛型
     * @return 转化后的对象数组
     * @throws CastException 如果 {@code elementTargetClass} 为 {@code null} 则会抛出该异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> obj, Class<T> elementTargetClass, T[] defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        if(elementTargetClass == null) {
            throw new CastException("the second argument [elementTargetClass] is null");
        }
        T[] array = (T[]) Array.newInstance(elementTargetClass, obj.size());
        return obj.toArray(array);
    }

    /**
     * 解析指定的字符串 {@code charsetStr} 为 {@link Charset} 实例。如果指定的字符串为 {@code null} 或空或不能解析为 {@link Charset}，返回指定的默认值。
     *
     * @param charsetStr 要解析的字符串
     * @param defaultCharset 提供的默认值
     * @return 解析后的 {@link Charset} 实例或提供的默认值
     */
    public static Charset toCharset(String charsetStr, Charset defaultCharset) {
        if(Utils.isEmpty(charsetStr)) {
            return defaultCharset;
        }
        try {
            return Charset.forName(charsetStr.trim());
        } catch (Exception e) {
            return defaultCharset;
        }
    }

    /**
     * 解析指定的字符串 {@code charsetStr} 为 {@link Charset} 实例。如果指定的字符串为 {@code null} 或空或不能解析为 {@link Charset}，则会抛出异常。
     *
     * @param charsetStr 要解析的字符串
     * @return 解析后的 {@link Charset} 实例或提供的默认值
     * @throws CastException 指定的字符串为 {@code null} 或空或不能解析为 {@link Charset}时，抛出该异常
     */
    public static Charset toCharset(String charsetStr) {
        if(Utils.isEmpty(charsetStr)) {
            throw new CastException("the charset string is null or empty");
        }
        try {
            return Charset.forName(charsetStr);
        } catch (Exception e) {
            throw new CastException(e);
        }
    }

    /**
     * 解析指定的字符串为 {@code TimeZone}。如果指定的字符串为 {@code null} 或空字符串或不能解析为相应的 {@link TimeZone}时，返回指定的默认值。
     *
     * @param timeZoneStr 要解析的字符串
     * @param defaultValue 不能解析或解析出错时返回的默认值
     * @return 解析后的 {@link TimeZone} 实例
     */
    public static TimeZone toTimeZone(String timeZoneStr, TimeZone defaultValue) {
        if(Utils.isEmpty(timeZoneStr)) {
            return defaultValue;
        }
        return TimeZone.getTimeZone(timeZoneStr);
    }

    /**
     * 解析指定的字符串为 {@link TimeZone}。如果指定的字符串为 {@code null} 或空字符串或不能解析为相应的 {@link TimeZone}时，则会抛出异常。
     *
     * @param timeZoneStr 要解析的字符串
     * @return 解析后的 {@link TimeZone} 实例
     * @throws CastException 指定的字符串为 {@code null} 或空字符串或不能解析为相应的 {@link TimeZone}时，抛出该异常
     */
    @Deprecated
    public static TimeZone toTimeZone(String timeZoneStr) {
        if(Utils.isEmpty(timeZoneStr)) {
            throw new CastException("the time zone string is null or empty");
        }
        return TimeZone.getTimeZone(timeZoneStr);
    }

    /**
     * 解析指定的字符串为 {@link Locale}，如果指定的字符串为 {@code null} 或空字符串，或不能解析为 {@link Locale} 时，则会返回指定的默认值。
     * <p>字符串是以下划线（_）进行分隔的格式化字符串，示例：</p>
     * <ul>
     *     <li>language</li>
     *     <li>language_country</li>
     *     <li>language_country_variant</li>
     * </ul>
     *
     * @param localStr 以下划线（_）分隔的可以表示 {@code Local} 的特定字符串
     * @param defaultValue 提供的默认值
     * @return 转化后的 {@link Locale} 实例或提供的默认值
     * @see Locale
     */
    public static Locale toLocale(String localStr, Locale defaultValue) {
        if(Utils.isEmpty(localStr)) {
            return defaultValue;
        }
        try {
            String[] items = localStr.split("_");
            if(items.length == 1) {
                return new Locale(items[0]);
            } else {
                return items.length == 2 ? new Locale(items[0], items[1]) : new Locale(items[0], items[1], items[2]);
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T to(Object obj, Class<T> targetClass) {
        if(targetClass == null) {
            throw new CastException("the target class is null");
        }
        if(obj == null) {
            if(targetClass == Character.TYPE) {
                return (T) Character.valueOf((char) 0);
            }
            if(targetClass == Byte.TYPE) {
                return (T) Byte.valueOf((byte) 0);
            }
            if(targetClass == Short.TYPE) {
                return (T) Short.valueOf((short) 0);
            }
            if(targetClass == Integer.TYPE) {
                return (T) Integer.valueOf(0);
            }
            if(targetClass == Long.TYPE) {
                return (T) Long.valueOf(0);
            }
            if(targetClass == Float.TYPE) {
                return (T) Float.valueOf(0.0F);
            }
            if(targetClass == Double.TYPE) {
                return (T) Double.valueOf(0.0);
            }
            if(targetClass == Boolean.TYPE) {
                return (T) Boolean.FALSE;
            }
            return null;
        }
        Class<?> srcClass = obj.getClass();
        if(targetClass.isAssignableFrom(srcClass)) {
            return (T) obj;
        }
        if(targetClass == Boolean.TYPE || targetClass == Boolean.class) {
            return (T) toBool(obj, null);
        }
        if(targetClass == Character.TYPE || targetClass == Character.class) {
            return (T) toChar(obj, null);
        }
        if(CharSequence.class.isAssignableFrom(targetClass)) {
            return (T) toStr(obj, null);
        }
        if(Number.class.isAssignableFrom(targetClass) || targetClass.isPrimitive()) {
            Class<? extends Number> clazz = (Class<? extends Number>) targetClass;
            try {
                return (T) Converter.TO_NUMBER_CONVERTER.convert(obj, clazz);
            } catch (Exception e) {
                return null;
            }
        }
        if(targetClass.isArray()) {
            if(obj instanceof Collection) {
                return (T) toArray((Collection) obj, targetClass.getComponentType(), null);
            }
            if(targetClass == byte[].class) {
                return (T) toBytes(obj, null);
            }
        }
        if(targetClass.isEnum()) {
            return (T) toEnum(obj, (Class<? extends Enum>) targetClass, null);
        }
        if(targetClass == Date.class) {
            return (T) toDate(obj, (Date) null);
        }
        if(targetClass == java.sql.Date.class) {
            return (T) toSqlDate(obj, null);
        }
        if(targetClass == Time.class) {
            return (T) toSqlTime(obj, null);
        }
        if(targetClass == Timestamp.class) {
            return (T) toSqlTimestamp(obj, null);
        }
        if(targetClass == Charset.class) {
            return (T) toChar(obj.toString(), null);
        }
        if(targetClass == TimeZone.class) {
            return (T) toTimeZone(obj.toString(), null);
        }
        if(targetClass == Locale.class) {
            return (T) toLocale(obj.toString(), null);
        }
        return null;
    }

}

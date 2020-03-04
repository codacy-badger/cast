package com.cast;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

class ToDateConverter extends ConditionConverter<Date> {

    private String format;

    ToDateConverter() {

    }

    ToDateConverter(String format) {
        this.format = format;
    }

    @Override
    public Date convert(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Calendar) {
            return ((Calendar) obj).getTime();
        }
        if (obj instanceof CharSequence) {
            try {
                return Utils.isEmpty(format) ? new SimpleDateFormat().parse(obj.toString())
                    : new SimpleDateFormat(format).parse(obj.toString());
            } catch (Exception e) {
                throw new CastException(obj.getClass(), Date.class, e);
            }
        }
        if (obj instanceof Number) {
            return new Date(((Number) obj).longValue());
        }
        if (obj instanceof TemporalAccessor) {
            Instant instant = Utils.toInstant((TemporalAccessor) obj);
            return new Date(instant.toEpochMilli());
        }
        throw new CastException(obj.getClass(), Date.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Date> T convert(Object obj, Class<T> targetClass) {
        Date date = convert(obj);
        if (date == null) {
            return null;
        }
        if (targetClass == Date.class) {
            return (T) date;
        }
        if (targetClass == java.sql.Date.class) {
            return (T) new java.sql.Date(date.getTime());
        }
        if (targetClass == Timestamp.class) {
            return (T) new Timestamp(date.getTime());
        }
        if (targetClass == Time.class) {
            return (T) new Time(date.getTime());
        }
        throw new CastException(obj.getClass(), targetClass);
    }

}

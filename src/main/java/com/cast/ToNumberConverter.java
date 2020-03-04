package com.cast;

class ToNumberConverter extends ConditionConverter<Number> {

    @Override
    public Number convert(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return (Number) obj;
        }
        if (obj instanceof Character) {
            return (short) ((Character) obj).charValue();
        }
        if (obj instanceof CharSequence) {
            String str = obj.toString();
            try {
                return Double.valueOf(str);
            } catch (Exception e) {
                throw new CastException("the value [" + str + "] cannot cast to number", e);
            }
        }
        if (Utils.isEnum(obj)) {
            return ((Enum) obj).ordinal();
        }
        throw new CastException(obj.getClass(), Number.class);
    }

    @Override
    public <T extends Number> T convert(Object obj, Class<T> targetClass) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return Utils.numberToTarget((Number) obj, targetClass);
        }
        if (obj instanceof CharSequence) {
            return Utils.stringToTargetNumber(obj.toString(), targetClass);
        }
        if (obj instanceof Character) {
            return Utils.numberToTarget((short) ((Character) obj).charValue(), targetClass);
        }
        if (Utils.isEnum(obj)) {
            return Utils.numberToTarget(((Enum) obj).ordinal(), targetClass);
        }
        throw new CastException(obj.getClass(), targetClass);
    }

}

package com.cast;

class ToEnumConverter extends ConditionConverter<Enum> {

    @Override
    public Enum convert(Object obj) {
        throw new CastException("");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Enum> T convert(Object obj, Class<T> targetClass) {
        if (obj == null) {
            return null;
        }
        if(targetClass == null) {
            throw new CastException("the target class is null");
        }
        if (obj.getClass() == targetClass) {
            return (T) obj;
        }
        if (obj instanceof CharSequence) {
            String str = obj.toString().trim();
            if (str.isEmpty()) {
                throw new CastException("empty string cannot cast to " + targetClass);
            }
            try {
                return (T) Enum.valueOf(targetClass, str);
            } catch (Exception e) {
                throw new CastException(obj.getClass(), targetClass, e);
            }
        }
        throw new CastException(obj.getClass(), targetClass);
    }

}

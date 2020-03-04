package com.cast;

class ToCharacterConverter implements Converter<Character> {

    @Override
    public Character convert(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Character) {
            return (Character) obj;
        }
        if (obj instanceof CharSequence) {
            String val = obj.toString();
            if (val.isEmpty()) {
                throw new CastException("empty string cannot cast to Character");
            }
            if (val.length() > 1) {
                throw new CastException("the string length greater than 1, cannot cast to Character");
            }
            return val.charAt(0);
        }
        if (obj instanceof Number) {
            return (char) ((Number) obj).shortValue();
        }
        throw new CastException(obj.getClass(), Character.class);
    }

}

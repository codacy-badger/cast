package com.cast;

import java.util.HashSet;
import java.util.Set;

class ToBooleanConverter implements Converter<Boolean> {

    private static final Set<String> TRUE_VALUES = new HashSet<>();
    private static final Set<String> FALSE_VALUES = new HashSet<>();

    static {
        TRUE_VALUES.add("true");
        TRUE_VALUES.add("on");
        TRUE_VALUES.add("yes");
        TRUE_VALUES.add("1");

        FALSE_VALUES.add("false");
        FALSE_VALUES.add("off");
        FALSE_VALUES.add("no");
        FALSE_VALUES.add("0");
    }

    @Override
    public Boolean convert(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof CharSequence || obj instanceof Character) {
            String str = obj.toString();
            if (str.isEmpty()) {
                throw new CastException("empty string cannot cast to Boolean");
            }
            str = str.toLowerCase();
            if (TRUE_VALUES.contains(str)) {
                return Boolean.TRUE;
            }
            if (FALSE_VALUES.contains(str)) {
                return Boolean.FALSE;
            }
            throw new CastException("the string [" + str + "] cannot cast to Boolean");
        }
        throw new CastException(obj.getClass(), Boolean.class);
    }

}

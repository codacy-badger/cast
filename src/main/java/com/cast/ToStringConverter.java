package com.cast;

import java.sql.Clob;
import java.util.Collection;

class ToStringConverter implements Converter<String> {

    @Override
    public String convert(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CharSequence) {
            return obj.toString();
        }
        if (Utils.isArray(obj)) {
            return Utils.arrayToString(obj);
        }
        if (obj instanceof Collection) {
            return Utils.collectionToString((Collection) obj);
        }
        if (obj instanceof Clob) {
            return Utils.clobToString((Clob) obj);
        }
        if (Utils.isEnum(obj)) {
            return ((Enum) obj).name();
        }
        return obj.toString();
    }

}

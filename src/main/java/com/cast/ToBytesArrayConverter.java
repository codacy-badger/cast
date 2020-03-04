package com.cast;

import java.io.InputStream;
import java.sql.Blob;

class ToBytesArrayConverter implements Converter<byte[]> {

    @Override
    public byte[] convert(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        if (obj instanceof CharSequence) {
            return obj.toString().getBytes();
        }
        if (obj instanceof Number) {
            return Utils.numberToBytes((Number) obj);
        }
        if (obj instanceof InputStream) {
            return Utils.getBytesFromInputStream((InputStream) obj);
        }
        if (obj instanceof Blob) {
            return Utils.blobToBytesArray((Blob) obj);
        }
        throw new CastException(obj.getClass(), byte[].class);
    }

}

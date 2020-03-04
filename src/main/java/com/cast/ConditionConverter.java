package com.cast;

abstract class ConditionConverter<R> implements Converter<R> {

    @Override
    public abstract R convert(Object obj);

    public abstract  <T extends R> T convert(Object obj, Class<T> targetClass);

}

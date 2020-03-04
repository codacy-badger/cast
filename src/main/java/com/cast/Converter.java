package com.cast;

interface Converter<R> {

    R convert(Object obj);

    ToBooleanConverter TO_BOOLEAN_CONVERTER = new ToBooleanConverter();

    ToBytesArrayConverter TO_BYTES_ARRAY_CONVERTER = new ToBytesArrayConverter();

    ToCharacterConverter TO_CHAR_CONVERTER = new ToCharacterConverter();

    ToDateConverter TO_DATE_CONVERTER = new ToDateConverter();

    ToEnumConverter TO_ENUM_CONVERTER = new ToEnumConverter();

    ToNumberConverter TO_NUMBER_CONVERTER = new ToNumberConverter();

    ToStringConverter TO_STRING_CONVERTER = new ToStringConverter();

}

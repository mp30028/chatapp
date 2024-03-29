package com.zonesoft.chatapp.utils.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.util.Date;

public class OffsetDateTimeReaderConverter implements Converter<OffsetDateTime, Date> {
    @Override
    public Date convert(OffsetDateTime odt) {
        return Date.from(odt.toInstant());
    }
}
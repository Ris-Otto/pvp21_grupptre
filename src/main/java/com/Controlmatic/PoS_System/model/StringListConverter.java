package com.Controlmatic.PoS_System.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://stackoverflow.com/a/50448142
@Converter
class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
    }

    private List<String> emptyList() {
        return new ArrayList<>();
    }
}

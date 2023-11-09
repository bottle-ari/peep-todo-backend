package com.peeptodo.peeptodo_backend.domain.subtodo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Converter
public class SubTodoConverter implements AttributeConverter<SubTodos,String> {

    @Override
    public String convertToDatabaseColumn(SubTodos attribute) {
        return attribute.toString();
    }

    @Override
    public SubTodos convertToEntityAttribute(String dbData) {
        // TODO: 11/7/2023 여기 때문에 내용에 따옴표나 작은따옴표 들어가면 문제 발생 가능 (테스트 필요)
        if (dbData.isEmpty()) {
            return new SubTodos();
        }
        // TODO: 11/7/2023 substring 안하고 다른 좋은 방법..?
        dbData = dbData.substring(1, dbData.length() - 1); // 맨 앞과 맨 뒤의 " 제거
        String[] split = dbData.split("\",\"");
        return new SubTodos(Arrays.asList(split));
    }
}

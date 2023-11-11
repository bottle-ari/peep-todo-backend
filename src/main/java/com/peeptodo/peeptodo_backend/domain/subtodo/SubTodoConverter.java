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
        if (dbData.isEmpty()) {
            return new SubTodos();
        }
        // code20231111164730
        // 11/7/2023 여기 때문에 내용에 따옴표나 작은따옴표 들어가면 문제 발생 가능 (테스트 필요)
        // 11/7/2023 substring 안하고 다른 좋은 방법..?
        // 11/9/2023 그리고 루틴 "추가"할 때 맨 앞이랑 맨 뒤에 큰따옴표 짤림 -> 이걸 또 수정하면 막상 받아올 때 또 짤림 -> 일관된 방법 연구 필요
        // 11/9/2023 잘리는 문제는, 리스트를 스트링으로 "join"할 때 맨앞과 맨뒤에 큰 따옴표 붙여주면 될듯
        dbData = dbData.substring(1, dbData.length() - 1); // 맨 앞과 맨 뒤의 " 제거
        String[] split = dbData.split("\",\"");
        return new SubTodos(Arrays.asList(split));
    }
}

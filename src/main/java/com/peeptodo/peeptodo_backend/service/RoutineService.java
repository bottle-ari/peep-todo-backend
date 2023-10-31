package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Routine;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.repository.RoutineRepository;
import com.peeptodo.peeptodo_backend.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    public void swapOrders(Long routineId, Long swapRoutineId) {
        // TODO: 10/31/2023 아래 내용 로우틴에 맞게 수정
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found!"));
        Routine swapRoutine = routineRepository.findById(swapRoutineId)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found!"));


        if (routine.equals(swapRoutine)) return;

        // 카테고리가 다른 경우
        Category category1 = (Category) Utils.unproxy(routine.getCategory());
        Category category2 = (Category) Utils.unproxy(swapRoutine.getCategory());
        if (!category1.equals(category2)) {
            throw new IllegalArgumentException("순서를 바꾸고자 하는 두 루틴의 카테고리가 일치하지 않음");
        }

        Integer o1 = routine.getOrders();
        Integer o2 = swapRoutine.getOrders();

        routine.setOrders(o2);
        swapRoutine.setOrders(o1);
        routineRepository.saveAll(List.of(routine, swapRoutine)); // 동일한 트랜잭션으로 처리해서 하나가 오류나면 둘다 오류가 발생해야 함
    }


}

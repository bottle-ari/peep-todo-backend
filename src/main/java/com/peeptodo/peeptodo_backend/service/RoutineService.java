package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Routine;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.RoutineRequestDto;
import com.peeptodo.peeptodo_backend.dto.RoutineResponseDto;
import com.peeptodo.peeptodo_backend.dto.RoutinesResponseDto;
import com.peeptodo.peeptodo_backend.repository.RoutineRepository;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import com.peeptodo.peeptodo_backend.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoutineService implements OrdersService {

    @Autowired
    private RoutineRepository routineRepository;
    @Autowired
    private UserRepository userRepository;

    public Routine createRoutine(RoutineRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Integer orders = requestDto.getOrders();
        if (orders == null) {
            orders = getNextOrders(user.getId());
        }

        Routine routine = new Routine();;
        routine.setName(requestDto.getName());
        routine.setIs_active(requestDto.getIs_active());
        routine.setPriority(requestDto.getPriority());
        routine.setRepeat_condition(requestDto.getRepeat_condition());
        routine.setSub_todo(requestDto.getSub_todo());
        routine.setOrders(orders);
        routine.setCategory(requestDto.getCategory());
        routine.setReminder(requestDto.getReminder());
        return routineRepository.save(routine);

    }

    public void swapOrders(Long routineId, Long swapRoutineId) {
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

    @Override
    public int getNextOrders(Long categoryId) {
        Optional<Routine> maxOrderRoutine = routineRepository.findFirstByCategoryIdOrderByOrdersDesc(categoryId);
        return maxOrderRoutine.map(routine -> routine.getOrders() + 1).orElse(1);
    }

    public List<RoutineResponseDto> getAllRoutines(Long categoryId) {
        //  userId와 categoryId가 동일한 소속인지 검증 -> EntityListener에 구현
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
//        Long userId = user.getId();

        List<Routine> routines = routineRepository.findByCategoryIdOrderByOrdersAsc(categoryId).orElseThrow(() -> new IllegalArgumentException("Routine not found!"));
        return routines.stream()
                .map(routine -> {
                    RoutineResponseDto dto = new RoutineResponseDto();
                    dto.setId(routine.getId());
                    dto.setName(routine.getName());
                    dto.setIs_active(routine.getIs_active());
                    dto.setPriority(routine.getPriority());
                    dto.setRepeat_condition(routine.getRepeat_condition());
                    dto.setSub_todo(routine.getSub_todo());
                    dto.setOrders(routine.getOrders());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}

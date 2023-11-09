package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.domain.Routine;
import com.peeptodo.peeptodo_backend.dto.RoutineRequestDto;
import com.peeptodo.peeptodo_backend.dto.RoutineResponseDto;
import com.peeptodo.peeptodo_backend.dto.RemindersResponseDto;
import com.peeptodo.peeptodo_backend.dto.RoutinesResponseDto;
import com.peeptodo.peeptodo_backend.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    @Autowired
    private RoutineService routineService;


    @PostMapping(value = "", produces = "application/json;charset=UTF-8")
    public ResponseEntity<RoutineResponseDto> createRoutine(@RequestBody RoutineRequestDto requestDto) {
        Routine routine = routineService.createRoutine(requestDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/category/{categoryId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<RoutinesResponseDto> getAllRoutines(@PathVariable Long categoryId) {
        List<RoutineResponseDto> reminderResponseDtoList = routineService.getAllRoutines(categoryId);
        RoutinesResponseDto remindersResponseDto = new RoutinesResponseDto(reminderResponseDtoList);
        return ResponseEntity.ok().body(remindersResponseDto);
    }

    @PatchMapping(value = "/{routineId}/name", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateName(@PathVariable Long routineId, @RequestBody String newName) {
        routineService.updateName(routineId, newName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{routineId}/is_active", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateIsActive(@PathVariable Long routineId, @RequestBody Boolean isActive) {
        routineService.updateIsActive(routineId, isActive);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{routineId}/priority", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updatePriority(@PathVariable Long routineId, @RequestBody Integer priority) {
        routineService.updatePriority(routineId, priority);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{routineId}/repeat_condition", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateRepeatCondition(@PathVariable Long routineId, @RequestBody String repeatCondition) {
        routineService.updateRepeatCondition(routineId, repeatCondition);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{routineId}/subtodo", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateSubTodo(@PathVariable Long routineId, @RequestBody List<String> subTodos) {
        routineService.updateSubTodo(routineId,subTodos);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{routineId}/order", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateOrder(@PathVariable Long routineId, @RequestBody Integer order) {
        routineService.updateOrders(routineId, order);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{routineId}/orders/swap", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> swapOrders(@PathVariable Long routineId, @RequestParam("id") Long swapId) {
        routineService.swapOrders(routineId, swapId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{routineId}")
    public ResponseEntity<String> deleteRoutine(@PathVariable Long routineId) {
        routineService.deleteCategory(routineId);
        return ResponseEntity.ok().build();
    }




}

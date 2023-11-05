package com.peeptodo.peeptodo_backend.controller;

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

    @PatchMapping(value = "/{routineId}/orders/swap", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> swapOrders(@PathVariable Long routineId, @RequestParam("id") Long swapId) {
        routineService.swapOrders(routineId, swapId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/category/{categoryId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<RoutinesResponseDto> getAllRoutines(@PathVariable Long categoryId) {
        List<RoutineResponseDto> reminderResponseDtoList = routineService.getAllRoutines(categoryId);
        RoutinesResponseDto remindersResponseDto = new RoutinesResponseDto(reminderResponseDtoList);
        return ResponseEntity.ok().body(remindersResponseDto);
    }

}

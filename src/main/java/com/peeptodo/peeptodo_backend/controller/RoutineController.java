package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}

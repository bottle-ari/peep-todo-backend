package com.peeptodo.peeptodo_backend.controller;


import com.peeptodo.peeptodo_backend.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PatchMapping(value = "/{reminderId}/orders/swap", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> swapOrders(@PathVariable Long reminderId, @RequestParam("id") Long swapId) {
        reminderService.swapOrders(reminderId, swapId);
        return ResponseEntity.ok().build();
    }
}

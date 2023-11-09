package com.peeptodo.peeptodo_backend.controller;


import com.peeptodo.peeptodo_backend.domain.Reminder;
import com.peeptodo.peeptodo_backend.dto.*;
import com.peeptodo.peeptodo_backend.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping(value = "", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ReminderResponseDto> createReminder(@RequestBody ReminderRequestDto requestDto) {
        Reminder reminder = reminderService.createReminder(requestDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "", produces = "application/json;charset=UTF-8")
    public ResponseEntity<RemindersResponseDto> getAllReminders() {
        List<ReminderResponseDto> reminderResponseDtoList = reminderService.getAllReminders();
        RemindersResponseDto remindersResponseDto = new RemindersResponseDto(reminderResponseDtoList);
        return ResponseEntity.ok().body(remindersResponseDto);
    }

    @PatchMapping(value = "/{reminderId}/name", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateName(@PathVariable Long reminderId, @RequestBody String newName) {
        reminderService.updateName(reminderId, newName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{reminderId}/icon", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateIcon(@PathVariable Long reminderId, @RequestBody String newIcon) {
        reminderService.updateIcon(reminderId, newIcon);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{reminderId}/if_condition", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateIfCondition(@PathVariable Long reminderId, @RequestBody String newIfCondition) {
        reminderService.updateIfCondition(reminderId, newIfCondition);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{reminderId}/notify_condition", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateNotifyCondition(@PathVariable Long reminderId, @RequestBody String notifyCondition) {
        reminderService.updateNotifyCondition(reminderId, notifyCondition);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{reminderId}/order", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateOrder(@PathVariable Long reminderId, @RequestBody Integer orders) {
        reminderService.updateOrders(reminderId,orders);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{reminderId}/orders/swap", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> swapOrders(@PathVariable Long reminderId, @RequestParam("id") Long swapId) {
        reminderService.swapOrders(reminderId, swapId);
        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/{reminderId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> deleteReminder(@PathVariable Long reminderId) {
        reminderService.deleteReminder(reminderId);
        return ResponseEntity.ok().build();
    }


}

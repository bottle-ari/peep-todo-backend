package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.domain.Todo;
import com.peeptodo.peeptodo_backend.dto.ScheduledTodoResponseDto;
import com.peeptodo.peeptodo_backend.dto.TodoRequestDto;
import com.peeptodo.peeptodo_backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping(value = "/", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> createTodo(@RequestBody TodoRequestDto requestDto) {
        Todo newTodo = todoService.createTodo(requestDto);
        return ResponseEntity.ok().build();
    }

    //http://localhost:8080/api/todos/scheduled/1?from=20230908&to=20230910
    @GetMapping(value = "/scheduled/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ScheduledTodoResponseDto> getScheduled(@PathVariable Long id, @RequestParam("from") String fromDate, @RequestParam("to") String toDate) {
        ScheduledTodoResponseDto responseDto = todoService.getScheduledTodo(id, fromDate, toDate);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{todoId}/name", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateName(@PathVariable Long todoId, @RequestBody String newName) {
        todoService.updateName(todoId, newName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{todoId}/reminder", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateReminder(@PathVariable Long todoId, @RequestBody Long reminderId) {
        todoService.updateReminder(todoId, reminderId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{todoId}/completed_at", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateCompletedAt(@PathVariable Long todoId, @RequestBody LocalDateTime newCompletedAt) {
        todoService.updateCompletedAt(todoId, newCompletedAt);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{todoId}/subtodo", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateSubTodo(@PathVariable Long todoId, @RequestBody String newSubTodo) {
        todoService.updateSubTodo(todoId, newSubTodo);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{todoId}/date", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateDates(@PathVariable Long todoId, @RequestBody String newDates) {
        todoService.updateDates(todoId, newDates);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{todoId}/priority", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updatePriority(@PathVariable Long todoId, @RequestBody Integer newPriority) {
        todoService.updatePriority(todoId, newPriority);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{todoId}/memo", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateMemo(@PathVariable Long todoId, @RequestBody String newMemo) {
        todoService.updateMemo(todoId, newMemo);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{todoId}/orders", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateOrders(@PathVariable Long todoId, @RequestBody Integer newOrders) {
        todoService.updateOrders(todoId, newOrders);
        return ResponseEntity.ok().build();
    }
}

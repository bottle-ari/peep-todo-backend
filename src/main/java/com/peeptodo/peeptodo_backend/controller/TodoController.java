package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.domain.Todo;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.ScheduledTodoResponseDto;
import com.peeptodo.peeptodo_backend.dto.TodoRequestDto;
import com.peeptodo.peeptodo_backend.service.TodoService;
import com.peeptodo.peeptodo_backend.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

//    @GetMapping(value = "/count", produces = "application/json;charset=UTF-8")
//    public ResponseEntity<String> getCount(@RequestParam("start-year") Integer startYear,@RequestParam("start-month") Integer startMonth,
//                                           @RequestParam("end-year") Integer endYear ,@RequestParam("end-month") Integer endMonth) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        return ResponseEntity.ok().body(count.toString:());
//    }

    @GetMapping(value = "/scheduled", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ScheduledTodoResponseDto> getScheduled(@RequestParam("from") String fromDate, @RequestParam("to") String toDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        assert principal instanceof User : "Authentication.principal is not User instance";
        User userInPrincipal = (User) principal;
        Long userId = userInPrincipal.getId();
        ScheduledTodoResponseDto responseDto = todoService.getScheduledTodo(userId, fromDate, toDate);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * 지연된 투두
     * dates컬럼이 "현재 날짜"보다 이전이면서 completed_at이 null인경우
     * @return
     */
    @GetMapping(value = "/overdue", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ScheduledTodoResponseDto> getOverdue(@RequestParam("to") String toDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        assert principal instanceof User : "Authentication.principal is not User instance";
        User userInPrincipal = (User) principal;
        Long userId = userInPrincipal.getId();

        if (toDate.isEmpty()) {
            LocalDateTime now = DateUtils.getNowDateTime();
            toDate = DateUtils.convertLocalDateTimeToString(now);
        }

        ScheduledTodoResponseDto responseDto = todoService.getOverdueTodo(userId,toDate);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = "/constant", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ScheduledTodoResponseDto> getConstant() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        assert principal instanceof User : "Authentication.principal is not User instance";
        User userInPrincipal = (User) principal;
        Long userId = userInPrincipal.getId();
        ScheduledTodoResponseDto responseDto = todoService.getConstantTodo(userId);
        return ResponseEntity.ok(responseDto);
    }



//    /**
//     * "현재 시간"을 기준으로 지연된 투두
//     * @return
//     */
//    @GetMapping(value = "/overdue", produces = "application/json;charset=UTF-8")
//    public ResponseEntity<ScheduledTodoResponseDto> getOverdue() {
//        LocalDateTime now = DateUtils.getNowDateTime();
//        String toDateStr = DateUtils.convertLocalDateTimeToString(now);
//        return getOverdue(toDateStr);
//    }


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
    public ResponseEntity<Void> updateDates(@PathVariable Long todoId, @RequestBody LocalDateTime newDates) {
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

    @PatchMapping(value = "/{todoId}/orders/swap", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> swapOrders(@PathVariable Long todoId, @RequestParam("id") Long swapId) {
        todoService.swapOrders(todoId, swapId);
        return ResponseEntity.ok().build();
    }
}

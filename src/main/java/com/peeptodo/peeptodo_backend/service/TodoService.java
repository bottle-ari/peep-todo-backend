package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Reminder;
import com.peeptodo.peeptodo_backend.domain.Todo;
import com.peeptodo.peeptodo_backend.dto.CategoryResponseDto;
import com.peeptodo.peeptodo_backend.dto.ScheduledTodoResponseDto;
import com.peeptodo.peeptodo_backend.dto.TodoRequestDto;
import com.peeptodo.peeptodo_backend.dto.TodoResponseDto;
import com.peeptodo.peeptodo_backend.repository.CategoryRepository;
import com.peeptodo.peeptodo_backend.repository.ReminderRepository;
import com.peeptodo.peeptodo_backend.repository.TodoRepository;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService implements OrdersService{
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public int getNextOrders(Long categoryId) {
        Optional<Todo> maxOrderTodo = todoRepository.findFirstByCategoryIdOrderByOrdersDesc(categoryId);
        return maxOrderTodo.map(todo -> todo.getOrders() + 1).orElse(1);
    }


    //Create
    public Todo createTodo(TodoRequestDto requestDto) {
        Reminder reminder = null;
        Category category = categoryRepository.findById(requestDto.getCategory_id())
                    .orElseThrow(() -> new IllegalArgumentException("category not found!"));


        if(requestDto.getReminder_id() != null) {
            reminder = reminderRepository.findById(requestDto.getReminder_id())
                    .orElseThrow(() -> new IllegalArgumentException("reminder not found!"));
        }

        Todo todo = new Todo();
        todo.setName(requestDto.getName());
        todo.setCompleted_at(requestDto.getCompleted_at());
        todo.setSub_todo(requestDto.getSub_todo());
        todo.setDates(requestDto.getDates());
        todo.setPriority(requestDto.getPriority());
        todo.setMemo(requestDto.getMemo());
        todo.setOrders(requestDto.getOrders());
        todo.setCategory(category);
        todo.setReminder(reminder);
        return todoRepository.save(todo);
    }

    //Delete
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    //Read
    public ScheduledTodoResponseDto getScheduledTodo(Long userId, String fromDate, String toDate) {
        List<Category> categories = categoryRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));

        List<TodoResponseDto> todoResponseDtos = new ArrayList<>();

        for (Category category : categories) {
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
            categoryResponseDto.setId(category.getId());
            categoryResponseDto.setName(category.getName());
            categoryResponseDto.setColor(category.getColor());
            categoryResponseDto.setEmoji(category.getEmoji());
            categoryResponseDto.setOrders(category.getOrders());

            //TODO : 현재 fromDate 하루에 해당하는 데이터만 뽑아냄. 여러 날짜로 변경하기.
            List<Todo> todos = todoRepository.findByCategoryIdAndFromDate(category.getId(), fromDate)
                    .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));

            List<TodoRequestDto> todoRequestDtos = todos.stream().map(
                    todo -> {
                        TodoRequestDto dto = new TodoRequestDto();
                        dto.setName(todo.getName());
                        dto.setCompleted_at(todo.getCompleted_at());
                        dto.setSub_todo(todo.getSub_todo());
                        dto.setDates(todo.getDates());
                        dto.setPriority(todo.getPriority());
                        dto.setMemo(todo.getMemo());
                        dto.setOrders(todo.getOrders());
                        dto.setCategory_id(todo.getCategory().getId());
                        if(todo.getReminder() != null) dto.setReminder_id(todo.getReminder().getId());
                        return dto;
                    }
            ).collect(Collectors.toList());
            TodoResponseDto todoResponseDto = TodoResponseDto.builder()
                    .category(categoryResponseDto)
                    .todoList(todoRequestDtos)
                    .build();
            todoResponseDtos.add(todoResponseDto);
        }

        return ScheduledTodoResponseDto.builder()
                .content(todoResponseDtos)
                .build();
    }

    //Update
    public void updateName(Long id, String newName) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        todo.setName(newName);
        todoRepository.save(todo);
    }

    public void updateReminder(Long id, Long reminderId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new IllegalArgumentException("Reminder not found!"));
        todo.setReminder(reminder);
        todoRepository.save(todo);
    }

    public void updateCompletedAt(Long id, LocalDateTime newCompleted_at) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        todo.setCompleted_at(newCompleted_at);
        todoRepository.save(todo);
    }

    public void updateSubTodo(Long id, String newSubTodo) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        todo.setSub_todo(newSubTodo);
        todoRepository.save(todo);
    }

    public void updateDates(Long id, String newDates) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        todo.setDates(newDates);
        todoRepository.save(todo);
    }

    public void updatePriority(Long id, Integer newPriority) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        todo.setPriority(newPriority);
        todoRepository.save(todo);
    }

    public void updateMemo(Long id, String newMemo) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        todo.setMemo(newMemo);
        todoRepository.save(todo);
    }

    public void updateOrders(Long id, Integer newOrders) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        todo.setOrders(newOrders);
        todoRepository.save(todo);
    }

    public void swapOrders(Long todoId, Long swapTodoId) {
        // todoId가 동일하면 따로 처리 X
        if (todoId.equals(swapTodoId)) {
            return;
        }

        Todo todo1 = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));
        Todo todo2 = todoRepository.findById(swapTodoId)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found!"));

        // 다른 카테고리 소속의 투두를 수정하려는 경우
        if (!todo1.getCategory().equals(todo2.getCategory())) {
            throw new IllegalArgumentException("Todo category not match!");
        }

        Integer o1 = todo1.getOrders();
        Integer o2 = todo2.getOrders();


        todo1.setOrders(o2);
        todo2.setOrders(o1);
        todoRepository.saveAll(List.of(todo1, todo2)); // 동일한 트랜잭션으로 처리해서 하나가 오류나면 둘다 오류가 발생해야 함

    }
}

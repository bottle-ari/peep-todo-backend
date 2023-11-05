package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Reminder;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.*;
import com.peeptodo.peeptodo_backend.repository.ReminderRepository;
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
public class ReminderService implements OrdersService{

    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private UserRepository userRepository;

    public Reminder createReminder(ReminderRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
        Integer orders = requestDto.getOrders();
        if (orders == null) {
            orders = getNextOrders(user.getId());
        }
        Reminder reminder = new Reminder();
        reminder.setName(requestDto.getName());
        reminder.setIcon(requestDto.getIcon());
        reminder.setIf_condition(requestDto.getIf_condition());
        reminder.setNotify_condition(requestDto.getNotify_condition());
        reminder.setOrders(orders);
        reminder.setUser(user);
        return reminderRepository.save(reminder);

    }


    @Override
    public int getNextOrders(Long userId) {
        Optional<Reminder> maxOrderReminder = reminderRepository.findFirstByUserIdOrderByOrdersDesc(userId);
        return maxOrderReminder.map(reminder -> reminder.getOrders() + 1).orElse(1);
    }

    public void swapOrders(Long reminderId, Long swapReminderId) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new IllegalArgumentException("Reminder not found!"));
        Reminder swapReminder = reminderRepository.findById(swapReminderId)
                .orElseThrow(() -> new IllegalArgumentException("Reminder not found!"));

        if (reminder.equals(swapReminder)) return;

        // 리마인더의 주인이 다른 경우
        User user1 = (User) Utils.unproxy(reminder.getUser());
        User user2 = (User) Utils.unproxy(swapReminder.getUser());
        if (!user1.equals(user2)) {
            throw new IllegalArgumentException("순서를 바꾸고자 하는 두 루틴의 카테고리가 일치하지 않음");
        }

        Integer o1 = reminder.getOrders();
        Integer o2 = swapReminder.getOrders();

        reminder.setOrders(o2);
        swapReminder.setOrders(o1);
        reminderRepository.saveAll(List.of(reminder, swapReminder)); // 동일한 트랜잭션으로 처리해서 하나가 오류나면 둘다 오류가 발생해야 함
    }


    public List<ReminderResponseDto> getAllReminders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        List<Reminder> reminders = reminderRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found!"));
        return reminders.stream()
                .map(reminder -> {
                    ReminderResponseDto dto = new ReminderResponseDto();
                    dto.setId(reminder.getId());
                    dto.setName(reminder.getName());
                    dto.setIcon(reminder.getIcon());
                    dto.setIf_condition(reminder.getIf_condition());
                    dto.setNotify_condition(reminder.getNotify_condition());
                    dto.setOrders(reminder.getOrders());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Reminder;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.repository.ReminderRepository;
import com.peeptodo.peeptodo_backend.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

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


}

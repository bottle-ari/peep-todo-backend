package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.entity.Account;
import com.peeptodo.peeptodo_backend.repository.AccountRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AccountController {
    private final AccountRepository personRepository;

    @Autowired
    public AccountController(AccountRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/account")
    public String postAccount(@RequestBody Account account){
        personRepository.save(account);
        return "account_post";
    }

    @GetMapping("/account")
    public String getAccountTest(Model model){
        // 아무거나 가져와서 return
//        Account account = personRepository.findAll().get(0);
        model.addAttribute("accounts",personRepository.findAll());
//        model.addAttribute("snsId", account.getSnsId());
//        model.addAttribute("snsInfo", account.getSnsInfo());
//        model.addAttribute("email", account.getEmail());
        return "accounts";
//        return personRepository.findAll().stream().map(obj->"snsId:"+obj.getSnsId()).collect(Collectors.joining());
    }

    @PostMapping("/account/save_test")
    public void personSave(@RequestBody Account account) {
        personRepository.save(account);
    }
}

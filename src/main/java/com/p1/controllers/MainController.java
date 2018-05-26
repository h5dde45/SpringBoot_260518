package com.p1.controllers;

import com.p1.domain.Message;
import com.p1.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        model.put("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag,
                      Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepo.save(message);
        model.put("messages", messageRepo.findAll());

        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter,
                         Map<String, Object> model) {
        if (filter == null || filter.isEmpty()) {
            model.put("messages", messageRepo.findAll());
        } else {
            model.put("messages", messageRepo.findByTag(filter));
        }
        return "main";
    }

}

package com.kob.backend.controller.user.bot;

import com.kob.backend.service.impl.bot.AddServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddController {
    @Autowired
    private AddServiceImpl addService;

    @PostMapping("/api/user/bot/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
//        for (Map.Entry<String, String> entry: data.entrySet()) {
//            System.out.println(entry.getKey() + "||" + entry.getValue());
//        }
        return addService.add(data);
    }
}

package com.kob.backend.service.impl.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.user.bot.UpdateService;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetail = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userDetail.getUser();

        int bot_id = Integer.parseInt(data.get("bot_id"));
        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");
        Map<String, String> map = new HashMap<>();

        if (title == null || title.length() == 0) {
            map.put("error_message", "title can't be null");
            return map;
        }

        if (title.length() > 100) {
            map.put("error_message", "title limit in 100 words");
            return map;
        }

        if (description == null || description.length() == 0) {
            description = "there is empty";
        }

        if (description.length() > 300) {
            map.put("error_message", "description limit in 300 words");
            return map;
        }

        if (content == null || content.length() == 0) {
            map.put("error_message", "code can't be null");
            return map;
        }

        if (content.length() > 10000) {
            map.put("error_message", "content limit in 10000 words");
            return map;
        }

        Bot bot = botMapper.selectById(bot_id);

        if (bot == null) {
            map.put("error_message", "bot not exit or deleted");
            return map;
        }

        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "exceed your authority");
            return map;
        }

        Bot newBot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getRating(),
                bot.getCreatetime(),
                new Date()
        );

        botMapper.updateById(newBot);
        map.put("error_message", "updated");
        return map;
    }
}

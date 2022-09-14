package com.kob.matchingsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static RestTemplate restTemplate;
    private final String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }
    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    public void addPlayer(Integer userId, Integer rating) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, 0));
        }finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.unlock();
        try {
            List<Player> tmp = new ArrayList<>();
            for (Player player: players) {
                if (!userId.equals(player.getUserId())){
                    tmp.add(player);
                }
            }
            players = tmp;
        }finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime() {
        for (Player player: players) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    private void matchPlayers() {
        System.out.println("match players: " + players.toString() );
        boolean[] used = new boolean[players.size()];
        for (int i = 0; i < players.size(); i++) {
            if (used[i]) continue;
            for (int j = i + 1; j < players.size(); j++) {
                Player a = players.get(i), b = players.get(j);
                if (checkMatched(a, b)) {
                    used[i] = used[j] = true;
                    sendResult(a, b);
                    break;
                }
            }
        }
        List<Player> tmp = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (!used[i])tmp.add(players.get(i));
        }
        players = tmp;
    }
    private boolean checkMatched (Player a, Player b) {
        int ratingGap = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return ratingGap <= waitingTime * 10;
    }

    private void sendResult (Player a, Player b) {
        System.out.println("send result: " + a.getUserId() + " " + b.getUserId());
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("b_id", b.getUserId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayers();
                }finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

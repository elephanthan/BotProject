package com.worksmobile.android.botproject.feature.chat.newchat;

import com.worksmobile.android.botproject.model.Bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 4. 26..
 */

public class BotLab {
    private static BotLab botLab;
    private List<Bot> bots;

    public static BotLab get() {
        if (botLab == null) {
            return new BotLab();
        }
        return botLab;
    }

    private BotLab() {
        bots = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Bot bot = new Bot(i, (i + 1) + "번째 봇");
            bots.add(bot);
        }
    }

    public List<Bot> getBots() {
        return bots;
    }

    public Bot getBot(int id) {
        for (Bot bot : bots) {
            if (bot.getBotId() == id) {
                return bot;
            }
        }
        return new Bot();
    }
}

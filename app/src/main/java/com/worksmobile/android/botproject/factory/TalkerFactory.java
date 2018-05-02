package com.worksmobile.android.botproject.factory;

import com.worksmobile.android.botproject.feature.chat.chatroom.UserLab;
import com.worksmobile.android.botproject.feature.chat.newchat.BotLab;
import com.worksmobile.android.botproject.model.Bot;
import com.worksmobile.android.botproject.model.Talker;
import com.worksmobile.android.botproject.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018. 5. 2..
 */

public class TalkerFactory {
    public Talker getTalker(int talkerType) {
        if (talkerType == Talker.TALKER_TYPE_USER) {
            return new User();
        } else if (talkerType == Talker.TALKER_TYPE_BOT) {
            return new Bot();
        }

        return null;
    }

    public List<Talker> getTalkers(int talkerType) {
        if (talkerType == Talker.TALKER_TYPE_USER) {
            UserLab userLab = UserLab.get();
            List<? extends Talker> users = userLab.getUsers();
            List<Talker> talkers = new ArrayList<>(users);
            return talkers;
        } else if (talkerType == Talker.TALKER_TYPE_BOT) {
            BotLab botLab = BotLab.get();
            List<? extends Talker> bots = botLab.getBots();
            List<Talker> talkers = new ArrayList<>(bots);
            return talkers;
        }

        return null;
    }

}

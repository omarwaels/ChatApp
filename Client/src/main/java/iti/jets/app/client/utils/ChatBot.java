package iti.jets.app.client.utils;

import com.google.code.chatterbotapi.*;

public class ChatBot {

    ChatterBotFactory factory = new ChatterBotFactory();
    ChatterBot bot2;

    public ChatBot() throws Exception {
        bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
    }

    public String getResponse(String s) throws Exception {
        ChatterBotSession bot2session = bot2.createSession();
        return bot2session.think(s);
    }
}
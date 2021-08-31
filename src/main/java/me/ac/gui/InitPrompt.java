package me.ac.gui;

import me.ac.Main;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class InitPrompt extends StringPrompt {
    Main plugin;
    String pass;
    public InitPrompt(Main plugin){this.plugin=plugin;pass="init170379315";}
    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return " ";
    }

    @Override
    public Prompt acceptInput(ConversationContext conversationContext, String s) {
        if(s.equals(pass)){plugin.init();}
        return Prompt.END_OF_CONVERSATION;
    }
}

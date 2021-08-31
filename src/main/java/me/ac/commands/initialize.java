package me.ac.commands;

import me.ac.Main;
import me.ac.gui.ConvPrompt;
import me.ac.gui.InitPrompt;
import me.ac.gui.gui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

public class initialize implements CommandExecutor {
    private Main plugin;
    Player p;
    public initialize(Main plugin){
        this.plugin=plugin;
    }
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getServer().getLogger().warning(plugin.getConfig().getString("NoConsole"));
            return true;
        }
        p = (Player) sender;
        ConversationFactory cf = new ConversationFactory(plugin);
        Conversation conv= cf.withFirstPrompt(new InitPrompt(plugin)).withLocalEcho(true).withEscapeSequence("cancel").buildConversation(p);conv.begin();


        return true;
    }
}

package me.ac.commands;

import me.ac.Main;
import me.ac.gui.gui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class top10 implements CommandExecutor {
    private Main plugin;
    Player p;
    public top10(Main plugin){
        this.plugin=plugin;
    }
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getServer().getLogger().warning(plugin.getConfig().getString("NoConsole"));
            return true;
        }
        p = (Player) sender;
        if (!p.hasPermission("AdvancedCasino.top10")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("NoPermission")));
            return true;
        }

        /*try {
            Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InvalidUsageWithdrawNoNum")));
            return true;
            //not a double
        }
        double amount = Double.parseDouble(args[0]);
        */
        p.sendMessage(ChatColor.DARK_AQUA+"------------"+ChatColor.AQUA+"TOP 10 WAGERS"+ChatColor.DARK_AQUA+"------------");
        Main.mainData.wagers=sortByValue(Main.mainData.wagers,false);
        int i=0;
        for (Map.Entry<String, Double> entry : Main.mainData.wagers.entrySet()) {
            if(i==10){break;}
            String key = entry.getKey();
            Double value = entry.getValue();
            p.sendMessage(ChatColor.GOLD+String.valueOf(i+1)+ChatColor.GRAY+" - "+ChatColor.AQUA+key+": "+value);
            i++;
            // ...
        }

        return true;
    }
    private static HashMap<String, Double> sortByValue(HashMap<String, Double> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

}

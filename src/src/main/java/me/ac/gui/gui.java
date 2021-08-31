package me.ac.gui;

import me.ac.Main;
import me.ac.playerbets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class gui implements Listener {
    private final Inventory inv;
    private String maintitle;
    private ConversationFactory cf;
    Main plugin;
    public gui(Main plugin){
        this.plugin=plugin;
        cf=new ConversationFactory(plugin);
        maintitle=new StringBuilder().append(ChatColor.GOLD).append(ChatColor.BOLD).append("Place Bet").toString();
        inv= Bukkit.createInventory(null, 27, maintitle);
        initializeItems();

    }
    public void initializeItems(){
        inv.setItem(12,createGuiItem(Material.RED_WOOL,new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD).append("Bet On Red").toString(),new StringBuilder().append(ChatColor.GRAY).append("Place your bet on color red.").toString(),new StringBuilder().append(ChatColor.GOLD).append("Multiplier: x2").toString()));
        inv.setItem(13,createGuiItem(Material.BLACK_WOOL,new StringBuilder().append(ChatColor.GRAY).append(ChatColor.BOLD).append("Bet On Black").toString(),new StringBuilder().append(ChatColor.GRAY).append("Place your bet on color black.").toString(),new StringBuilder().append(ChatColor.GOLD).append("Multiplier: x2").toString()));
        inv.setItem(14,createGuiItem(Material.GREEN_WOOL,new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("Bet On Green").toString(),new StringBuilder().append(ChatColor.GRAY).append("Place your bet on color green.").toString(),new StringBuilder().append(ChatColor.GOLD).append("Multiplier: x14").toString()));
        inv.setItem(26,createGuiItem(Material.BARRIER,new StringBuilder().append(ChatColor.DARK_RED).append(ChatColor.BOLD).append("Close").toString()));
    }
    protected ItemStack createGuiItem(Material material, String name, String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        if (lore != null) {
            meta.setLore(Arrays.asList(lore));
        }
        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(HumanEntity p) {
        p.openInventory(inv);
    }
    public void closeInventory(HumanEntity p) {
        p.closeInventory();
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        if(e.getView().getTitle().equalsIgnoreCase(maintitle)){
            if(clickedItem.getType()==Material.BARRIER){e.setCancelled(true);closeInventory(p);return;}
            if(clickedItem.getType()==Material.BLACK_WOOL){
                e.setCancelled(true);
                closeInventory(p);
                Conversation conv= cf.withFirstPrompt(new ConvPrompt(plugin, playerbets.Color.BLACK)).withLocalEcho(true).buildConversation(p);conv.begin();
                return;
            }
            if(clickedItem.getType()==Material.RED_WOOL){
                e.setCancelled(true);
                closeInventory(p);
                Conversation conv= cf.withFirstPrompt(new ConvPrompt(plugin, playerbets.Color.RED)).withLocalEcho(true).withEscapeSequence("cancel").buildConversation(p);conv.begin();
                return;
            }
            if(clickedItem.getType()==Material.GREEN_WOOL){
                e.setCancelled(true);
                closeInventory(p);
                Conversation conv= cf.withFirstPrompt(new ConvPrompt(plugin, playerbets.Color.GREEN)).withLocalEcho(true).withEscapeSequence("cancel").buildConversation(p);conv.begin();
                return;
            }
        }

    }


}

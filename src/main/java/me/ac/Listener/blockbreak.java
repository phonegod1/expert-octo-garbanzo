package me.ac.Listener;

import me.ac.Main;
import me.ac.blocksets;
import me.ac.commands.casino;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class blockbreak implements Listener {
    Main plugin;
    Material mat;
    private ArrayList<Block> a;
    public blockbreak(Main plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onblockbreak(BlockBreakEvent e){
        //System.out.println("BLOCK BREAK");
        a=new ArrayList<>();
        if(casino.setwheel!=null) {
           // System.out.println("NN");
            for (int i = 0; i < casino.setwheel.size(); i++) {
                //System.out.println("looping");
                //System.out.println(casino.setwheel.get(i));System.out.println(e.getPlayer().getUniqueId());
                if (casino.setwheel.get(i).equals(e.getPlayer().getUniqueId())) {
                    //add blocks to the list
                    //blocks=recursive(e.getBlock());
                    //add
                    mat = e.getBlock().getType();
                    recursive(e.getBlock());
                    Main.blocks.add(new blocksets(a));
                    Main.mainData.saveData("plugins"+System.getProperty("file.separator")+"AdvancedCasino"+System.getProperty("file.separator")+"Blocks.data");
                    e.getPlayer().sendMessage("Set the blockset.");
                    e.setCancelled(true);
                    break;
                }
            }
        }

    }
    private void recursive(Block b){
        a.add(b);
        if(b.getRelative(BlockFace.DOWN).getType().equals(mat)){
            if(!a.contains(b.getRelative(BlockFace.DOWN))){
                recursive(b.getRelative(BlockFace.DOWN));
            }
        }
        if(b.getRelative(BlockFace.NORTH).getType().equals(mat)){
            if(!a.contains(b.getRelative(BlockFace.NORTH))){
                recursive(b.getRelative(BlockFace.NORTH));
            }
        }
        if(b.getRelative(BlockFace.SOUTH).getType().equals(mat)){
            if(!a.contains(b.getRelative(BlockFace.SOUTH))){
                recursive(b.getRelative(BlockFace.SOUTH));
            }
        }
        if(b.getRelative(BlockFace.EAST).getType().equals(mat)){
            if(!a.contains(b.getRelative(BlockFace.EAST))){
                recursive(b.getRelative(BlockFace.EAST));
            }
        }
        if(b.getRelative(BlockFace.WEST).getType().equals(mat)){
            if(!a.contains(b.getRelative(BlockFace.WEST))){
                recursive(b.getRelative(BlockFace.WEST));
            }
        }
        if(b.getRelative(BlockFace.UP).getType().equals(mat)){
            if(!a.contains(b.getRelative(BlockFace.UP))){
                recursive(b.getRelative(BlockFace.UP));
            }
        }

    }
}

package me.ac;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
public class Data implements Serializable {
private static transient final long serialVersionUID = -1681012202363346243L;

public HashMap<String,String> wintotalratio;
public HashMap<String,Double> wagers;
public ArrayList<String> b;
//private ArrayList
//public ArrayList<blocksets> blocks;//here the player is the one who submitted the report


//public final HashSet<UUID> previouslyOnlinePlayers;

// Can be used for saving
public Data(ArrayList<String> b,HashMap<String,Double> w,HashMap<String,String> z) {
        //this.blocks=c;
        this.b=b;
        this.wagers=w;
        this.wintotalratio=z;
        }
// Can be used for loading
public Data(Data loadedData) {
        this.b=loadedData.b;

        this.wagers=loadedData.wagers;
        this.wintotalratio=loadedData.wintotalratio;
        }

public boolean saveData(String filePath) {
        try {
        BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
        out.writeObject(this);
        out.close();
        return true;
        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return false;
        }
        }
public static Data loadData(String filePath) {
        try {
        BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
        Data data = (Data) in.readObject();
        in.close();
        return data;
        } catch (ClassNotFoundException | IOException e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        return null;
        }
        }
public static void getBlocksPlayersAndSave() {
        // HashMap used for storing blocks
        HashMap<Location, String> blockSnapShot = new HashMap<Location, String>();
        // HashSet used for storing the online players
        HashSet<UUID> previouslyOnlinePlayers = new HashSet<UUID>();
        // Grabs the spawn location of the first world the server finds
        Location spawnLocation = Bukkit.getServer().getWorlds().iterator().next().getSpawnLocation();
        // One variable to store our blockLocation
        Location blockLocation;
        // Variables to store our x y z coordinates
        int x, y, z;
        // We will first retrieve all the currently online players
        //Bukkit.getServer().getOnlinePlayers().forEach(player -> previouslyOnlinePlayers.add(player.getUniqueId()));
        // Next we will retrieve all block data in a 64 by 64 radius around the spawn.

        // While looping, using the new keyword and making declarations like "int x = 0;"
        // will create garbage, and that garbage will start to pile up if the loop is
        // extensive. We will take as much of a load off of the garbage collector as we
        // can here by re-assigning x,y,z not re-declaring, and re-assigning the declared
        // blockLocation to retrieve the block data. (we are going to store 262,144 blocks...)
        /*for (x = 0; x <= 32; x++) {
            for (y = 0; y <= 32; y++) {
                for(z = 0; z <= 32; z++) {
                    blockSnapShot.put(blockLocation = new Location(spawnLocation.getWorld(),
                            spawnLocation.getX() - x,
                            spawnLocation.getY() - y,
                            spawnLocation.getZ() - z), blockLocation.getBlock().getBlockData().getAsString());
                    blockSnapShot.put(blockLocation = new Location(spawnLocation.getWorld(),
                            spawnLocation.getX() + x,
                            spawnLocation.getY() - y,
                            spawnLocation.getZ() - z), blockLocation.getBlock().getBlockData().getAsString());
                    blockSnapShot.put(blockLocation = new Location(spawnLocation.getWorld(),
                            spawnLocation.getX() - x,
                            spawnLocation.getY() + y,
                            spawnLocation.getZ() - z), blockLocation.getBlock().getBlockData().getAsString());
                    blockSnapShot.put(blockLocation = new Location(spawnLocation.getWorld(),
                            spawnLocation.getX() - x,
                            spawnLocation.getY() - y,
                            spawnLocation.getZ() + z), blockLocation.getBlock().getBlockData().getAsString());
                    blockSnapShot.put(blockLocation = new Location(spawnLocation.getWorld(),
                            spawnLocation.getX() + x,
                            spawnLocation.getY() + y,
                            spawnLocation.getZ() + z), blockLocation.getBlock().getBlockData().getAsString());
                    blockSnapShot.put(blockLocation = new Location(spawnLocation.getWorld(),
                            spawnLocation.getX() - x,
                            spawnLocation.getY() + y,
                            spawnLocation.getZ() + z), blockLocation.getBlock().getBlockData().getAsString());
                    blockSnapShot.put(blockLocation = new Location(spawnLocation.getWorld(),
                            spawnLocation.getX() + x,
                            spawnLocation.getY() - y,
                            spawnLocation.getZ() + z), blockLocation.getBlock().getBlockData().getAsString());
                    blockSnapShot.put(blockLocation = new Location(spawnLocation.getWorld(),
                            spawnLocation.getX() + x,
                            spawnLocation.getY() + y,
                            spawnLocation.getZ() - z), blockLocation.getBlock().getBlockData().getAsString());
                }
            }
        }
        // Finally we save the retrieved data to a file

        // You will most likely want to change the file location to your some other directory,
        // like your plugin's data directory, instead of the Tutorial's.
        */
        //new Data(blockSnapShot, previouslyOnlinePlayers).saveData("Tutorial.data");
        //Bukkit.getServer().getLogger().log(Level.INFO, "Data Saved");
        }
    /*public static void welcomePlayersAndResetBlocks() {
        // Load the data from disc using our loadData method.
        Data data = new Data(Data.loadData("Tutorial.data"));
        // For each player that is current online send them a message
        data.previouslyOnlinePlayers.forEach(playerId -> {
            if (Bukkit.getServer().getPlayer(playerId).isOnline()) {
                Bukkit.getServer().getPlayer(playerId).sendMessage("Thanks for coming back after downtime. Hope you see the spawn blocks change!");
            }
        });
        // Change all of the blocks around the spawn to what we have saved in our file.
        data.blockSnapShot.keySet().forEach(location -> location.getBlock().setBlockData(Bukkit.getServer().createBlockData(data.blockSnapShot.get(location))));
        Bukkit.getServer().getLogger().log(Level.INFO, "Data loaded");
    }*/
}

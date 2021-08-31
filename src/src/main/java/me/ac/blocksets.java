package me.ac;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class blocksets {

    private ArrayList<Block> blocks;
    private ArrayList<String> s;
    //private ArrayList<Location> locs;
    private String w;
    public blocksets(ArrayList<Block> b){
        //w= b.get(0).getWorld().getName();
        //locs=new ArrayList<>();
        //s=new ArrayList<>();
        this.blocks=b;
        /*for(Block z:b){
            Location y=z.getLocation();
            s.add(w+":"+y.getBlockX()+":"+y.getBlockY()+":"+y.getBlockZ());
            //locs.add(z.getLocation());

        }*/
    }

    /*public void setblocks(){
        blocks=new ArrayList<>();
        for(Location y:locs) {
            blocks.add(w.getBlockAt(y));
        }
    }*/

    public ArrayList<Block> getarray(){



        return blocks;


    }

    public void setarray(ArrayList<Block> a){
        this.blocks=a;
    }

    public void setmat(Material mat){
        /*ArrayList<Block> blocks=new ArrayList<>();
        ArrayList<Location> locs=new ArrayList<>();

        for(String st:s){
            String[] constu=st.split(":");

            locs.add(new Location(Bukkit.getWorld(constu[0]),Double.parseDouble(constu[1]),Double.parseDouble(constu[2]),Double.parseDouble(constu[3])));
        }
        for(Location y:locs) {
            blocks.add(Bukkit.getWorld(w).getBlockAt(y));
        }*/
        for(Block x :blocks){
            x.setType(mat);
        }
    }
    public Material getmat(){
        /*ArrayList<Location> locs=new ArrayList<>();

        for(String st:s){
            String[] constu=st.split(":");

            locs.add(new Location(Bukkit.getWorld(constu[0]),Double.parseDouble(constu[1]),Double.parseDouble(constu[2]),Double.parseDouble(constu[3])));
        }*/
        return blocks.get(0).getType();
    }
}

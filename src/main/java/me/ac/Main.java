package me.ac;

import me.ac.CryptoService.ServiceManager;
import me.ac.CryptoService.WithdrawService;
import me.ac.Listener.blockbreak;
import me.ac.Placeholder.CasinoPlaceholder;
import me.ac.commands.*;
import me.ac.databasemanagersql.DatabaseManagerMysql;
import me.ac.gui.gui;
import me.ac.timers.timer;
import me.ac.timers.wheelrolling;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Main extends JavaPlugin {
    public static Boolean walletset;
    //static public Data mainData;
    public static DatabaseManagerMysql databaseManager;
    private static ServiceManager SM;
    public static timer timer;
    public static Boolean allowtransfer;
    public static Boolean ls;
    public static Boolean isRoll;
    public static int time;
    public static WithdrawService WS;
    public static playerbets.Color LastWonColor;
    public static ArrayList<playerbets> pb,pb2;
    public static ArrayList<blocksets> blocks;
    static public Data mainData;
    public static boolean initialized;


    @Override
    public void onEnable(){
        initialized=false;
        // Small check to make sure that PlaceholderAPI is installed
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new CasinoPlaceholder(this).register();
        }
        //this.getCommand("initialize").setExecutor(new initialize(this));
        init();

    }

    @Override
    public void onDisable(){
        mainData.b=new ArrayList<>();
        for(blocksets o:blocks){
            String temp=null;
            ArrayList<Block> u=o.getarray();
            for(Block t:u){
                String w=t.getWorld().getName();
                int x=t.getX();
                int y=t.getY();
                int z=t.getZ();
                if(temp==null) {
                    temp = w + ":" + x + ":" + y + ":" + z;
                } else{
                    temp=temp+ ":"+w + ":" + x + ":" + y + ":" + z;
                }

            }
            mainData.b.add(temp);
        }

        mainData.saveData("plugins"+System.getProperty("file.separator")+"AdvancedCasino"+System.getProperty("file.separator")+"Blocks.data");
        // here
    }


    public void InitializeConfig(){
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
    public void createFile(){

        try {
            (new File("plugins"+System.getProperty("file.separator")+"AdvancedCasino"+System.getProperty("file.separator")+"Wallet")).mkdir();

        } catch (Exception e) {
            System.out.println("AdvancedCasino wallet file created/or failed to create");
        }

    }
    public void init(){
        initialized=true;
        String path = "plugins"+System.getProperty("file.separator")+"AdvancedCasino"+System.getProperty("file.separator")+"Blocks.data";

        blocks=new ArrayList<>();
        if(Data.loadData(path)!=null) {
            mainData = new Data(Objects.requireNonNull(Data.loadData(path)));
            for(String s:mainData.b){
                ArrayList<Block> bs=new ArrayList<>();
                String[] constu=s.split(":");
                for(int i=0;i<constu.length-3;i+=4){

                    World w=Bukkit.getWorld(constu[i]);
                    /*System.out.println(constu[i]);
                    System.out.println(constu[i+1]);
                    System.out.println(constu[i+2]);
                    System.out.println(constu[i+3]);*/
                    int x=Integer.parseInt(constu[i+1]);//
                    int y=Integer.parseInt(constu[i+2]);
                    int z=Integer.parseInt(constu[i+3]);

                    bs.add(w.getBlockAt(new Location(w,x,y,z)));

                }
                blocks.add(new blocksets(bs));
            }
            /*for(blocksets j: mainData.blocks){
                j.setblocks();
            }*/
        } else {
            System.out.println("Instantiating BLOCKS.DATA");
            ArrayList<String>b=new ArrayList<>();
            HashMap<String,Double> wagers=new HashMap<>();
            HashMap<String,String> wintotalratio=new HashMap<>();
            mainData=new Data(b,wagers,wintotalratio);//here
        }

        InitializeConfig();
        getServer().getPluginManager().registerEvents(new blockbreak(this), this);
        this.getCommand("bet").setExecutor(new bet(this));
        this.getCommand("casino").setExecutor(new casino(this));
        this.getCommand("deposit").setExecutor(new deposit(this));
        this.getCommand("withdraw").setExecutor(new withdraw(this));
        this.getCommand("top10").setExecutor(new top10(this));
        this.getCommand("howtobet").setExecutor(new howtobet());
        this.getCommand("howtodeposit").setExecutor(new howtodeposit());
        this.getCommand("howtowithdraw").setExecutor(new howtowithdraw());
        createFile();

        LastWonColor=playerbets.Color.GREEN;

        isRoll=false;
        wheelrolling w=new wheelrolling();
        w.runTaskTimer(this,0,10);

        //roll y= new roll(5,this);
        //y.runTaskTimer(this,0,20);
        timer=new timer(this);
        timer.runTaskTimer(this,0,20);


        pb= new ArrayList<>();
        pb2= new ArrayList<>();

        allowtransfer=true;
        walletset=true;
        ls=true;
        SM=new ServiceManager(this);
        SM.start();
        WS= new WithdrawService(this);


        databaseManager = new DatabaseManagerMysql(this);
        Bukkit.getPluginManager().registerEvents(new gui(this), this);

    }
}

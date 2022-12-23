package net.jack.lightning;


import lombok.Getter;
import net.jack.lightning.crews.admincommands.AdminPoints;
import net.jack.lightning.crews.commandmanager.CrewCommandManager;
import net.jack.lightning.crews.commands.Points;
import net.jack.lightning.crews.listeners.CrewListeners;
import net.jack.lightning.serverutils.SpartanBoard;
import net.jack.lightning.spleef.commands.SpleefCommand;
import net.jack.lightning.spleef.handler.SpleefHandler;
import net.jack.lightning.spleef.instance.Arena;
import net.jack.lightning.spleef.listeners.ConnectListener;
import net.jack.lightning.spleef.listeners.SpleefListener;
import net.jack.lightning.stattrack.TopKills;
import net.jack.lightning.stattrack.listeners.Events;
import net.jack.lightning.utilities.Config;
import net.jack.lightning.utilities.PAPIExpansions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class LightningCore extends JavaPlugin {

    private Arena arena;
    private SpleefHandler spleefHandler;
    private SpleefListener spleefListener;
    private TopKills topKills;
    private final String prefix = "&7(&e&l⚡&7)";

    private static LightningCore instance;
    private final File crews = new File(getDataFolder(), "crews.yml");
    private final File crewSettings = new File(getDataFolder(), "crewsettings.yml");
    private final File crewUser = new File(getDataFolder(), "crewuser.yml");
    private final File spleef = new File(getDataFolder(), "spleef.yml");
    private final File stattrack = new File(getDataFolder(), "stattrack.yml");


    private final FileConfiguration statTrackConfiguration = YamlConfiguration.loadConfiguration(stattrack);
    private final FileConfiguration spleefConfiguration = YamlConfiguration.loadConfiguration(spleef);
    private final FileConfiguration crewConfiguration = YamlConfiguration.loadConfiguration(crews);
    private final FileConfiguration crewUserConfiguration = YamlConfiguration.loadConfiguration(crewUser);
    private final FileConfiguration crewSettingsConfiguration = YamlConfiguration.loadConfiguration(crewSettings);


    public void onEnable() {
        instance = this;
        this.Config();
        this.spleefHandler = new SpleefHandler(this, arena);
        this.spleefListener = new SpleefListener(this, arena);
        this.topKills = new TopKills(this);
        topKills.killTopUpdater();
        spleefListener.waterCheck();

        long duration = System.currentTimeMillis();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            try{
                getLogger().info("Trying to load PAPI expansion...");
                new PAPIExpansions(this).register();
                getLogger().info("Success!");
            } catch (Exception e){
                getLogger().severe("Failed!");
                e.printStackTrace();
            }
        }

        String prefix = "§3[" + getDescription().getName() + " " + getDescription().getVersion() + "] ";
        Bukkit.getConsoleSender().sendMessage(prefix + " §6=== ENABLE START ===");
        Bukkit.getConsoleSender().sendMessage(prefix + " §aLoading §dListeners");
        registerEvents();
        Bukkit.getConsoleSender().sendMessage(prefix + " §aLoading §dCommands");
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + " §aMade by §dJack");
        Bukkit.getConsoleSender().sendMessage(
                prefix + "§6=== ENABLE §aCOMPLETE §6(Took §d" + (System.currentTimeMillis() - duration) + "ms§6) ===");

    }

    private void registerCommands() {
        getCommand("crew").setExecutor(new CrewCommandManager(this));
        getCommand("spleef").setExecutor(new SpleefCommand(this));
        getCommand("points").setExecutor(new Points(this));
        getCommand("crewadmin").setExecutor(new AdminPoints(this));
    }

    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new SpartanBoard(this), this);
        manager.registerEvents(new CrewListeners(this), this);
        manager.registerEvents(new SpleefListener(this, arena), this);
        manager.registerEvents(new ConnectListener(this), this);
        manager.registerEvents(new Events(this), this);
    }

    public SpleefHandler getSpleefHandler() {
        return spleefHandler;
    }

    public Arena getArena() {
        return arena;
    }

    private void Config() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        new Config(crews, crewConfiguration, "crews.yml", this);
        new Config(crews, crewConfiguration, "crews.yml", this);
        new Config(crewSettings, crewSettingsConfiguration, "crewsettings.yml", this);
        new Config(crewUser, crewUserConfiguration, "crewuser.yml", this);
        new Config(spleef, spleefConfiguration, "spleef.yml", this);
        new Config(stattrack, statTrackConfiguration, "stattrack.yml", this);
    }




    public void onDisable() {
        instance = null;
        this.Config();
        long duration = System.currentTimeMillis();
        String prefix = "§3[" + getDescription().getName() + " " + getDescription().getVersion() + "] ";
        Bukkit.getConsoleSender().sendMessage(prefix + "§6=== DISABLING ===");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aDisabling §dListeners");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aDisabling §dCommands");
        Bukkit.getConsoleSender().sendMessage(prefix + "§aMade by §dJack");
        Bukkit.getConsoleSender().sendMessage(
                prefix + "§6=== DISABLE §aCOMPLETE §6(Took §d" + (System.currentTimeMillis() - duration) + "ms§6) =");
    }

}

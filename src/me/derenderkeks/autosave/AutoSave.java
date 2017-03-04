package me.derenderkeks.autosave;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by DerEnderKeks on 27.08.2015.
 **/

public class AutoSave extends JavaPlugin {

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);

        getConfig().addDefault("autosave-enabled", true);
        getConfig().addDefault("interval", 300);

        saveConfig();

        boolean autosave = getConfig().getBoolean("autosave-enabled", true);
        int interval = getConfig().getInt("interval", 300);

        if (autosave) {
            for (World w : getServer().getWorlds()) {
                w.setAutoSave(false);
            }

            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    getLogger().info("Saving worlds...");
                    for (World w : getServer().getWorlds()) {
                        w.setAutoSave(false);
                        w.save();
                    }
                    getLogger().info("Save complete!");
                    getServer().broadcast(ChatColor.translateAlternateColorCodes('&', "&8[&6AutoSave&8] &6Save complete!"), "autosave.info");
                }
            }, interval * 20 * 60, interval * 20);
        }
    }

    @Override
    public void onDisable() {
        for (World w : getServer().getWorlds()) {
            w.setAutoSave(true);
        }
    }
}

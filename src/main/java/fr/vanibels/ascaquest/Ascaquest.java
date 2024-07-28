package fr.vanibels.ascaquest;

import fr.vanibels.ascaquest.Manager.RegisterManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ascaquest extends JavaPlugin {

    public static Ascaquest instance;

    @Override
    public void onEnable() {
        instance = this;
        RegisterManager.Load();

    }

    @Override
    public void onDisable() {
        RegisterManager.Unload();
    }
}

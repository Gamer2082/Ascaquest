package fr.vanibels.ascaquest;

import fr.vanibels.ascaquest.Manager.RegisterManager;
import fr.vanibels.ascaquest.Quest.Quest;
import fr.vanibels.ascaquest.Quest.QuestState;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Ascaquest extends JavaPlugin {
    public static Ascaquest instance;
    public ArrayList<Quest> list = new ArrayList<Quest>();
    public QuestState state = QuestState.STARTING;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;
        RegisterManager.Load();

    }

    @Override
    public void onDisable() {
        RegisterManager.Unload();
    }

    public QuestState getState() {
        return state;
    }

    public void setState(QuestState state) {
        this.state = state;
    }
}

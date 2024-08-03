package fr.vanibels.ascaquest.Manager;

import fr.vanibels.ascaquest.Ascaquest;
import fr.vanibels.ascaquest.Command.QuestCommandExecutor;
import fr.vanibels.ascaquest.Command.QuestNPCCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CommandManager {
    public static Ascaquest ins = Ascaquest.instance;
    public static void load() {
        ins.getCommand("quest").setExecutor(new QuestCommandExecutor(ins));
        Bukkit.getLogger().info(ChatColor.GREEN + "Load quest command");
        ins.getCommand("qnpc").setExecutor(new QuestNPCCommandExecutor());
        Bukkit.getLogger().info(ChatColor.GREEN + "Load npc quest command");
    }
}

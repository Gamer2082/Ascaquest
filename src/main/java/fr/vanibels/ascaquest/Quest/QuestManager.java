package fr.vanibels.ascaquest.Quest;

import fr.vanibels.ascaquest.Ascaquest;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QuestManager {
    public Ascaquest main;
    public Player player;
    public Quest quest;
    public QuestManager(Ascaquest main, Player player, Quest quest){
        this.main = main;
        this.player = player;
        this.quest = quest;
    }

    public void CreateQuest(){
        main.list.add(quest);
    }

    public void RemoveQuest(){
        main.list.remove(quest);
    }

    public void StartQuest(){
        quest.setState(QuestState.STARTING);
        player.sendMessage(ChatColor.GREEN + "Début de la quête" + quest.getTitle());
        player.sendMessage(ChatColor.GRAY + "--------------------");
        player.sendMessage(ChatColor.GRAY + quest.getDescription());
        player.sendMessage(ChatColor.GRAY + "--------------------");
    }

    public void FinishQuest(){
        quest.setState(QuestState.FINISH);
        player.sendMessage(ChatColor.RED + "Fin de la quête" + quest.getTitle());
        player.sendMessage(ChatColor.GRAY + "Félicitation pour votre récompense ...");
        RemoveQuest();
    };
    public String getTitle(){
        return quest.getTitle();
    }
}

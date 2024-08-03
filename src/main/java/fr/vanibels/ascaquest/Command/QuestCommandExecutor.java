package fr.vanibels.ascaquest.Command;

import fr.vanibels.ascaquest.Ascaquest;
import fr.vanibels.ascaquest.Quest.Quest;
import fr.vanibels.ascaquest.Quest.QuestManager;
import fr.vanibels.ascaquest.Quest.QuestState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class QuestCommandExecutor implements CommandExecutor {
    private final Ascaquest main;

    public QuestCommandExecutor(Ascaquest ins) {
        this.main = ins;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Cette commande ne peut être exécutée que par un joueur.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "/quest create <nom>" + ChatColor.GRAY + " - Crée une nouvelle quête.");
            player.sendMessage(ChatColor.YELLOW + "/quest remove <nom>" + ChatColor.GRAY + " - Supprime une quête.");
            player.sendMessage(ChatColor.YELLOW + "/quest list" + ChatColor.GRAY + " - Liste toutes les quêtes.");
            player.sendMessage(ChatColor.YELLOW + "/quest start <nom>" + ChatColor.GRAY + " - Démarre une quête.");
            player.sendMessage(ChatColor.YELLOW + "/quest progress" + ChatColor.GRAY + " - Affiche les progrès de la quête pour le joueur spécifié ou pour vous.");
            player.sendMessage(ChatColor.YELLOW + "/quest stat {player}" + ChatColor.GRAY + " - Affiche les statistiques de la quête pour le joueur spécifié.");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                player.sendMessage(ChatColor.YELLOW + "/quest create  <nom> <description>" + ChatColor.GRAY + " - Crée une nouvelle quête.");
                player.sendMessage(ChatColor.YELLOW + "/quest remove <nom>" + ChatColor.GRAY + " - Supprime une quête.");
                player.sendMessage(ChatColor.YELLOW + "/quest list" + ChatColor.GRAY + " - Liste toutes les quêtes.");
                player.sendMessage(ChatColor.YELLOW + "/quest start <nom>" + ChatColor.GRAY + " - Démarre une quête.");
                player.sendMessage(ChatColor.YELLOW + "/quest progress" + ChatColor.GRAY + " - Affiche les progrès de la quête pour le joueur spécifié ou pour vous.");
                player.sendMessage(ChatColor.YELLOW + "/quest stat {player}" + ChatColor.GRAY + " - Affiche les statistiques de la quête pour le joueur spécifié.");
                return false;
            case "create":
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "Usage : /quest create <nom> <description>");
                    return false;
                }
                QuestManager quest = new QuestManager(Ascaquest.instance, player, new Quest("test","test",1, QuestState.STARTING));
                quest.CreateQuest();
                player.sendMessage(main.getConfig().getString("messages.create").toString().replace("{player}", player.getDisplayName()).replace("{quest}", quest.getTitle()));
                break;

            case "remove":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage : /quest remove <nom>");
                    return false;
                }
                String questToRemove = args[1];
                // Code pour supprimer une quête
                player.sendMessage(ChatColor.GREEN + "Quête supprimée : " + ChatColor.AQUA + questToRemove);
                break;

            case "list":
                // Code pour lister les quêtes
                player.sendMessage(ChatColor.GREEN + "Liste des quêtes : " + ChatColor.AQUA + "...");
                break;

            case "start":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage : /quest start <nom>");
                    return false;
                }
                String questToStart = args[1];
                // Code pour démarrer une quête
                player.sendMessage(ChatColor.GREEN + "Quête démarrée : " + ChatColor.AQUA + questToStart);
                break;

            case "progress":
                String targetPlayer = args.length > 1 ? args[1] : player.getName();
                // Code pour afficher les progrès de la quête
                player.sendMessage(ChatColor.GREEN + "Progrès de la quête pour " + ChatColor.AQUA + targetPlayer + ChatColor.GREEN + " : ...");
                break;

            case "stat":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage : /quest stat <player>");
                    return false;
                }
                String statPlayer = args[1];
                // Code pour afficher les statistiques de la quête
                player.sendMessage(ChatColor.GREEN + "Statistiques de la quête pour " + ChatColor.AQUA + statPlayer + ChatColor.GREEN + " : ...");
                break;

            default:
                player.sendMessage(ChatColor.RED + "Commande inconnue.");
                break;
        }
        return true;
    }
}

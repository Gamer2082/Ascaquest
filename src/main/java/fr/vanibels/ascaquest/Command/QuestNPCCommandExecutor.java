package fr.vanibels.ascaquest.Command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QuestNPCCommandExecutor implements CommandExecutor {
    private final Set<String> npcNames = new HashSet<>();
    private final Map<String, Location> npcLocations = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Cette commande ne peut être exécutée que par un joueur.");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            showHelp(player);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                showHelp(player);
                break;

            case "create":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage : /qnpc create <nom>");
                    return false;
                }
                createNPC(player, args[1]);
                break;

            case "remove":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage : /qnpc remove <nom>");
                    return false;
                }
                removeNPC(player, args[1]);
                break;

            case "list":
                listNPCs(player);
                break;

            case "tp":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage : /qnpc tp <nom>");
                    return false;
                }
                teleportToNPC(player, args[1]);
                break;

            case "htp":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage : /qnpc htp <nom>");
                    return false;
                }
                teleportNPCToPlayer(player, args[1]);
                break;

            case "edit":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage : /qnpc edit <nom>");
                    return false;
                }
                editNPC(player, args[1]);
                break;

            default:
                player.sendMessage(ChatColor.RED + "Commande inconnue.");
                break;
        }
        return true;
    }

    private void showHelp(Player player) {
        player.sendMessage(ChatColor.YELLOW + "/qnpc create <nom>" + ChatColor.GRAY + " - Crée un PNJ pour la quête spécifiée.");
        player.sendMessage(ChatColor.YELLOW + "/qnpc remove <nom>" + ChatColor.GRAY + " - Supprime un PNJ.");
        player.sendMessage(ChatColor.YELLOW + "/qnpc list" + ChatColor.GRAY + " - Liste de tous les PNJs.");
        player.sendMessage(ChatColor.YELLOW + "/qnpc tp <nom>" + ChatColor.GRAY + " - Téléporte le joueur au PNJ spécifié.");
        player.sendMessage(ChatColor.YELLOW + "/qnpc htp <nom>" + ChatColor.GRAY + " - Téléporte le PNJ spécifié à vous.");
        player.sendMessage(ChatColor.YELLOW + "/qnpc edit <nom>" + ChatColor.GRAY + " - Modifie les propriétés du PNJ.");
    }

    private void createNPC(Player player, String npcName) {
        if (npcNames.contains(npcName)) {
            player.sendMessage(ChatColor.RED + "Un PNJ avec ce nom existe déjà.");
            return;
        }
        npcNames.add(npcName);
        Location location = player.getLocation();
        // Crée un PNJ de type Villager avec un nom custom
        Entity npc = player.getWorld().spawnEntity(location, EntityType.PLAYER);
        if (npc instanceof LivingEntity) {
            ((LivingEntity) npc).setCustomName(ChatColor.GREEN + npcName);
            ((LivingEntity) npc).setCustomNameVisible(true);
            ((LivingEntity) npc).setInvulnerable(true); // PNJ invincible
            npcLocations.put(npcName, location);
            player.sendMessage(ChatColor.GREEN + "PNJ créé : " + ChatColor.AQUA + npcName);
        } else {
            player.sendMessage(ChatColor.RED + "Impossible de créer le PNJ.");
        }
    }

    private void removeNPC(Player player, String npcName) {
        if (!npcNames.contains(npcName)) {
            player.sendMessage(ChatColor.RED + "Aucun PNJ trouvé avec ce nom.");
            return;
        }
        npcNames.remove(npcName);
        Location location = npcLocations.get(npcName);
        if (location != null) {
            // Supprimer le PNJ (recherche par location ou autres moyens)
            Entity npc = location.getWorld().getNearbyEntities(location, 1, 1, 1).stream()
                    .filter(entity -> entity instanceof LivingEntity && ((LivingEntity) entity).getCustomName() != null
                            && ((LivingEntity) entity).getCustomName().equals(ChatColor.GREEN + npcName))
                    .findFirst().orElse(null);

            if (npc != null) {
                npc.remove();
                player.sendMessage(ChatColor.GREEN + "PNJ supprimé : " + ChatColor.AQUA + npcName);
            } else {
                player.sendMessage(ChatColor.RED + "PNJ introuvable.");
            }
        }
    }

    private void listNPCs(Player player) {
        if (npcNames.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Aucun PNJ n'a été créé.");
        } else {
            player.sendMessage(ChatColor.GREEN + "Liste des PNJs : " + ChatColor.AQUA + String.join(", ", npcNames));
        }
    }

    private void teleportToNPC(Player player, String npcName) {
        if (!npcNames.contains(npcName)) {
            player.sendMessage(ChatColor.RED + "Aucun PNJ trouvé avec ce nom.");
            return;
        }
        Location location = npcLocations.get(npcName);
        if (location != null) {
            player.teleport(location);
            player.sendMessage(ChatColor.GREEN + "Téléportation au PNJ : " + ChatColor.AQUA + npcName);
        } else {
            player.sendMessage(ChatColor.RED + "Emplacement du PNJ introuvable.");
        }
    }

    private void teleportNPCToPlayer(Player player, String npcName) {
        if (!npcNames.contains(npcName)) {
            player.sendMessage(ChatColor.RED + "Aucun PNJ trouvé avec ce nom.");
            return;
        }
        Location npcLocation = npcLocations.get(npcName);
        if (npcLocation != null) {
            Entity npc = player.getWorld().getNearbyEntities(player.getLocation(), 50, 50, 50).stream()
                    .filter(entity -> entity instanceof LivingEntity && ((LivingEntity) entity).getCustomName() != null
                            && ((LivingEntity) entity).getCustomName().equals(ChatColor.GREEN + npcName))
                    .findFirst().orElse(null);

            if (npc != null) {
                npc.teleport(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Téléportation du PNJ vers vous : " + ChatColor.AQUA + npcName);
            } else {
                player.sendMessage(ChatColor.RED + "PNJ introuvable.");
            }
        }
    }

    private void editNPC(Player player, String npcName) {
        if (!npcNames.contains(npcName)) {
            player.sendMessage(ChatColor.RED + "Aucun PNJ trouvé avec ce nom.");
            return;
        }
        // Code pour modifier les propriétés du PNJ (à ajouter)
        player.sendMessage(ChatColor.GREEN + "Modification du PNJ : " + ChatColor.AQUA + npcName);
    }
}

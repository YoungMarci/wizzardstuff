package me.youngmarci.wizardstuff.spells;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.HashMap;
import java.util.Map;
public class spellBook implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> iceSpellEq;
    public Map<String, String> iceSpellUUID;

    public Map<String, String> teleportSpellEq;
    public Map<String, String> teleportSpellUUID;

    public Map<String, String> decaySpellEq;
    public Map<String, String> decaySpellUUID;

    public Map<String, String> manaStealSpellEq;
    public Map<String, String> manaStealSpellUUID;

    public Map<String, String> switchPlaceSpellEq;
    public Map<String, String> switchPlaceSpellUUID;

    public HashMap<String, Integer> manaCount;

    public spellBook(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;

        this.iceSpellEq = w.iceSpellEq;
        this.iceSpellUUID = w.iceSpellUUID;

        this.teleportSpellEq = w.teleportSpellEq;
        this.teleportSpellUUID = w.teleportSpellUUID;

        this.decaySpellEq = w.decaySpellEq;
        this.decaySpellUUID = w.decaySpellUUID;

        this.manaStealSpellEq = w.manaStealSpellEq;
        this.manaStealSpellUUID = w.manaStealSpellUUID;

        this.switchPlaceSpellEq = w.switchPlaceSpellEq;
        this.switchPlaceSpellUUID = w.switchPlaceSpellUUID;

        this.manaCount = w.manaCount;
    }

    @EventHandler
    public void spellWriting(PlayerEditBookEvent event) {
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        String playerName = player.getName();
        BookMeta bookmeta = event.getNewBookMeta();

        if (!iceSpellEq.containsKey(playerUUID) && !teleportSpellEq.containsKey(playerUUID) && !decaySpellEq.containsKey(playerUUID) && !manaStealSpellEq.containsKey(playerUUID) && !switchPlaceSpellEq.containsKey(playerUUID)) { // If any spell is not equipped
            if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("SpellBook")) { // Check for SpellBook in main hand
                String pageContent = bookmeta.getPage(1);

                if (pageContent.contains("ice")) { // Check if content in book matches spell name
                    wizzStuff.addIceSpellEq(playerUUID, playerName);

                    player.sendMessage("You equipped ice spell!");
                } else if (pageContent.contains("tp")) {
                    wizzStuff.addTeleportSpellEq(playerUUID, playerName);

                    player.sendMessage("You equipped teleportation spell!");
                } else if (pageContent.contains("dcy")) {
                    wizzStuff.addDecaySpellEq(playerUUID, playerName);

                    player.sendMessage("You equipped decay spell!");
                } else if (pageContent.contains("mstl")) {
                    wizzStuff.addManaStealSpellEq(playerUUID, playerName);

                    player.sendMessage("You equipped mana steal spell!");
                } else if (pageContent.contains("swtch")) {
                    wizzStuff.addSwitchPlaceSpellEq(playerUUID, playerName);

                    player.sendMessage("You equipped place switch spell!");
                }
            }
        }
    }
}

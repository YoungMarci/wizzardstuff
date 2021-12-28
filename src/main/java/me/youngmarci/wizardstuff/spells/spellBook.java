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

    public HashMap<String, Integer> manaCount;

    public spellBook(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.iceSpellEq = w.iceSpellEq;
        this.iceSpellUUID = w.iceSpellUUID;

        this.manaCount = w.manaCount;
    }

    @EventHandler
    public void spellWriting(PlayerEditBookEvent event) {
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        String playerName = player.getName();
        BookMeta bookmeta = event.getNewBookMeta();

        if (!iceSpellEq.containsKey(playerUUID)) { // If any spell is not equipped
            if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("SpellBook")) { // Check for SpellBook in main hand
                String pageContent = bookmeta.getPage(1);

                if (pageContent.contains("ice")) { // Check if content in book matches spell name
                    wizzStuff.addIceSpellEq(playerUUID, playerName);

                    player.sendMessage("You equiped ice spell!");
                }
            }
        }
    }
}

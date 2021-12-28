package me.youngmarci.wizardstuff.spells;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class castSpell implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> iceSpellEq;
    public Map<String, String> iceSpellUUID;

    public HashMap<String, Integer> manaCount;

    public castSpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.iceSpellEq = w.iceSpellEq;
        this.iceSpellUUID = w.iceSpellUUID;

        this.manaCount = w.manaCount;
    }

    @EventHandler
    public void wandCastSpell(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        String playerUUID = player.getUniqueId().toString();
        String playerName = player.getName();
        Integer mana = manaCount.get(playerUUID);
        Action action = e.getAction();
        ItemStack item = e.getItem();


        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item != null && player.getInventory().getItemInMainHand().getType() == Material.STICK) { // If player clicks with wand
                if (mana >= 20) {
                    if (iceSpellEq.containsKey((player.getUniqueId().toString()))) {

                        Integer iceSpellCost = 20;
                        Integer manaLeft = mana - iceSpellCost;

                        wizzStuff.rmvManaPoints(playerUUID, mana);
                        wizzStuff.addManaPoints(playerUUID, manaLeft);

                        wizzStuff.rmvIceSpellEq(playerUUID, playerName); // Remove equipment of spell from HashMap (iceSpellUUID)
                        String projectileUUID = player.launchProjectile(Snowball.class).getUniqueId().toString(); // Launch a snowball & get its UUID
                        wizzStuff.addIceSpellUUID(projectileUUID, playerName); // Put snowballUUID and PlayerName to HashMap (iceSpellUUID)

                        player.sendMessage("You casted an ice spell! Remaining mana: " + manaLeft);
                    }
                }
            }
        }
    }
}

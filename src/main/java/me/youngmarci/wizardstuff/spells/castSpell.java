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

    public Integer iceSpellCost;
    public Map<String, String> iceSpellEq;
    public Map<String, String> iceSpellUUID;

    public Integer teleportationSpellCost;
    public Map<String, String> teleportSpellEq;
    public Map<String, String> teleportSpellUUID;

    public Integer decaySpellCost;
    public Map<String, String> decaySpellEq;
    public Map<String, String> decaySpellUUID;

    public Integer manaStealSpellCost;
    public Map<String, String> manaStealSpellEq;
    public Map<String, String> manaStealSpellUUID;

    public Integer switchPlaceSpellCost;
    public Map<String, String> switchPlaceSpellEq;
    public Map<String, String> switchPlaceSpellUUID;

    public HashMap<String, Integer> manaCount;

    public castSpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;

        this.iceSpellCost = w.iceSpellCost;
        this.iceSpellEq = w.iceSpellEq;
        this.iceSpellUUID = w.iceSpellUUID;

        this.teleportationSpellCost = w.teleportationSpellCost;
        this.teleportSpellEq = w.teleportSpellEq;
        this.teleportSpellUUID = w.teleportSpellUUID;

        this.decaySpellCost = w.decaySpellCost;
        this.decaySpellEq = w.decaySpellEq;
        this.decaySpellUUID = w.decaySpellUUID;

        this.manaStealSpellCost = w.manaStealSpellCost;
        this.manaStealSpellEq = w.manaStealSpellEq;
        this.manaStealSpellUUID = w.manaStealSpellUUID;

        this.switchPlaceSpellCost = w.switchPlaceSpellCost;
        this.switchPlaceSpellEq = w.switchPlaceSpellEq;
        this.switchPlaceSpellUUID = w.switchPlaceSpellUUID;


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

                if (iceSpellEq.containsKey(playerUUID)) {
                    if (mana >= iceSpellCost) {
                        Integer manaLeft = mana - iceSpellCost;

                        wizzStuff.rmvManaPoints(playerUUID, mana);
                        wizzStuff.addManaPoints(playerUUID, manaLeft);

                        wizzStuff.rmvIceSpellEq(playerUUID, playerName); // Remove equipment of spell from HashMap (iceSpellUUID)

                        String projectileUUID = player.launchProjectile(Snowball.class).getUniqueId().toString(); // Launch a snowball & get its UUID

                        wizzStuff.addIceSpellUUID(projectileUUID, playerName); // Put snowballUUID and PlayerName to HashMap (iceSpellUUID)
                        player.sendMessage("You casted ice spell! Remaining mana: " + manaLeft);
                    }
                } else if (teleportSpellEq.containsKey(playerUUID)) {
                    if (mana >= teleportationSpellCost) {
                        Integer manaLeft = mana - teleportationSpellCost;

                        wizzStuff.rmvManaPoints(playerUUID, mana);
                        wizzStuff.addManaPoints(playerUUID, manaLeft);

                        wizzStuff.rmvTeleportSpellEq(playerUUID, playerName); // Remove equipment of spell from HashMap (teleportationSpellUUID)

                        String projectileUUID = player.launchProjectile(Snowball.class).getUniqueId().toString(); // Launch a snowball & get its UUID

                        wizzStuff.addTeleportSpellUUID(projectileUUID, playerName); // Put snowballUUID and PlayerName to HashMap (teleportationSpellUUID)
                        player.sendMessage("You casted a teleportation spell! Remaining mana: " + manaLeft);
                    }
                } else if (decaySpellEq.containsKey(playerUUID)) {
                    if (mana >= decaySpellCost) {
                        Integer manaLeft = mana - decaySpellCost;

                        wizzStuff.rmvManaPoints(playerUUID, mana);
                        wizzStuff.addManaPoints(playerUUID, manaLeft);

                        wizzStuff.rmvDecaySpellEq(playerUUID, playerName);

                        String projectileUUID = player.launchProjectile(Snowball.class).getUniqueId().toString(); // Launch a snowball & get its UUID

                        wizzStuff.addDecaySpellUUID(projectileUUID, playerName);
                        player.sendMessage("You casted a decay spell! Remaining mana: " + manaLeft);
                    }
                } else if (manaStealSpellEq.containsKey(playerUUID)) {
                    if (mana >= manaStealSpellCost) {
                        Integer manaLeft = mana - manaStealSpellCost;

                        wizzStuff.rmvManaPoints(playerUUID, mana);
                        wizzStuff.addManaPoints(playerUUID, manaLeft);

                        wizzStuff.rmvManaStealSpellEq(playerUUID, playerName);

                        String projectileUUID = player.launchProjectile(Snowball.class).getUniqueId().toString(); // Launch a snowball & get its UUID

                        wizzStuff.addManaStealSpellUUID(projectileUUID, playerName);
                        player.sendMessage("You casted a manasteal spell! Remaining mana: " + manaLeft);
                    }
                } else if (switchPlaceSpellEq.containsKey(playerUUID)) {
                    if (mana >= switchPlaceSpellCost) {
                        Integer manaLeft = mana - switchPlaceSpellCost;

                        wizzStuff.rmvManaPoints(playerUUID, mana);
                        wizzStuff.addManaPoints(playerUUID, manaLeft);

                        wizzStuff.rmvSwitchPlaceSpellEq(playerUUID, playerName);

                        String projectileUUID = player.launchProjectile(Snowball.class).getUniqueId().toString(); // Launch a snowball & get its UUID

                        wizzStuff.addSwitchPlaceSpellUUID(projectileUUID, playerName);
                        player.sendMessage("You casted a switch place spell! Remaining mana: " + manaLeft);

                    }
                }
            }
        }
    }
}

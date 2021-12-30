package me.youngmarci.wizardstuff.mana;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class manaPoints implements Listener {

    Wizardstuff wizzStuff;

    public HashMap<String, Integer> manaCount;

    public manaPoints(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;

        this.manaCount = w.manaCount;
    }

    public int maxMana = 100;

    @EventHandler
    public void setManaOnJoin(PlayerJoinEvent event) { // Adds player to HashMap (manaCount) with default mana count (maxMana) and add 5 mana every 2 seconds
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();

        if (!manaCount.containsKey(playerUUID)) {
            wizzStuff.addManaPoints(playerUUID, maxMana);
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(wizzStuff, new Runnable() {
            public void run() {
                if(manaCount.containsKey(playerUUID)) {
                    Integer mana = manaCount.get(playerUUID);
                    if (95 >= mana) {
                        wizzStuff.rmvManaPoints(playerUUID, mana);
                        wizzStuff.addManaPoints(playerUUID, mana + 5);
                        player.setLevel(mana); // Display mana as XP level
                        player.setExp((float) mana / 100); // Display mana at XP Bar
                        player.sendMessage("Mana recharging... Current mana level: " + mana);
                    } else if (mana == 100 || mana > 100) {
                        player.setLevel(mana); // Display mana as XP level
                        player.setExp(1); // Display mana at XP Bar
                    }
                }
            }
        },0, 40);
    }

    @EventHandler
    public void delManaOnQuit(PlayerQuitEvent event) { // Removes player from HashMap (manaCount)
        Player player = event.getPlayer();
        String playerUUID = player.getUniqueId().toString();

        if (manaCount.containsKey(playerUUID)) {
            Integer mana = manaCount.get(playerUUID);
            wizzStuff.rmvManaPoints(playerUUID, mana);
        }
    }
}

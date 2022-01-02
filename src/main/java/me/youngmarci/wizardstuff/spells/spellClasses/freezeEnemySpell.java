package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;

public class freezeEnemySpell implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> freezeEnemySpellUUID;

    public freezeEnemySpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.freezeEnemySpellUUID = w.freezeEnemySpellUUID;
    }

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) {
            String snowballUUID = event.getDamager().getUniqueId().toString();
            Player damagedPlayer = ((Player) event.getEntity()).getPlayer();
            Location damagedPlayerLocation = damagedPlayer.getLocation();

            if (freezeEnemySpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(freezeEnemySpellUUID.get(snowballUUID));

                for (double y = -2; y <= 2; y++) { //create outer cube with ice
                    for (double x = -2; x <= 2; x++) {
                        for (double z = -2; z <= 2; z++) {
                            damagedPlayerLocation.add(x, y, z);
                            damagedPlayerLocation.add(0, 1, 0);
                            if (damagedPlayerLocation.getBlock().getType() == Material.AIR) {
                                damagedPlayerLocation.getBlock().setType(Material.ICE);
                            }
                            damagedPlayerLocation.subtract(0, 1, 0);
                            damagedPlayerLocation.subtract(x, y, z);
                        }
                    }
                }

                for (double y = -1; y <= 1; y++) { //create air inner cube
                    for (double x = -1; x <= 1; x++) {
                        for (double z = -1; z <= 1; z++) {
                            damagedPlayerLocation.add(x, y, z);
                            damagedPlayerLocation.add(0, 1, 0);
                            if (damagedPlayerLocation.getBlock().getType() == Material.ICE) {
                                damagedPlayerLocation.getBlock().setType(Material.AIR);
                            }
                            damagedPlayerLocation.subtract(0, 1, 0);
                            damagedPlayerLocation.subtract(x, y, z);
                        }
                    }
                }

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(wizzStuff, new Runnable() { //replace created structure back to air
                    public void run() {
                        for (double y = -2; y <= 2; y++) {
                            for (double x = -2; x <= 2; x++) {
                                for (double z = -2; z <= 2; z++) {
                                    damagedPlayerLocation.add(x, y, z);
                                    damagedPlayerLocation.add(0, 1, 0);
                                    if (damagedPlayerLocation.getBlock().getType() == Material.ICE) {
                                        damagedPlayerLocation.getBlock().setType(Material.AIR);
                                    }
                                    damagedPlayerLocation.subtract(0, 1, 0);
                                    damagedPlayerLocation.subtract(x, y, z);
                                }
                            }
                        }
                    }
                }, 200);

                shooterPlayer.sendMessage("You hit " + damagedPlayer.getName() + " with a enemy freeze spell!");
            }
        }
    }
}
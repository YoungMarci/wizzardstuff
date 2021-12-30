package me.youngmarci.wizardstuff.spells;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;

public class spellEffect implements Listener {

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

    public spellEffect(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
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
    public void entityDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) { // Check if snowball hits Player
            String snowballUUID = event.getDamager().getUniqueId().toString(); //get the UUID of the snowball
            Player damagedPlayer = ((Player) event.getEntity()).getPlayer(); //get damaged player
            Location damagedPlayerLocation = damagedPlayer.getLocation(); //get location of damaged player

            if(iceSpellUUID.containsKey(snowballUUID)) { // Check if snowball is in iceSpellUUID HashMap
                Player shooterPlayer = Bukkit.getPlayer(iceSpellUUID.get(snowballUUID)); //get shooter

                for (double y = -2; y <= 2; y++) { //create outer cube with ice
                    for (double x = -2; x <= 2; x++) {
                        for (double z = -2; z <= 2; z++) {
                            damagedPlayerLocation.add(x,y,z);
                            damagedPlayerLocation.add(0,1,0);
                            if (damagedPlayerLocation.getBlock().getType() == Material.AIR) {
                                damagedPlayerLocation.getBlock().setType(Material.ICE);
                            }
                            damagedPlayerLocation.subtract(0,1,0);
                            damagedPlayerLocation.subtract(x,y,z);
                        }
                    }
                }

                for (double y = -1; y <= 1; y++) { //create air inner cube
                    for (double x = -1; x <= 1; x++) {
                        for (double z = -1; z <= 1; z++) {
                            damagedPlayerLocation.add(x,y,z);
                            damagedPlayerLocation.add(0,1,0);
                            if (damagedPlayerLocation.getBlock().getType() == Material.ICE) {
                                damagedPlayerLocation.getBlock().setType(Material.AIR);
                            }
                            damagedPlayerLocation.subtract(0,1,0);
                            damagedPlayerLocation.subtract(x,y,z);
                        }
                    }
                }

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(wizzStuff, new Runnable() { //replace created structure back to air
                    public void run() {
                        for (double y = -2; y <= 2; y++) {
                            for (double x = -2; x <= 2; x++) {
                                for (double z = -2; z <= 2; z++) {
                                    damagedPlayerLocation.add(x,y,z);
                                    damagedPlayerLocation.add(0,1,0);
                                    if (damagedPlayerLocation.getBlock().getType() == Material.ICE) {
                                        damagedPlayerLocation.getBlock().setType(Material.AIR);
                                    }
                                    damagedPlayerLocation.subtract(0,1,0);
                                    damagedPlayerLocation.subtract(x,y,z);
                                }
                            }
                        }
                    }
                }, 200);

                shooterPlayer.sendMessage("You hit " + damagedPlayer.getName() + " with a ice spell!");
            } else if (manaStealSpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(manaStealSpellUUID.get(snowballUUID)); //get shooter

                String shooterUUID = shooterPlayer.getUniqueId().toString();
                String damagedUUID = damagedPlayer.getUniqueId().toString();

                Integer shooterPlMana = manaCount.get(shooterPlayer.getUniqueId().toString());
                Integer damagedPlMana = manaCount.get(damagedPlayer.getUniqueId().toString());

                wizzStuff.rmvManaPoints(damagedUUID, damagedPlMana);
                wizzStuff.addManaPoints(damagedUUID,0);

                Integer manaGained = damagedPlMana + shooterPlMana + 100;

                wizzStuff.rmvManaPoints(shooterUUID, shooterPlMana);
                wizzStuff.addManaPoints(shooterUUID, manaGained);

                shooterPlayer.sendMessage("You hit " + damagedPlayer.getName() + " with a mana steal spell! Mana gained: " + manaGained);
            } else if (switchPlaceSpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(switchPlaceSpellUUID.get(snowballUUID)); //get shooter

                Location shooterLocation = shooterPlayer.getLocation();

                shooterPlayer.teleport(damagedPlayerLocation);
                damagedPlayer.teleport(shooterLocation);

                shooterPlayer.sendMessage("You hit " + damagedPlayer.getName() + " with a place switch spell! Places switched!");
            }
        }
    }

    @EventHandler
    public void projectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile instanceof Snowball) {
            String snowballUUID = projectile.getUniqueId().toString(); //get the UUID of the snowball

            if (teleportSpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(teleportSpellUUID.get(snowballUUID));
                Location placeToTeleport = event.getHitBlock().getLocation().add(0,1,0);

                shooterPlayer.teleport(placeToTeleport);

                shooterPlayer.sendMessage("You used teleportation spell!");
            } else if (decaySpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(decaySpellUUID.get(snowballUUID));
                Location placeToTeleport = event.getHitBlock().getLocation().add(0,1,0);
                Location oldLocation = shooterPlayer.getLocation();

                shooterPlayer.teleport(placeToTeleport);
                shooterPlayer.sendMessage("You used decay spell! You have 5 seconds from now to decay your enemy!");

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(wizzStuff, new Runnable() { //get player back to old position
                    public void run() {
                        shooterPlayer.teleport(oldLocation);
                    }
                }, 200);

                shooterPlayer.sendMessage("Decay spell ended, you were teleported back to old position!");
            }
        }
    }
}

package me.youngmarci.wizardstuff.spells;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    public Map<String, String> meteorRainSpellEq;
    public Map<String, String> meteorRainSpellUUID;
    public Map<String, String> meteorslUUID = new HashMap<String, String>();

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

        this.meteorRainSpellEq = w.meteorRainSpellEq;
        this.meteorRainSpellUUID = w.meteorRainSpellUUID;

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
            } else if (meteorRainSpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(meteorRainSpellUUID.get(snowballUUID));

                if (event.getHitBlock() == null) {
                    Location hitEntity = event.getHitEntity().getLocation();

                    for (double x = -6; x <= 6; x++) { //Get Random Location 5 Times
                        for (double z = -6; z <= 6; z++) {
                            hitEntity.add(x,0,z);
                            hitEntity.setY(120);

                            //Spawn falling block if randint > 50
                            int upper = 20;
                            Random random = new Random();
                            int randomInt = random.nextInt(upper);
                            if (randomInt >= 18) {
                                FallingBlock block = hitEntity.getWorld().spawnFallingBlock(hitEntity, Material.COAL_BLOCK, (byte) 0);
                                block.setDropItem(false);
                                String fallingBlockUUID = block.getUniqueId().toString();
                                meteorslUUID.put(fallingBlockUUID, shooterPlayer.getName());
                            }
                            hitEntity.subtract(x,0,z);

                        }
                    }
                } else {
                    Location hitBlock = event.getHitBlock().getLocation();

                    for (double x = -6; x <= 6; x++) { //Get Random Location in radius
                        for (double z = -6; z <= 6; z++) {
                            hitBlock.add(x,0,z);
                            hitBlock.setY(120);

                            //Spawn falling block if randint > = 18
                            int upper = 20;
                            Random random = new Random();
                            int randomInt = random.nextInt(upper);
                            if (randomInt >= 18) {
                                // I want to make a delay between spawning this fallingblock (block) below
                                FallingBlock block = hitBlock.getWorld().spawnFallingBlock(hitBlock, Material.COAL_BLOCK, (byte) 0);
                                block.setDropItem(false);
                                String fallingBlockUUID = block.getUniqueId().toString();
                                meteorslUUID.put(fallingBlockUUID, shooterPlayer.getName());
                            }
                            hitBlock.subtract(x,0,z);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFallingBlockFall(EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.FALLING_BLOCK) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            String fallingBlockUUID = fallingBlock.getUniqueId().toString();
            Location fallingBlockLocation = fallingBlock.getLocation();

            if (meteorslUUID.containsKey(fallingBlockUUID)) {
                fallingBlockLocation.getBlock().setType(Material.AIR);
                fallingBlockLocation.getWorld().createExplosion(fallingBlockLocation.getBlockX(),fallingBlockLocation.getBlockY(), fallingBlockLocation.getBlockZ(),2, false, false);
                fallingBlock.remove();
                event.setCancelled(true);
            }
        }
    }
}

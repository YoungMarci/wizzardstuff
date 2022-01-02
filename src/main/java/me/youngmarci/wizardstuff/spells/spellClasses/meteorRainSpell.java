package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class meteorRainSpell implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> meteorRainSpellUUID;

    public Map<String, String> meteorslUUID = new HashMap<String, String>();

    public meteorRainSpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.meteorRainSpellUUID = w.meteorRainSpellUUID;
    }

    @EventHandler
    public void projectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile instanceof Snowball) {
            String snowballUUID = projectile.getUniqueId().toString(); //get the UUID of the snowball

            if (meteorRainSpellUUID.containsKey(snowballUUID)) {
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

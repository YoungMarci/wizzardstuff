package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;

public class decaySpell implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> decaySpellUUID;

    public decaySpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.decaySpellUUID = w.decaySpellUUID;
    }

    @EventHandler
    public void projectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile instanceof Snowball) {
            String snowballUUID = projectile.getUniqueId().toString(); //get the UUID of the snowball

            if (decaySpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(decaySpellUUID.get(snowballUUID));
                String playerUUID = shooterPlayer.getUniqueId().toString();
                Location placeToTeleport = event.getHitBlock().getLocation().add(0, 1, 0);
                Location oldLocation = shooterPlayer.getLocation();

                shooterPlayer.teleport(placeToTeleport);
                shooterPlayer.sendMessage("You used decay spell! You have 5 seconds from now to decay your enemy!");


                new BukkitRunnable() {
                    final ArrayList<Location> locations = getCircle(oldLocation.add(0,1,0), 0.5, 10);
                    Integer x = 1;
                    public void run() {
                        if (x == 10) {
                            this.cancel();
                        }
                        x++;
                        for(int i = 0; locations.size() > i; i++) {
                            Location loc = locations.get(i);
                            oldLocation.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0 ,0 ,0);
                        }
                    }
                }.runTaskTimer(wizzStuff, 0, 20);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(wizzStuff, new Runnable() { //get player back to old position
                    public void run() {
                        shooterPlayer.teleport(oldLocation);
                        shooterPlayer.sendMessage("Decay spell ended, you were teleported back to old position!");
                    }
                }, 200);
            }
        }
    }

    public ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for(int i = 0;i < amount; i++)
        {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }
}

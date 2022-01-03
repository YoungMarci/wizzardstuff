package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;

public class teleportSpell implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> teleportSpellUUID;

    public teleportSpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.teleportSpellUUID = w.teleportSpellUUID;
    }

    @EventHandler
    public void projectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile instanceof Snowball) {
            String snowballUUID = projectile.getUniqueId().toString();

            if (teleportSpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(teleportSpellUUID.get(snowballUUID));
                Location placeToTeleport = event.getHitBlock().getLocation().add(0, 1, 0);

                new BukkitRunnable() {
                    final ArrayList<Location> locations = getCircle(placeToTeleport.add(0,1,0), 0.5, 10);
                    Integer x = 1;
                    public void run() {
                        if (x == 5) { // After 5 seconds tp to place
                            shooterPlayer.teleport(placeToTeleport);
                            this.cancel();
                        }
                        x++;
                        for(int i = 0; locations.size() > i; i++) {
                            Location loc = locations.get(i);
                            placeToTeleport.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0 ,0 ,0);
                        }
                    }
                }.runTaskTimer(wizzStuff, 0, 20);



                shooterPlayer.sendMessage("You used teleport spell! You will be teleported in 5 seconds!");
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

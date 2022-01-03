package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class circleOfFireSpell implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> circleOfFireSpellUUID;

    public circleOfFireSpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.circleOfFireSpellUUID = w.circleOfFireSpellUUID;
    }

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) {
            String snowballUUID = event.getDamager().getUniqueId().toString();
            Player damagedPlayer = ((Player) event.getEntity()).getPlayer();
            Location damagedPlayerLocation = damagedPlayer.getLocation();

            if (circleOfFireSpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(circleOfFireSpellUUID.get(snowballUUID));

                new BukkitRunnable() {
                    final ArrayList<Location> locations = getCircle(damagedPlayerLocation.add(0,0.5,0), 2, 25);
                    Integer w = 1;
                    public void run() {
                        if (w == 10) {
                            this.cancel();
                        }
                        w++;
                        for(int i = 0; locations.size() > i; i++) {
                            Location loc = locations.get(i);
                            damagedPlayerLocation.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0 ,0 ,0);
                        }
                        List<Entity> nearbyEntites = (List<Entity>) damagedPlayerLocation.getWorld().getNearbyEntities(damagedPlayerLocation, 2, 1, 2);

                        for (int v = 0; nearbyEntites.size() > v; v++) {
                            if (nearbyEntites.get(v) instanceof Player) {
                                Player playertoFire = (Player) nearbyEntites.get(v);
                                if (shooterPlayer != playertoFire) {
                                    playertoFire.setFireTicks(20);
                                } else {
                                    shooterPlayer.getWorld().spawnParticle(Particle.CLOUD ,shooterPlayer.getLocation().add(0, 2,0), 0, 0, 0, 0);
                                }
                            }
                        }
                    }
                }.runTaskTimer(wizzStuff, 0, 20);
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

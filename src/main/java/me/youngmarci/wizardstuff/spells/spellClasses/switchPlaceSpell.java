package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;

public class switchPlaceSpell implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> switchPlaceSpellUUID;

    public switchPlaceSpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.switchPlaceSpellUUID = w.switchPlaceSpellUUID;
    }

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) { // Check if snowball hits Player
            String snowballUUID = event.getDamager().getUniqueId().toString(); //get the UUID of the snowball
            Player damagedPlayer = ((Player) event.getEntity()).getPlayer(); //get damaged player
            Location damagedPlayerLocation = damagedPlayer.getLocation(); //get location of damaged player

            if (switchPlaceSpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(switchPlaceSpellUUID.get(snowballUUID)); //get shooter

                Location shooterLocation = shooterPlayer.getLocation();

                shooterPlayer.teleport(damagedPlayerLocation);
                damagedPlayer.teleport(shooterLocation);

                new BukkitRunnable() {
                    final ArrayList<Location> locations = getCircle(shooterLocation.add(0,1,0), 0.5, 10);
                    Integer x = 1;
                    public void run() {
                        if (x == 5) {
                            this.cancel();
                        }
                        x++;
                        for(int i = 0; locations.size() > i; i++) {
                            Location loc = locations.get(i);
                            shooterLocation.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0 ,0 ,0);
                        }
                    }
                }.runTaskTimer(wizzStuff, 0, 20);

                new BukkitRunnable() {
                    final ArrayList<Location> locations = getCircle(damagedPlayerLocation.add(0,1,0), 0.5, 10);
                    Integer x = 1;
                    public void run() {
                        if (x == 5) {
                            this.cancel();
                        }
                        x++;
                        for(int i = 0; locations.size() > i; i++) {
                            Location loc = locations.get(i);
                            damagedPlayerLocation.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0 ,0 ,0);
                        }
                    }
                }.runTaskTimer(wizzStuff, 0, 20);

                shooterPlayer.sendMessage("You hit " + damagedPlayer.getName() + " with a place switch spell! Places switched!");
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

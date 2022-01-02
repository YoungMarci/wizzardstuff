package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

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
                Location placeToTeleport = event.getHitBlock().getLocation().add(0, 1, 0);
                Location oldLocation = shooterPlayer.getLocation();

                shooterPlayer.teleport(placeToTeleport);
                shooterPlayer.sendMessage("You used decay spell! You have 5 seconds from now to decay your enemy!");

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(wizzStuff, new Runnable() { //get player back to old position
                    public void run() {
                        shooterPlayer.teleport(oldLocation);
                        shooterPlayer.sendMessage("Decay spell ended, you were teleported back to old position!");
                    }
                }, 200);
            }
        }
    }
}

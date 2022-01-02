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

                shooterPlayer.teleport(placeToTeleport);

                shooterPlayer.sendMessage("You used teleport spell!");
            }
        }
    }
}

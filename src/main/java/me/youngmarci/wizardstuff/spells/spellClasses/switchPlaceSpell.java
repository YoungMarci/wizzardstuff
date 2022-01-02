package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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

                shooterPlayer.sendMessage("You hit " + damagedPlayer.getName() + " with a place switch spell! Places switched!");
            }
        }
    }
}

package me.youngmarci.wizardstuff.spells.spellClasses;

import me.youngmarci.wizardstuff.Wizardstuff;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class manaStealSpell implements Listener {

    Wizardstuff wizzStuff;
    public Map<String, String> manaStealSpellUUID;

    public HashMap<String, Integer> manaCount;

    public manaStealSpell(Wizardstuff w) { // (Require instance of Wizardstuff) Accessing variables from main class (Wizardstuff)
        this.wizzStuff = w;
        this.manaStealSpellUUID = w.manaStealSpellUUID;

        this.manaCount = w.manaCount;
    }

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) {
            String snowballUUID = event.getDamager().getUniqueId().toString();
            Player damagedPlayer = ((Player) event.getEntity()).getPlayer();
            Location damagedPlayerLocation = damagedPlayer.getLocation();

            if (manaStealSpellUUID.containsKey(snowballUUID)) {
                Player shooterPlayer = Bukkit.getPlayer(manaStealSpellUUID.get(snowballUUID));

                String shooterUUID = shooterPlayer.getUniqueId().toString();
                String damagedUUID = damagedPlayer.getUniqueId().toString();

                Integer shooterPlMana = manaCount.get(shooterPlayer.getUniqueId().toString());
                Integer damagedPlMana = manaCount.get(damagedPlayer.getUniqueId().toString());

                wizzStuff.rmvManaPoints(damagedUUID, damagedPlMana);
                wizzStuff.addManaPoints(damagedUUID, 0);

                Integer manaGained = damagedPlMana + shooterPlMana + 100;

                wizzStuff.rmvManaPoints(shooterUUID, shooterPlMana);
                wizzStuff.addManaPoints(shooterUUID, manaGained);

                shooterPlayer.sendMessage("You hit " + damagedPlayer.getName() + " with a mana steal spell! Mana gained: " + manaGained);
            }
        }
    }
}

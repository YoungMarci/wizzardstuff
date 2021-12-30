package me.youngmarci.wizardstuff;
import me.youngmarci.wizardstuff.mana.manaPoints;
import me.youngmarci.wizardstuff.spells.castSpell;
import me.youngmarci.wizardstuff.spells.spellBook;
import me.youngmarci.wizardstuff.spells.spellEffect;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public final class Wizardstuff extends JavaPlugin implements Listener {

    // Letting access integers to other classes

    castSpell cstSpell;
    spellBook splBook;
    spellEffect splEffect;

    public Integer iceSpellCost = 20;
    public Map<String, String> iceSpellEq = new HashMap<String, String>();
    public Map<String, String> iceSpellUUID = new HashMap< String, String>();

    public Integer teleportationSpellCost = 50;
    public Map<String, String> teleportSpellEq = new HashMap<String, String>();
    public Map<String, String> teleportSpellUUID = new HashMap< String, String>();

    public Integer decaySpellCost = 30;
    public Map<String, String> decaySpellEq = new HashMap<String, String>();
    public Map<String, String> decaySpellUUID = new HashMap< String, String>();

    public Integer manaStealSpellCost = 100;
    public Map<String, String> manaStealSpellEq = new HashMap<String, String>();
    public Map<String, String> manaStealSpellUUID = new HashMap< String, String>();

    public Integer switchPlaceSpellCost = 100;
    public Map<String, String> switchPlaceSpellEq = new HashMap<String, String>();
    public Map<String, String> switchPlaceSpellUUID = new HashMap< String, String>();

    manaPoints mnaPoints;
    public HashMap<String, Integer> manaCount = new HashMap<String, Integer>();

    public Wizardstuff() { // Add reference to class that can access variables
        cstSpell = new castSpell(this);
        splBook = new spellBook(this);
        splEffect = new spellEffect(this);
        mnaPoints = new manaPoints(this);
    }

    // Ice Spell

    public void addIceSpellEq(String uuid, String name) {
        iceSpellEq.put(uuid,name);
    }

    public void rmvIceSpellEq(String uuid, String name) {
        iceSpellEq.remove(uuid,name);
    }

    public void addIceSpellUUID(String uuid, String name) {
        iceSpellUUID.put(uuid,name);
    }

    // Teleportation Spell

    public void addTeleportSpellEq(String uuid, String name) {
        teleportSpellEq.put(uuid,name);
    }

    public void rmvTeleportSpellEq(String uuid, String name) {
        teleportSpellEq.remove(uuid,name);
    }

    public void addTeleportSpellUUID(String uuid, String name) {
        teleportSpellUUID.put(uuid,name);
    }

    // Decay Spell

    public void addDecaySpellEq(String uuid, String name) {
        decaySpellEq.put(uuid,name);
    }

    public void rmvDecaySpellEq(String uuid, String name) {
        decaySpellEq.remove(uuid,name);
    }

    public void addDecaySpellUUID(String uuid, String name) {
        decaySpellUUID.put(uuid,name);
    }

    // Mana Steal Spell

    public void addManaStealSpellEq(String uuid, String name) {
        manaStealSpellEq.put(uuid,name);
    }

    public void rmvManaStealSpellEq(String uuid, String name) {
        manaStealSpellEq.remove(uuid,name);
    }

    public void addManaStealSpellUUID(String uuid, String name) {
        manaStealSpellUUID.put(uuid,name);
    }

    // Place Switch Spell

    public void addSwitchPlaceSpellEq(String uuid, String name) {
        switchPlaceSpellEq.put(uuid,name);
    }

    public void rmvSwitchPlaceSpellEq(String uuid, String name) {
        switchPlaceSpellEq.remove(uuid,name);
    }

    public void addSwitchPlaceSpellUUID(String uuid, String name) {
        switchPlaceSpellUUID.put(uuid,name);
    }

    // Mana Points

    public void addManaPoints(String uuid, Integer mana) {
        manaCount.put(uuid,mana);
    }

    public void rmvManaPoints(String uuid, Integer mana) {
        manaCount.remove(uuid,mana);
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new castSpell(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new spellBook(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new spellEffect(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new manaPoints(this), this);
    }
}

package me.youngmarci.wizardstuff;
import me.youngmarci.wizardstuff.mana.manaPoints;
import me.youngmarci.wizardstuff.spells.castSpell;
import me.youngmarci.wizardstuff.spells.spellBook;
import me.youngmarci.wizardstuff.spells.spellClasses.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public final class Wizardstuff extends JavaPlugin implements Listener {

    // Letting access integers to other classes

    castSpell cstSpell;
    spellBook splBook;

    freezeEnemySpell freezeEnemySpl;
    public Integer freezeEnemySpellCost = 20;
    public Map<String, String> freezeEnemySpellEq = new HashMap<String, String>();
    public Map<String, String> freezeEnemySpellUUID = new HashMap< String, String>();

    teleportSpell teleportSpl;
    public Integer teleportSpellCost = 50;
    public Map<String, String> teleportSpellEq = new HashMap<String, String>();
    public Map<String, String> teleportSpellUUID = new HashMap< String, String>();

    decaySpell decaySpl;
    public Integer decaySpellCost = 30;
    public Map<String, String> decaySpellEq = new HashMap<String, String>();
    public Map<String, String> decaySpellUUID = new HashMap< String, String>();

    manaStealSpell manaStealSpl;
    public Integer manaStealSpellCost = 100;
    public Map<String, String> manaStealSpellEq = new HashMap<String, String>();
    public Map<String, String> manaStealSpellUUID = new HashMap< String, String>();

    switchPlaceSpell switchPlaceSpl;
    public Integer switchPlaceSpellCost = 50;
    public Map<String, String> switchPlaceSpellEq = new HashMap<String, String>();
    public Map<String, String> switchPlaceSpellUUID = new HashMap<String, String>();

    meteorRainSpell meteorRainSpl;
    public Integer meteorRainSpellCost = 10;
    public Map<String, String> meteorRainSpellEq = new HashMap<String, String>();
    public Map<String, String> meteorRainSpellUUID = new HashMap< String, String>();

    manaPoints mnaPoints;
    public HashMap<String, Integer> manaCount = new HashMap<String, Integer>();

    public Wizardstuff() { // Add reference to class that can access variables
        cstSpell = new castSpell(this);
        splBook = new spellBook(this);
        mnaPoints = new manaPoints(this);

        // Spell Classes
        freezeEnemySpl = new freezeEnemySpell(this);
        teleportSpl = new teleportSpell(this);
        decaySpl = new decaySpell(this);
        manaStealSpl = new manaStealSpell(this);
        switchPlaceSpl = new switchPlaceSpell(this);
        meteorRainSpl = new meteorRainSpell(this);
    }

    // Freeze Enemy Spell

    public void addFreezeEnemySpellEq(String uuid, String name) {
        freezeEnemySpellEq.put(uuid,name);
    }

    public void rmvFreezeEnemySpellEq(String uuid, String name) {
        freezeEnemySpellEq.remove(uuid,name);
    }

    public void addFreezeEnemySpellUUID(String uuid, String name) {
        freezeEnemySpellUUID.put(uuid,name);
    }

    // Teleport Spell

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

    // Add Meteor Rain Spell

    public void addMeteorRainSpellEq(String uuid, String name) {
        meteorRainSpellEq.put(uuid,name);
    }

    public void rmvMeteorRainSpellEq(String uuid, String name) {
        meteorRainSpellEq.remove(uuid,name);
    }

    public void addMeteorRainSpellUUID(String uuid, String name) {
        meteorRainSpellUUID.put(uuid,name);
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
        Bukkit.getServer().getPluginManager().registerEvents(new manaPoints(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new spellBook(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new castSpell(this), this);

        //Spells Listeners

        Bukkit.getServer().getPluginManager().registerEvents(new freezeEnemySpell(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new teleportSpell(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new decaySpell(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new manaStealSpell(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new switchPlaceSpell(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new meteorRainSpell(this), this);
    }
}

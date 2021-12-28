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
    public Map<String, String> iceSpellEq = new HashMap<String, String>();
    public Map<String, String> iceSpellUUID = new HashMap< String, String>();

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

    public void rmvIceSpellUUID(String uuid, String name) {
        iceSpellUUID.remove(uuid,name);
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

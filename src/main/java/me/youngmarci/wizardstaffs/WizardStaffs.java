package me.youngmarci.wizardstaffs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class WizardStaffs extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    public Map<String, String> freezeSpellUUID = new HashMap< String, String>();
    public Map<String, String> fireSpellUUID = new HashMap< String, String>();

    @EventHandler
    public void onSpellCasted(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item != null && player.getInventory().getItemInMainHand().getType() == Material.STICK) {
                if (player.getInventory().getItemInOffHand().getType() == Material.BOOK && player.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals("Ice Spell")) {
                    String uuid = player.launchProjectile(Snowball.class).getUniqueId().toString(); //Launch a snowball & get the UUID
                    freezeSpellUUID.put(uuid, player.getName()); //Put UUID and PlayerName to HashMap

                    player.sendMessage("You casted a spell!");
                } else if (player.getInventory().getItemInOffHand().getType() == Material.BOOK && player.getInventory().getItemInOffHand().getItemMeta().getDisplayName().equals("Fire Spell")) {
                    String uuid = player.launchProjectile(Snowball.class).getUniqueId().toString(); //Launch a snowball & get the UUID
                    fireSpellUUID.put(uuid, player.getName()); //Put UUID and PlayerName to HashMap

                    player.sendMessage("You casted a spell!");
                }
            }
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) { //make sure the damager is a snowball & the damaged entity is a Player

            String uuid = event.getDamager().getUniqueId().toString(); //get the UUID of the snowball

            if(freezeSpellUUID.containsKey(uuid)) {//check if our HashMap contains the UUID
                Player damaged = ((Player) event.getEntity()).getPlayer(); //get damaged player
                Player shooter = Bukkit.getPlayer(freezeSpellUUID.get(uuid)); //get shooter
                Location location = damaged.getLocation(); //get location of damaged player

                for (double y = -2; y <= 2; y++) {
                    for (double x = -2; x <= 2; x++) {
                        for (double z = -2; z <= 2; z++) {
                            location.add(x,y,z);
                            location.add(0,1,0);
                            if (location.getBlock().getType() == Material.AIR) {
                                location.getBlock().setType(Material.ICE);
                            }
                            location.subtract(0,1,0);
                            location.subtract(x,y,z);
                        }
                    }
                }

                for (double y = -1; y <= 1; y++) {
                    for (double x = -1; x <= 1; x++) {
                        for (double z = -1; z <= 1; z++) {
                            location.add(x,y,z);
                            location.add(0,1,0);
                            if (location.getBlock().getType() == Material.ICE) {
                                location.getBlock().setType(Material.AIR);
                            }
                            location.subtract(0,1,0);
                            location.subtract(x,y,z);
                        }
                    }
                }

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    public void run() {
                        for (double y = -2; y <= 2; y++) {
                            for (double x = -2; x <= 2; x++) {
                                for (double z = -2; z <= 2; z++) {
                                    location.add(x,y,z);
                                    location.add(0,1,0);
                                    if (location.getBlock().getType() == Material.ICE) {
                                        location.getBlock().setType(Material.AIR);
                                    }
                                    location.subtract(0,1,0);
                                    location.subtract(x,y,z);
                                }
                            }
                        }
                    }
                }, 200);

                shooter.sendMessage("You hit " + damaged.getName() + " with a spell!");
            } else if (fireSpellUUID.containsKey(uuid)) {
                Player damaged = ((Player) event.getEntity()).getPlayer(); //get damaged player
                Player shooter = Bukkit.getPlayer(freezeSpellUUID.get(uuid)); //get shooter
                Location location = damaged.getLocation(); //get location of damaged player
            }
        }
    }
}

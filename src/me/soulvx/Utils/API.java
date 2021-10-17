package me.soulvx.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import me.soulvx.PotterCraft;
import me.soulvx.Spells.Expecto_Patronum;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;
import net.minecraft.server.v1_8_R2.AxisAlignedBB;


public class API {
	
	private static PotterCraft plugin = (PotterCraft) Bukkit.getPluginManager().getPlugin("PotterCraft");
	
	public static void killWolf(final Wolf wolf, final Player owner) {
		Expecto_Patronum.killWolfCountdown.put(owner.getName(), 30);
		if(plugin == null) 
			plugin = (PotterCraft) Bukkit.getPluginManager().getPlugin("PotterCraft"); 
		new BukkitRunnable() {

			@Override
			public void run() {
				Expecto_Patronum.killWolfCountdown.put(owner.getName(), Expecto_Patronum.killWolfCountdown.get(owner.getName()) - 1);
				ParticleEffect.CLOUD.display((float) 0,(float) 0,(float) 0, (float) 0.05, 50, wolf.getLocation(), 100);
				if(Expecto_Patronum.killWolfCountdown.get(owner.getName()) == 0) {
					wolf.setHealth(0D);
					owner.sendMessage(ChatColor.RED + "Your patronus disappeard!");
					startPatronusCountDown(owner);
					cancel();
				}
			}
			
		}.runTaskTimer(plugin, 0L, 20L);
	}
	
	public static void startPatronusCountDown(final Player p) {
		new BukkitRunnable() {

			@Override
			public void run() {
				Expecto_Patronum.canPatronus.put(p.getName(), true);
				p.sendMessage(ChatColor.GREEN + "You can do another patronus now!");
			}
			
		}.runTaskLater(plugin, 30 * 20L);
	}
	
	public static Integer getRandomNumber(int min, int max) {
		Random rand = new Random();
		int randInt = rand.nextInt((max-min) +1 ) + min;
		return  randInt;
	}

	public static Entity getThatEntity(Player player, int range, ParticleEffect particleGiven , int partNumber) {
	    List<Entity> entities = player.getNearbyEntities(range, range, range);
	    Iterator<Entity> iterator = entities.iterator(); 
	    while (iterator.hasNext()) {
	        Entity next = iterator.next(); 
	        if (!(next instanceof LivingEntity) || next == player) {
	            iterator.remove();
	        }
	    }
	    Location location = player.getEyeLocation(); 
        BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, range); 
        Block block = null; 
        while(blocksToAdd.hasNext()) {
            block = blocksToAdd.next();
	        if (block.getType() != Material.AIR && block.getType() != Material.LONG_GRASS && block.getType() != Material.SNOW) {
	            break;
	        }
	        particleGiven.display(0, 0, 0, (float) 0.01, partNumber, block.getLocation(), range);
	        Location low = block.getLocation();
	        Location high = low.clone().add(1, 1, 1); 
	        AxisAlignedBB blockBoundingBox = AxisAlignedBB.a(low.getX(), low.getY(), low.getZ(), high.getX(), high.getY(), high.getZ()); 
	        for (Entity entity : entities) { 
	            if (entity.getLocation().distance(player.getEyeLocation()) <= range && ((CraftEntity) entity).getHandle().getBoundingBox().b(blockBoundingBox)) {
	                return entity;
	            }
	        }
	    }
	    return null;
	}
	
	public static Entity getThatEntity(Player player, int range, ParticleEffect particleGiven , int partNumber, float speed) {
	    List<Entity> entities = player.getNearbyEntities(range, range, range);
	    Iterator<Entity> iterator = entities.iterator(); 
	    while (iterator.hasNext()) {
	        Entity next = iterator.next(); 
	        if (!(next instanceof LivingEntity) || next == player) {
	            iterator.remove();
	        }
	    }
	    Location location = player.getEyeLocation(); 
        BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, range); 
        Block block = null; 
        while(blocksToAdd.hasNext()) {
            block = blocksToAdd.next();
	        if (block.getType() != Material.AIR && block.getType() != Material.LONG_GRASS && block.getType() != Material.SNOW) {
	            break;
	        }
	        particleGiven.display(0, 0, 0, speed, partNumber, block.getLocation(), range);
	        Location low = block.getLocation();
	        Location high = low.clone().add(1, 1, 1); 
	        AxisAlignedBB blockBoundingBox = AxisAlignedBB.a(low.getX(), low.getY(), low.getZ(), high.getX(), high.getY(), high.getZ()); 
	        for (Entity entity : entities) { 
	            if (entity.getLocation().distance(player.getEyeLocation()) <= range && ((CraftEntity) entity).getHandle().getBoundingBox().b(blockBoundingBox)) {
	                return entity;
	            }
	        }
	    }
	    return null;
	}
	
	public static Block getThatBlock(Player player, int range, ParticleEffect particleGiven , int partNumber) {
		 Location location = player.getEyeLocation(); 
	        BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, range); 
	        Block block = null; 
	        while(blocksToAdd.hasNext()) {
	            block = blocksToAdd.next();
		        if (block.getType() != Material.AIR && block.getType() != Material.LONG_GRASS && block.getType() != Material.SNOW) {
		            break;
		        }
		        particleGiven.display(0, 0, 0, (float) 0.01, partNumber, block.getLocation(), range); 
		    }
		        return block;
	}
	
	public static Block getThatBlock(Player player, int range) {
		 Location location = player.getEyeLocation(); 
	        BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, range); 
	        Block block = null; 
	        while(blocksToAdd.hasNext()) {
	            block = blocksToAdd.next();
		        if (block.getType() != Material.AIR && block.getType() != Material.LONG_GRASS && block.getType() != Material.WATER && block.getType() != Material.STATIONARY_WATER && block.getType() != Material.STATIONARY_LAVA  && block.getType() != Material.LAVA && block.getType() != Material.SNOW ) {
		            break;
		        }
		    }
		        return block;
	}
	
	public static Entity getThatEntity(Player player, int range) {
	    List<Entity> entities = player.getNearbyEntities(range, range, range);
	    Iterator<Entity> iterator = entities.iterator(); 
	    while (iterator.hasNext()) {
	        Entity next = iterator.next(); 
	        if (!(next instanceof LivingEntity) || next == player) {
	            iterator.remove();
	        }
	    }
	    Location location = player.getEyeLocation(); 
        BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, range); 
        Block block = null; 
        while(blocksToAdd.hasNext()) {
            block = blocksToAdd.next();
	        if (block.getType() != Material.AIR) {
	            break;
	        }
	        Location low = block.getLocation();
	        Location high = low.clone().add(1, 1, 1); 
	        AxisAlignedBB blockBoundingBox = AxisAlignedBB.a(low.getX(), low.getY(), low.getZ(), high.getX(), high.getY(), high.getZ()); 
	        for (Entity entity : entities) { 
	            if (entity.getLocation().distance(player.getEyeLocation()) <= range && ((CraftEntity) entity).getHandle().getBoundingBox().b(blockBoundingBox)) {
	                return entity;
	            }
	        }
	    }
	    return null; 
	}
	
	public static void createSpellCountdown(final Player p, final HashMap<String,Boolean>  wroteLocalSpell, final HashMap<String,Boolean>  canDoLocalSpell) {
		
		if(PotterCraft.onCountdown.contains(p.getName())) 
			return;
		
		PotterCraft.spellCountdown.put(p.getName(), 5);
		if(plugin == null) 
			plugin = (PotterCraft) Bukkit.getPluginManager().getPlugin("PotterCraft"); 
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(!p.isOnline()) {
					PotterCraft.canDoMagic.put(p.getName(), false);
					PotterCraft.onCountdown.remove(p.getName());
					wroteLocalSpell.put(p.getName(), false);
					canDoLocalSpell.put(p.getName(), false);
					//Bukkit.broadcastMessage("Nu e online!");
					cancel();
					return;
				}
				
				if(PotterCraft.didASpell.get(p.getName())) {
					//Bukkit.broadcastMessage("A facut vraja!");
					PotterCraft.canDoMagic.put(p.getName(), false);
					PotterCraft.onCountdown.remove(p.getName());
					PotterCraft.didASpell.put(p.getName(), false);
					wroteLocalSpell.put(p.getName(), false);
					canDoLocalSpell.put(p.getName(), false);
					cancel();
					return;
				}
				
				if(PotterCraft.spellCountdown.get(p.getName()) != 0) {
					if(!PotterCraft.onCountdown.contains(p.getName())) {
						PotterCraft.onCountdown.add(p.getName());
					}
					PotterCraft.canDoMagic.put(p.getName(), true);
					PotterCraft.spellCountdown.put(p.getName(), PotterCraft.spellCountdown.get(p.getName()) -1 );
					//Bukkit.broadcastMessage("" + PotterCraft.spellCountdown.get(p.getName()));
				}
				
				else {
					p.sendMessage("Spell effect gone!");
					PotterCraft.canDoMagic.put(p.getName(), false);
					PotterCraft.onCountdown.remove(p.getName());
					wroteLocalSpell.put(p.getName(), false);
					canDoLocalSpell.put(p.getName(), false);
					cancel();
					return;
				}
			}
			
		}.runTaskTimer(plugin, 0L, 20L);
		
	}
	
	public static void onChat(AsyncPlayerChatEvent e, HashMap<String,Boolean> wroteLocalSpell, String Spell, boolean wl, HashMap<String,Boolean>  canDoLocalSpell) {
		if(e.getPlayer().hasPermission("wizard")) {
			//Bukkit.broadcastMessage("Are perm!");
			if(!(PotterCraft.isImperiated.contains(e.getPlayer().getName())) && !(PotterCraft.isStupefyed.contains(e.getPlayer().getName())) ) {
				//Bukkit.broadcastMessage("Nu e vrajit!");
				if(e.getMessage().equals(Spell)) {
					//Bukkit.broadcastMessage("A zis vraja! " + Spell );
					if(!PotterCraft.onCountdown.contains(e.getPlayer().getName())) {
						if(wl) {
							wroteLocalSpell.put(e.getPlayer().getName(), true);
							API.createSpellCountdown(e.getPlayer(),wroteLocalSpell, canDoLocalSpell);
						} else {
							e.getPlayer().sendMessage(ChatColor.RED + "You can not do this spell with this wand!");
						}
					}
				}
			}
		}
	}
	
	public static void leftClick(PlayerInteractEvent e, HashMap<String,Boolean> wroteLocalSpell, HashMap<String,Boolean> canDoLocalSpell) {

		if(e.getPlayer().hasPermission("wizard")) {
			if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(PotterCraft.canDoMagic.get(e.getPlayer().getName()) == null)
					PotterCraft.canDoMagic.put(e.getPlayer().getName(), false);
				if(e.getPlayer().getItemInHand().getType() == Material.STICK && PotterCraft.canDoMagic.get(e.getPlayer().getName())) {
					if(wroteLocalSpell.get(e.getPlayer().getName())) {
						canDoLocalSpell.put(e.getPlayer().getName(), true);
					}
				}
			}
		}
	}
	
	public static void rightClick(PlayerInteractEvent e, HashMap<String,Boolean> wroteLocalSpell, HashMap<String,Boolean> canDoLocalSpell) {

		if(e.getPlayer().hasPermission("wizard")) {
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(PotterCraft.canDoMagic.get(e.getPlayer().getName()) == null)
					PotterCraft.canDoMagic.put(e.getPlayer().getName(), false);
				if(e.getPlayer().getItemInHand().getType() == Material.STICK && PotterCraft.canDoMagic.get(e.getPlayer().getName())) {
					if(wroteLocalSpell.get(e.getPlayer().getName())) {
						canDoLocalSpell.put(e.getPlayer().getName(), true);
					}
				}
			}
		}
	}
	
	public static void broadcastSpell(Player p, String spell , String color) {
		Bukkit.broadcastMessage(ChatColor.valueOf(color) + "§lPlayer " + p.getName() + " did the " + spell + " spell!");
	}
	
	public static ItemStack make(Material material, int amount, int shrt, String displayname, String lore) {
	    ItemStack item = new ItemStack(material, amount, (short) shrt);
	    ItemMeta meta = item.getItemMeta();
	    meta.setDisplayName(displayname);
	    ArrayList<String> Lore = new ArrayList<String>();
	    Lore.add(lore);
	    meta.setLore(Lore);
	    item.setItemMeta(meta);
	    return item;
	}
	
	public static boolean Lvl1Perm(Player p) {
		return ( p.getItemInHand().equals(PotterCraft.Lvl1Wand) || p.getItemInHand().equals(PotterCraft.Lvl2Wand)|| p.getItemInHand().equals(PotterCraft.Lvl3Wand) );
	}
	
	public static boolean Lvl2Perm(Player p) {
		return ( p.getItemInHand().equals(PotterCraft.Lvl2Wand)|| p.getItemInHand().equals(PotterCraft.Lvl3Wand) );
	}
	
	public static boolean Lvl3Perm(Player p) {
		return  p.getItemInHand().equals(PotterCraft.Lvl3Wand) ;
	}
	
}

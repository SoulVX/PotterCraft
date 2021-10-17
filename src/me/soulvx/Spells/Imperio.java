package me.soulvx.Spells;

import java.util.HashMap;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Imperio implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
public static WeakHashMap<String,String>  imperioRelation = new WeakHashMap<>();
public static WeakHashMap<String,Entity>  animalImperioRelation = new WeakHashMap<>();
private String Spell = "Imperio";
	
@EventHandler
public void onChat(AsyncPlayerChatEvent e) {
	if(PotterCraft.onCountdown.contains(e.getPlayer().getName()))
		return;
	API.onChat(e, wroteLocalSpell, Spell, API.Lvl3Perm(e.getPlayer()),canDoLocalSpell);
	if(imperioRelation.containsKey(e.getPlayer().getName())) {
		Player imp = e.getPlayer();
		@SuppressWarnings("deprecation")
		Player slave = Bukkit.getPlayer(imperioRelation.get(imp.getName()));
		String message = e.getMessage();
		
		if(message.startsWith("Move:")) {
		String[] messageComp = message.split(" ");
		Vector v = new Vector(Float.parseFloat(messageComp[1]) , Float.parseFloat(messageComp[2]) , Float.parseFloat(messageComp[3]));
		slave.setVelocity(v);
		e.setCancelled(true);
		}
		
		if(message.startsWith("Say:")) {
			try {
			slave.chat(message.substring(4));
			} catch (Exception exception) {}
			e.setCancelled(true);
		}
		
		if(message.startsWith("Execute:")) {
			try {
			slave.performCommand(message.substring(8));
			} catch (Exception exception) {}
			e.setCancelled(true);
		}
		
		if(message.equalsIgnoreCase("come here")) {
			slave.teleport(imp);
			e.setCancelled(true);
		}
		
		if(message.equalsIgnoreCase("kill yourself")) {
			slave.setHealth(0D);
		}
	}
	
	if(animalImperioRelation.containsKey(e.getPlayer().getName())) {
		Player animp = e.getPlayer();
		Entity eslave = animalImperioRelation.get(animp.getName());
		String message = e.getMessage();
		
		if(message.startsWith("Move:")) {
		String[] messageComp = message.split(" ");
		Vector v = new Vector(Float.parseFloat(messageComp[1]) , Float.parseFloat(messageComp[2]) , Float.parseFloat(messageComp[3]));
		eslave.setVelocity(v);
		e.setCancelled(true);
		}
		
		if(message.equalsIgnoreCase("come here")) {
			eslave.teleport(animp);
			e.setCancelled(true);
		}
		
		if(message.equalsIgnoreCase("kill yourself")) {
			if(eslave instanceof Damageable) {
			((Damageable) eslave).setHealth(0D);
			e.setCancelled(true);
			}
		}
	}
}

@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
	
	API.leftClick(e, wroteLocalSpell, canDoLocalSpell);
	
	if(canDoLocalSpell.get(e.getPlayer().getName())) {
		
		Entity entity = API.getThatEntity(e.getPlayer(), 15, ParticleEffect.SMOKE_NORMAL, 60);
		
		Player imperator = e.getPlayer();
		
		if(entity != null) {
		
		if(!imperioRelation.containsKey(imperator.getName()) && !animalImperioRelation.containsKey(imperator.getName())) {
			if(entity instanceof Player) {
				Player ver = (Player) entity; 
				if(imperioRelation.containsValue(ver.getName())) {
					imperator.sendMessage(ChatColor.RED + "That player is already imperiated!");
					API.broadcastSpell(e.getPlayer(), Spell, "BLACK" );
					wroteLocalSpell.put(e.getPlayer().getName(), false);
					canDoLocalSpell.put(e.getPlayer().getName(), false);
					PotterCraft.didASpell.put(e.getPlayer().getName(), true);
					return;
				}
			}
			if(animalImperioRelation.containsValue(entity.getUniqueId())) {
				imperator.sendMessage(ChatColor.RED + "That entity is already imperiated!");
				API.broadcastSpell(e.getPlayer(), Spell, "BLACK" );
				wroteLocalSpell.put(e.getPlayer().getName(), false);
				canDoLocalSpell.put(e.getPlayer().getName(), false);
				PotterCraft.didASpell.put(e.getPlayer().getName(), true);
				return;
			}
		if(entity instanceof Player) {
			Player imperiated = (Player) entity;
			if(PotterCraft.isImperiated.contains(imperiated.getName())) {
				imperator.sendMessage(ChatColor.RED + "That player is already imperiated!");
				API.broadcastSpell(e.getPlayer(), Spell, "BLACK" );
				wroteLocalSpell.put(e.getPlayer().getName(), false);
				canDoLocalSpell.put(e.getPlayer().getName(), false);
				PotterCraft.didASpell.put(e.getPlayer().getName(), true);
				return;
			}
			PotterCraft.isImperiated.add(imperiated.getName());
			imperioRelation.put(imperator.getName(), imperiated.getName());
		} else {
			Entity imperiated = entity;
			animalImperioRelation.put(imperator.getName(), imperiated);
		}
		
		
		} else imperator.sendMessage(ChatColor.RED + "You are already imperiator to someone else!");
		
		}
		
		API.broadcastSpell(e.getPlayer(), Spell, "BLACK" );
		wroteLocalSpell.put(e.getPlayer().getName(), false);
		canDoLocalSpell.put(e.getPlayer().getName(), false);
		PotterCraft.didASpell.put(e.getPlayer().getName(), true);
	}
}

@EventHandler
public void onJoin(PlayerJoinEvent event) {
	wroteLocalSpell.put(event.getPlayer().getName(), false);
	canDoLocalSpell.put(event.getPlayer().getName(), false);
	PotterCraft.didASpell.put(event.getPlayer().getName(), false);
}

@EventHandler
public void onBreak(BlockBreakEvent e) {
	if(e.getPlayer().getItemInHand().equals(PotterCraft.Lvl1Wand) || e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand) || e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand) )
		e.setCancelled(true);
}

@EventHandler
public void onQuit(PlayerQuitEvent e) {
	if(imperioRelation.containsKey(e.getPlayer().getName())){
		if(PotterCraft.isImperiated.contains(imperioRelation.get(e.getPlayer().getName()))) {
			PotterCraft.isImperiated.remove(imperioRelation.get(e.getPlayer().getName()));
		}
		imperioRelation.remove(e.getPlayer().getName());
	}
	if(animalImperioRelation.containsKey(e.getPlayer().getName())) {
		animalImperioRelation.remove(e.getPlayer().getName());
	}
}

@EventHandler
public void onDeath(EntityDeathEvent e) {
	if(e.getEntity() instanceof Player) {
		Player p = (Player) e.getEntity();
		if(imperioRelation.containsKey(p.getName())) {
			if(PotterCraft.isImperiated.contains(imperioRelation.get(p.getName())))
			PotterCraft.isImperiated.remove(imperioRelation.get(p.getName()));
			imperioRelation.remove(p.getName());
		}
		if(imperioRelation.containsValue(p.getName())) {
			PotterCraft.isImperiated.remove(p.getName());
			for(String s : imperioRelation.keySet() ) {
				if(imperioRelation.get(s).equals(p.getName())) {
					imperioRelation.remove(s);
				}
			}
		}
	} else 
	{ Entity entity = e.getEntity(); 
	  if(animalImperioRelation.containsValue(entity)){
		  for(String s : animalImperioRelation.keySet()) {
			  if(animalImperioRelation.get(s).equals(entity)) {
				  animalImperioRelation.remove(s);
			  }
		  }
	  }
	}
}

}

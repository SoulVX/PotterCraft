package me.soulvx.Spells;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Expecto_Patronum implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
public static HashMap<String, Integer> killWolfCountdown = new HashMap<>();
public static HashMap<String, Boolean> canPatronus = new HashMap<>();
private String Spell = "Expecto Patronum";
	
@EventHandler
public void onChat(AsyncPlayerChatEvent e) {
	if(PotterCraft.onCountdown.contains(e.getPlayer().getName()))
		return;
	API.onChat(e, wroteLocalSpell, Spell, API.Lvl2Perm(e.getPlayer()),canDoLocalSpell);
}

@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
	
	API.leftClick(e, wroteLocalSpell, canDoLocalSpell);
	
	if(canPatronus.get(e.getPlayer().getName()) == null)
		canPatronus.put(e.getPlayer().getName(), true);
	
	if(canDoLocalSpell.get(e.getPlayer().getName())) {
		Bukkit.broadcastMessage("canDoLocalSPell true!");
		if(canPatronus.get(e.getPlayer().getName())) {
			Bukkit.broadcastMessage("canPatronus true!");
		
		canPatronus.put(e.getPlayer().getName(), false);
		Bukkit.broadcastMessage("canPatronus set to false!");
		
		Block block = API.getThatBlock(e.getPlayer(), 10, ParticleEffect.CLOUD, 50);
		
		Wolf wolf = e.getPlayer().getWorld().spawn(block.getLocation().add(0,1,0), Wolf.class);
		wolf.setBreed(false);
		wolf.setMaxHealth(20D);
		wolf.setHealth(20D);
		wolf.setTamed(true);
		wolf.setOwner((AnimalTamer) e.getPlayer());
		wolf.setCustomName(e.getPlayer().getName());
		wolf.setCustomNameVisible(false);
		API.killWolf(wolf, e.getPlayer()); 
		
		API.broadcastSpell(e.getPlayer(), Spell, "GREEN" ); 
		PotterCraft.didASpell.put(e.getPlayer().getName(), true);}
		wroteLocalSpell.put(e.getPlayer().getName(), false);
		canDoLocalSpell.put(e.getPlayer().getName(), false);
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

}

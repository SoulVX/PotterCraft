package me.soulvx.Spells;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Meteolojinx_Recanto implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
private String Spell = "Meteolojinx Recanto";
private static PotterCraft plugin = (PotterCraft) Bukkit.getPluginManager().getPlugin("PotterCraft");
	
@EventHandler
public void onChat(AsyncPlayerChatEvent e) {
	if(PotterCraft.onCountdown.contains(e.getPlayer().getName()))
		return;
	API.onChat(e, wroteLocalSpell, Spell, API.Lvl1Perm(e.getPlayer()),canDoLocalSpell);
}

@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
	
	API.leftClick(e, wroteLocalSpell, canDoLocalSpell);
	
	if(canDoLocalSpell.get(e.getPlayer().getName())) {
		
		if(e.getPlayer().getWorld().getEnvironment() == Environment.NORMAL) {
			
			for(int i = (int) e.getPlayer().getLocation().getY() ; i<= e.getPlayer().getWorld().getMaxHeight() ; i++ ) {
				ParticleEffect.DRIP_WATER.display((float) 0, (float) 0, (float) 0, (float) 0.05, 50, new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX() , (double) i , e.getPlayer().getLocation().getZ()), 100);
			}
			
			if(plugin == null)
				plugin = (PotterCraft) Bukkit.getPluginManager().getPlugin("PotterCraft");
			
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "weather clear");
		}
		
		API.broadcastSpell(e.getPlayer(), Spell, "WHITE" );
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

}

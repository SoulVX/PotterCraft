package me.soulvx.Spells;

import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Avis implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
private String Spell = "Avis";
	
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
		
		Block block = API.getThatBlock(e.getPlayer(), 15, ParticleEffect.CLOUD, 20);
		
		if(block != null) {
		
		for(int i = 1 ; i<= API.getRandomNumber(3, 7)  ; i++)
			e.getPlayer().getWorld().spawn(block.getLocation().add(0, 3, 0), Chicken.class);
		
		API.broadcastSpell(e.getPlayer(), Spell, "GREEN" );
		wroteLocalSpell.put(e.getPlayer().getName(), false);
		canDoLocalSpell.put(e.getPlayer().getName(), false);
		PotterCraft.didASpell.put(e.getPlayer().getName(), true);
		}
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

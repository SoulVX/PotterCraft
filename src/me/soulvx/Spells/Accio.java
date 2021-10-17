package me.soulvx.Spells;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Accio implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
private String Spell = "Accio";
	
@EventHandler
public void onChat(AsyncPlayerChatEvent e) {
	if(PotterCraft.onCountdown.contains(e.getPlayer().getName()))
		return;
	API.onChat(e, wroteLocalSpell, Spell , API.Lvl1Perm(e.getPlayer()) , canDoLocalSpell);
}

@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
		
	API.leftClick(e, wroteLocalSpell, canDoLocalSpell);
	
	if(canDoLocalSpell.get(e.getPlayer().getName())) {
		
		Entity entity = null;
		entity = (Entity) API.getThatEntity(e.getPlayer(), 30 , ParticleEffect.SNOW_SHOVEL , 20);
		if(entity != null) {
			Vector from = e.getPlayer().getLocation().toVector();
			Vector to = entity.getLocation().toVector();
			Vector direction = from.subtract(to).normalize().multiply(2).add(new Vector(0, 0.25, 0));
			entity.setVelocity(direction); 
		}
		
		else {
			Block block = API.getThatBlock(e.getPlayer(), 30, ParticleEffect.SNOW_SHOVEL, 20);
			if(block.getType() != Material.AIR && block.getType() != Material.WATER && block.getType() != Material.STATIONARY_WATER && block.getType() != Material.STATIONARY_LAVA  && block.getType() != Material.LAVA) {
				@SuppressWarnings("deprecation")
				FallingBlock fb = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
				fb.setDropItem(false);
				block.setType(Material.AIR);
				Vector from = e.getPlayer().getLocation().toVector();
				Vector to = fb.getLocation().toVector();
				Vector direction = from.subtract(to).normalize().multiply(0.80).add(new Vector(0, 1, 0));
				fb.setVelocity(direction);
			}
		}
		
		API.broadcastSpell(e.getPlayer(), Spell, "GREEN" );
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

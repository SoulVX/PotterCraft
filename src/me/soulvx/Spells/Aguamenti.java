package me.soulvx.Spells;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Aguamenti implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
private String Spell = "Aguamenti";
	
@EventHandler
public void onChat(AsyncPlayerChatEvent e) {
	if(PotterCraft.onCountdown.contains(e.getPlayer().getName()))
		return;
	API.onChat(e, wroteLocalSpell, Spell , API.Lvl1Perm(e.getPlayer()),canDoLocalSpell);
}

@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
	API.leftClick(e, wroteLocalSpell, canDoLocalSpell);
	
	if(canDoLocalSpell.get(e.getPlayer().getName())) {
		
		Block target = API.getThatBlock(e.getPlayer(), 15, ParticleEffect.WATER_DROP, 20);
		
		Entity entity = target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
		TNTPrimed tnt = (TNTPrimed) entity;  
		tnt.setCustomName("AguamentiTNT");
		tnt.setCustomNameVisible(false);
		tnt.setYield(2f);
		tnt.setFuseTicks(0);
		
		API.broadcastSpell(e.getPlayer(), Spell, "BLUE" );
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
public void onExplode(EntityExplodeEvent e) {
	if(e.getEntity() instanceof TNTPrimed) {
		if(e.getEntity().getCustomName() != null) {
		if(e.getEntity().getCustomName().equals("AguamentiTNT")) {
			e.setCancelled(true);
			for(int i = 0; i <= e.blockList().size() -1 ; i++) {
				e.blockList().get(i).getLocation().getBlock().setType(Material.WATER);
			}
		}
	}
	}
}

@EventHandler
public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
	if(e.getEntity().getCustomName() != null) {
	if(e.getDamager() instanceof TNTPrimed && e.getDamager().getCustomName().equals("AguamentiTNT")) {
		e.setDamage(0D);
		e.setCancelled(true);
	}
	}
}

@EventHandler
public void onBreak(BlockBreakEvent e) {
	if(e.getPlayer().getItemInHand().equals(PotterCraft.Lvl1Wand) || e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand) || e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand) )
		e.setCancelled(true);
}

}

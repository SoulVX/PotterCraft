package me.soulvx.Spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Evanesco implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
public static ArrayList<String> onEvanesco = new ArrayList<>();
public static ArrayList<Entity> onEnEvanesco = new ArrayList<>();
private String Spell = "Evanesco";
	
@EventHandler
public void onChat(AsyncPlayerChatEvent e) {
	if(PotterCraft.onCountdown.contains(e.getPlayer().getName()))
		return;
	API.onChat(e, wroteLocalSpell, Spell, API.Lvl2Perm(e.getPlayer()),canDoLocalSpell);
}

@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
	
	API.leftClick(e, wroteLocalSpell, canDoLocalSpell);
	API.rightClick(e, wroteLocalSpell, canDoLocalSpell);
	
	if(canDoLocalSpell.get(e.getPlayer().getName())) {
		
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			
			Entity entity = null;
			entity = API.getThatEntity(e.getPlayer(), 15, ParticleEffect.CLOUD, 20);
			if(entity != null) {
				LivingEntity le = (LivingEntity) entity;
				le.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY ,1000000, 1, false, false));
				if(le instanceof Player) {
					Player player = (Player) le;
					onEvanesco.add(player.getName());
				}
				else {
					onEnEvanesco.add(entity);
				}
			} 
		}
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY ,1000000, 1, false, false));
			onEvanesco.add(e.getPlayer().getName());
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
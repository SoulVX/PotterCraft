package me.soulvx.Spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Locomotor_Mortis implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
public static ArrayList<String> onLocomotor = new ArrayList<>();
public static ArrayList<Entity> onEnLocomotor = new ArrayList<>();
private String Spell = "Locomotor Mortis";
	
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

		Entity entity = API.getThatEntity(e.getPlayer(), 20, ParticleEffect.CRIT_MAGIC, 30);
		
		if(entity != null ) {
			if (entity instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) entity;
				le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 200, false, false));
				if(le instanceof Player) {
					Player p = (Player) le;
					onLocomotor.add(p.getName());
				}
				else {
					onEnLocomotor.add(entity);
				}	
			}
		}
		API.broadcastSpell(e.getPlayer(), Spell, "GRAY" );
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

package me.soulvx.Spells;

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
import org.bukkit.potion.PotionEffectType;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Finite implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
private String Spell = "Finite";
	
@EventHandler
public void onChat(AsyncPlayerChatEvent e) {
	if(PotterCraft.onCountdown.contains(e.getPlayer().getName()))
		return;
	API.onChat(e, wroteLocalSpell, Spell, API.Lvl1Perm(e.getPlayer()),canDoLocalSpell);
}

@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
	
	API.leftClick(e, wroteLocalSpell, canDoLocalSpell);
	API.rightClick(e, wroteLocalSpell, canDoLocalSpell);
	
	if(canDoLocalSpell.get(e.getPlayer().getName())) {
		
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
		
			Entity entity = null;
			entity = API.getThatEntity(e.getPlayer(), 15, ParticleEffect.HEART, 20);
			if(entity != null) {
				if(entity instanceof Player) {
					Player p = (Player) entity;
					if(Confundo.onConfundo.contains(p.getName())) {
						Confundo.onConfundo.remove(p.getName());
						p.removePotionEffect(PotionEffectType.CONFUSION);
					}
					
					if(Crucio.onCrucio.contains(p.getName())) {
						Crucio.onCrucio.remove(p.getName());
						p.removePotionEffect(PotionEffectType.POISON);
						p.removePotionEffect(PotionEffectType.SLOW);
						p.removePotionEffect(PotionEffectType.HUNGER);
					}
					
					if(Evanesco.onEvanesco.contains(p.getName())) {
						Evanesco.onEvanesco.remove(p.getName());
						p.removePotionEffect(PotionEffectType.INVISIBILITY);
					}
					
					if(PotterCraft.isImperiated.contains(p.getName())) {
						PotterCraft.isImperiated.remove(p.getName());
						for(String s : Imperio.imperioRelation.keySet()) {
							if(Imperio.imperioRelation.get(s).equals(p.getName())) {
								Imperio.imperioRelation.remove(s);
							}
						}
					}
					
					if(Impervius.onImpervius.contains(p.getName())) {
						Impervius.onImpervius.remove(p.getName());
						p.removePotionEffect(PotionEffectType.WATER_BREATHING);
					}
					
					if(Locomotor_Mortis.onLocomotor.contains(p.getName())) {
						Locomotor_Mortis.onLocomotor.remove(p.getName());
						p.removePotionEffect(PotionEffectType.SLOW);
					}
					
					if(Obscuro.onObscuro.contains(p.getName())) {
						Obscuro.onObscuro.remove(p.getName());
						p.removePotionEffect(PotionEffectType.BLINDNESS);
					}
					
					if(Petrificus_Totalus.onPetrificus.contains(p.getName())) {
						Petrificus_Totalus.onPetrificus.remove(p.getName());
						p.removePotionEffect(PotionEffectType.SLOW);
						p.removePotionEffect(PotionEffectType.JUMP);
					}
					
				} 
				else {
					if(Evanesco.onEnEvanesco.contains(entity)) {
						Evanesco.onEnEvanesco.remove(entity);
						((LivingEntity) entity).removePotionEffect(PotionEffectType.INVISIBILITY);
					}
					
					if(Locomotor_Mortis.onEnLocomotor.contains(entity)) {
						Locomotor_Mortis.onEnLocomotor.remove(entity);
						((LivingEntity) entity).removePotionEffect(PotionEffectType.SLOW);
					}
					
					if(Petrificus_Totalus.onEnPetrificus.contains(entity)) {
						Petrificus_Totalus.onEnPetrificus.remove(entity);
						((LivingEntity) entity).removePotionEffect(PotionEffectType.SLOW);
						((LivingEntity) entity).removePotionEffect(PotionEffectType.JUMP);
					}
				}
			}
		}
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(Confundo.onConfundo.contains(e.getPlayer().getName())) {
				Confundo.onConfundo.remove(e.getPlayer().getName());
				e.getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
			}
			
			if(Evanesco.onEvanesco.contains(e.getPlayer().getName())) {
				Evanesco.onEvanesco.remove(e.getPlayer().getName());
				e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
			}
			
			if(Impervius.onImpervius.contains(e.getPlayer().getName())) {
				Impervius.onImpervius.remove(e.getPlayer().getName());
				e.getPlayer().removePotionEffect(PotionEffectType.WATER_BREATHING);
			}
			
			if(Locomotor_Mortis.onLocomotor.contains(e.getPlayer().getName())) {
				Locomotor_Mortis.onLocomotor.remove(e.getPlayer().getName());
				e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
			}
			
			if(Obscuro.onObscuro.contains(e.getPlayer().getName())) {
				Obscuro.onObscuro.remove(e.getPlayer().getName());
				e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
			}
			
			if(Petrificus_Totalus.onPetrificus.contains(e.getPlayer().getName())) {
				Petrificus_Totalus.onPetrificus.remove(e.getPlayer().getName());
				e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
				e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			}
			
		}
		
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
public void onBreak(BlockBreakEvent e) {
	if(e.getPlayer().getItemInHand().equals(PotterCraft.Lvl1Wand) || e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand) || e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand) )
		e.setCancelled(true);
}

}


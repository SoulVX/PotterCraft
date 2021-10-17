package me.soulvx.Spells;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

public class Defodio implements Listener  {
	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
private String Spell = "Defodio";
	
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
		
		Block block = API.getThatBlock(e.getPlayer(), 20, ParticleEffect.TOWN_AURA, 70);
		if(block != null) {
			if(block.getType() == Material.IRON_ORE) {
				block.setType(Material.AIR);
				Item item = e.getPlayer().getWorld().dropItem(block.getLocation(), new ItemStack(Material.IRON_INGOT, 1));
				Vector from = e.getPlayer().getLocation().toVector();
				Vector to = item.getLocation().toVector();
				Vector direction = from.subtract(to).normalize().add(new Vector(0, 0.5, 0));
				item.setVelocity(direction); 
			}
			if(block.getType() == Material.GOLD_ORE) {
				block.setType(Material.AIR);
				Item item = e.getPlayer().getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
				Vector from = e.getPlayer().getLocation().toVector();
				Vector to = item.getLocation().toVector();
				Vector direction = from.subtract(to).normalize().add(new Vector(0, 0.5, 0));
				item.setVelocity(direction); 
			}
			if(block.getType() == Material.COAL_ORE || block.getType() == Material.DIAMOND_ORE || block.getType() == Material.EMERALD_ORE 
					|| block.getType() == Material.GLOWING_REDSTONE_ORE || block.getType() == Material.LAPIS_ORE || block.getType() == Material.QUARTZ_ORE || block.getType() == Material.REDSTONE_ORE) {
			if(e.getPlayer().getItemInHand().equals(PotterCraft.Lvl1Wand)) {
				ItemStack case1 = new ItemStack(Material.DIAMOND_PICKAXE);
				case1.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
				block.breakNaturally(case1);
			}
			
			if(e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand)) {
				ItemStack case2 = new ItemStack(Material.DIAMOND_PICKAXE);
				case2.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
				block.breakNaturally(case2);
			}
			
			if(e.getPlayer().getItemInHand().equals(PotterCraft.Lvl3Wand)) {
				ItemStack case3 = new ItemStack(Material.DIAMOND_PICKAXE);
				case3.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
				block.breakNaturally(case3);
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

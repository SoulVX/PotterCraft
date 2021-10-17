package me.soulvx.Spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.material.Door;

import me.soulvx.PotterCraft;
import me.soulvx.Utils.API;
import me.soulvx.Utils.DoorAPI;
import me.soulvx.Utils.ParticleEffect.ParticleEffect;

@SuppressWarnings("deprecation")
public class Colloportus implements Listener {

	
private HashMap<String,Boolean>  wroteLocalSpell = new HashMap<>();
private HashMap<String,Boolean>  canDoLocalSpell = new HashMap<>();
private ArrayList<Location> LockedDoors = new ArrayList<>();
private String Spell = "Colloportus";
	
@EventHandler
public void onChat(AsyncPlayerChatEvent e) {
	if(PotterCraft.onCountdown.contains(e.getPlayer().getName()))
		return;
	API.onChat(e, wroteLocalSpell, Spell, API.Lvl2Perm(e.getPlayer()),canDoLocalSpell);
}

@EventHandler
public void onBreak(BlockBreakEvent e) {
		if(e.getPlayer().getItemInHand().equals(PotterCraft.Lvl1Wand) || e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand) || e.getPlayer().getItemInHand().equals(PotterCraft.Lvl2Wand) )
			e.setCancelled(true);
	if(DoorAPI.isDoor(e.getBlock().getRelative(BlockFace.UP))) {
		if(LockedDoors.contains(e.getBlock().getRelative(BlockFace.UP).getLocation())) {
			LockedDoors.remove(e.getBlock().getRelative(BlockFace.UP).getLocation());
		}
		if(LockedDoors.contains(e.getBlock().getRelative(BlockFace.UP).getLocation().add(0,1,0))) {
			LockedDoors.remove(e.getBlock().getRelative(BlockFace.UP).getLocation().add(0,1,0));
		}
	}
}

@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
	
	if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
		if(LockedDoors.contains(e.getClickedBlock().getLocation()))
			e.setCancelled(true);
			return;
	}
	
	API.leftClick(e, wroteLocalSpell, canDoLocalSpell);
	
	if(canDoLocalSpell.get(e.getPlayer().getName())) {
		
		Block block = API.getThatBlock(e.getPlayer(), 20);
		
		if(DoorAPI.isDoor(block)) {
		DoorAPI.closeDoor(block);
		ParticleEffect.BARRIER.display(0, 0, 0, (float) 0.1 , 1 , block.getLocation(), 20);
		Door door = (Door) block.getState().getData();
		if(door.isTopHalf()) {
			if(!LockedDoors.contains(block.getLocation()))
				LockedDoors.add(block.getLocation());
			if(!LockedDoors.contains(block.getRelative(BlockFace.DOWN).getLocation()))
				LockedDoors.add(block.getRelative(BlockFace.DOWN).getLocation());
		}
		else {
			if(!LockedDoors.contains(block.getLocation()))
				LockedDoors.add(block.getLocation());
			if(!LockedDoors.contains(block.getRelative(BlockFace.UP).getLocation()))
				LockedDoors.add(block.getRelative(BlockFace.UP).getLocation());
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




}

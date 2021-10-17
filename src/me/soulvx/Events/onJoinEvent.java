package me.soulvx.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.soulvx.PotterCraft;

public class onJoinEvent implements Listener {
@EventHandler
public void onJoin(PlayerJoinEvent e) {
	PotterCraft.canDoMagic.put(e.getPlayer().getName(), false);
}
}

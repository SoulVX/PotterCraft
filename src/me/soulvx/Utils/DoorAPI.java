package me.soulvx.Utils;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Door;

@SuppressWarnings("deprecation")
public class DoorAPI {
	static ArrayList<String> doors = new ArrayList<String>();
	 
	public static boolean isDoor(Block b) {
	    return (b.getState().getData() instanceof Door);
	}

public static void openDoor(Block b) {
	if(isDoor(b)) {
		Door door = (Door) b.getState().getData();
		if(door.isTopHalf()) {
			b = b.getRelative(BlockFace.DOWN);
		}
		if(b.getData() == (byte) 0 || b.getData() == (byte) 1 || b.getData() == (byte) 2 || b.getData() == (byte) 3 )
			b.setData((byte) (b.getData() + 4));
		}
	}

public static void closeDoor(Block b) {
	if(isDoor(b)) {
		Door door = (Door) b.getState().getData();
		if(door.isTopHalf()) {
			b = b.getRelative(BlockFace.DOWN);
		}
		if(b.getData() == (byte) 4 || b.getData() == (byte) 5 || b.getData() == (byte) 6 || b.getData() == (byte) 7 )
			b.setData((byte) (b.getData() - 4));
		}
	}
}



package me.soulvx;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import me.soulvx.Spells.Accio;
import me.soulvx.Spells.Aguamenti;
import me.soulvx.Spells.Alohomora;
import me.soulvx.Spells.Anapneo;
import me.soulvx.Spells.Avada_Kedavra;
import me.soulvx.Spells.Avis;
import me.soulvx.Spells.Colloportus;
import me.soulvx.Spells.Confrigo;
import me.soulvx.Spells.Confundo;
import me.soulvx.Spells.Crucio;
import me.soulvx.Spells.Defodio;
import me.soulvx.Spells.Duro;
import me.soulvx.Spells.Episkey;
import me.soulvx.Spells.Evanesco;
import me.soulvx.Spells.Expecto_Patronum;
import me.soulvx.Spells.Expelliarmus;
import me.soulvx.Spells.Expulso;
import me.soulvx.Spells.Finite;
import me.soulvx.Spells.Homenum_Revelio;
import me.soulvx.Spells.Impedimenta;
import me.soulvx.Spells.Imperio;
import me.soulvx.Spells.Impervius;
import me.soulvx.Spells.Incendio;
import me.soulvx.Spells.Locomotor_Mortis;
import me.soulvx.Spells.Lumos;
import me.soulvx.Spells.Meteolojinx;
import me.soulvx.Spells.Meteolojinx_Recanto;
import me.soulvx.Spells.Nox;
import me.soulvx.Spells.Obscuro;
import me.soulvx.Spells.Petrificus_Totalus;
import me.soulvx.Utils.API;

public class PotterCraft extends JavaPlugin implements Listener {
	
	public static ArrayList<String> isImperiated = new ArrayList<>();
	public static ArrayList<String> onCountdown = new ArrayList<>();
	public static ArrayList<String> isStupefyed = new ArrayList<>();
	public static HashMap<String, Integer> spellCountdown = new HashMap<>();
	public static HashMap<String, Boolean> canDoMagic = new HashMap<>();
	public static HashMap<String, Boolean> didASpell = new HashMap<>();
	
	public static ItemStack Lvl1Wand = API.make(Material.STICK, 1, 0, ChatColor.GREEN + "Level 1 Wand", ChatColor.DARK_GREEN + "A wand that can do some weak spells");
	public static ItemStack Lvl2Wand = API.make(Material.STICK, 1, 0, ChatColor.YELLOW + "Level 2 Wand", ChatColor.GOLD + "A wand that can do medium spells");
	public static ItemStack Lvl3Wand = API.make(Material.STICK, 1, 0, ChatColor.RED + "Level 3 Wand", ChatColor.DARK_RED + "A wand that can powerfull spells");
	
	MaterialData MDWand1 = Lvl1Wand.getData();
	MaterialData MDWand2 = Lvl2Wand.getData();
	
	public void onEnable() {
		
		ShapedRecipe Wand1 = new ShapedRecipe(Lvl1Wand);
		Wand1.shape("WGW", "WSW", "WDW");
		Wand1.setIngredient('W', Material.WOOD);
		Wand1.setIngredient('G', Material.GLOWSTONE_DUST);
		Wand1.setIngredient('S', Material.NETHER_STAR);
		Wand1.setIngredient('D', Material.DIAMOND);
		getServer().addRecipe(Wand1);
		
		ShapedRecipe Wand2 = new ShapedRecipe(Lvl2Wand);
		Wand2.shape("gig", "iwi", "gig");
		Wand2.setIngredient('g', Material.GOLD_BLOCK);
		Wand2.setIngredient('i', Material.IRON_BLOCK);
		Wand2.setIngredient('w', MDWand1);
		getServer().addRecipe(Wand2);
		
		ShapedRecipe Wand3 = new ShapedRecipe(Lvl3Wand);
		Wand3.shape("BEB", "OWO", "EBE");
		Wand3.setIngredient('B', Material.NETHER_BRICK);
		Wand3.setIngredient('E', Material.ENDER_STONE);
		Wand3.setIngredient('O', Material.OBSIDIAN);
		Wand3.setIngredient('W', MDWand2);
		getServer().addRecipe(Wand3);
		
		
		
		getServer().getPluginManager().registerEvents(new Accio(), this);
		getServer().getPluginManager().registerEvents(new Aguamenti(), this);
		getServer().getPluginManager().registerEvents(new Alohomora(), this);
		getServer().getPluginManager().registerEvents(new Anapneo(), this);
		getServer().getPluginManager().registerEvents(new Avada_Kedavra(), this);
		getServer().getPluginManager().registerEvents(new Avis(), this);
		
		getServer().getPluginManager().registerEvents(new Colloportus(), this);
		getServer().getPluginManager().registerEvents(new Confrigo(), this);
		getServer().getPluginManager().registerEvents(new Confundo(), this);
		getServer().getPluginManager().registerEvents(new Crucio(), this);
		
		getServer().getPluginManager().registerEvents(new Defodio(), this);
		getServer().getPluginManager().registerEvents(new Duro(), this);
		
		getServer().getPluginManager().registerEvents(new Episkey(), this);
		getServer().getPluginManager().registerEvents(new Evanesco(), this);
		getServer().getPluginManager().registerEvents(new Expecto_Patronum(), this);
		getServer().getPluginManager().registerEvents(new Expelliarmus(), this);
		getServer().getPluginManager().registerEvents(new Expulso(), this);
		
		getServer().getPluginManager().registerEvents(new Finite(), this);
		
		getServer().getPluginManager().registerEvents(new Homenum_Revelio(), this);
		
		getServer().getPluginManager().registerEvents(new Impedimenta(), this);
		getServer().getPluginManager().registerEvents(new Imperio(), this);
		getServer().getPluginManager().registerEvents(new Impervius(), this);
		getServer().getPluginManager().registerEvents(new Incendio(), this);
		
		getServer().getPluginManager().registerEvents(new Locomotor_Mortis(), this);
		getServer().getPluginManager().registerEvents(new Lumos(), this);
		
		getServer().getPluginManager().registerEvents(new Meteolojinx_Recanto(), this);
		getServer().getPluginManager().registerEvents(new Meteolojinx(), this);
		
		getServer().getPluginManager().registerEvents(new Nox(), this);
		
		getServer().getPluginManager().registerEvents(new Obscuro(), this);
		
		getServer().getPluginManager().registerEvents(new Petrificus_Totalus(), this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("wand1") && sender instanceof Player) {
			
			Player player = (Player) sender;
			
			player.getInventory().addItem(Lvl1Wand);
			
			return true;
			
		}
		
		if (cmd.getName().equalsIgnoreCase("wand2") && sender instanceof Player) {
			
			Player player = (Player) sender;
			
			player.getInventory().addItem(Lvl2Wand);
			
			return true;
			
		}
		
		if (cmd.getName().equalsIgnoreCase("wand3") && sender instanceof Player) {
			
			Player player = (Player) sender;
			
			player.getInventory().addItem(Lvl3Wand);
			
			return true;
			
		}
		
		return false;	
		
	}

	
}
package kreashenz.stuntguy3000.mctd;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
//import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class Upgrade implements Listener {

	public static MCTD plugin;
	public Upgrade(MCTD plugin){Upgrade.plugin=plugin;}

	public static HashMap<UUID, Location> a = new HashMap<UUID, Location>();
	public static HashMap<UUID, Location> b = new HashMap<UUID, Location>();
	public static HashMap<UUID, Location> c = new HashMap<UUID, Location>();
	public static HashMap<UUID, Location> d = new HashMap<UUID, Location>();
	public static HashMap<UUID, Location> e = new HashMap<UUID, Location>();

	//@EventHandler /*lets not register it at the moment */
	public void onPlayerUpgradeTower(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		if(e.getRightClicked() instanceof Skeleton){
			Skeleton skeleton = (Skeleton) e.getRightClicked();
			EntityEquipment skele = skeleton.getEquipment();
			if(!a.isEmpty()){
				if(Points.hasEnough(p, plugin.getConfig().getDouble("Skeleton.upgrade-Level1"))){
					Points.takePoints(p, plugin.getConfig().getDouble("Skeleton.upgrade-Level1"));
					skele.setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
					skele.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
					skele.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
					skele.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
					ItemStack bow = new ItemStack(Material.BOW, 1);
					bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
					skele.setItemInHand(bow);
				} else p.sendMessage("§cYou do not have enough money to upgrade that tower.");
			}
			if(!b.isEmpty()){
				if(Points.hasEnough(p, plugin.getConfig().getDouble("Skeleton.upgrade-Level2"))){
					Points.takePoints(p, plugin.getConfig().getDouble("Skeleton.upgrade-Level2"));
					skele.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
					skele.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
					skele.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
					skele.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
					ItemStack bow = new ItemStack(Material.BOW, 1);
					bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
					bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
					skele.setItemInHand(bow);
				} else p.sendMessage("§cYou do not have enough money to upgrade that tower.");
			}
			if(!c.isEmpty()){
				if(Points.hasEnough(p, plugin.getConfig().getDouble("Skeleton.upgrade-Level3"))){
					Points.takePoints(p, plugin.getConfig().getDouble("Skeleton.upgrade-Level3"));
					skeleton.setSkeletonType(SkeletonType.WITHER);
					skeleton.setMaxHealth(skeleton.getMaxHealth()*3);
					ItemStack bow = new ItemStack(Material.BOW, 1);
					bow.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
					bow.addEnchantment(Enchantment.ARROW_FIRE, 2);
					skele.setItemInHand(bow);
				} else p.sendMessage("§cYou do not have enough money to upgrade that tower.");
			}
		}
	}
}

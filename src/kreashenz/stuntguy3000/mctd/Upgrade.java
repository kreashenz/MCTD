package kreashenz.stuntguy3000.mctd;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class Upgrade implements Listener {

	public static MCTD plugin;
	public Upgrade(MCTD plugin){Upgrade.plugin=plugin;}

	public static HashMap<Entity, Location> a = new HashMap<Entity, Location>(); // level 1
	public static HashMap<Entity, Location> b = new HashMap<Entity, Location>(); // level 2
	public static HashMap<Entity, Location> c = new HashMap<Entity, Location>(); // level 3
	public static HashMap<Entity, Location> d = new HashMap<Entity, Location>(); // level 4
	public static HashMap<Entity, Location> e = new HashMap<Entity, Location>(); // level 5

	@EventHandler /*lets not register it at the moment */
	public void onPlayerUpgradeTower(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		if(e.getRightClicked() instanceof Skeleton){
			Skeleton skeleton = (Skeleton) e.getRightClicked();
			EntityEquipment skele = skeleton.getEquipment();
			if(b.containsKey(e.getRightClicked())){
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
			if(!c.isEmpty()){
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
			if(!d.isEmpty()){
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

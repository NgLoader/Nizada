package de.ng.nizada.freebuild.mobcatcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.mobcatcher.entitys.MobCatcherTypeSheep;
import de.ng.nizada.freebuild.mobcatcher.entitys.MobCatcherTypeZombie;
import de.ng.nizada.gamecore.util.ItemFactory;

public class MobCatcherManager {
	
	public static MobCatcherManager instance;
	
	public List<AbstractMobCatcherType> mobCatcherTypes;

	public MobCatcherManager() {
		instance = this;
		mobCatcherTypes = new ArrayList<>();

		mobCatcherTypes.add(new MobCatcherTypeSheep());
		mobCatcherTypes.add(new MobCatcherTypeZombie());
	}
	
	public boolean isMobCatcher(ItemStack item) {
		return item != null && item.getItemMeta() != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals("§aMob §2Catcher") && item.getItemMeta().hasLore();
	}
	
	public void save(Player player, ItemStack item, LivingEntity entity) {
		try {
			ItemFactory itemFactory = new ItemFactory(item.clone());
			List<String> lore = new ArrayList<>();
			itemFactory.setAmount(1);
			
			int tier = Integer.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Tier§8: §3").get(0).replaceFirst("§7Tier§8: §3", ""));
			lore.add("§7Tier§8: §3" + tier);
			
			if(!existLore(item.getItemMeta().getLore(), "§7Benutzungen§8: §3Unendlich")) {
				int usages = Integer.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Benutzungen§8: §3").get(0).replaceFirst("§7Benutzungen§8: §3", "")) - 1;
				
				if(usages == -1) {
					player.sendMessage(Freebuild.PREFIX + "§7Dieser §cMobCatcher §7ist kaputt§8.");
					player.getInventory().removeItem(item);
					return;
				}
				
				lore.add("§7Benutzungen§8: §3" + usages);
			} else
				lore.add("§7Benutzungen§8: §3Unendlich");
			
			if(getLoreValue(item.getItemMeta().getLore(), "§7Status§8: §3").get(0).replaceFirst("§7Status§8: §3", "").equals("In benutzung")) {
				player.sendMessage(Freebuild.PREFIX + "§7Dieser §cMobCatcher §7ist bereits in §cbenutzung§8.");
				return;
			}
			
			lore.add("§7Status§8: §3In benutzung");
			lore.add(" ");
			lore.add("§7Type§8: §3" + entity.getType());
			lore.add("§7Lebensgröße§8: §3" + entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			lore.add("§7Leben§8: §3" + entity.getHealth());
			
			if(entity.getCustomName() != null)
				lore.add("§7Name§8: §3" + entity.getCustomName());
			if(entity.isGlowing())
				lore.add("§7Leuchtet§8: §3" + entity.isGlowing());

			Properties properties = new Properties();
			
			for(AbstractMobCatcherType mobCatcherType : mobCatcherTypes)
				if(mobCatcherType.getType() == entity.getType())
					mobCatcherType.save(entity, item, lore, properties);
			
//			properties.setProperty("boots", entity.getEquipment().getBoots());
			
			if(!properties.isEmpty()) {
				File folder = new File("./plugins/Freebuild/MobCatcherStorage");
				if(!folder.exists())
					folder.mkdirs();
				
				int storageId = folder.listFiles().length;
				
				File file = new File(folder, storageId + ".properties");
				while(file.exists())
					file = new File(folder, storageId + ".properties");
				
				FileOutputStream outputStream = new FileOutputStream(file);
				properties.store(outputStream, "FINGER WEG HIER VON!");
				outputStream.close();
				
				lore.add("§7Speicher Nummer§8: §3" + storageId);
			}
			
			itemFactory.setLore(lore);
			itemFactory.addAllFlag();
			
			entity.remove();
			entity.getLocation().getWorld().spawnParticle(Particle.TOTEM, .5D, .5D, .5D, 20);
			
			if(item.getAmount() > 1)
				item.setAmount(item.getAmount() - 1);
			else
				player.getInventory().removeItem(item);
			
			player.getInventory().addItem(itemFactory.build());
			player.sendMessage(Freebuild.PREFIX + "§7Du hast ein §8\"§2" + entity.getType() + "§8\" §aeingefangen§8.");
		} catch(Exception ex) {
			ex.printStackTrace();
			player.sendMessage(Freebuild.PREFIX + "§7Es ist ein §cFehler §7beim §cSpeichern §7passiert§8. §7Bitte wende dich an einen §cAdmin§8.");
		}
	}
	
	public void load(Player player, ItemStack item, Location location) {
		LivingEntity entity = null;
		try {
			File file = null;
			ItemFactory itemFactory = new ItemFactory(item.clone());
			itemFactory.setAmount(1);
			
			if(!getLoreValue(item.getItemMeta().getLore(), "§7Status§8: §3").get(0).replaceFirst("§7Status§8: §3", "").equals("In benutzung")) {
				player.sendMessage(Freebuild.PREFIX + "§7Dieser §cMobCatcher §7ist §cunbenutzt§8.");
				return;
			}
			
			EntityType type = EntityType.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Type§8: §3").get(0).replaceFirst("§7Type§8: §3", ""));
			entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
			
			entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Double.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Lebensgröße§8: §3").get(0).replaceFirst("§7Lebensgröße§8: §3", "")));
			entity.setHealth(Double.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Leben§8: §3").get(0).replaceFirst("§7Leben§8: §3", "")));
			
			if(existLore(item.getItemMeta().getLore(), "§7Name§8: §3"))
				entity.setCustomName(getLoreValue(item.getItemMeta().getLore(), "§7Name§8: §3").get(0).replaceFirst("§7Name§8: §3", ""));
			if(existLore(item.getItemMeta().getLore(), "§7Leuchtet§8: §3"))
				entity.setCustomName(getLoreValue(item.getItemMeta().getLore(), "§7Leuchtet§8: §3").get(0).replaceFirst("§7Leuchtet§8: §3", ""));
			
			Properties properties = new Properties();
			File folder = new File("./plugins/Freebuild/MobCatcherStorage");
			if(folder.exists()) {
				if(existLore(item.getItemMeta().getLore(), "§7Speicher Nummer§8: §3")) {
					int storageId = Integer.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Speicher Nummer§8: §3").get(0).replaceFirst("§7Speicher Nummer§8: §3", ""));
					file = new File(folder, storageId + ".properties");
					if(file.exists()) {
						FileInputStream inputStream = new FileInputStream(file);
						properties.load(inputStream);
						inputStream.close();
						file.delete();
					}
				}
			}
			
			for(AbstractMobCatcherType mobCatcherType : mobCatcherTypes)
				if(mobCatcherType.getType() == entity.getType())
					mobCatcherType.load(entity, item, properties);
			
			List<String> lore = new ArrayList<>();
			
			int tier = Integer.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Tier§8: §3").get(0).replaceFirst("§7Tier§8: §3", ""));
			lore.add("§7Tier§8: §3" + tier);
			
			if(!existLore(item.getItemMeta().getLore(), "§7Benutzungen§8: §3Unendlich")) {
				int usages = Integer.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Benutzungen§8: §3").get(0).replaceFirst("§7Benutzungen§8: §3", ""));
				
				if(usages == 0) {
					player.sendMessage(Freebuild.PREFIX + "§7Dieser §cMobCatcher §7wurde aufgebraucht§8.");
					if(item.getAmount() > 1)
						item.setAmount(item.getAmount() - 1);
					else
						player.getInventory().removeItem(item);
					return;
				}
				
				lore.add("§7Benutzungen§8: §3" + usages);
			} else
				lore.add("§7Benutzungen§8: §3Unendlich");
			lore.add("§7Status§8: §3Unbenutzt");
			
			if(item.getAmount() > 1)
				item.setAmount(item.getAmount() - 1);
			else
				player.getInventory().removeItem(item);
			
			player.getInventory().addItem(itemFactory.setLore(lore).build());
			player.sendMessage(Freebuild.PREFIX + "§7Dieser §aMobCatcher §7ist nun wieder unbenutzt§8.");
			
			if(file != null)
				file.deleteOnExit();
		} catch(Exception ex) {
			ex.printStackTrace();
			player.sendMessage(Freebuild.PREFIX + "§7Es ist ein §cFehler §7beim §cSpawnen §7passiert§8. §7Bitte wende dich an einen §cAdmin§8.");
			if(entity != null)
				entity.remove();
		}
	}
	
	private List<String> getLoreValue(List<String> lores, String startWith) {
		List<String> found = lores.stream().filter(lore -> lore.startsWith(startWith)).collect(Collectors.toList());
		if(found.isEmpty())
			return null;
		return found;
	}
	
	private boolean existLore(List<String> lores, String... startWith) {
		return lores.stream().anyMatch(lore -> Arrays.asList(startWith).stream().anyMatch(sw -> lore.startsWith(sw)));
	}
	
	@SuppressWarnings("unused")
	private String itemstackToString(ItemStack item) {
		String[] args = new String[0];
		args[0] = item.getType().name();
		args[0] = item.getType().name();
		args[0] = String.valueOf(item.getMaxStackSize());
		args[0] = String.valueOf(item.getMaxStackSize());
		args[0] = String.valueOf(item.getAmount());
		args[0] = item.getItemMeta().getDisplayName();
		args[0] = item.getItemMeta().getLocalizedName();
		
		return String.join(";", args);
	}
	
	public ItemStack itemstackByString(String string) {
		return null;
	}
}
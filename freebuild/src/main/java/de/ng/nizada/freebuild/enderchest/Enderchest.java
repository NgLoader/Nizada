package de.ng.nizada.freebuild.enderchest;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.ng.nizada.gamecore.util.ItemFactory;
import de.ng.nizada.gamecore.util.ItemStackSaver;

public class Enderchest {
	
	private static final ItemStack ITEM_PAGELAST = new ItemFactory(Material.PAPER).setDisplayName("§aLetzte seite").build();
	private static final ItemStack ITEM_PAGENEXT = new ItemFactory(Material.PAPER).setDisplayName("§aNegste seite").build();
	private static final ItemStack ITEM_PAGENONE = new ItemFactory(Material.BARRIER).setDisplayName(" ").build();
	
	private UUID owner;
	private List<Inventory> inventorys;
	
	public Enderchest(UUID owner, List<Inventory> inventorys) {
		this.owner = owner;
		this.inventorys = inventorys;
	}
	
	public Enderchest(Properties properties) {
		
	}
	
	public void addNewPage() {
		Inventory newInventory = Bukkit.createInventory(null, InventoryType.ENDER_CHEST, "§5E§dnder§5K§diste §9Seite §d" + inventorys.size());
		newInventory.setItem(18, ITEM_PAGELAST);
		newInventory.setItem(26, ITEM_PAGENONE);
		
		Inventory lastInventory = inventorys.get(inventorys.size() - 1);
		lastInventory.setItem(26, ITEM_PAGENEXT);
		
		inventorys.add(newInventory);
	}
	
	public List<Inventory> getInventorys() {
		return inventorys;
	}
	
	public UUID getOwner() {
		return owner;
	}
	
	public Properties toProperties() {
		Properties properties = new Properties();
		
		int count = 0;
		for(Inventory inventory : inventorys) {
			int i = count++;
			properties.setProperty("page_" + i + "_contents", ItemStackSaver.itemStackArrayToBase64(inventory.getContents()));
			properties.setProperty("page_" + i + "_storagecontents", ItemStackSaver.itemStackArrayToBase64(inventory.getStorageContents()));
			properties.setProperty("page_" + i + "_name", inventory.getName());
			properties.setProperty("page_" + i + "_title", inventory.getTitle());
		}
		return properties;
	}
}
package de.ng.nizada.freebuild.mobcatcher;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractMobCatcherType {
	
	private EntityType type;
	
	public AbstractMobCatcherType(EntityType type) {
		this.type = type;
	}
	
	public abstract void load(LivingEntity entity, ItemStack item, Properties properties);
	public abstract void save(LivingEntity entity, ItemStack item, List<String> lore, Properties properties);
	
	public EntityType getType() {
		return type;
	}
	
	public List<String> getLoreValue(List<String> lores, String startWith) {
		List<String> found = lores.stream().filter(lore -> lore.startsWith(startWith)).collect(Collectors.toList());
		if(found.isEmpty())
			return null;
		return found;
	}
	
	public boolean existLore(List<String> lores, String... startWith) {
		return lores.stream().anyMatch(lore -> Arrays.asList(startWith).stream().anyMatch(sw -> lore.startsWith(sw)));
	}
}
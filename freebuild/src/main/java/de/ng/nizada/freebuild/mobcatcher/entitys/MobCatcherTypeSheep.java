package de.ng.nizada.freebuild.mobcatcher.entitys;

import java.util.List;
import java.util.Properties;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;

import de.ng.nizada.freebuild.mobcatcher.AbstractMobCatcherType;

public class MobCatcherTypeSheep extends AbstractMobCatcherType {

	public MobCatcherTypeSheep() {
		super(EntityType.SHEEP);
	}

	public void load(LivingEntity entity, ItemStack item, Properties properties) {
		Sheep sheep = (Sheep)entity;
		
		sheep.setColor(DyeColor.valueOf(getLoreValue(item.getItemMeta().getLore(), "§7Farbe§8: §3").get(0).replaceFirst("§7Farbe§8: §3", "")));
	}

	@Override
	public void save(LivingEntity entity, ItemStack item, List<String> lore, Properties properties) {
		Sheep sheep = (Sheep)entity;
		
		lore.add("§7Farbe§8: §3" + sheep.getColor());
	}
}
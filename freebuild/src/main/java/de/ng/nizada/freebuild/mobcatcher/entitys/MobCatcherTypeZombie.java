package de.ng.nizada.freebuild.mobcatcher.entitys;

import java.util.List;
import java.util.Properties;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import de.ng.nizada.freebuild.mobcatcher.AbstractMobCatcherType;

public class MobCatcherTypeZombie extends AbstractMobCatcherType {

	public MobCatcherTypeZombie() {
		super(EntityType.ZOMBIE);
	}

	public void load(LivingEntity entity, ItemStack item, Properties properties) {
//		Zombie zombie = (Zombie)entity;
	}

	@Override
	public void save(LivingEntity entity, ItemStack item, List<String> lore, Properties properties) {
//		Zombie zombie = (Zombie)entity;
	}
}
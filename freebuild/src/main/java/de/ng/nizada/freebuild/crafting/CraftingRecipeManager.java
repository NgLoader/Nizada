package de.ng.nizada.freebuild.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import de.ng.nizada.gamecore.util.ItemFactory;

public class CraftingRecipeManager {
	
	public static final ItemStack ITEM_CHAINMAIL_HELMET = new ItemFactory(Material.CHAINMAIL_HELMET).build();
	public static final ItemStack ITEM_CHAINMAIL_CHESTPLATE = new ItemFactory(Material.CHAINMAIL_CHESTPLATE).build();
	public static final ItemStack ITEM_CHAINMAIL_LEGGINGS = new ItemFactory(Material.CHAINMAIL_LEGGINGS).build();
	public static final ItemStack ITEM_CHAINMAIL_BOOTS = new ItemFactory(Material.CHAINMAIL_BOOTS).build();
	
	public static final ItemStack ITEM_MOBCATCHER_TIER_1 = new ItemFactory(Material.MAGMA_CREAM).addAllFlag().setDisplayName("§aMob §2Catcher").addLore("§7Tier§8: §31", "§7Benutzungen§8: §31", "§7Status§8: §3Unbenutzt").build();
	public static final ItemStack ITEM_MOBCATCHER_TIER_2 = new ItemFactory(Material.MAGMA_CREAM).addAllFlag().setDisplayName("§aMob §2Catcher").addLore("§7Tier§8: §32", "§7Benutzungen§8: §310", "§7Status§8: §3Unbenutzt").build();
	public static final ItemStack ITEM_MOBCATCHER_TIER_3 = new ItemFactory(Material.MAGMA_CREAM).addAllFlag().setDisplayName("§aMob §2Catcher").addLore("§7Tier§8: §33", "§7Benutzungen§8: §3Unendlich", "§7Status§8: §3Unbenutzt").build();
	
	public CraftingRecipeManager() {
		registerCraftingRecept_Chainmail_Helmet();
		registerCraftingRecept_Chainmail_Chestplate();
		registerCraftingRecept_Chainmail_Leggings();
		registerCraftingRecept_Chainmail_Boots();
		registerCraftingRecept_MobCatcher_Tier_1();
		registerCraftingRecept_MobCatcher_Tier_2();
		registerCraftingRecept_MobCatcher_Tier_3();
	}
	
	private void registerCraftingRecept_Chainmail_Helmet() {
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe1 = new ShapedRecipe(ITEM_CHAINMAIL_HELMET);
		recipe1.shape("III", "CAC", "AAA");
		recipe1.setIngredient('A', Material.AIR);
		recipe1.setIngredient('I', Material.IRON_INGOT);
		recipe1.setIngredient('C', Material.COBBLESTONE);
		Bukkit.addRecipe(recipe1);
		
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe2 = new ShapedRecipe(ITEM_CHAINMAIL_HELMET);
		recipe2.shape("AAA", "III", "CAC");
		recipe2.setIngredient('A', Material.AIR);
		recipe2.setIngredient('I', Material.IRON_INGOT);
		recipe2.setIngredient('C', Material.COBBLESTONE);
		Bukkit.addRecipe(recipe2);
	}
	
	private void registerCraftingRecept_Chainmail_Chestplate() {
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe = new ShapedRecipe(ITEM_CHAINMAIL_CHESTPLATE);
		recipe.shape("IAI", "CIC", "CCC");
		recipe.setIngredient('A', Material.AIR);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('C', Material.COBBLESTONE);
		Bukkit.addRecipe(recipe);
	}
	
	private void registerCraftingRecept_Chainmail_Leggings() {
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe = new ShapedRecipe(ITEM_CHAINMAIL_LEGGINGS);
		recipe.shape("III", "CAC", "CAC");
		recipe.setIngredient('A', Material.AIR);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('C', Material.COBBLESTONE);
		Bukkit.addRecipe(recipe);
	}
	
	private void registerCraftingRecept_Chainmail_Boots() {
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe1 = new ShapedRecipe(ITEM_CHAINMAIL_BOOTS);
		recipe1.shape("AAA", "IAI", "CAC");
		recipe1.setIngredient('A', Material.AIR);
		recipe1.setIngredient('I', Material.IRON_INGOT);
		recipe1.setIngredient('C', Material.COBBLESTONE);
		Bukkit.addRecipe(recipe1);
		
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe2 = new ShapedRecipe(ITEM_CHAINMAIL_BOOTS);
		recipe2.shape("IAI", "CAC", "AAA");
		recipe2.setIngredient('A', Material.AIR);
		recipe2.setIngredient('I', Material.IRON_INGOT);
		recipe2.setIngredient('C', Material.COBBLESTONE);
		Bukkit.addRecipe(recipe2);
	}
	
	private void registerCraftingRecept_MobCatcher_Tier_1() {
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe = new ShapedRecipe(ITEM_MOBCATCHER_TIER_1);
		recipe.shape("DGD", "GEG", "DGD");
		recipe.setIngredient('D', Material.DIAMOND);
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('E', Material.EMERALD);
		Bukkit.addRecipe(recipe);
	}
	
	private void registerCraftingRecept_MobCatcher_Tier_2() {
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe = new ShapedRecipe(ITEM_MOBCATCHER_TIER_2);
		recipe.shape("DGD", "GEG", "DGD");
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('G', Material.GOLD_BLOCK);
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		Bukkit.addRecipe(recipe);
	}
	
	private void registerCraftingRecept_MobCatcher_Tier_3() {
		@SuppressWarnings("deprecation")
		ShapedRecipe recipe = new ShapedRecipe(ITEM_MOBCATCHER_TIER_3);
		recipe.shape("DED", "ENE", "DED");
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('N', Material.NETHER_STAR);
		Bukkit.addRecipe(recipe);
	}
	
}
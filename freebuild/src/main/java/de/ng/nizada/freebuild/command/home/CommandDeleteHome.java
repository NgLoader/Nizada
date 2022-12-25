package de.ng.nizada.freebuild.command.home;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.ng.nizada.freebuild.Freebuild;
import de.ng.nizada.freebuild.callback.CallbackManager;
import de.ng.nizada.freebuild.callback.CallbackType;
import de.ng.nizada.freebuild.callback.TypeCallback;
import de.ng.nizada.freebuild.home.Home;
import de.ng.nizada.freebuild.home.HomeManager;
import de.ng.nizada.gamecore.util.Pair;
import de.ng.nizada.gamecore.util.StringGenerator;

public class CommandDeleteHome implements CommandExecutor {

	private static final HashMap<UUID, Entry<String, Entry<Home, UUID>>> REQUESTS = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Freebuild.PREFIX + "§7Die Console kann kein §cHome §7löschen§8.");
			return true;
		}
		Player player = (Player) sender;

		if (!player.hasPermission("nizada.home.delete")) {
			player.sendMessage(Freebuild.PREFIX + "§7Du hast keine §cRechte §7um diesen §cCommand §7zu nutzen§8.");
			return true;
		}
		if (args.length > 0) {
			String homeName = args[0].toLowerCase();
			int targetUUIDArgPos = args.length - 1;
			UUID targetUUID = null;
			if (args.length > 1 && player.hasPermission("nizada.home.delete.other"))
				if (args[targetUUIDArgPos].length() > 16)
					targetUUID = UUID.fromString(args[targetUUIDArgPos]);
				else {
					Player target = Bukkit.getPlayer(args[targetUUIDArgPos]);
					if (target != null)
						targetUUID = target.getUniqueId();
					else {
						sender.sendMessage(Freebuild.PREFIX + "§7Der angegebene §cSpieler §8\"§4" + args[targetUUIDArgPos] + "§8\" §7ist nicht §cOnline§8.");
						return true;
					}
				}
			Home home = HomeManager.HOME_MANAGER.getHome(targetUUID != null ? targetUUID : player.getUniqueId(), homeName);

			if ((args.length == 1 || args.length == 2) && !REQUESTS.containsKey(player.getUniqueId())) {
				if(home == null) {
					player.sendMessage(Freebuild.PREFIX + "§7Dieser §cHome §7exestiert nicht§8.");
					return true;
				}

				String generatedCode = StringGenerator.ALPHANUMERIC.generateString(6);
				REQUESTS.put(player.getUniqueId(), new Pair<String, Entry<Home, UUID>>(generatedCode, new Pair<Home, UUID>(home, targetUUID == null || args.length == 1 ? null : targetUUID)));
				CallbackManager.addCallback(player.getUniqueId(), CallbackType.CHAT, new TypeCallback<Object>() {
					long elapsed = System.currentTimeMillis() + 15000;

					@Override
					public void done(Object result, Throwable error) {
						String message = (String) result;
						String code = REQUESTS.get(player.getUniqueId()).getKey();
						UUID targetUUID = REQUESTS.get(player.getUniqueId()).getValue().getValue();
						Home home = REQUESTS.get(player.getUniqueId()).getValue().getKey();
						REQUESTS.remove(player.getUniqueId());

						if(!message.equals("abbruch"))
							if (message.equals(code)) {
								if (HomeManager.HOME_MANAGER.deleteHome(targetUUID != null ? targetUUID : player.getUniqueId(), home.getName().toLowerCase()) != null)
									player.sendMessage(Freebuild.PREFIX + "§7Du hast erfolgreich das §aHome §8\"§a" + home.getName() + "§8\" §cgelöscht§8.");
								else
									player.sendMessage(Freebuild.PREFIX + "§7Dieser §cHome §7wurde bereits §cgelöscht§8.");
							} else
								player.sendMessage(Freebuild.PREFIX + "§7Der angegebene §cCode §7ist §cfalsch§8.");
						else
							player.sendMessage(Freebuild.PREFIX + "§7Deine anfrage zum §clöschen §7des §aHomes §7wurde §cabgebrochen§8.");
					}

					@Override
					public void expired() {
						if(REQUESTS.containsKey(player.getUniqueId())) {
							player.sendMessage(Freebuild.PREFIX + "§7Deine anfrage zum §cLöschen §7deines §aHomes §8\"§2"
									+ REQUESTS.get(player.getUniqueId()).getValue().getKey().getName() + "§8\" §7wurde §cabgebrochen§8.");
							REQUESTS.remove(player.getUniqueId());
						}
					}

					@Override
					public boolean isExpired() {
						return !REQUESTS.containsKey(player.getUniqueId()) || elapsed < System.currentTimeMillis();
					}
				});
				player.sendMessage(
											Freebuild.PREFIX + "§7Bestätige das du dein §cHome §8\"§c" + home.getName() + "§8\" §cLöschen §7willst§8."
								+ "\n" + 	Freebuild.PREFIX + "§7Gebe dazu den volgenen §aCode im §2Chat §7ein §8\"§2" + generatedCode + "§8\""
								+ "\n" +	Freebuild.PREFIX + "§7Fals du dieses §cabbrechen §7möchtest schreibe §8\"§cabbruch§8\"");
			} else if(args.length > 2)
				player.sendMessage(Freebuild.PREFIX + "§7/DeleteHome §8<§7name§8>");
			else
				player.sendMessage(Freebuild.PREFIX + "§7Bitte schreibe deinen §aCode §7nur im §aChat §7nicht als §cCommand§8.");
		} else
			player.sendMessage(Freebuild.PREFIX + "§7/DeleteHome §8<§7name§8>");
		return true;
	}
}
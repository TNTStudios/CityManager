package de.tntstudios.CityManager.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import de.tntstudios.CityManager.CityManager;
import de.tntstudios.CityManager.CityPlayer;

public class PlayerJoin implements Listener {
	
	private static Plugin plugin;
	
	public PlayerJoin(Plugin plugin) {
		PlayerJoin.plugin = plugin;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	/**
	 * Methode wird beim Join eines Players auf dem Server ausgeführt
	 * Bool check gibt an ob ein Player bereits in der Datenbank existiert oder nicht
	 * Falls nicht wird ein neuer Datensatz für den Player in der DB erstellt
	 * @param PlayerJoin-Objekt
	 */
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		boolean check = CityManager.checkPlayerInDB(e.getPlayer());
		
		if (!check) {
			CityManager.insertPlayerIntoDB(e.getPlayer());
			System.out.println("Player erfolgreich in der Datenbank gespeichert");
		}
		else {
			System.out.println("Player existiert bereits in der Datenbank");
		}
		
		System.out.println("Player Anzahl: " + CityPlayer.getCityPlayers().size());
	}
}

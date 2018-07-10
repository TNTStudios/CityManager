package de.tntstudios.CityManager.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import de.tntstudios.CityManager.CityManager;
import de.tntstudios.CityManager.CityPlayer;

public class PlayerQuit implements Listener {

	private static Plugin plugin;
	
	public PlayerQuit(Plugin plugin) {
		PlayerQuit.plugin = plugin;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	/**
	 * Methode wird beim Quit eines Players auf dem Server ausgeführt
	 * @param PlayerQuit-Objekt
	 */
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		CityManager.updatePlayerInDB(e.getPlayer());
		//CityPlayer wird aus der Liste entfernt
		CityPlayer.removeCityPlayer(e.getPlayer().getUniqueId().toString());
		System.out.println("Player Anzahl: " + CityPlayer.getCityPlayers().size());
	}
}

package de.tntstudios.CityManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.tntstudios.CityManager.Events.PlayerJoin;
import de.tntstudios.CityManager.Events.PlayerQuit;

public class CityManager extends JavaPlugin {

	/**
	 * Liste für Online-Player wird erstellt
	 * Hashmap Key => Player-UUID, Hashmap Value => CityPlayer-Objekt
     */
	
	public static HashMap<String,CityPlayer> cityPlayers = new HashMap<String,CityPlayer>();
	
	@Override
	public void onEnable() {
		//Events werden einzeln aufgerufen, beim Start des Plugins
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
		System.out.println("CityManager Plugin erfolgreich gestartet");
	}
	
	@Override
	public void onDisable() {
		System.out.println("CityManager Plugin erfolgreich beendet");
	}
	
	/** 
	 * Methode überprüft ob das übergebene Player-Objekt bereits in der Datenbank existiert
	 * @param Player-Objekt
	 * @return Bool-Wert, Player existiert in der DB oder Player existiert nicht
	 */
	
	public static boolean checkPlayerInDB(Player p)  {

		boolean check = false;
		
		DbConnector.openConnection();
		Connection conn = DbConnector.getConnection();
		
		//Bestimmt das aktuelles Datum und Uhrzeit
		Calendar calendar = Calendar.getInstance();
		Date date = new java.sql.Date(calendar.getTime().getTime());
		
		String query = "SELECT * FROM player WHERE uuid = ?";
		
		try {

			//PreparedStatement für erweitere Sicherheit der Abfrage
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, p.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			
			//Falls ein Player-Objekt in der DB existiert
			if (rs.next()) {
				check = true;
				//Player wird der CityPlayer Online-Liste hinzugefügt
				CityPlayer.addCityPlayer(rs.getString("uuid"), new CityPlayer(rs.getString("uuid"), rs.getString("name"), rs.getInt("money"), rs.getDate("first_join"), date, rs.getInt("exp")));
			}
			
			DbConnector.closeConnection();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
		return check;
	}
	
	/**
	 * Speichert Player-Datensatz in der Tabelle 'Player' ab
	 * @param Player-Objekt
	 */
	
	public static void insertPlayerIntoDB(Player p) {
		
		try {
			DbConnector.openConnection();
			Connection conn = DbConnector.getConnection();
			
			//Bestimmt das aktuelles Datum und Uhrzeit
			Calendar calendar = Calendar.getInstance();
			Date date = new java.sql.Date(calendar.getTime().getTime());
			
			String query = "INSERT INTO player VALUES (?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, p.getUniqueId().toString());
			ps.setString(2, p.getDisplayName());
			ps.setInt(3, 1000);
			ps.setDate(4, date);
			ps.setDate(5, date);
			ps.setInt(6, 0);
			ps.executeUpdate();
			
			//Player wird der CityPlayer Online-Liste hinzugefügt
			CityPlayer.addCityPlayer(p.getUniqueId().toString(), new CityPlayer(p.getUniqueId().toString(), p.getDisplayName(), 1000, date, date, 0));
			
			DbConnector.closeConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Updated Player-Datensatz in der Tabelle 'Player'
	 * @param Player-Objekt
	 */
	
	public static void updatePlayerInDB(Player p) {
		
		DbConnector.openConnection();
		Connection conn = DbConnector.getConnection();
		
		String query = "UPDATE player Set name = ?, money = ?, last_join = ?, exp = ? WHERE uuid = ?";
		CityPlayer cp = CityPlayer.getCityPlayer(p.getUniqueId().toString());
		
		if (cp != null) {
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, cp.Name);
				ps.setInt(2, cp.Money);
				ps.setDate(3, cp.LastJoin);
				ps.setInt(4, cp.EXP);
				ps.setString(5, cp.UUID);
				
				ps.executeUpdate();
				
				DbConnector.closeConnection();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			} 
		}
	}
}

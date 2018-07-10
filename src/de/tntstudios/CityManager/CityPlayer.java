package de.tntstudios.CityManager;

import java.sql.Date;
import java.util.HashMap;

public class CityPlayer {

	/**
	 * CityPlayer Properties
	 * UUID => eindeutige Player-ID
	 * Name => Anzeige-Name
	 * Money => Player-Guthaben
	 * FirstJoin => Erste Anmeldung am Server; Datum mit Uhrzeit
	 * LastJoin => Letzte Anmeldung am Server; Datum mit Uhrzeit
	 * EXP => Spieler-Erfahrung
	 */
	
	public String UUID;
	public String Name;
	public int Money;
	public Date FirstJoin;
	public Date LastJoin;
	public int EXP;
	
	//Konstruktor
	public CityPlayer(String uuid, String name, int money, Date firstjoin, Date lastjoin, int exp) {
		UUID = uuid;
		Name = name;
		Money = money;
		FirstJoin = firstjoin;
		LastJoin = lastjoin;
		EXP = exp;
	}
	
	/**
	 * Gibt Spieler-Liste zurück
	 * @return Hashmap mit Online-Playern 
	 */
	
	public static HashMap<String, CityPlayer> getCityPlayers() {
		return CityManager.cityPlayers;
	}
	
	/**
	 * Fügt Player der Spieler-Liste hinzu 
	 * @param uuid
	 * @param CityPlayer-Objekt
	 */
	
	public static void addCityPlayer(String uuid, CityPlayer cp) {
		CityManager.cityPlayers.put(uuid, cp);
	}
	
	/**
	 * Enfernt Player aus der Spieler-Liste
	 * @param uuid
	 */
	
	public static void removeCityPlayer(String uuid) {
		CityManager.cityPlayers.remove(uuid);
	}
	
	/**
	 * Holt Player aus Spieler-Liste über die UUID
	 * @param uuid
	 * @return City-Player Objekt 
	 */
	
	public static CityPlayer getCityPlayer(String uuid) {
		CityPlayer cp = CityManager.cityPlayers.get(uuid);
		return cp;
	}
}

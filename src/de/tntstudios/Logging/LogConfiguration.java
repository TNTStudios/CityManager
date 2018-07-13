package de.tntstudios.Logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogConfiguration {

	public static void init(Logger l) {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logconfig.properties"));
		} catch (SecurityException | IOException ex) {
			ex.printStackTrace();
		}
		
		l.setLevel(Level.INFO);
		
		l.addHandler(new ConsoleHandler());
		
		l.addHandler(new LogHandler());
		
		try {
			Handler fileHandler = new FileHandler("../log/server.log", true);
			fileHandler.setFormatter(new LogFormatter());
			l.addHandler(fileHandler);
		} catch (SecurityException | IOException ex) {
			ex.printStackTrace();
		}
	}
}

package de.tntstudios.Logging;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class LogHandler extends StreamHandler {
	
	@Override
	public void publish(LogRecord rec) {
		super.publish(rec);
	}
	
	@Override
	public void flush() {
		super.flush();
	}
	
	@Override
	public void close() throws SecurityException{
		super.close();
	}
}

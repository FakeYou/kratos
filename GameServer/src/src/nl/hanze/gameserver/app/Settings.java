package nl.hanze.gameserver.app;

import java.io.File;

import nl.hanze.gameserver.util.SettingsFile;

public class Settings {

	public static final int ACTION_REMOVE = 0;
	public static final int ACTION_LOSS = 1;
	
	private static final String LISTENER_PORT = "listener_port";
	private static final String GAMEMODULE_PATH = "gamemodule_path";
	private static final String TURN_TIMELIMIT = "turn_timelimit_sec";
	private static final String TOURNAMENT_TURN_DELAY = "tournament_turn_delay_sec";
	private static final String TOURNAMENT_DISCONNECT = "tournament_disconnect";
    private static final String RUN_WITH_GUI = "run_with_gui";
	
	private SettingsFile settingsFile;
	
	private int listenerPort;
	private String gameModulePath;
	private int turnTimeLimit;
	private int tournamentTurnDelay;
	private int tournamentDisconnectAction;
    private boolean runWithGui;
	
	public Settings(File file) {
		settingsFile = new SettingsFile(file);
		settingsFile.load();
		
		initValues();
	}
	
	private void initValues() {
		listenerPort = settingsFile.getIntValue(LISTENER_PORT, 7789);
		gameModulePath = settingsFile.getValue(GAMEMODULE_PATH, "gamemodules");
		turnTimeLimit = settingsFile.getIntValue(TURN_TIMELIMIT, 10);
		tournamentTurnDelay = settingsFile.getIntValue(TOURNAMENT_TURN_DELAY, 0);
		tournamentDisconnectAction = settingsFile.getValue(TOURNAMENT_DISCONNECT, "remove").equals("loss") ? ACTION_LOSS : ACTION_REMOVE;
        runWithGui = settingsFile.getBooleanValue(RUN_WITH_GUI, true);
	}
	
	public void save() {
		settingsFile.setIntValue(LISTENER_PORT, listenerPort);
		settingsFile.setValue(GAMEMODULE_PATH, gameModulePath);
		settingsFile.setIntValue(TURN_TIMELIMIT, turnTimeLimit);
		settingsFile.setIntValue(TOURNAMENT_TURN_DELAY, tournamentTurnDelay);
		settingsFile.setValue(TOURNAMENT_DISCONNECT, tournamentDisconnectAction == ACTION_LOSS ? "loss" : "remove");
        settingsFile.setBooleanValue(RUN_WITH_GUI, runWithGui);
		
		settingsFile.save();
	}
	
	public int getListenerPort() {
		return listenerPort;
	}
	
	public void setListenerPort(int port) {
		listenerPort = port;
	}
	
	public String getGameModulePath() {
		return gameModulePath;
	}
	
	public void setGameModulePath(String gameModulePath) {
		this.gameModulePath = gameModulePath;
	}
	
	public int getTurnTimeLimit() {
		return turnTimeLimit;
	}
	
	public void setTurnTimeLimit(int timeLimit) {
		turnTimeLimit = timeLimit;
	}
	
	public int getTournamentTurnDelay() {
		return tournamentTurnDelay;
	}
	
	public void setTournamentTurnDelay(int delay) {
		tournamentTurnDelay = delay;
	}
	
	public int getTournamentDisconnectAction() {
		return tournamentDisconnectAction;
	}
	
	public void setTournamentDisconnectAction(int action) {
		tournamentDisconnectAction = action;
	}

    public boolean getRunWithGui() { return runWithGui; }

    public void setRunWithGui(boolean value) { runWithGui = value; }

}

package de.louidev.magicmonke.main;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import de.louidev.magicmonke.cutscenes.NeptuneDeathCutscene;
import de.louidev.magicmonke.data.BuffGoal;
import de.louidev.magicmonke.data.BuffGoals;
import de.louidev.magicmonke.data.DataManager;
import de.louidev.magicmonke.inputs.ActionsInput;
import de.louidev.magicmonke.inputs.MovementInput;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.magicmonke.objects.PlayerObject;
import de.louidev.magicmonke.objects.TransitionObject;
import de.louidev.magicmonke.rooms.BossRoom;
import de.louidev.magicmonke.rooms.EndRoom;
import de.louidev.magicmonke.rooms.GameRoom;
import de.louidev.magicmonke.rooms.IntroRoom;
import de.louidev.magicmonke.rooms.Room;
import de.louidev.magicmonke.rooms.TitleScreenRoom;
import de.louidev.magicmonke.wave.Wave;
import de.louidev.magicmonke.wave.WaveManager;
import de.louidev.meteorbanex.plugin.Plugin;

public class Game {
	
	public static int windowWidth = 1280;
	public static int windowHeigth = 800;
	public static int waveCount = 1;
	
	public static WaveManager waveManager;
	public static Wave currentWave;
	
	public static ResourceManager resourceManager;
	
	public static BuffGoals buffGoals;
	public static BuffGoal nextGoal;
	public static boolean extraNeptuneLife;
	
	private Room currentRoom;
	private GameRoom gameRoom;
	private TitleScreenRoom tsRoom;
	private IntroRoom introRoom;
	private EndRoom endRoom;
	private BossRoom bossRoom;
	
	private PlayerObject player;
	
	private boolean isRunning;
	private boolean pauseLoop;
	
	public static int maxNeptuneHealth;
	public static int neptuneHealth;
	
	public static List<Plugin> plugins;
	
	private NeptuneDeathCutscene neptuneDeathCutscene;
	
	private MovementInput movementInput;
	private ActionsInput actionsInput;
	
	private WindowFrame frame;

	public Game() {
//		plugins = new ArrayList<>();
		resourceManager = new ResourceManager();
		
		this.player = new PlayerObject();
		this.movementInput = new MovementInput();
		this.actionsInput = new ActionsInput();
		
		extraNeptuneLife = false;
		
		DataManager.save(new BuffGoals(), "buffgoals.sav");        //RESET
		
		resetWaves();
		
		loadData();
		
//		buffGoals.getGoalByBuff("ExtraNeptuneLife").setReached(true);
//		buffGoals.getGoalByBuff("LifeMeteor").setReached(true);
//		buffGoals.save();
		
		maxNeptuneHealth = 3;
		if(extraNeptuneLife) maxNeptuneHealth = 4;
		neptuneHealth = maxNeptuneHealth;
		
		neptuneDeathCutscene = new NeptuneDeathCutscene();
		
		initRooms();
		
//		plugins = PluginManager.loadPlugins();
		
		frame = new WindowFrame(currentRoom);
		frame.getFrame().addKeyListener(movementInput);
		frame.getFrame().addKeyListener(actionsInput);
		
	    int targetFPS = 120;
	    long targetTime = 1000000000 / targetFPS; 
		this.isRunning = true;
		this.pauseLoop = false;
		
		while (isRunning) {
			long startTime = System.nanoTime();
			
			if(!pauseLoop) currentRoom.update();
			
			currentRoom.updateCutscenes();
			
			updateNeptuneHealth();
			
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            long waitTime = targetTime - elapsedTime;
            
            if(waitTime > 0) {
            	try {
                	long waitMillis = waitTime / 1000000;
					Thread.sleep(waitMillis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
		}
		
		System.exit(0);
	}
	
	public void loadData() {
		buffGoals = DataManager.get(BuffGoals.class, "buffgoals.sav");
		nextGoal = buffGoals.getNextGoal();
		
		extraNeptuneLife = buffGoals.getGoalByBuff("ExtraNeptuneLife").isReached();
		if(extraNeptuneLife) maxNeptuneHealth = 4;
		if(neptuneHealth == 3) neptuneHealth = maxNeptuneHealth;
	}
	
	private void updateNeptuneHealth() {
		if(neptuneHealth <= 0 && neptuneHealth >= -10) {
			currentRoom.playCutscene(neptuneDeathCutscene);
			neptuneHealth = -11;
		}
	}

	private void initRooms() {
		resetRooms();
		
		this.currentRoom = tsRoom;
		((TitleScreenRoom) tsRoom).init();
		spawnObject(new TransitionObject(true, null));
		
	}
	
	public void resetRooms() {
		this.gameRoom = new GameRoom(this);
		this.tsRoom = new TitleScreenRoom(this);
		this.introRoom = new IntroRoom(this);
		this.endRoom = new EndRoom(this);
		this.bossRoom = new BossRoom(this);
		
		loadData();
		
		if(extraNeptuneLife) maxNeptuneHealth = 4;
		neptuneHealth = maxNeptuneHealth;
	}
	
	public static void resetWaves() {
		waveManager = new WaveManager();
		currentWave = waveManager.getNextWave();
	}
	
	public BossRoom getBossRoom() {
		return bossRoom;
	}
	
	public EndRoom getEndRoom() {
		return endRoom;
	}
	
	public int getNeptuneHealth() {
		return neptuneHealth;
	}
	
	public void reduceNeptuneHealth() {
		neptuneHealth--;
	}
	
	public GameRoom getGameRoom() {
		return gameRoom;
	}
	
	public TitleScreenRoom getTitleScreenRoom() {
		return tsRoom;
	}
	
	public IntroRoom getIntroRoom() {
		return introRoom;
	}
	
	public void gotToRoom(Room room) {
		currentRoom = frame.gotToRoom(room);
	}
	
	public void pauseUpdateLoop() {
		pauseLoop = true;
	}
	
	public void continueUpdateLoop() {
		pauseLoop = false;
	}

	public void resetInputs() {
		movementInput.reset();
		actionsInput.reset();
	}
	
	public void spawnObject(GameObject obj) {
		currentRoom.spawnObject(obj);
	}
	
	public void despawnObject(GameObject obj) {
		currentRoom.despawnObject(obj);
	}
	
	public void endGame() {
		this.isRunning = false;
	}
	
	public PlayerObject getPlayer() {
		return player;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeigth() {
		return windowHeigth;
	}
	
	public MovementInput getMovementInput() {
		return movementInput;
	}
	
	public ActionsInput getActionsInput() {
		return actionsInput;
	}
	
	public class WindowFrame {
		
		private JFrame frame;
		private Room currentRoom;
		
		public WindowFrame(Room room) {
			this.frame = new JFrame();
			this.currentRoom = room;
			
			frame.setTitle("Meteorbane X");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setSize(new Dimension(windowWidth, windowHeigth));
			frame.setUndecorated(true);
			frame.add(currentRoom.getPanel());
			frame.pack();
			
			frame.setVisible(true);
		}
		
		public Room gotToRoom(Room room) {
			frame.remove(currentRoom.getPanel());
			frame.add(room.getPanel());
			frame.revalidate();
			room.getPanel().repaint();
			return room;
		}
		
		public JFrame getFrame() {
			return frame;
		}
		
	}

}

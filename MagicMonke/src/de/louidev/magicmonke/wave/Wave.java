package de.louidev.magicmonke.wave;

import de.louidev.magicmonke.main.Game;

public class Wave {
	
	private boolean hasStarted;
	private boolean isFinished;
	private IWave iWave;
	private IWaveFinish onFinish;
	private MeteorSet[] sets;
	
	public Wave(IWave iWave) {
		this.iWave = iWave;
		this.onFinish = (game) -> {};
		
		hasStarted = false;
		isFinished = false;
	}
	
	public Wave(IWave iWave, IWaveFinish onFinish) {
		this.iWave = iWave;
		this.onFinish = onFinish;
		
		hasStarted = false;
		isFinished = false;
	}
	
	public void start(Game game) {
		hasStarted = true;
		sets = iWave.startWave(game);
	}
	
	public void onFinish(Game game) {
		onFinish.onFinish(game);
	}
	
	public void setFinished(boolean finished) {
		isFinished = finished;
	}
	
	public boolean hasStarted() {
		return hasStarted;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public MeteorSet[] getMeteorSets() {
		return sets;
	}
	
	public interface IWave {
		
		public MeteorSet[] startWave(Game game);
		
	}
	
	public interface IWaveFinish {
		
		public void onFinish(Game game);
		
	}
	
}

package de.louidev.magicmonke.wave;

import java.util.ArrayList;
import java.util.List;

import de.louidev.magicmonke.cutscenes.NewBuffCutscene;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.objects.HeavyMeteorObject;
import de.louidev.magicmonke.objects.HeavyMeteorObject.HeavyMeteorValuePlaceholder;
import de.louidev.magicmonke.objects.MeteorObject;
import de.louidev.magicmonke.objects.MeteorObject.MeteorValuePlaceholder;
import de.louidev.magicmonke.objects.boss.FatherOfMeteorsBossObject;
import de.louidev.magicmonke.rooms.GameRoom;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class WaveManager {
	
	private List<Wave> waves;
	
	public WaveManager() {
		waves = new ArrayList<>();
		
		// Wave 1
		waves.add(new Wave((game) -> {
			MeteorValuePlaceholder mvp1 = new MeteorValuePlaceholder();
			mvp1.setSpeed(2);
			
			MeteorValuePlaceholder mvp2 = new MeteorValuePlaceholder();
			mvp2.setSpeed(3);
			
			MeteorSet set1 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set2 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set3 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set4 = new MeteorSet(mvp1, mvp2, mvp1);
			MeteorSet set5 = new MeteorSet(mvp1, mvp2, mvp1);
			MeteorSet set6 = new MeteorSet(mvp1, mvp2, mvp1);
			
			MeteorSet[] sets = {set1, set2, set3, set4, set5, set6};
			
			SimpleTimer.repeatFor(3000, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}));
		
//		, (game) -> {
//			game.gotToRoom(game.getBossRoom());
//			game.getBossRoom().startBossFight(new FatherOfMeteorsBossObject());
//		}

		
		// Wave 2
		waves.add(new Wave((game) -> {
			MeteorValuePlaceholder mvp1 = new MeteorValuePlaceholder();
			mvp1.setSpeed(2);
			
			MeteorValuePlaceholder mvp2 = new MeteorValuePlaceholder();
			mvp2.setSpeed(4);
			
			MeteorSet set1 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set2 = new MeteorSet(mvp1, mvp2, mvp1);
			MeteorSet set3 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set4 = new MeteorSet(mvp1, mvp2, mvp1);
			MeteorSet set5 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set6 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set7 = new MeteorSet(mvp1, mvp2, mvp1);
			
			MeteorSet[] sets = {set1, set2, set3, set4, set5, set6, set7};
			
			SimpleTimer.repeatFor(3000, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}));
		
		// Wave 3
		waves.add(new Wave((game) -> {
			MeteorValuePlaceholder mvp = new MeteorValuePlaceholder();
			mvp.setSpeed(1);
			
			MeteorSet set1 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set2 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set3 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set4 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set5 = new MeteorSet(mvp, mvp, mvp);
			
			MeteorSet[] sets = {set1, set2, set3, set4, set5};
			
			SimpleTimer.repeatFor(1500, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}));
		
		// Wave 4
		waves.add(new Wave((game) -> {
			MeteorValuePlaceholder mvp1 = new MeteorValuePlaceholder();
			mvp1.setSpeed(3);
			
			MeteorValuePlaceholder mvp2 = new MeteorValuePlaceholder();
			mvp2.setSpeed(5);
			
			MeteorSet set1 = new MeteorSet(mvp1, mvp2, mvp1);
			MeteorSet set2 = new MeteorSet(mvp1, mvp2, mvp1);
			MeteorSet set3 = new MeteorSet(mvp1, mvp2, mvp1);
			
			MeteorSet set4 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set5 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set6 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set7 = new MeteorSet(mvp1, mvp1, mvp1);
			
			MeteorSet[] sets = {set1, set2, set3, set4, set5, set6, set7};
			
			SimpleTimer.repeatFor(2000, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}, (game) -> {
			if(!Game.buffGoals.getGoalByBuff("ExtraNeptuneLife").isReached()) {
				NewBuffCutscene cutscene = new NewBuffCutscene();
				game.getGameRoom().playCutscene(cutscene);
			}
		}));
		
		// Wave 5
		waves.add(new Wave((game) -> {
			MeteorValuePlaceholder mvp = new MeteorValuePlaceholder();
			mvp.setSpeed(3);
			
			MeteorSet set1 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set2 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set3 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set4 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set5 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set6 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set7 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set8 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set9 = new MeteorSet(mvp, mvp, mvp);
			MeteorSet set10 = new MeteorSet(mvp, mvp, mvp);
			
			MeteorSet[] sets = {set1, set2, set3, set4, set5, set6, set7, set8, set9, set10};
			
			SimpleTimer.repeatFor(2000, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}));
		
		// Wave 6
		waves.add(new Wave((game) -> {
			HeavyMeteorValuePlaceholder hmvp = new HeavyMeteorValuePlaceholder();
			hmvp.setSpeed(2);
			
			MeteorValuePlaceholder mvp1 = new MeteorValuePlaceholder();
			mvp1.setSpeed(5);
			
			MeteorValuePlaceholder mvp2 = new MeteorValuePlaceholder();
			mvp2.setSpeed(3);
			
			MeteorSet set1 = new MeteorSet(hmvp, mvp2, mvp2);
			MeteorSet set2 = new MeteorSet(hmvp, mvp2, mvp2);
			MeteorSet set3 = new MeteorSet(hmvp, mvp2, mvp2);
			MeteorSet set4 = new MeteorSet(hmvp, hmvp, hmvp);
			MeteorSet set5 = new MeteorSet(mvp1, hmvp, mvp2);
			MeteorSet set6 = new MeteorSet(hmvp, hmvp, mvp1);
			
			MeteorSet[] sets = {set1, set2, set3, set4, set5, set6};
			
			SimpleTimer.repeatFor(2000, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}));
		
		// Wave 7
		waves.add(new Wave((game) -> {
			MeteorValuePlaceholder mvp1 = new MeteorValuePlaceholder();
			mvp1.setSpeed(3);
			
			HeavyMeteorValuePlaceholder hmvp = new HeavyMeteorValuePlaceholder();
			hmvp.setSpeed(2);
			
			MeteorSet set1 = new MeteorSet(mvp1, mvp1, mvp1, mvp1);
			MeteorSet set2 = new MeteorSet(mvp1, mvp1, mvp1, mvp1);
			MeteorSet set3 = new MeteorSet(mvp1, mvp1, hmvp, mvp1);
			MeteorSet set4 = new MeteorSet(mvp1, hmvp, hmvp);
			
			MeteorSet[] sets = {set1, set2, set3, set4};
			
			SimpleTimer.repeatFor(2500, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}));
		
		// Wave 8
		waves.add(new Wave((game) -> {
			MeteorValuePlaceholder mvp1 = new MeteorValuePlaceholder();
			mvp1.setSpeed(3);
			
			HeavyMeteorValuePlaceholder hmvp = new HeavyMeteorValuePlaceholder();
			hmvp.setSpeed(3);
			
			MeteorSet set1 = new MeteorSet(hmvp, hmvp);
			MeteorSet set2 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set3 = new MeteorSet(mvp1, mvp1, hmvp, mvp1);
			MeteorSet set4 = new MeteorSet(mvp1, mvp1, mvp1);
			
			MeteorSet[] sets = {set1, set2, set3, set4};
			
			SimpleTimer.repeatFor(3000, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}, (game) -> {
			if(!Game.buffGoals.getGoalByBuff("LifeMeteor").isReached()) {
				NewBuffCutscene cutscene = new NewBuffCutscene();
				game.getGameRoom().playCutscene(cutscene);
			}
		}));
		
		// Wave 9
		waves.add(new Wave((game) -> {
			MeteorValuePlaceholder mvp1 = new MeteorValuePlaceholder();
			mvp1.setSpeed(2);
			
			HeavyMeteorValuePlaceholder hmvp = new HeavyMeteorValuePlaceholder();
			hmvp.setSpeed(3);
			
			MeteorSet set1 = new MeteorSet(hmvp, hmvp);
			MeteorSet set2 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set3 = new MeteorSet(mvp1, mvp1, hmvp, mvp1);
			MeteorSet set4 = new MeteorSet(mvp1, mvp1, hmvp, mvp1);
			MeteorSet set5 = new MeteorSet(mvp1, mvp1, hmvp, mvp1);
			MeteorSet set6 = new MeteorSet(mvp1, mvp1, mvp1);
			MeteorSet set7 = new MeteorSet(hmvp, hmvp);
			
			MeteorSet[] sets = {set1, set2, set3, set4, set5, set6, set7};
			
			SimpleTimer.repeatFor(2000, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}));
		
		// Wave 10
		waves.add(new Wave((game) -> {			
			HeavyMeteorValuePlaceholder hmvp = new HeavyMeteorValuePlaceholder();
			hmvp.setSpeed(3);
			
			MeteorSet set1 = new MeteorSet(hmvp, hmvp, hmvp);
			MeteorSet set2 = new MeteorSet(hmvp, hmvp, hmvp);
			MeteorSet set3 = new MeteorSet(hmvp, hmvp, hmvp);
			MeteorSet set4 = new MeteorSet(hmvp, hmvp, hmvp);
			
			MeteorSet[] sets = {set1, set2, set3, set4};
			
			SimpleTimer.repeatFor(1500, 0, sets.length, () -> {
				for(MeteorSet set : sets) {
					if(!set.isSpawned()) {
						set.spawn(game);
						break;
					}
				}
			});
			
			return sets;
		}));
	}
	
	public int completedWaveCount() {
		int x = 0;
		for(Wave w : waves) {
			if(w.isFinished()) {
				x++;
			}
		}
		return x;
	}
	
	public boolean checkFinished(GameRoom room, Wave wave) {
		MeteorSet[] sets = wave.getMeteorSets();
		if(sets[sets.length - 1].isSpawned()) {
			if(!room.containsClass(MeteorObject.class) && !room.containsClass(HeavyMeteorObject.class)) {
				return true;
			}
		}
		return false;
	}
	
	public void setFinished(Wave wave) {
		waves.get(waves.indexOf(wave)).setFinished(true);
	}
	
	public Wave getCurrentWave() {
		for(Wave wave : waves) {
			if(wave.hasStarted() && !wave.isFinished()) {
				return wave;
			}
		}
		return null;
	}
	
	public Wave getNextWave() {
		for(Wave wave : waves) {
			if(!wave.isFinished() && !wave.hasStarted()) {
				return wave;
			}
		}
		return null;
	}
	
}

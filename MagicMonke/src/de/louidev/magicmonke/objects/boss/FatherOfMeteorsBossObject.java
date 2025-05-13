package de.louidev.magicmonke.objects.boss;

import java.awt.image.BufferedImage;
import java.util.Random;

import de.louidev.magicmonke.animations.Animation;
import de.louidev.magicmonke.cutscenes.Cutscene;
import de.louidev.magicmonke.main.AudioManager;
import de.louidev.magicmonke.main.Game;
import de.louidev.magicmonke.main.ResourceManager;
import de.louidev.magicmonke.objects.GameObject;
import de.louidev.magicmonke.objects.HeavyMeteorObject;
import de.louidev.magicmonke.objects.MeteorObject;
import de.louidev.magicmonke.objects.PlayerObject;
import de.louidev.sdk.simpletimer.SimpleTimer;

public class FatherOfMeteorsBossObject extends BossObject {
	
	private Cutscene spawnCutscene;
	
	private Animation currentAnimation;
	private Animation laughAnimation;
	private Animation idleAnimation;
	private Animation castMeteorsAnimation;
	
	private AnimationIndex animIndex;
	
	private BossAttack currentTask;
	
	private int attackCooldown;
	private int actionIndex;
	
	private boolean active;
	
	public FatherOfMeteorsBossObject() {
		super(Game.windowWidth, (Game.windowHeigth / 2) - 64, 256, 256, 20, "father-of-meteors");
		
		spawnCutscene = new SpawnCutscene(this);
		
		laughAnimation = new Animation("father-of-meteors-laughing", 64, 7, 30);
		idleAnimation = new Animation("father-of-meteors-idle", 64, 5, 90);
		castMeteorsAnimation = new Animation("father-of-meteors-cast-meteors", 64, 5, 20);
		
		animIndex = AnimationIndex.IDLE;
		
		attackCooldown = 600;
		actionIndex = -1;
		
		currentTask = null;
		
		active = false;
	}
	
	@Override
	public void onStart(Game game) {
		changeAnimation(AnimationIndex.IDLE);
		currentAnimation.pause();
		game.getBossRoom().playCutscene(spawnCutscene);
	}
	
	@Override
	public void act(Game game) {
		if(active) {
			
			// KI
			if(currentTask == null) {
				if(attackCooldown <= 0) {
					chooseAction();
					attackCooldown = 600;
				}
				
				// Meteor Cast
				if(actionIndex == 0) {
					currentTask = new CastMeteorAttack(game);
					currentTask.init(game);
					currentTask.start(game);
					actionIndex = -1;
				}
				
				//Airstrike Attack
				if(actionIndex == 1) {
					currentTask = new AirstrikeAttack(game);
					currentTask.init(game);
					currentTask.start(game);
					actionIndex = -1;
				}
				
				attackCooldown--;
			} else {
				currentTask.update(game);
				
				if(currentTask.isFinished()) {
					currentTask = null;
				}
			}
			
		} else {
			if(!spawnCutscene.isPlaying()) {
				active = true;
			}
		}
		
		if(currentAnimation != null) currentAnimation.update();
	}

	@Override
	public BufferedImage getCurrentImage() {
		return currentAnimation.getCurrentImage();
	}
	
	private void chooseAction() {
		if(currentTask == null) {
			Random rnd = new Random();
			actionIndex = rnd.nextInt(0, 3);
		}
	}
	
	public void changeAnimation(AnimationIndex index) {
		animIndex = index;
		laughAnimation.reset();
		idleAnimation.reset();
		castMeteorsAnimation.reset();
		
		if(animIndex == AnimationIndex.LAUGHING) {
			currentAnimation = laughAnimation;
		}
		
		if(animIndex == AnimationIndex.IDLE) {
			currentAnimation = idleAnimation;
		}
		
		if(animIndex == AnimationIndex.CAST_METEORS) {
			currentAnimation = castMeteorsAnimation;
		}
	}
	
	public Cutscene getSpawnCutscene() {
		return spawnCutscene;
	}
	
	public enum AnimationIndex {
		IDLE,
		LAUGHING,
		CAST_METEORS
	}
	
	public class SpawnCutscene extends Cutscene {
		
		private FatherOfMeteorsBossObject obj;
		
		private int step;
		
		public SpawnCutscene(FatherOfMeteorsBossObject obj) {
			this.obj = obj;
			
			step = 0;
		}
		
		@Override
		public void update(Game game) {
			if(step == 0) {
				if(obj.getxPos() > Game.windowWidth - 230) {
					updatePos(xPos - 1, yPos);
				} else {
					step = 1;
					obj.changeAnimation(AnimationIndex.LAUGHING);
					
					SimpleTimer.delay(2000, () -> {
						obj.changeAnimation(AnimationIndex.IDLE);
						idleAnimation.resume();
						stop(game);
					});
				}
			}
		}
		
	}
	
	public class CastMeteorAttack extends BossAttack {

		private int meteorGap;
		private int duration;
		private boolean moveUp;
		private boolean moveDown;
		
		public CastMeteorAttack(Game game) {
			super(game);
		}
		
		@Override
		public void init(Game game) {
			isFinished = false;
			meteorGap = 80;
			duration = new Random().nextInt(900, 1400);
			moveDown = true;
			moveUp = false;
			
			changeAnimation(AnimationIndex.CAST_METEORS);
		}

		@Override
		public void end(Game game) {
			castMeteorsAnimation.resume();
			actionIndex = -1;
			isFinished = true;
			changeAnimation(AnimationIndex.IDLE);
		}

		@Override
		public void update(Game game) {
			if(isFinished) return;
			
			duration--;
			meteorGap--;
			
			updateMovement();
			
			if(meteorGap == 0) {
				spawnMeteor(game);
				meteorGap = 80;
			}
			
			if(castMeteorsAnimation.getCurrentIndex() == 2) castMeteorsAnimation.pause();
			
			if(duration == 0) end(game);
		}
		
		private void updateMovement() {
			if(moveUp) {
				updatePos(xPos, yPos - 1);
				
				if(yPos == 75) {
					moveUp = false;
					moveDown = true;
				}
			}
			
			if(moveDown) {
				updatePos(xPos, yPos + 1);
				
				if(yPos == Game.windowHeigth - 350) {
					moveUp = true;
					moveDown = false;
				}
			}
		}
		
		private void spawnMeteor(Game game) {
			Random rnd = new Random();
			int type = rnd.nextInt(0, 5);
			
			if(type == 0) {
				HeavyMeteorObject hmo = new HeavyMeteorObject(yPos, 3);
				hmo.updatePos(xPos - 5, yPos + 92);
				game.spawnObject(hmo);
			} else {
				MeteorObject obj = new MeteorObject(yPos, 3);
				obj.updatePos(xPos - 5, yPos + 92);
				game.spawnObject(obj);
			}
		}
		
	}
	
	public class AirstrikeAttack extends BossAttack {

		public AirstrikeAttack(Game game) {
			super(game);
		}
		
		@Override
		public void start(Game game) {
//			AudioManager.playSound("/sounds/beep-alert.wav");
			
			for(int i = 0; i < 3; i++) {
				Random rnd = new Random();
				int rndy = rnd.nextInt(64, Game.windowHeigth - 100);
				AirstrikeObject obj = new AirstrikeObject(game.getPlayer().getxPos(), rndy);
				game.spawnObject(obj);
			}
			isFinished = true;
		}
		
	}
	
	public class AirstrikeObject extends GameObject {
		
		private int timer;
		
		public AirstrikeObject(int xPos, int yPos) {
			super(xPos, yPos, 64, 64, "target-indikator");
			
			timer = 240;
		}
		
		@Override
		public void act(Game game) {			
			if(timer == 0) {
				game.spawnObject(new DeathZoneObject(xPos, yPos, 64, 64));
				game.despawnObject(this);
			} else {
				timer--;
			}
		}
		
	}
	
	public class DeathZoneObject extends GameObject {
		
		Animation animation;
		
		public DeathZoneObject(int xPos, int yPos, int width, int heigth) {
			super(xPos, yPos, width, heigth, "explosion");
			
			animation = new Animation("explosion", 32, 3, 10);
		}
		
		@Override
		public void onCollision(Game game, GameObject object) {
			if(object instanceof PlayerObject) {
				game.getPlayer().death(game);
			}
		}
		
		@Override
		public void onSpawn(Game game) {
			AudioManager.playSound("/sounds/explosion.wav");
		}
		
		@Override
		public void act(Game game) {
			animation.update();
			
			if(animation.getCurrentIndex() == 2 && animation.getCurrentTime() == animation.getTimePerImage() - 1) {
				game.despawnObject(this);
			}
		}
		
		@Override
		public BufferedImage getCurrentImage() {
			return animation.getCurrentImage();
		}
		
	}

}

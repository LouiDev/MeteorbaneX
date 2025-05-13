package de.louidev.magicmonke.data;

import java.io.Serializable;
import java.util.ArrayList;

public class BuffGoals implements Serializable {

	private static final long serialVersionUID = 3113612375806722815L;
	private ArrayList<BuffGoal> goals;
	
	public BuffGoals() {
		goals = new ArrayList<>();
		
		goals.add(new BuffGoal("ExtraNeptuneLife", "neptune-life-buff"));
		goals.add(new BuffGoal("LifeMeteor", "life-meteor-buff"));
		goals.add(new BuffGoal("BulletSizeIncrease", "bullet-size-increase-buff"));
		goals.add(new BuffGoal("BulletSpeedIncrease", "bullet-speed-increase-buff"));
	}
	
	public void save() {
		DataManager.save(this, "buffgoals.sav");
	}
	
	public void setReached(BuffGoal goal) {
		goals.get(goals.indexOf(goal)).setReached(true);
	}
	
	public void setReached(String byBuff) {
		goals.get(goals.indexOf(getGoalByBuff(byBuff))).setReached(true);
	}
	
	public BuffGoal getNextGoal() {
		for(BuffGoal goal : goals) {
			if(!goal.isReached()) {
				return goal;
			}
		}
		return null;
	}
	
	public BuffGoal getGoalByBuff(String buff) {
		for(BuffGoal goal : goals) {
			if(goal.getBuff().equals(buff)) {
				return goal;
			}
		}
		return null;
	}
	
	public ArrayList<BuffGoal> getGoals() {
		return goals;
	}

}

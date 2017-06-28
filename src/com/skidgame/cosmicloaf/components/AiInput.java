
package com.skidgame.cosmicloaf.components;

import java.util.ArrayList;

import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

import com.skidgame.cosmicloaf.Level;
import com.skidgame.cosmicloaf.game.Camera;
import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;
import com.skidgame.cosmicloaf.systems.AiInputSystem;
import com.skidgame.cosmicloaf.systems.CollisionSystem;

public class AiInput extends Component implements Mover{

	private boolean alert;
	private boolean patrol;
	private boolean isHostile;
	private int targetBox;
	private ArrayList<Integer> patrolPoints;
	private int currentPatrolPoint;
	private Path pathToTarget;
	private int sightDistance;
	
	private static final int FOLLOW_RADIUS = 3;
	
	/**
	 * A constructor, wow!
	 * @param alert
	 * @param patrol
	 * @param targetBox
	 * @param sightDistance
	 */
	public AiInput(boolean alert, boolean patrol, int targetBox, int sightDistance)
	{
		this.alert = alert;
		this.patrol = patrol;
		this.targetBox = targetBox;
		this.sightDistance = sightDistance;
	}
	
	
	@Override
	public int getID() {
		return ComponentID.AI_INPUT.ordinal();
	}


	public boolean isAlert() {
		return alert;
	}


	public void setAlert(boolean alert) {
		this.alert = alert;
	}


	public boolean isPatrol() {
		return patrol;
	}


	public void setPatrol(boolean patrol) {
		this.patrol = patrol;
	}


	public int getTargetBox(Entity ai, Level level, TheCosmicLoafGame game) {
		return 1001;
//		if(alert)
//		{
//			//TODO make this work
//			int playerPosition = CollisionSystem.convertLiteralXYToBox(level.getPlayer().get(0).getPosition().getLiteralCoordinates().get(0), game, level);
//			
//			//int[] betterTargetBox = AiInputSystem.convertToAiBox(playerPosition, level);		//where the target is
//			int[] betterAiBox = AiInputSystem.convertToAiBox(CollisionSystem.convertLiteralXYToBox(ai.getPosition().getLiteralCoordinates().get(0), game, level), level);	//where the ai is
//			return playerPosition;
////			if(Math.abs(betterAiBox[0] - betterTargetBox[0]) > AiInput.FOLLOW_RADIUS)
////			{
//////				if(betterAiBox[0] < betterTargetBox[0])		//ai is left of target
//////					betterTargetBox[0] -= AiInput.FOLLOW_RADIUS;
//////				else if(betterAiBox[0] > betterTargetBox[0])	//ai is right of target
//////					betterTargetBox[0] += AiInput.FOLLOW_RADIUS;
////			}
////			else
////			{
////				betterTargetBox[0] = betterAiBox[0];
////			}
////			
////			if(Math.abs(betterAiBox[1] - betterTargetBox[1]) > AiInput.FOLLOW_RADIUS)
////			{
//////				if(betterAiBox[1] < betterTargetBox[1])		//ai is above target
//////					betterTargetBox[1] -= AiInput.FOLLOW_RADIUS;
//////				else if(betterAiBox[1] > betterTargetBox[1])	//ai is below target
//////					betterTargetBox[1] += AiInput.FOLLOW_RADIUS;
////			
////			}
////			else
////			{
////				betterTargetBox[1] = betterAiBox[1];
////			}
//			
//			//return AiInputSystem.convertFromAiBox(betterTargetBox[0], betterTargetBox[1], level);
//		}
//		else
//		{
//			if(patrol == true)
//			{
//				return patrolPoints.get(currentPatrolPoint);
//			}
//			else
//			{
//				//TODO put in the calling entities current position.
//				return 0;
//			}
//		}
	}


	public void setTargetBox(int targetBox) {
		this.targetBox = targetBox;
	}


	public Path getPathToTarget() {
		return pathToTarget;
	}


	public void setPathToTarget(Path pathToTarget) {
		this.pathToTarget = pathToTarget;
	}


	public int getSightDistance() {
		return sightDistance;
	}


	public void setSightDistance(int sightDistance) {
		this.sightDistance = sightDistance;
	}

}

package pl.admund.MVM_Server.MatchEngine;

import java.io.Serializable;

public class SingleMatchAction implements Serializable
{
	private static final long serialVersionUID = -740548518648902770L;
	
	private int volleyballerId;
	private int actionType;
	private int actionResult;
	
	public SingleMatchAction(int volleyballerId, int actionType, int actionResult)
	{
		this.volleyballerId = volleyballerId;
		this.actionType = actionType;
		this.actionResult = actionResult;
	}
	
	public int getVolleyballerId() {
		return volleyballerId;
	}
	public int getActionType() {
		return actionType;
	}
	public int getActionResult() {
		return actionResult;
	}
	public void setVolleyballerId(int volleyballerId) {
		this.volleyballerId = volleyballerId;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public void setActionResult(int actionResult) {
		this.actionResult = actionResult;
	}
}

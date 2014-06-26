package pl.admund.MVM_Client.PlayMatch;

import java.util.ArrayList;
import java.util.Collection;

public class MatchAction 
{
	private int LP;
	private ArrayList<SingleMatchAction> actions = new ArrayList<SingleMatchAction>();
	
	public void addAction(SingleMatchAction singleAction)
	{
		actions.add(singleAction);
	}
	public void addAction(int volleyballerId, int actionType, int actionResult)
	{
		actions.add(new SingleMatchAction(volleyballerId, actionType, actionResult));
	}
	public void addActions(Collection<? extends SingleMatchAction> collection)
	{
		actions.addAll(collection);
	}
	public int getSingleActionsCnt()
	{
		return actions.size();
	}
	public SingleMatchAction getSingleAction(int pos)
	{
		return actions.get(pos);
	}
	public int getLP() {
		return LP;
	}
	public void setLP(int lP) {
		LP = lP;
	}
}

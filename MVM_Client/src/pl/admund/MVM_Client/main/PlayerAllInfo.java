package pl.admund.MVM_Client.main;

import pl.admund.MVM_Client.Tactics.Tactic;
import pl.admund.MVM_Client.Tactics.TeamMatchSquad;
import pl.admund.MVM_Client.Volleyballer.VolleyballerClnt;
import pl.admund.MVM_Client.sportStuff.StadiumClnt;
import pl.admund.MVM_Client.sportStuff.TeamClnt;

public class PlayerAllInfo
{
	private static PlayerAllInfo myInstance;
	public static PlayerAllInfo getInstance()
	{
		if(myInstance == null)
			myInstance = new PlayerAllInfo();
		return myInstance;
	};
	private PlayerAllInfo()
	{};
	
//--------------------------------------------------------	
	private int mPlayerId = -1;
	private String mLogin = null;
	private String mToken = null;
	private String mMail = null;
	private boolean isLogenOn = false;
	
	private TeamClnt mTeam = null;
	private StadiumClnt mStadium = null;
	private Tactic mTactic = null;
	private TeamMatchSquad mTeamMatchSquad = null;
	
	public void reset()
	{
		mPlayerId = -1;
		mLogin = null;
		mToken = null;
		isLogenOn = false;
		
		mTeam = null;
		mStadium = null;
		mTactic = null;
		mTeamMatchSquad = null;
	}
	
	public VolleyballerClnt getVolleyballerFromId(int id)
	{
		for(int i=0; i<mTeam.getVolleyballersList().size(); i++)
		{
			if(mTeam.getVolleyballersList().get(i).getId() == id )
				return mTeam.getVolleyballersList().get(i);
		}
		return null;
	}

	public String getLogin() {
		return mLogin;
	}
	public void setLogin(String mLogin) {
		this.mLogin = mLogin;
	}
	public String getToken() {
		return mToken;
	}
	public void setToken(String mToken) {
		this.mToken = mToken;
	}
	public boolean isLogenOn() {
		return isLogenOn;
	}
	public void setLogenOn(boolean isLogenOn) {
		this.isLogenOn = isLogenOn;
	}
	public int getPlayerId() {
		return mPlayerId;
	}
	public void setPlayerId(int mPlayerId) {
		this.mPlayerId = mPlayerId;
	}
	public TeamClnt getTeam() {
		return mTeam;
	}
	public void setTeam(TeamClnt mTeam) {
		this.mTeam = mTeam;
	}
	public StadiumClnt getStadium() {
		return mStadium;
	}
	public void setStadium(StadiumClnt mStadium) {
		this.mStadium = mStadium;
	}
	public Tactic getTactic() {
		return mTactic;
	}
	public void setTactic(Tactic mTactic) {
		this.mTactic = mTactic;
	}
	public TeamMatchSquad getTeamMatchSquad() {
		return mTeamMatchSquad;
	}
	public void setTeamMatchSquad(TeamMatchSquad mTeamMatchSquad) {
		this.mTeamMatchSquad = mTeamMatchSquad;
	}
	public String getMail() {
		return mMail;
	}
	public void setMail(String mMail) {
		this.mMail = mMail;
	}
}

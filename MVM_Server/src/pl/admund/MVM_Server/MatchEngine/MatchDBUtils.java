package pl.admund.MVM_Server.MatchEngine;

import pl.admund.MVM_Server.DBConnect.DBUtils;

public class MatchDBUtils 
{
	public static boolean updateMatchResult(int id, int homeSetPts, int awaySetPts, String result)
	{
		String updateMatchStatus = "UPDATE WM_MECZ SET STATUS=5," + " GOSPODARZ_SET=" + homeSetPts
				+ ", GOSC_SET=" + awaySetPts + ", MALE_PKT='" + result + "' WHERE ID=" + id;
		if(DBUtils.executeUpdate(updateMatchStatus) == -1)
			return false;
		
		return true;
	}
}

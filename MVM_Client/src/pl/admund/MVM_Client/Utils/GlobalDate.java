package pl.admund.MVM_Client.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GlobalDate
{
	public static String getCurrentDateAndTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	public static String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	public static String getCurrentTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	public static int getCurrentDay()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
	}
	
	public static int getCurrentMonth()
	{
		DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
	}
	
	public static int getCurrentYear()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
	}
	
	public static int getCurrentSec()
	{
		DateFormat dateFormat = new SimpleDateFormat("ss");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
	}
	
	public static int getCurrentMin()
	{
		DateFormat dateFormat = new SimpleDateFormat("mm");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
	}
	
	public static int getCurrentHour()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
	}
	
	public static String getDateFromDBDate(String date)
	{
		String[] dateAndTime = date.split(" ");
		if(dateAndTime[0] != null)
			return dateAndTime[0];
		else
			return null;
	}
	
	public static String getTimeFromDBDate(String date)
	{
		String[] dateAndTime = date.split(" ");
		if(dateAndTime[1] != null)
			return dateAndTime[1];
		else
			return null;
	}
	
	public static String getDayFromDBDate(String date)
	{
		String shortDate = getDateFromDBDate(date);
		String[] dayAndMonthAndYear = shortDate.split("/");
		if(dayAndMonthAndYear[2] != null)
			return dayAndMonthAndYear[2];
		else
			return null;
	}
	
	public static String getMonthFromDBDate(String date)
	{
		String shortDate = getDateFromDBDate(date);
		String[] dayAndMonthAndYear = shortDate.split("/");
		if(dayAndMonthAndYear[1] != null)
			return dayAndMonthAndYear[1];
		else
			return null;
	}
	
	public static String getYearFromDBDate(String date)
	{
		String shortDate = getDateFromDBDate(date);
		String[] dayAndMonthAndYear = shortDate.split("/");
		if(dayAndMonthAndYear[0] != null)
			return dayAndMonthAndYear[0];
		else
			return null;
	}
	
	public static String getSecFromDBDate(String date)
	{
		String shortTime = getTimeFromDBDate(date);
		String[] secAndMinAndHour = shortTime.split(":");
		if(secAndMinAndHour[2] != null)
			return secAndMinAndHour[2];
		else
			return null;
	}
	
	public static String getMinFromDBDate(String date)
	{
		String shortTime = getTimeFromDBDate(date);
		String[] secAndMinAndHour = shortTime.split(":");
		if(secAndMinAndHour[1] != null)
			return secAndMinAndHour[1];
		else
			return null;
	}
	
	public static String getHourFromDBDate(String date)
	{
		String shortTime = getTimeFromDBDate(date);
		String[] secAndMinAndHour = shortTime.split(":");
		if(secAndMinAndHour[0] != null)
			return secAndMinAndHour[0];
		else
			return null;
	}
	
	public static String addToDateMin(int min)
	{
		int add = 0;
		String date = ":" + GlobalDate.getCurrentSec();
//System.out.println("0 " + (GlobalDate.getCurrentMin() + min) + " "+ date);
		if(GlobalDate.getCurrentMin() + min > 59)
		{
			date = ":" + ((GlobalDate.getCurrentMin() + min)%60) + date;
			add = (GlobalDate.getCurrentMin() + min) / 60;
		}
		else
			return GlobalDate.getCurrentDate()+ " " + GlobalDate.getCurrentHour() + ":" + (GlobalDate.getCurrentMin()+min) + date;
//System.out.println("1 " + date);
		if(GlobalDate.getCurrentHour() + add > 23)
		{
			date = " " + (GlobalDate.getCurrentHour() + add) % 23 + date;
			add = (GlobalDate.getCurrentHour() + add) / 23;
		}
		else
			return GlobalDate.getCurrentDate()+ " " + (GlobalDate.getCurrentHour() + add) + date;
//System.out.println("2" + date);
		Calendar calendar = Calendar.getInstance();
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(GlobalDate.getCurrentDay() + add > days)
		{
			date = "/" + (GlobalDate.getCurrentDay() + add) % (days+1) + date;
			add = (GlobalDate.getCurrentDay() + add) / (days+1);
		}
		else
			return GlobalDate.getCurrentYear()+"/"+GlobalDate.getCurrentMonth()+ "/" + (GlobalDate.getCurrentDay() + add) + date;
//System.out.println("3" + date);
		if(GlobalDate.getCurrentMonth() + add > 12)
		{
			date = "/" + ((GlobalDate.getCurrentMonth() + add) % 12) + date;
			add = (GlobalDate.getCurrentDay() + add) / 12;
			return ""+(GlobalDate.getCurrentYear() + add) + date;
		}
		else
			return GlobalDate.getCurrentYear()+"/"+GlobalDate.getCurrentMonth() + date;
	}
}

package services;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Dates
{
//	* Choix de la langue francaise
	static Locale locale = Locale.FRANCE;
	static Date actuelle = new Date();
	static GregorianCalendar calendar=new GregorianCalendar();
	static Date date = calendar.getTime();
	
//	* Definition du format utilise pour les dates
	static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,locale);

//	* Donne la date au format "aaaa-mm-jj"
	public static String date()
	{
		String dat = dateFormat.format(date);
		return dat;
	}
}
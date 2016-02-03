package j7.coin;

import org.apache.commons.lang3.Validate;

public class StringSwitch
{
	public int convertDayToInt(String day)
	{
		Validate.notEmpty(day);
		
		String d = day.toLowerCase();

		switch (d)
		{
		case "mon"       : return 1;
		case "monday"    : return 1;
		case "tue"       : return 2;
		case "tuesday"   : return 2;
		case "wed"       : return 3;
		case "wednesday" : return 3;
		case "thu"       : return 4;
		case "thursday"  : return 4;
		case "fri"       : return 5;
		case "friday"    : return 5;
		case "sat"       : return 6;
		case "saturday"  : return 6;
		case "sun"       : return 7;
		case "sunday"    : return 7;
			
		default:
			String msg = String.format("Unfortunately, %s cannot be construed as a day of the week", day);
			System.err.println(msg);
			break;
		}
		
		return -1;
	}

}

package io.gupshup.expensetest.util;

import android.preference.PreferenceManager;
import android.widget.EditText;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.gupshup.expensetest.ExpenseTest;
import io.gupshup.expensetest.R;

public class AppUtil
{
	public static String formatDateToString(Date date, String pattern)
	{
		DateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	public static boolean isEmptyField(EditText et)
	{
		return (et.getText() == null || et.getText().toString().isEmpty());
	}

	public static List<Integer> getListColors()
	{
		ArrayList<Integer> colors = new ArrayList<>();
		for (int c : ColorTemplate.LIBERTY_COLORS)
		{
			colors.add(c);
		}
		for (int c : ColorTemplate.VORDIPLOM_COLORS)
		{
			colors.add(c);
		}
		for (int c : ColorTemplate.JOYFUL_COLORS)
		{
			colors.add(c);
		}
		for (int c : ColorTemplate.COLORFUL_COLORS)
		{
			colors.add(c);
		}
		for (int c : ColorTemplate.PASTEL_COLORS)
		{
			colors.add(c);
		}
		colors.add(ColorTemplate.getHoloBlue());
		return colors;
	}

	public static String getFormattedCurrency(float number)
	{
		String countryCode = PreferenceManager.getDefaultSharedPreferences(ExpenseTest.getContext()).getString(ExpenseTest.getContext().getString(R.string.pref_country_key), ExpenseTest.getContext().getString(R.string.default_country));
		Locale locale = new Locale("EN", countryCode);
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		String formattedNumber = numberFormat.format(number);
		String symbol = numberFormat.getCurrency().getSymbol(locale);
		return formattedNumber.replace(symbol, symbol + " ");
	}

	public static String getCurrentDateFormat()
	{
		return PreferenceManager.getDefaultSharedPreferences(ExpenseTest.getContext()).getString(ExpenseTest.getContext().getString(R.string.date_format_key), ExpenseTest.getContext().getString(R.string.default_date_format));
	}

	public static Calendar setDateStartOfDay(Calendar calendar)
	{
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		return calendar;
	}

	public static Date getFirstDateOfCurrentMonth()
	{
		Calendar cal = setDateStartOfDay(Calendar.getInstance());
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static Date getLastDateOfCurrentMonth()
	{
		Calendar cal = setDateStartOfDay(Calendar.getInstance());
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static Date getRealLastDateOfCurrentMonth()
	{
		Calendar cal = setDateStartOfDay(Calendar.getInstance());
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static Date getFirstDateOfCurrentWeek()
	{
		return getCalendarFirstDayOfCurrentWeek().getTime();
	}

	public static Calendar getCalendarFirstDayOfCurrentWeek()
	{
		Calendar cal = setDateStartOfDay(Calendar.getInstance());
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return setDateStartOfDay(cal);
	}

	public static Date getLastDateOfCurrentWeek()
	{
		Calendar cal = setDateStartOfDay(Calendar.getInstance());
//        Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DAY_OF_WEEK, 7);
		cal.add(Calendar.DATE, 2);
		return cal.getTime();
	}

	public static Date getRealLastDateOfCurrentWeek()
	{
		Calendar cal = setDateStartOfDay(Calendar.getInstance());
		cal.set(Calendar.DAY_OF_WEEK, 7);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static Date getTomorrowDate()
	{
		Calendar cal = setDateStartOfDay(Calendar.getInstance());
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static Date addDaysToDate(Date currentDate, int numDays)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, numDays);
		return c.getTime();
	}

	public static List<Date> getWeekDates()
	{
		Calendar cal = getCalendarFirstDayOfCurrentWeek();
		List<Date> dateList = new ArrayList<>();
		for (int i = 0; i < 7; i++)
		{
			dateList.add(cal.getTime());
			cal.add(Calendar.DAY_OF_WEEK, 1);
		}
		return dateList;
	}

	public static boolean isToday(Date date)
	{
		return android.text.format.DateUtils.isToday(date.getTime());
	}

	public static Date getToday()
	{
		return setDateStartOfDay(Calendar.getInstance()).getTime();
	}

}

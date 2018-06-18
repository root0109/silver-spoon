package io.gupshup.expensetest.constants;

public interface AppConstants
{
	public static final int SYSTEM_CREATED = 0;
	public static final int USER_CREATED = 1;

	public static final int MODE_CREATE = 0;
	public static final int MODE_UPDATE = 1;
	public static final String MODE_TAG = "_user_action_mode";


	public static final int TYPE_EXPENSE = 0;
	public static final int TYPE_INCOME = 1;

	public static final int MODE_TODAY = 100;
	public static final int MODE_WEEK = 101;
	public static final int MODE_MONTH = 102;
	public static final String DATE_TODAY_TAG = "_today";
	public static final String DATE_WEEK_TAG = "_week";
	public static final String DATE_MONTH_TAG = "_month";
	public static final String DATE_MODE_TAG = "_date_user_mode";
}

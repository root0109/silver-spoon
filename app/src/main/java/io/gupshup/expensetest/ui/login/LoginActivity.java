package io.gupshup.expensetest.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import io.gupshup.expensetest.ExpenseTest;
import io.gupshup.expensetest.R;
import io.gupshup.expensetest.ui.BaseActivity;
import io.gupshup.expensetest.ui.MainActivity;


public class LoginActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		boolean logged = PreferenceManager.getDefaultSharedPreferences(ExpenseTest.getContext()).getBoolean(getString(R.string.already_accepted_user_key), false);
		if (logged)
		{
			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			finish();
		}
	}
}

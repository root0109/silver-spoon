package io.gupshup.expensetest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.ui.login.WelcomePage;

public class WelcomePageAdapter extends FragmentStatePagerAdapter
{
	private static final int NUM_PAGES = 3;

	public WelcomePageAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int position)
	{
		WelcomePage welcomePage = null;
		switch (position)
		{
			case 0:
				welcomePage = WelcomePage.newInstance(R.layout.layout_welcome_first);
				break;
			case 1:
				welcomePage = WelcomePage.newInstance(R.layout.layout_welcome_second);
				break;
			case 2:
				welcomePage = WelcomePage.newInstance(R.layout.layout_welcome_third);
				break;
		}
		return welcomePage;
	}

	@Override
	public int getCount()
	{
		return NUM_PAGES;
	}
}

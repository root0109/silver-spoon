package io.gupshup.expensetest.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import io.gupshup.expensetest.ExpenseTest;
import io.gupshup.expensetest.R;
import io.gupshup.expensetest.adapters.WelcomePageAdapter;
import io.gupshup.expensetest.ui.BaseFragment;
import io.gupshup.expensetest.ui.MainActivity;

public class LoginFragment extends BaseFragment implements View.OnClickListener
{
	private ViewPager viewPager;

	public static LoginFragment newInstance()
	{
		return new LoginFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		WelcomePageAdapter welcomePageAdapter = new WelcomePageAdapter(getFragmentManager());
		viewPager.setAdapter(welcomePageAdapter);
		CirclePageIndicator circlePageIndicator = (CirclePageIndicator) getView().findViewById(R.id.cpi_welcome);
		circlePageIndicator.setViewPager(viewPager);
		getView().findViewById(R.id.sign_in).setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_login, container, false);
		viewPager = (ViewPager) rootView.findViewById(R.id.vp_welcome);
		return rootView;
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.sign_in)
		{
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ExpenseTest.getContext());
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(getString(R.string.already_accepted_user_key), true);
			editor.apply();
			Intent intent = new Intent(getActivity(), MainActivity.class);
			getActivity().finish();
			startActivity(intent);
		}
	}
}

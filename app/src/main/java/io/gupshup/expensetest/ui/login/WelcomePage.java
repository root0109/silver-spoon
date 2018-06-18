package io.gupshup.expensetest.ui.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomePage extends Fragment
{
	private final static String WELCOME_LAYOUT_ID = "layoutid";

	public WelcomePage()
	{
		// Required empty public constructor
	}


	public static WelcomePage newInstance(int layoutId)
	{
		WelcomePage pane = new WelcomePage();
		Bundle args = new Bundle();
		args.putInt(WELCOME_LAYOUT_ID, layoutId);
		pane.setArguments(args);
		return pane;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(getArguments().getInt(WELCOME_LAYOUT_ID,
													  -1), container, false);
	}

}

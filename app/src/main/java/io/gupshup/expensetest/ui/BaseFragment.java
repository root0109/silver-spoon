package io.gupshup.expensetest.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.interfaces.FragmentListener;
import io.gupshup.expensetest.interfaces.MainActivityListener;

public class BaseFragment extends Fragment
{
	protected FragmentListener     fragmentListener;
	protected Toolbar              toolbar;

	public View onCreateFragmentView(@LayoutRes int layoutId, LayoutInflater inflater, ViewGroup container, boolean withToolbar)
	{
		if (!withToolbar)
		{
			return inflater.inflate(layoutId, container, false);
		}
		View viewWithToolbar = inflater.inflate(R.layout.fragment_base, container, false);
		ViewGroup llMainContainer = (ViewGroup) viewWithToolbar.findViewById(R.id.base_fragment);
		View content = inflater.inflate(layoutId, container, false);
		llMainContainer.addView(content);

		toolbar = (Toolbar) viewWithToolbar.findViewById(R.id.toolbar);
		fragmentListener.setToolbar(toolbar);
		return viewWithToolbar;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_base, container, false);
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		fragmentListener = (FragmentListener) context;
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		fragmentListener = null;
	}

	public void setTitle(String title)
	{
		if (getActivity() != null && getActivity() instanceof BaseActivity)
		{
			((BaseActivity)getActivity()).getSupportActionBar().setTitle(title);
		}
	}
}

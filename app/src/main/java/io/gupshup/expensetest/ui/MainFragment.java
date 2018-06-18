package io.gupshup.expensetest.ui;

import android.content.Context;

import io.gupshup.expensetest.interfaces.MainActivityListener;

/**
 * Created by pcarrillo on 17/09/2015.
 */
public class MainFragment extends BaseFragment
{

    protected MainActivityListener mainActivityListener;

    @Override
    public void onAttach(Context context)
	{
        super.onAttach(context);
        mainActivityListener = (MainActivityListener)context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mainActivityListener = null;
    }

}

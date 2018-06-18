package io.gupshup.expensetest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.interfaces.FragmentListener;

public class BaseActivity extends AppCompatActivity implements FragmentListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
	}

	@Override
	public void replaceFragment(Fragment fragment, boolean addToBackStack)
	{
		replaceFragment(R.id.main_content, fragment, addToBackStack);
	}

	@Override
	public void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack)
	{
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		String fragmentName = fragment.getClass().getSimpleName();
		fragmentTransaction.replace(containerId, fragment, fragmentName);
		if (addToBackStack)
		{
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.commit();
	}

	@Override
	public void setResultWithData(int status, Intent intent)
	{
		setResult(status, intent);
		closeActivity();
	}

	@Override
	public void setToolbar(Toolbar toolbar)
	{
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp, getTheme()));
		toolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
	}

	@Override
	public void closeActivity()
	{
		finish();
	}
}

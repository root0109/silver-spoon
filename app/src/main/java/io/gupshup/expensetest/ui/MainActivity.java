package io.gupshup.expensetest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.interfaces.MainActivityListener;
import io.gupshup.expensetest.ui.categories.CategoriesFragment;
import io.gupshup.expensetest.ui.help.HelpActivity;
import io.gupshup.expensetest.ui.history.HistoryFragment;
import io.gupshup.expensetest.ui.reminder.ReminderFragment;
import io.gupshup.expensetest.ui.settings.SettingsActivity;
import io.gupshup.expensetest.ui.statistics.StatisticsFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainActivityListener
{
	public static final String NAVIGATION_POSITION = "navigation_position";

	private DrawerLayout         mainDrawerLayout;
	private NavigationView       mainNavigationView;
	private Toolbar              mainToolbar;
	private TabLayout            mainTabLayout;
	private FloatingActionButton mainFloatingActionButton;

	private int idSelectedNavigationItem;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mainTabLayout = (TabLayout) findViewById(R.id.tab_layout);
		mainNavigationView = (NavigationView) findViewById(R.id.nav_view);
		mainNavigationView.setNavigationItemSelectedListener(this);
		mainFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_main);
		mainToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mainToolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
		mainToolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mainDrawerLayout.openDrawer(GravityCompat.START);
			}
		});

		if (savedInstanceState != null)
		{
			int menuItemId = savedInstanceState.getInt(NAVIGATION_POSITION);
			mainNavigationView.setCheckedItem(menuItemId);
			mainNavigationView.getMenu().performIdentifierAction(menuItemId, 0);
		}
		else
		{
			mainNavigationView.getMenu().performIdentifierAction(R.id.nav_expenses, 0);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		outState.putInt(NAVIGATION_POSITION, idSelectedNavigationItem);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
	{
		menuItem.setChecked(true);
		mainDrawerLayout.closeDrawers();
		int menuItemId = menuItem.getItemId();
		idSelectedNavigationItem = menuItemId;
		Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
		switch (menuItemId)
		{
			case R.id.nav_expenses:
				/*if (!(currentFragment instanceof ExpensesContainerFragment))
				{
					replaceFragment(ExpensesContainerFragment.newInstance(), false);
				}*/
				break;
			case R.id.nav_categories:
				if (!(currentFragment instanceof CategoriesFragment))
				{
					replaceFragment(CategoriesFragment.newInstance(), false);
				}
				break;
			case R.id.nav_statistics:
				if (!(currentFragment instanceof StatisticsFragment))
				{
					replaceFragment(StatisticsFragment.newInstance(), false);
				}
				break;
			case R.id.nav_reminders:
				if (!(currentFragment instanceof ReminderFragment))
				{
					replaceFragment(ReminderFragment.newInstance(), false);
				}
				break;
			case R.id.nav_history:
				if (!(currentFragment instanceof HistoryFragment))
				{
					replaceFragment(HistoryFragment.newInstance(), false);
				}
				break;
		}
		return false;
	}

	/*-----------------------Menu---------------START--------------------*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		else if (id == R.id.action_help)
		{
			startActivity(new Intent(this, HelpActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	/*-----------------------Menu---------------END--------------------*/

	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			super.onBackPressed();
		}
	}

	/*-----------------------MainActivityListener---------------START--------------------*/

	@Override
	public void setTabs(List<String> tabList, TabLayout.OnTabSelectedListener onTabSelectedListener)
	{
		mainTabLayout.removeAllTabs();
		mainTabLayout.setVisibility(View.VISIBLE);
		mainTabLayout.addOnTabSelectedListener(onTabSelectedListener);
		for (String tab : tabList)
		{
			mainTabLayout.addTab(mainTabLayout.newTab().setText(tab).setTag(tab));
		}
	}

	@Override
	public void setFloatingActionButton(int drawableId, View.OnClickListener onClickListener)
	{
		mainFloatingActionButton.setImageDrawable(getResources().getDrawable(drawableId, getApplicationContext().getTheme()));
		mainFloatingActionButton.setOnClickListener(onClickListener);
		mainFloatingActionButton.show();
	}

	@Override
	public void setTitle(String title)
	{
		getSupportActionBar().setTitle(title);
	}

	@Override
	public void setPager(ViewPager vp, TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener)
	{

	}

	@Override
	public ActionMode setActionMode(final ActionMode.Callback actionModeCallback)
	{
		return mainToolbar.startActionMode(new ActionMode.Callback()
		{
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu)
			{
				mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
				return actionModeCallback.onCreateActionMode(mode, menu);
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu)
			{
				return actionModeCallback.onPrepareActionMode(mode, menu);
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item)
			{
				return actionModeCallback.onActionItemClicked(mode, item);
			}

			@Override
			public void onDestroyActionMode(ActionMode mode)
			{
				mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
				actionModeCallback.onDestroyActionMode(mode);
			}
		});
	}

	/*-----------------------MainActivityListener---------------END--------------------*/
}

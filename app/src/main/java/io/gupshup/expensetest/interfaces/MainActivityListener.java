package io.gupshup.expensetest.interfaces;

import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.View;

import java.util.List;

public interface MainActivityListener
{
	public void setTabs(List<String> tabList, TabLayout.OnTabSelectedListener onTabSelectedListener);
	public void setFloatingActionButton(@DrawableRes int drawableId, View.OnClickListener onClickListener);
	public void setTitle(String title);
	public void setPager(ViewPager vp, TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener);
	public ActionMode setActionMode(ActionMode.Callback actionModeCallback);
}

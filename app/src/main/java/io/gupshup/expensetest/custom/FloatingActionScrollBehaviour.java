package io.gupshup.expensetest.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import io.gupshup.expensetest.ExpenseTest;
import io.gupshup.expensetest.R;

public class FloatingActionScrollBehaviour extends FloatingActionButton.Behavior
{
	public FloatingActionScrollBehaviour(Context context, AttributeSet attrs)
	{
		//this is mandatory for initialization of behaviour
		super();
	}

	@Override
	public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
									   @NonNull View directTargetChild, @NonNull View target, int axes, int type)
	{
		String a = directTargetChild.getClass().getSimpleName();
		//excluding Statistics case and History for FAB behavior
		if (target instanceof NestedScrollView && target.getTag() != null)
		{
			if (target.getTag().toString().equalsIgnoreCase(ExpenseTest.getContext().getString(R.string.statistics))
					|| target.getTag().toString().equalsIgnoreCase(ExpenseTest.getContext().getString(R.string.history)))
			{
				return false;
			}
		}
		return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
	}

	@Override
	public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
							   @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
							   int dyUnconsumed, int type)
	{
		super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
		if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE)
		{
			child.hide();
		}
		else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE)
		{
			child.show();
		}
	}
}

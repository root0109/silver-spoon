package io.gupshup.expensetest.ui.expenses;

import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;

import io.gupshup.expensetest.bo.Expense;
import io.gupshup.expensetest.ui.BaseFragment;

public class ExpenseDetailFragment extends BaseFragment implements View.OnClickListener
{
	public static final String EXPENSE_ID_KEY = "_expense_id";

	private BarChart bcWeekExpenses;
	private Expense  expense;

	static ExpenseDetailFragment newInstance(String id) {
		ExpenseDetailFragment expenseDetailFragment = new ExpenseDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString(EXPENSE_ID_KEY, id);
		expenseDetailFragment.setArguments(bundle);
		return expenseDetailFragment;
	}

	public ExpenseDetailFragment() {
	}

	@Override
	public void onClick(View v)
	{

	}
}

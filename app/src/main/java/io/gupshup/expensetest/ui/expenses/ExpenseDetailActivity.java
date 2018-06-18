package io.gupshup.expensetest.ui.expenses;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.ui.BaseActivity;

public class ExpenseDetailActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		String expenseId = getIntent().getStringExtra(ExpenseDetailFragment.EXPENSE_ID_KEY);
		replaceFragment(ExpenseDetailFragment.newInstance(expenseId), false);
	}

}

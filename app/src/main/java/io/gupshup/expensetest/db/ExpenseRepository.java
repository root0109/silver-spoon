package io.gupshup.expensetest.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.bo.Expense;
import io.gupshup.expensetest.interfaces.dao.CategoryDao;
import io.gupshup.expensetest.interfaces.dao.ExpensesDao;

public class ExpenseRepository
{
	private ExpensesDao             expensesDao;
	private LiveData<List<Expense>> allExpenses;

	public ExpenseRepository(Context context)
	{
		ExpenseDB db = ExpenseDB.getDatabase(context);
		expensesDao = db.expensesDao();
		allExpenses = expensesDao.getAllExpenses();
	}

	LiveData<List<Expense>> getAllExpenses()
	{
		return allExpenses;
	}

	public LiveData<Expense> getExpense(String id)
	{
		AsyncTask asyncTask = null;
		LiveData<Expense> expense = null;
		try
		{
			asyncTask = new AsyncTask<String ,Void, LiveData<Expense>>()
			{
				@Override
				protected LiveData<Expense> doInBackground(String... expenseIds)
				{
					return expensesDao.getExpense(expenseIds[0]);
				}
			};
			expense = (LiveData<Expense>) asyncTask.execute(id).get();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return expense;
	}

	public void insert(Expense expense)
	{
		AsyncTask asyncTask = new AsyncTask<Expense ,Void,Void>()
		{
			@Override
			protected Void doInBackground(Expense... expenses)
			{
				expensesDao.insert(expenses[0]);
				return null;
			}
		};
		asyncTask.execute(expense);
	}

	public void update(Expense expense)
	{
		AsyncTask asyncTask = new AsyncTask<Expense ,Void,Void>()
		{
			@Override
			protected Void doInBackground(Expense... expenses)
			{
				expensesDao.update(expenses[0]);
				return null;
			}
		};
		asyncTask.execute(expense);
	}

	public float getTotalExpensesByDateMode(int dateMode)
	{
		return 0.0f;
	}
}
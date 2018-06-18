package io.gupshup.expensetest.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.bo.Expense;

public class ExpenseViewModel extends AndroidViewModel
{

	private ExpenseRepository       expenseRepository;
	// Using LiveData and caching what getAllCategories returns has several benefits:
	// - We can put an observer on the data (instead of polling for changes)
	//  and only update the the UI when the data actually changes.
	// - Repository is completely separated from the UI through the ViewModel.
	private LiveData<List<Expense>> allExpenses;

	public ExpenseViewModel(Application application)
	{
		super(application);
		expenseRepository = new ExpenseRepository(application);
		allExpenses = expenseRepository.getAllExpenses();
	}

	public LiveData<List<Expense>> getAllExpenses()
	{
		return allExpenses;
	}

	public LiveData<Expense> getExpenses(String id)
	{
		return expenseRepository.getExpense(id);
	}

	public void insert(Expense expense)
	{
		expenseRepository.insert(expense);
	}

	public void update(Expense expense)
	{
		expenseRepository.update(expense);
	}
}

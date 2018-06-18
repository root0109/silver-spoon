package io.gupshup.expensetest.interfaces.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.gupshup.expensetest.bo.Expense;

@Dao
public interface ExpensesDao
{
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(Expense expense);

	@Update
	void update(Expense expense);

	@Query("DELETE FROM et_expense")
	void deleteAllExpense();

	@Query("SELECT * from et_expense where expense_id = :id")
	public LiveData<Expense> getExpense(String id);

	@Query("SELECT * from et_expense ORDER BY expense_id ASC")
	public LiveData<List<Expense>> getAllExpenses();
}

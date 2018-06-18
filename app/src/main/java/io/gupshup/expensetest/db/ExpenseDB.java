package io.gupshup.expensetest.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.bo.Expense;
import io.gupshup.expensetest.interfaces.dao.CategoryDao;
import io.gupshup.expensetest.interfaces.dao.ExpensesDao;

@Database(entities = {Expense.class}, version = 1)
public abstract class ExpenseDB extends RoomDatabase
{
	private static ExpenseDB INSTANCE;
	private static Callback sRoomDatabaseCallback = new Callback()
	{
		@Override
		public void onOpen(@NonNull SupportSQLiteDatabase db)
		{
			super.onOpen(db);
			// If you want to keep the data through app restarts,
			// comment out the following line.
			//new PopulateDbAsync(INSTANCE).execute();
		}
	};

	public abstract ExpensesDao expensesDao();

	public static ExpenseDB getDatabase(final Context context)
	{
		if (INSTANCE == null)
		{
			synchronized (ExpenseDB.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
													ExpenseDB.class, "et_expense")
							// Wipes and rebuilds instead of migrating if no Migration object.
							// Migration is not part of this codelab.
							.fallbackToDestructiveMigration()
							.addCallback(sRoomDatabaseCallback)
							.build();

				}
			}
		}
		return INSTANCE;
	}

	private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
	{
		private final ExpensesDao mDao;

		PopulateDbAsync(ExpenseDB db)
		{
			mDao = db.expensesDao();
		}

		@Override
		protected Void doInBackground(final Void... params)
		{
			return null;
		}
	}
}

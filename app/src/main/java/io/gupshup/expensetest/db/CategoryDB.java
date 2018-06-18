package io.gupshup.expensetest.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.interfaces.dao.CategoryDao;

@Database(entities = {Category.class}, version = 1)
public abstract class CategoryDB extends RoomDatabase
{
	private static CategoryDB INSTANCE;
	private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback()
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

	public abstract CategoryDao categoryDao();

	public static CategoryDB getDatabase(final Context context)
	{
		if (INSTANCE == null)
		{
			synchronized (CategoryDB.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
													CategoryDB.class, "et_category")
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
		private final CategoryDao mDao;

		PopulateDbAsync(CategoryDB db)
		{
			mDao = db.categoryDao();
		}

		@Override
		protected Void doInBackground(final Void... params)
		{
			return null;
		}
	}
}

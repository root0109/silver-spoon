package io.gupshup.expensetest.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.interfaces.dao.CategoryDao;

public class CategoryRepository
{
	private CategoryDao              categoryDao;
	private LiveData<List<Category>> allCategories;

	public CategoryRepository(Context context)
	{
		CategoryDB db = CategoryDB.getDatabase(context);
		categoryDao = db.categoryDao();
		allCategories = categoryDao.getAllCategories();
	}

	LiveData<List<Category>> getAllCategories()
	{
		return allCategories;
	}

	public void insert(Category category)
	{
		new AsyncTask<Category,Void,Void>()
		{
			@Override
			protected Void doInBackground(Category[] categories)
			{
				categoryDao.insert(categories[0]);
				return null;
			}
		}.execute(category);
	}

	public void update(Category category)
	{
		new AsyncTask<Category,Void,Void>()
		{
			@Override
			protected Void doInBackground(Category[] categories)
			{
				categoryDao.updateCategory(categories[0]);
				return null;
			}
		}.execute(category);
	}

	public void delete(Category category)
	{
		new AsyncTask<Category,Void,Void>()
		{
			@Override
			protected Void doInBackground(Category[] categories)
			{
				categoryDao.deleteCategory(categories[0]);
				return null;
			}
		}.execute(category);
	}

	public void deleteCategories(final List<Category> categoryList)
	{
		Category [] categories = categoryList.toArray(new Category[categoryList.size()]);
		new AsyncTask<Category,Void,Void>()
		{
			@Override
			protected Void doInBackground(Category[] categories)
			{
				categoryDao.deleteCategories(categories);
				return null;
			}
		}.execute(categories);
	}
}
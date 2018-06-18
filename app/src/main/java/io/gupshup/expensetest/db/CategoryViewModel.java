package io.gupshup.expensetest.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import io.gupshup.expensetest.bo.Category;

public class CategoryViewModel extends AndroidViewModel
{

	private CategoryRepository       categoryRepository;
	// Using LiveData and caching what getAllCategories returns has several benefits:
	// - We can put an observer on the data (instead of polling for changes)
	//  and only update the the UI when the data actually changes.
	// - Repository is completely separated from the UI through the ViewModel.
	private LiveData<List<Category>> allCategories;

	public CategoryViewModel(Application application)
	{
		super(application);
		categoryRepository = new CategoryRepository(application);
		allCategories = categoryRepository.getAllCategories();
	}

	public LiveData<List<Category>> getAllCategories()
	{
		return allCategories;
	}

	public void insert(Category category)
	{
		categoryRepository.insert(category);
	}

	public void update(Category category)
	{
		categoryRepository.update(category);
	}

	public void delete(Category category)
	{
		categoryRepository.delete(category);
	}

	public void deleteCategories(List<Category> categoryList)
	{
		categoryRepository.deleteCategories(categoryList);
	}
}

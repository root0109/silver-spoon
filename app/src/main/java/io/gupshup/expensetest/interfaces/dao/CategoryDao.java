package io.gupshup.expensetest.interfaces.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.gupshup.expensetest.bo.Category;

@Dao
public interface CategoryDao
{
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(Category category);

	@Query("SELECT * FROM et_category WHERE category_id =:categoryId")
	Category getCategory(String categoryId);


	@Query("SELECT * from et_category ORDER BY category_id ASC")
	LiveData<List<Category>> getAllCategories();

	@Update
	void updateCategory(Category category);

	@Query("DELETE FROM et_category")
	void deleteAllCategories();

	@Delete
	void deleteCategory(Category category);

	@Delete
	void deleteCategories(Category[] categories);
}

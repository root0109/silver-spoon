package io.gupshup.expensetest.bo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.gupshup.expensetest.constants.AppConstants;

@Entity(tableName = "et_category")
public class Category
{
	@NonNull
	@PrimaryKey
	@ColumnInfo(name = "category_id")
	private int categoryId;
	@NonNull
	@ColumnInfo(name = "parent_category")
	private String parentCategory;
	@NonNull
	@ColumnInfo(name = "child_category")
	private String childCategory;
	@NonNull
	@ColumnInfo(name = "category_type")
	private int    type = AppConstants.SYSTEM_CREATED;

	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int id)
	{
		this.categoryId = categoryId;
	}

	public String getParentCategory()
	{
		return parentCategory;
	}

	public void setParentCategory(String parentCategory)
	{
		this.parentCategory = parentCategory;
	}

	@NonNull
	public String getChildCategory()
	{
		return childCategory;
	}

	public void setChildCategory(@NonNull String childCategory)
	{
		this.childCategory = childCategory;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}

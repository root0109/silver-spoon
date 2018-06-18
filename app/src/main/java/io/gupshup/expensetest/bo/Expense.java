package io.gupshup.expensetest.bo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import io.gupshup.expensetest.constants.AppConstants;
import io.gupshup.expensetest.db.converters.DBTypeConverters;

@Entity(tableName = "et_expense")
public class Expense
{
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "expense_id")
	private String expenseId;
	@ColumnInfo(name = "description")
	private String description;
	@NonNull
	@ColumnInfo(name = "date")
	@TypeConverters({DBTypeConverters.class})
	private Date   date;
	@ColumnInfo(name = "type")
	@NonNull
	private int    type = AppConstants.TYPE_EXPENSE;
	@ColumnInfo(name = "total")
	@NonNull
	private float  total;

	@Embedded
	@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
	private Category category;

	public String getExpenseId()
	{
		return expenseId;
	}

	public void setExpenseId(String id)
	{
		this.expenseId = expenseId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public float getTotal()
	{
		return total;
	}

	public void setTotal(float total)
	{
		this.total = total;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}
}

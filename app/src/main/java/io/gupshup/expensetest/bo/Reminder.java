package io.gupshup.expensetest.bo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "et_reminder")
public class Reminder
{
	@PrimaryKey
	private String  id;
	@ColumnInfo(name = "name")
	private String  name;
	@ColumnInfo(name = "state")
	private boolean state;
	@ColumnInfo(name = "day")
	private int     day;
	@NonNull
	@ColumnInfo(name = "date")
	private Date    date;
	@NonNull
	@ColumnInfo(name = "created_on")
	private Date    createdOn;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isState()
	{
		return state;
	}

	public void setState(boolean state)
	{
		this.state = state;
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Date getCreatedOn()
	{
		return createdOn;
	}

	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}
}

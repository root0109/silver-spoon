package io.gupshup.expensetest.interfaces.dao;

import android.arch.persistence.room.Dao;

import java.util.List;

import io.gupshup.expensetest.bo.Reminder;

@Dao
public interface ReminderDao
{
	public void deleteReminders(List<Reminder> reminderList);
}

package io.gupshup.expensetest.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import io.gupshup.expensetest.ExpenseTest;
import io.gupshup.expensetest.R;
import io.gupshup.expensetest.adapters.base.BaseRecyclerViewAdapter;
import io.gupshup.expensetest.bo.Reminder;
import io.gupshup.expensetest.util.AppUtil;

public class RemindersAdapter extends BaseRecyclerViewAdapter<RemindersAdapter.ReminderViewHolder>
{
	private List<Reminder> reminderList;
	private int lastPosition = -1;

	public RemindersAdapter(List<Reminder> mReminderList)
	{
		this.reminderList = mReminderList;
	}

	@NonNull
	@Override
	public RemindersAdapter.ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.layout_reminder_item, parent, false);
		return new ReminderViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull ReminderViewHolder reminderViewHolder, int position)
	{
		final Reminder reminder = reminderList.get(position);
		if (reminder.getName() != null)
			reminderViewHolder.textViewTitle.setText(reminder.getName());
		reminderViewHolder.textViewDate.setText("Day of the Month: ".concat(String.valueOf(reminder.getDay())).concat(" - ").concat("Time: ").concat(AppUtil.formatDateToString(reminder.getDate(), "HH:mm")));
		reminderViewHolder.switchCompatState.setChecked(reminder.isState());
		reminderViewHolder.itemView.setTag(reminder);
		reminderViewHolder.itemView.setSelected(isSelected(position));

		if (position > lastPosition)
		{
			Animation animation = AnimationUtils.loadAnimation(ExpenseTest.getContext(), R.anim.push_left_in);
			reminderViewHolder.itemView.startAnimation(animation);
			lastPosition = position;
		}
	}

	@Override
	public int getItemCount()
	{
		return reminderList.size();
	}

	public static class ReminderViewHolder extends RecyclerView.ViewHolder
	{
		public  TextView     textViewTitle;
		public  TextView     textViewDate;
		public  SwitchCompat switchCompatState;

		public ReminderViewHolder(View itemView)
		{
			super(itemView);
			textViewTitle = (TextView) itemView.findViewById(R.id.tv_name);
			textViewDate = (TextView) itemView.findViewById(R.id.tv_date);
			switchCompatState = (SwitchCompat) itemView.findViewById(R.id.sc_reminder);
		}
	}
}

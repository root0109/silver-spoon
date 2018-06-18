package io.gupshup.expensetest.ui.reminder;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.adapters.RemindersAdapter;
import io.gupshup.expensetest.bo.Reminder;
import io.gupshup.expensetest.ui.BaseFragment;
import io.gupshup.expensetest.util.DialogManager;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class ReminderFragment extends BaseFragment
{
	public static final int ADD_REMINDER_REQUEST_CODE = 1002;
	private RecyclerView     recyclerViewReminders;
	private List<Reminder>   reminderList;
	private RemindersAdapter remindersAdapter;
	private TextView         textViewEmpty;

	// Action mode for reminders.
	private ActionMode actionMode;

	public static ReminderFragment newInstance()
	{
		return new ReminderFragment();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_reminder, container, false);
		recyclerViewReminders = (RecyclerView) rootView.findViewById(R.id.rv_reminders);

		textViewEmpty = (TextView) rootView.findViewById(R.id.tv_empty);
		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		// replace with Dao
		//reminderList = Reminder.getReminders();
		reminderList = new ArrayList<>();
		Reminder reminder = new Reminder();
		reminder.setCreatedOn(new Date());
		reminder.setDate(new Date());
		reminder.setDay(2);
		reminder.setId(1 + "");
		reminder.setName("Vaibhav Reminder");
		reminder.setState(true);
		reminderList.add(reminder);
		remindersAdapter = new RemindersAdapter(reminderList);
		recyclerViewReminders.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerViewReminders.setAdapter(remindersAdapter);
		recyclerViewReminders.setHasFixedSize(true);
		recyclerViewReminders.addItemDecoration(new RecyclerView.ItemDecoration()
		{
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
			{
				super.getItemOffsets(outRect, view, parent, state);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ADD_REMINDER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
		{
			reloadData();
		}
	}

	private void reloadData()
	{
		// get current reminder List from dao
		//reminderList = Reminder.getReminders();
		if (reminderList.isEmpty())
		{
			textViewEmpty.setVisibility(View.VISIBLE);
		}
		else
		{
			textViewEmpty.setVisibility(View.GONE);
		}
		// update the reminder  commenting for now
		//remindersAdapter.updateReminders(mReminderList);
	}

	// --------------------------Action Mode Call Back Start-----------------------


	private ActionMode.Callback reminderActionModeCallback = new ActionMode.Callback()
	{

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.expenses_context_menu, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu)
		{
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item)
		{
			switch (item.getItemId())
			{
				case R.id.delete:
					DialogManager.getInstance().createCustomAcceptDialog(getActivity(), getString(R.string.delete), getString(R.string.confirm_delete_items), getString(R.string.confirm), getString(R.string.cancel), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == DialogInterface.BUTTON_POSITIVE) {
								List<Reminder> remindersToDelete = new ArrayList<>();
								for (int position : remindersAdapter.getSelectedItems()) {
									remindersToDelete.add(reminderList.get(position));
								}
								// Call Dao to delete reminders  commenting for now
								//reminderDao.deleteReminders(remindersToDelete);
							}
							reloadData();
							actionMode.finish();
							actionMode = null;
						}
					});
					return true;
				default:
					return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode)
		{
			remindersAdapter.clearSelection();
			actionMode = null;
		}
	};
	// --------------------------Action Mode Call Back  End -----------------------
}

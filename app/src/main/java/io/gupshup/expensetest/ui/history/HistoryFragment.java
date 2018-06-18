package io.gupshup.expensetest.ui.history;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.adapters.ExpensesAdapter;
import io.gupshup.expensetest.bo.Expense;
import io.gupshup.expensetest.custom.BaseViewHolder;
import io.gupshup.expensetest.interfaces.constants.IConstants;
import io.gupshup.expensetest.ui.BaseFragment;
import io.gupshup.expensetest.ui.MainFragment;
import io.gupshup.expensetest.ui.expenses.ExpenseDetailActivity;
import io.gupshup.expensetest.ui.expenses.ExpenseDetailFragment;
import io.gupshup.expensetest.ui.statistics.SelectDateFragment;
import io.gupshup.expensetest.util.DialogManager;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class HistoryFragment extends MainFragment implements BaseViewHolder.RecyclerViewClickListener
{
	private RecyclerView rvHistory;

	private SelectDateFragment selectDateFragment;

	private android.view.ActionMode actionMode;

	private ExpensesAdapter expensesAdapter;

	public static final int REQUEST_WRITE_EXTERNAL_STORE = 101;

	public static HistoryFragment newInstance()
	{
		return new HistoryFragment();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_history, container, false);
		rvHistory = (RecyclerView) rootView.findViewById(R.id.rv_history);
		selectDateFragment = (SelectDateFragment) getChildFragmentManager().findFragmentById(R.id.select_date_fragment);
		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		mainActivityListener.setTitle(getString(R.string.history));

		rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
		rvHistory.setHasFixedSize(true);
		rvHistory.addItemDecoration(new RecyclerView.ItemDecoration()
		{
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
			{
				super.getItemOffsets(outRect, view, parent, state);
			}
		});
		rvHistory.setNestedScrollingEnabled(false);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState)
	{
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null)
		{
			if (savedInstanceState.containsKey(IConstants.TAG_SELECTED_ITEMS))
			{

			}
		}
		boolean isActionMode = savedInstanceState.getBoolean(IConstants.IS_ACTION_MODE_ACTIVATED);
		if (isActionMode)
		{
			actionMode = mainActivityListener.setActionMode(actionModeCallback);
			//actionMode.setTitle(String.valueOf(mExpensesAdapter.getSelectedItems().size()));
			actionMode.invalidate();
		}
	}

	private android.view.ActionMode.Callback actionModeCallback = new android.view.ActionMode.Callback()
	{

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu)
		{
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.expenses_context_menu, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu)
		{
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item)
		{
			switch (item.getItemId())
			{
				case R.id.delete:
					DialogManager.getInstance().createCustomAcceptDialog(getActivity(), getString(R.string.delete), getString(R.string.confirm_delete_items), getString(R.string.confirm), getString(R.string.cancel), new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							if (which == DialogInterface.BUTTON_POSITIVE)
							{
								//ExpensesManager.getInstance().eraseSelectedExpenses();
							}
							//updateData();
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
		public void onDestroyActionMode(android.view.ActionMode mode)
		{
			//mExpensesAdapter.clearSelection();
			actionMode = null;
		}
	};

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu_history, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_export)
		{
			exportExpenses();
		}
		return super.onOptionsItemSelected(item);
	}

	private void exportExpenses()
	{
		if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
		{
			writeFile();
		}
		else
		{
			requestPermission(getActivity());
		}
	}

	public void requestPermission(final Context context)
	{
		if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE))
		{
			// Provide an additional rationale to the user if the permission was not granted
			// and the user would benefit from additional context for the use of the permission.
			// For example if the user has previously denied the permission.

			new AlertDialog.Builder(context)
					.setMessage(context.getString(R.string.write_external_message))
					.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							ActivityCompat.requestPermissions((Activity) context,
															  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
															  REQUEST_WRITE_EXTERNAL_STORE);
						}
					}).show();

		}
		else
		{
			// permission has not been granted yet. Request it directly.
			ActivityCompat.requestPermissions((Activity) context,
											  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
											  REQUEST_WRITE_EXTERNAL_STORE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults)
	{
		switch (requestCode)
		{
			case REQUEST_WRITE_EXTERNAL_STORE:
			{
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					writeFile();
				}
				return;
			}
		}
	}

	private void writeFile()
	{

	}

	@Override
	public void onClick(RecyclerView.ViewHolder vh, int position)
	{
		if (actionMode == null)
		{
			Expense expenseSelected = (Expense) vh.itemView.getTag();
			Intent expenseDetail = new Intent(getActivity(), ExpenseDetailActivity.class);
			expenseDetail.putExtra(ExpenseDetailFragment
										   .EXPENSE_ID_KEY, expenseSelected.getExpenseId());
			startActivity(expenseDetail);
		}
		else
		{
			toggleSelection(position);
		}
	}

	@Override
	public void onLongClick(RecyclerView.ViewHolder vh, int position)
	{
		if (actionMode == null)
		{
			actionMode = mainActivityListener.setActionMode(actionModeCallback);
		}
		toggleSelection(position);
	}

	public void toggleSelection(int position)
	{
		expensesAdapter.toggleSelection(position);
		int count = expensesAdapter.getSelectedItemCount();
		if (count == 0)
		{
			actionMode.finish();
		}
		else
		{
			actionMode.setTitle(String.valueOf(count));
			actionMode.invalidate();
		}
	}
}

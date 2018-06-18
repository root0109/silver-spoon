package io.gupshup.expensetest.ui.expenses;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.adapters.CategoriesAdapter;
import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.bo.Expense;
import io.gupshup.expensetest.constants.AppConstants;
import io.gupshup.expensetest.db.CategoryViewModel;
import io.gupshup.expensetest.db.ExpenseViewModel;
import io.gupshup.expensetest.services.ExpensesWidgetService;
import io.gupshup.expensetest.util.AppUtil;
import io.gupshup.expensetest.util.DialogManager;
import io.gupshup.expensetest.widget.ExpensesWidgetProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewExpenseFragment extends DialogFragment implements View.OnClickListener
{
	private TextView titleTextView;
	private Button   dateButton;
	private Spinner  categorySpinner;
	private EditText descriptionEditText;
	private EditText totalEditText;

	private Date    selectedDate;
	private Expense expense;

	private int userActionMode;
	private ArrayAdapter<Category> categoriesArrayAdapter;

	private ExpenseViewModel expenseViewModel;
	private CategoryViewModel categoryViewModel;

	public static NewExpenseFragment newInstance(int mode, String expenseId)
	{
		NewExpenseFragment newExpenseFragment = new NewExpenseFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(AppConstants.MODE_TAG, mode);
		if (expenseId != null)
		{
			bundle.putString(ExpenseDetailFragment.EXPENSE_ID_KEY, expenseId);
		}
		newExpenseFragment.setArguments(bundle);
		return newExpenseFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);

		// Add an observer on the LiveData returned by getAlphabetizedWords.
		// The onChanged() method fires when the observed data changes and the activity is
		// in the foreground.
		expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
			@Override
			public void onChanged(@Nullable final List<Expense> expenseList) {
				// Update the cached copy of the words in the adapter.
				//expensesAdapter.setExpenses(expenseList);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_new_expense, container, false);
		titleTextView = (TextView) rootView.findViewById(R.id.tv_title);
		dateButton = (Button) rootView.findViewById(R.id.btn_date);
		categorySpinner = (Spinner) rootView.findViewById(R.id.sp_categories);
		descriptionEditText = (EditText) rootView.findViewById(R.id.et_description);
		totalEditText = (EditText) rootView.findViewById(R.id.et_total);
		return rootView;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null)
		{
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.btn_date)
		{
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(selectedDate);
			DialogManager.getInstance().showDatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
			{
				@Override
				public void onDateSet(DatePicker datePicker, int year, int month, int day)
				{
					calendar.set(year, month, day);
					selectedDate = calendar.getTime();
					dateButton.setText(AppUtil.formatDateToString(selectedDate, AppUtil.getCurrentDateFormat()));
				}
			}, calendar);
		}
		else if (view.getId() == R.id.btn_cancel)
		{
			dismiss();
		}
		else if (view.getId() == R.id.btn_save)
		{
			if (categoriesArrayAdapter.getCount() > 0)
			{
				if (!AppUtil.isEmptyField(totalEditText))
				{
					Category currentCategory = (Category) categorySpinner.getSelectedItem();
					String total = totalEditText.getText().toString();
					String description = descriptionEditText.getText().toString();
					if (userActionMode == AppConstants.MODE_CREATE)
					{
						Expense expense = new Expense();
						expense.setDescription(description);
						expense.setDate(selectedDate);
						expense.setCategory(currentCategory);
						expense.setTotal(Float.parseFloat(total));
						expenseViewModel.insert(expense);
					}
					else
					{
						Expense editExpense = new Expense();
						editExpense.setExpenseId(expense.getExpenseId());
						editExpense.setTotal(Float.parseFloat(total));
						editExpense.setDescription(description);
						editExpense.setCategory(currentCategory);
						editExpense.setDate(selectedDate);
						expenseViewModel.update(expense);
					}
					// update widget if the expense is created today
					if (AppUtil.isToday(selectedDate))
					{
						Intent intent = new Intent(getActivity(), ExpensesWidgetProvider.class);
						intent.setAction(ExpensesWidgetService.UPDATE_WIDGET);
						getActivity().sendBroadcast(intent);
					}
					getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
					dismiss();
				}
				else
				{
					DialogManager.getInstance().showShortToast(getString(R.string.error_total));
				}
			}
			else
			{
				DialogManager.getInstance().showShortToast(getString(R.string.no_categories_error));
			}
		}
	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		if (getArguments() != null)
		{
			userActionMode = getArguments().getInt(AppConstants.MODE_TAG) == AppConstants.MODE_CREATE ? AppConstants.MODE_CREATE : AppConstants.MODE_UPDATE;
		}
		List<Category> categoriesList = categoryViewModel.getAllCategories().getValue();

		Category[] categoriesArray = new Category[categoriesList.size()];
		categoriesArray = categoriesList.toArray(categoriesArray);

		categoriesArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, categoriesArray);
		categorySpinner.setAdapter(categoriesArrayAdapter);
		switch (userActionMode)
		{
			case AppConstants.MODE_CREATE:
				selectedDate = new Date();
				break;
			case AppConstants.MODE_UPDATE:
				if (getArguments() != null)
				{
					String id = getArguments().getString(ExpenseDetailFragment.EXPENSE_ID_KEY);
					expense = expenseViewModel.getExpenses(id).getValue();
					titleTextView.setText("Edit");
					selectedDate = expense.getDate();
					descriptionEditText.setText(expense.getDescription());
					totalEditText.setText(String.valueOf(expense.getTotal()));
					int categoryPosition = 0;
					for (int i = 0; i < categoriesArray.length; i++)
					{
						if (categoriesArray[i].getCategoryId() == expense.getCategory().getCategoryId())
						{
							categoryPosition = i;
							break;
						}
					}
					categorySpinner.setSelection(categoryPosition);
				}
				break;
		}
		dateButton.setText(AppUtil.formatDateToString(selectedDate, AppUtil.getCurrentDateFormat()));
		dateButton.setOnClickListener(this);
		(getView().findViewById(R.id.btn_cancel)).setOnClickListener(this);
		(getView().findViewById(R.id.btn_save)).setOnClickListener(this);
	}
}

package io.gupshup.expensetest.ui.statistics;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.util.AppUtil;
import io.gupshup.expensetest.util.DialogManager;

public class SelectDateFragment extends Fragment implements View.OnClickListener
{
	private Button   fromDateButton;
	private Button   toDateButton;
	private TextView textViewTotal;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.layout_date_range, container, false);
		fromDateButton = (Button) rootView.findViewById(R.id.btn_date_from);
		toDateButton = (Button) rootView.findViewById(R.id.btn_date_to);
		textViewTotal = (TextView) rootView.findViewById(R.id.tv_total);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		fromDateButton.setOnClickListener(this);
		toDateButton.setOnClickListener(this);
		fromDateButton.setText(AppUtil.formatDateToString(AppUtil.getFirstDateOfCurrentMonth(), AppUtil.getCurrentDateFormat()));
		toDateButton.setText(AppUtil.formatDateToString(AppUtil.getLastDateOfCurrentMonth(), AppUtil.getCurrentDateFormat()));
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.btn_date_from || view.getId() == R.id.btn_date_to)
		{
			showDateDialog(view.getId());
		}
	}

	private void showDateDialog(final int id)
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(id == R.id.btn_date_from ? AppUtil.getFirstDateOfCurrentMonth() : AppUtil.getLastDateOfCurrentMonth());
		DialogManager.getInstance()
				.showDatePicker(
						getContext(),
						new DatePickerDialog.OnDateSetListener()
						{
							@Override
							public void onDateSet(DatePicker datePicker, int year, int month, int day)
							{
								calendar.set(year, month, day);
								AppUtil.setDateStartOfDay(calendar);
								if (id == R.id.btn_date_from)
								{
									fromDateButton.setText(AppUtil.formatDateToString(calendar.getTime(), AppUtil.getCurrentDateFormat()));
								}
								else
								{
									toDateButton.setText(AppUtil.formatDateToString(calendar.getTime(), AppUtil.getCurrentDateFormat()));
								}
							}
						},
						calendar,
						(R.id.btn_date_from == id) ? null : AppUtil.getFirstDateOfCurrentMonth(),
						(R.id.btn_date_from == id) ? AppUtil.getLastDateOfCurrentMonth() : null);
	}

	public TextView getTextViewTotal()
	{
		return textViewTotal;
	}
}
package io.gupshup.expensetest.adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.gupshup.expensetest.ExpenseTest;
import io.gupshup.expensetest.R;
import io.gupshup.expensetest.adapters.base.BaseRecyclerViewAdapter;
import io.gupshup.expensetest.bo.Expense;
import io.gupshup.expensetest.constants.AppConstants;
import io.gupshup.expensetest.custom.BaseViewHolder;
import io.gupshup.expensetest.db.CategoryViewModel;
import io.gupshup.expensetest.db.ExpenseRepository;
import io.gupshup.expensetest.db.ExpenseViewModel;
import io.gupshup.expensetest.util.AppUtil;

public class ExpensesAdapter extends BaseRecyclerViewAdapter<ExpensesAdapter.ExpenseViewHolder>
{
	protected List<Expense> expenseList;
	protected int lastPosition = -1;
	protected int    colorExpense;
	protected int    colorIncome;
	protected String prefixExpense;
	protected String prefixIncome;
	private   String titleTransitionName;

	private static final int VIEW_TYPE_HEADER = 0;
	private static final int VIEW_TYPE_EXPENSE_ROW = 1;
	private int currentDateMode;

	private ExpenseRepository expenseRepository;

	protected BaseViewHolder.RecyclerViewClickListener onRecyclerClickListener;

	public ExpensesAdapter(Context context, BaseViewHolder.RecyclerViewClickListener onRecyclerClickListener, int currentDateMode)
	{
		this.expenseList = new ArrayList<>();
		this.onRecyclerClickListener = onRecyclerClickListener;
		this.colorExpense = ExpenseTest.getContext().getResources().getColor(R.color.colorAccentRed, context.getTheme());
		this.colorIncome = ExpenseTest.getContext().getResources().getColor(R.color.colorAccentGreen, context.getTheme());
		this.prefixExpense = ExpenseTest.getContext().getResources().getString(R.string.expense_prefix);
		this.prefixIncome = ExpenseTest.getContext().getResources().getString(R.string.income_prefix);
		this.titleTransitionName = ExpenseTest.getContext().getString(R.string.tv_title_transition);
		this.currentDateMode = currentDateMode;
		expenseRepository = new ExpenseRepository(context);
	}

	@NonNull
	@Override
	public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		int layoutId = -1;
		switch (viewType) {
			case VIEW_TYPE_HEADER: {
				layoutId = R.layout.layout_expense_header_item;
				break;
			}
			case VIEW_TYPE_EXPENSE_ROW: {
				layoutId = R.layout.layout_expense_item;
				break;
			}
		}
		View v = LayoutInflater.from(parent.getContext())
				.inflate(layoutId, parent, false);
		return new ExpenseViewHolder(v, onRecyclerClickListener);
	}

	@Override
	public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position)
	{
		holder.itemView.setSelected(isSelected(position));
		switch (getItemViewType(position)) {
			case VIEW_TYPE_HEADER:
				float total = expenseRepository.getTotalExpensesByDateMode(currentDateMode);
				holder.tvTotal.setText(AppUtil.getFormattedCurrency(total));
				String date;
				switch (currentDateMode) {
					case AppConstants.MODE_TODAY:
						date = AppUtil.formatDateToString(AppUtil.getToday(), AppUtil.getCurrentDateFormat());
						break;
					case AppConstants.MODE_WEEK:
						date = AppUtil.formatDateToString(AppUtil.getFirstDateOfCurrentWeek(), AppUtil.getCurrentDateFormat()).concat(" - ").concat(AppUtil.formatDateToString(AppUtil.getRealLastDateOfCurrentWeek(), AppUtil.getCurrentDateFormat()));
						break;
					case AppConstants.MODE_MONTH:
						date = AppUtil.formatDateToString(AppUtil.getFirstDateOfCurrentMonth(), AppUtil.getCurrentDateFormat()).concat(" - ").concat(AppUtil.formatDateToString(AppUtil.getRealLastDateOfCurrentMonth(), AppUtil.getCurrentDateFormat()));
						break;
					default:
						date = "";
						break;
				}
				((ExpenseViewHolder)holder).tvDate.setText(date);
				break;
			case VIEW_TYPE_EXPENSE_ROW:
				final Expense expense = (Expense) expenseList.get(position);
				String prefix;
				switch (expense.getType()) {
					case AppConstants.TYPE_EXPENSE:
						holder.tvTotal.setTextColor(colorExpense);
						prefix = String.format(prefixExpense, AppUtil.getFormattedCurrency(expense.getTotal()));
						break;
					/*case AppConstants.TYPE_INCOME:
						holder.tvTotal.setTextColor(colorIncome);
						prefix = String.format(prefixIncome, Util.getFormattedCurrency(expense.getTotal()));
						break;*/
					default:
						prefix = "";
				}
				if (expense.getCategory() != null)holder.tvCategory.setText(expense.getCategory().getChildCategory());
				if (expense.getDescription() != null && !expense.getDescription().isEmpty()) {
					holder.tvDescription.setText(expense.getDescription());
					holder.tvDescription.setVisibility(View.VISIBLE);
				} else {
					holder.tvDescription.setVisibility(View.GONE);
				}
				holder.tvTotal.setText(prefix);
				holder.itemView.setTag(expense);
//                ViewCompat.setTransitionName(holder.tvTotal, titleTransitionName);
				break;
		}
		if (position > lastPosition)
		{
			Animation animation = AnimationUtils.loadAnimation(ExpenseTest.getContext(), R.anim.push_left_in);
			holder.itemView.startAnimation(animation);
			lastPosition = position;
		}
	}

	@Override
	public int getItemCount()
	{
		return expenseList.size();
	}

	public static class ExpenseViewHolder extends BaseViewHolder
	{

		public TextView tvCategory;
		public TextView tvDescription;
		public TextView tvTotal;
		public TextView tvDate;

		public ExpenseViewHolder(View v, RecyclerViewClickListener onRecyclerClickListener)
		{
			super(v, onRecyclerClickListener);
			tvCategory = (TextView) v.findViewById(R.id.tv_category);
			tvDescription = (TextView) v.findViewById(R.id.tv_description);
			tvTotal = (TextView) v.findViewById(R.id.tv_total);
			tvDate = (TextView)v.findViewById(R.id.tv_date);
		}

	}
}

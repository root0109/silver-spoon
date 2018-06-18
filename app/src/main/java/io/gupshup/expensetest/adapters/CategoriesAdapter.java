package io.gupshup.expensetest.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import io.gupshup.expensetest.ExpenseTest;
import io.gupshup.expensetest.R;
import io.gupshup.expensetest.adapters.base.BaseRecyclerViewAdapter;
import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.custom.BaseViewHolder;

public class CategoriesAdapter extends BaseRecyclerViewAdapter<CategoriesAdapter.CategoryViewHolder>
{
	private List<Category> categoryList;
	private int lastPosition = -1;

	private BaseViewHolder.RecyclerViewClickListener recyclerViewClickListener;

	public CategoriesAdapter(List<Category> categoryList, BaseViewHolder.RecyclerViewClickListener recyclerViewClickListener)
	{
		this.categoryList = categoryList;
		this.recyclerViewClickListener = recyclerViewClickListener;
	}

	@NonNull
	@Override
	public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.layout_category_item, parent, false);
		return new CategoryViewHolder(itemView, recyclerViewClickListener);
	}

	@Override
	public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position)
	{
		final Category category = categoryList.get(position);
		holder.textViewTitle.setText(category.getParentCategory());
		holder.itemView.setTag(category);
		holder.itemView.setSelected(isSelected(position));

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
		return categoryList.size();
	}

	public void updateCategories(List<Category> categoryList)
	{
		this.categoryList = categoryList;
		notifyDataSetChanged();
	}



	public static class CategoryViewHolder extends BaseViewHolder
	{
		public TextView textViewTitle;

		public CategoryViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener)
		{
			super(itemView,recyclerViewClickListener);
			textViewTitle = (TextView)itemView.findViewById(R.id.tv_title);
		}
	}
}

package io.gupshup.expensetest.custom;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
	protected RecyclerViewClickListener onRecyclerClickListener;

	public interface RecyclerViewClickListener
	{
		void onClick(RecyclerView.ViewHolder viewHolder, int position);

		void onLongClick(RecyclerView.ViewHolder viewHolder, int position);
	}

	public BaseViewHolder(View v, RecyclerViewClickListener onRecyclerClickListener)
	{
		super(v);
		this.onRecyclerClickListener = onRecyclerClickListener;
		v.setOnClickListener(this);
		v.setOnLongClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (onRecyclerClickListener != null)
		{
			onRecyclerClickListener.onClick(this, getAdapterPosition());
		}
	}

	@Override
	public boolean onLongClick(View v)
	{
		if (onRecyclerClickListener != null)
		{
			onRecyclerClickListener.onLongClick(this, getAdapterPosition());
		}
		return true;
	}
}

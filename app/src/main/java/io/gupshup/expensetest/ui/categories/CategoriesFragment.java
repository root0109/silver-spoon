package io.gupshup.expensetest.ui.categories;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.adapters.CategoriesAdapter;
import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.constants.AppConstants;
import io.gupshup.expensetest.custom.BaseViewHolder;
import io.gupshup.expensetest.custom.SparseBooleanArrayParcelable;
import io.gupshup.expensetest.db.CategoryViewModel;
import io.gupshup.expensetest.interfaces.constants.IConstants;
import io.gupshup.expensetest.ui.BaseFragment;
import io.gupshup.expensetest.ui.MainFragment;
import io.gupshup.expensetest.util.AppUtil;
import io.gupshup.expensetest.util.DialogManager;

public class CategoriesFragment extends MainFragment implements BaseViewHolder.RecyclerViewClickListener
{
	private RecyclerView recyclerViewCategories;
	private TextView     textViewEmpty;

	private List<Category>    categoryList;
	private CategoriesAdapter categoriesAdapter;
	private static CategoryViewModel categoryViewModel;

	// Action mode for categories.
	private ActionMode actionMode;

	public static CategoriesFragment newInstance()
	{
		return new CategoriesFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Resources res = getResources();
		categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

		categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>()
		{
			@Override
			public void onChanged(@Nullable List<Category> categories)
			{
				categoriesAdapter.updateCategories(categories);
			}
		});

		String[] preDefinedCatgories = res.getStringArray(R.array.predefined_categories);

		categoryList = new ArrayList<>();

		for (int i = 0; i < preDefinedCatgories.length; i++)
		{
			Category category = new Category();
			category.setCategoryId(i);
			category.setType(AppConstants.SYSTEM_CREATED);
			category.setParentCategory(preDefinedCatgories[i]);
			category.setChildCategory("child Test Vaibhav" + i);
			categoryList.add(category);
		}
		categoriesAdapter = new CategoriesAdapter(categoryList, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
		recyclerViewCategories = (RecyclerView) rootView.findViewById(R.id.recycler_view_categories);
		textViewEmpty = (TextView) rootView.findViewById(R.id.textview_empty);
		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		mainActivityListener.setFloatingActionButton(R.drawable.ic_add_white_48dp, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createCategoryDialog(null);
			}
		});
		mainActivityListener.setTitle(getString(R.string.categories));
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
		recyclerViewCategories.setLayoutManager(mLayoutManager);
		recyclerViewCategories.setItemAnimator(new DefaultItemAnimator());
		recyclerViewCategories.setAdapter(categoriesAdapter);
		recyclerViewCategories.setHasFixedSize(true);
		recyclerViewCategories.addItemDecoration(new RecyclerView.ItemDecoration()
		{
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
			{
				super.getItemOffsets(outRect, view, parent, state);
			}
		});

		if (savedInstanceState != null)
		{
			if (savedInstanceState.containsKey(IConstants.TAG_SELECTED_ITEMS))
			{
				categoriesAdapter.setSelectedItems((SparseBooleanArray) savedInstanceState.getParcelable(IConstants.TAG_SELECTED_ITEMS));
				categoriesAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putBoolean(IConstants.IS_ACTION_MODE_ACTIVATED, actionMode != null);
		outState.putParcelable(IConstants.TAG_SELECTED_ITEMS, new SparseBooleanArrayParcelable(categoriesAdapter.getSelectedBooleanArray()));
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState)
	{
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null)
		{
			boolean isActionMode = savedInstanceState.getBoolean(IConstants.IS_ACTION_MODE_ACTIVATED);
			if (isActionMode)
			{
				actionMode = mainActivityListener.setActionMode(actionModeCallback);
				actionMode.setTitle(String.valueOf(categoriesAdapter.getSelectedItems().size()));
				actionMode.invalidate();
			}
		}
	}


	private ActionMode.Callback actionModeCallback = new ActionMode.Callback()
	{
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.expenses_context_menu, menu);
			return true;
		}

		// Called each time the action mode is shown.
		// Always called after onCreateActionMode, but
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
					DialogManager.getInstance().createCustomAcceptDialog(getActivity(), getString(R.string.delete), getString(R.string.confirm_delete_items), getString(R.string.confirm), getString(R.string.cancel), new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							if (which == DialogInterface.BUTTON_POSITIVE)
							{
								List<Category> categoriesToDelete = new ArrayList<>();
								for (int position : categoriesAdapter.getSelectedItems())
								{
									categoriesToDelete.add(categoryList.get(position));
								}
								categoryViewModel.deleteCategories(categoriesToDelete);
							}
							categoryList = categoryViewModel.getAllCategories().getValue();
							if (categoryList.isEmpty())
							{
								textViewEmpty.setVisibility(View.VISIBLE);
							}
							else
							{
								textViewEmpty.setVisibility(View.GONE);
							}
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
			categoriesAdapter.clearSelection();
			actionMode = null;
		}
	};


	@Override
	public void onClick(RecyclerView.ViewHolder viewHolder, int position)
	{
		if (actionMode == null)
		{
			createCategoryDialog(viewHolder);
		}
		else
		{
			toggleSelection(position);
		}
	}

	@Override
	public void onLongClick(RecyclerView.ViewHolder viewHolder, int position)
	{
		if (actionMode == null)
		{
			actionMode = mainActivityListener.setActionMode(actionModeCallback);
			//actionMode = this.getActivity().startActionMode(actionModeCallback);
		}
		toggleSelection(position);
	}

	private void createCategoryDialog(final RecyclerView.ViewHolder viewHolder)
	{
		AlertDialog alertDialog = DialogManager.getInstance().createEditTextDialog(getActivity(), viewHolder != null ? getString(R.string.edit_category) : getString(R.string.create_category), getString(R.string.save), getString(R.string.cancel), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (which == DialogInterface.BUTTON_POSITIVE)
				{
					EditText etTest = (EditText) ((AlertDialog) dialog).findViewById(R.id.et_main);
					if (!AppUtil.isEmptyField(etTest))
					{
						Category category = new Category();
						category.setParentCategory(etTest.getText().toString());
						category.setChildCategory(etTest.getText().toString());
						if (viewHolder != null)
						{
							Category categoryToUpdate = (Category) viewHolder.itemView.getTag();
							category.setCategoryId(categoryToUpdate.getCategoryId());
							categoryViewModel.update(category);
						}
						else
						{
							categoryViewModel.insert(category);
						}
						if (categoryList.isEmpty())
						{
							textViewEmpty.setVisibility(View.VISIBLE);
						}
						else
						{
							textViewEmpty.setVisibility(View.GONE);
						}
						categoriesAdapter.updateCategories(categoryList);
					}
					else
					{
						DialogManager.getInstance().showShortToast(getString(R.string.error_name));
					}
				}
			}
		});
		if (viewHolder != null)
		{
			EditText editTextCategoryName = (EditText) alertDialog.findViewById(R.id.et_main);
			Category category = (Category) viewHolder.itemView.getTag();
			editTextCategoryName.setText(category.getParentCategory());
		}
	}

	public void toggleSelection(int position)
	{
		categoriesAdapter.toggleSelection(position);
		int count = categoriesAdapter.getSelectedItemCount();
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

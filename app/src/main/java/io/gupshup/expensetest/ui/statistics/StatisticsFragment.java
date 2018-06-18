package io.gupshup.expensetest.ui.statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;

import java.util.ArrayList;
import java.util.List;

import io.gupshup.expensetest.R;
import io.gupshup.expensetest.bo.Category;
import io.gupshup.expensetest.constants.AppConstants;
import io.gupshup.expensetest.ui.BaseFragment;
import io.gupshup.expensetest.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends BaseFragment
{
	private TextView textViewPieChartCategoriesEmpty;
	private TextView textViewBarChartCategoriesEmpty;

	private PieChart pieChartCategories;
	private BarChart barChartCategories;

	private List<Category>     categoryList;
	private SelectDateFragment selectDateFragment;

	public static StatisticsFragment newInstance()
	{
		return new StatisticsFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
		pieChartCategories = (PieChart) rootView.findViewById(R.id.pc_categories);
		barChartCategories = (BarChart) rootView.findViewById(R.id.bc_categories);
		textViewPieChartCategoriesEmpty = (TextView)rootView.findViewById(R.id.tv_bar_chart_category_empty);
		textViewBarChartCategoriesEmpty = (TextView)rootView.findViewById(R.id.tv_pie_categories_chart_empty);
		selectDateFragment = (SelectDateFragment)getChildFragmentManager().findFragmentById(R.id.select_date_fragment);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//categoryList = categoryDao.getCategoriesExpense();
		categoryList = new ArrayList<>();
		Category category = new Category();
		category.setParentCategory("Test Vaibhav");
		category.setChildCategory("child Test Vaibhav");
		category.setCategoryId(1);
		category.setType(AppConstants.SYSTEM_CREATED);
		categoryList.add(category);
		// set up pie chart
		pieChartCategories.setCenterText("");
		pieChartCategories.setCenterTextSize(10f);
		pieChartCategories.setHoleRadius(50f);
		pieChartCategories.setTransparentCircleRadius(55f);
		pieChartCategories.setUsePercentValues(true);
		pieChartCategories.setDescription(new Description());
		pieChartCategories.setNoDataText("");

		Legend l = pieChartCategories.getLegend();
		l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
		pieChartCategories.animateY(1500, Easing.EasingOption.EaseInOutQuad);

	}
}

package com.socialnetwork.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socialnetwork.R;
import com.socialnetwork.adapters.helper.ItemTouchHelperAdapter;
import com.socialnetwork.adapters.helper.ItemTouchHelperViewHolder;
import com.socialnetwork.adapters.helper.OnStartDragListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BusinessCardAdapter extends RecyclerView.Adapter<BusinessCardAdapter.ViewHolder>
		implements ItemTouchHelperAdapter
{
	private final List<String> mItems = new ArrayList<>();
	private final OnStartDragListener mDragStartListener;

	public BusinessCardAdapter(Context context, OnStartDragListener dragStartListener)
	{
		mDragStartListener = dragStartListener;
		mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.cards_default)));
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{

		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_business_cards, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{

	}

	@Override
	public int getItemCount()
	{
		return 3;
	}

	@Override
	public boolean onItemMove(int fromPosition, int toPosition)
	{
		Collections.swap(mItems, fromPosition, toPosition);
		notifyItemMoved(fromPosition, toPosition);
		return true;
	}

	@Override
	public void onItemDismiss(int position)
	{

	}

	public class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder
	{
		public ViewHolder(View itemView)
		{
			super(itemView);
		}

		@Override
		public void onItemSelected()
		{
			itemView.setBackgroundColor(Color.LTGRAY);
		}

		@Override
		public void onItemClear()
		{
			itemView.setBackgroundColor(0);
		}
	}
}

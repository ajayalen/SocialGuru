package com.socialnetwork.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.socialnetwork.R;
import com.socialnetwork.adapters.BusinessCardAdapter;
import com.socialnetwork.adapters.helper.OnStartDragListener;
import com.socialnetwork.adapters.helper.SimpleItemTouchHelperCallback;
import com.socialnetwork.base.BaseActivity;

public class BusinessCardsActivity extends BaseActivity implements View.OnClickListener,OnStartDragListener {


    private static final String TAG = BusinessCardsActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initializeUi() {
        setupTitleBar();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_business_cards);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        BusinessCardAdapter adapter = new BusinessCardAdapter(this, this);
        mRecyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void setupTitleBar() {
        mTitleTextView.setText(getString(R.string.business_card_label));
        mBackImageView.setVisibility(View.GONE);
        mAddMoreImageView.setVisibility(View.VISIBLE);
        mAddMoreImageView.setImageResource(R.drawable.add_business_card_icon);
        mAddMoreImageView.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_business_cards;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_add_image:
                break;
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

    }
}

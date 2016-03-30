package com.cat14.moran.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.cat14.moran.R;
import com.cat14.moran.adapter.NodeAdapter;
import com.cat14.moran.model.Node;

import java.util.List;

/**
 * 广场
 */
public class SquareActivity extends BaseActivity {

    private Spinner mSpinner;
    private ListView mListView;
    private LinearLayout mLogging;

    private List<Node> mNodes;

    /**
     * 更新ListView
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mLogging.setVisibility(View.INVISIBLE);
            NodeAdapter adapter = new NodeAdapter(SquareActivity.this, mNodes);
            mListView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initDate();
    }

    private void initDate() {
        handler.sendEmptyMessage(0);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mSpinner = (Spinner) findViewById(R.id.spinner_square_distance);
        mListView = (ListView) findViewById(R.id.list_square_context);
        mLogging = (LinearLayout) findViewById(R.id.ll_logging);
    }




    @Override
    protected int getLayoutId() {
        return R.layout.activity_square;
    }

    @Override
    protected String getName() {
        return getString(R.string.btn_menu_square);
    }
}

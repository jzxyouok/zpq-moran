package com.cat14.moran.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cat14.moran.R;
import com.cat14.moran.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NodeAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Node> mNodes = new ArrayList<>();

    public NodeAdapter(Context context, List<Node> nodes) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mNodes = nodes;
    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mLayoutInflater.inflate(R.layout.item_square_node, null);
            viewHolder = new ViewHolder();
            viewHolder.addressTextView = (TextView) convertView.findViewById(R.id.text_node_address);
            viewHolder.imageRecyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_node_content);

            convertView.setTag(viewHolder);
        }
        // 绑定数据
        viewHolder.addressTextView.setText(mNodes.get(position).getAddress());
        // 创建布局管理器
        AppLayoutManager layoutManger = new AppLayoutManager(mContext);
        // 设置布局方向水平
        layoutManger.setOrientation(LinearLayout.HORIZONTAL);
        // 关联布局
        viewHolder.imageRecyclerView.setLayoutManager(layoutManger);


        // 创建数据适配器
        ImageItemAdapter itemAdapter = new ImageItemAdapter(mNodes.get(position).getImages());
        viewHolder.imageRecyclerView.setAdapter(itemAdapter);

        return convertView;
    }

    static class ViewHolder {
        TextView addressTextView;
        RecyclerView imageRecyclerView;
    }

    static class AppLayoutManager extends LinearLayoutManager {

        public AppLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            View view = recycler.getViewForPosition(0);
            if (view != null) {
                measureChild(view, widthSpec, heightSpec);
                int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                int measuredHeight = view.getMeasuredHeight();
                setMeasuredDimension(measuredWidth, measuredHeight);
            }
        }
    }
}

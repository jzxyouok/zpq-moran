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
        viewHolder.addressTextView.setText(mNodes.get(position).getAddress());
        // 创建布局管理器
        LinearLayoutManager layoutManger = new LinearLayoutManager(mContext);
        layoutManger.setOrientation(LinearLayout.HORIZONTAL);
        viewHolder.imageRecyclerView.setLayoutManager(layoutManger);

        ImageItemAdapter itemAdapter = new ImageItemAdapter(mNodes.get(position).getImages());
        return convertView;
    }

    static class ViewHolder {
        TextView addressTextView;
        RecyclerView imageRecyclerView;
    }
}

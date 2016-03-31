package com.cat14.moran.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cat14.moran.R;
import com.cat14.moran.model.ImageItem;

import java.util.List;

/**
 * 图片项目适配器
 */
public class ImageItemAdapter extends RecyclerView.Adapter<ImageItemAdapter.ViewHolder> {

    private List<ImageItem> mImageItems;

    public ImageItemAdapter(List<ImageItem> imageItems) {
        mImageItems = imageItems;
    }

    // 根据布局文件创建新视图
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_node_image, parent, false);
        return new ViewHolder(view);
    }

    // 替换视图控件的内容
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mPhoto.setImageResource(mImageItems.get(position).getImageId());
        holder.mComment.setText(mImageItems.get(position).getComment());
    }

    // 返回数据集包含项目的数量
    @Override
    public int getItemCount() {
        return mImageItems == null ? 0 : mImageItems.size();
    }

    // 自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPhoto;
        public TextView mComment;

        public ViewHolder(View itemView) {
            super(itemView);
            mPhoto= (ImageView) itemView.findViewById(R.id.image_node_content);
            mComment = (TextView) itemView.findViewById(R.id.text_node_comment);
        }
    }
}

package com.luhuan.rxsimple.drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.luhuan.rxsimple.R;

import java.util.LinkedList;
import java.util.List;

public class DragAdapter extends RecyclerView.Adapter<DragAdapter.Holder> {

    Context mContext;
    LinkedList<ImageItem> mList;
    LayoutInflater inflater;
    OnDragClickListener onDragClickListener;

    //item点击事件设置回调
    public void setOnDragClickListener(OnDragClickListener onDragClickListener) {
        this.onDragClickListener = onDragClickListener;
    }

    public static final int NORMAL = 111;
    public static final int ADD_BUTTON = 222;

    private RequestOptions requestOptions = new RequestOptions().centerCrop();

    public DragAdapter(Context context, LinkedList<ImageItem> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    public void refresh(LinkedList<ImageItem> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getName().equals("ADD_BUTTON")) {
            return ADD_BUTTON;
        } else {
            return NORMAL;
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SquareView squareView = new SquareView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        squareView.setLayoutParams(layoutParams);
        return new Holder(squareView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (position != mList.size() - 1) {
            String path = mList.get(position).getPath();
            Glide.with(mContext).load(path).apply(requestOptions).into(holder.squareView);
        } else {
            Glide.with(mContext).load(R.mipmap.ic_launcher).apply(requestOptions).into(holder.squareView);
        }
        if (holder.getItemViewType()==ADD_BUTTON){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDragClickListener.onClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        SquareView squareView;

        Holder(View itemView) {
            super(itemView);
            squareView = (SquareView) itemView;
        }
    }

    public interface OnDragClickListener{
        void onClick();
        void onLongClick();
    }
}

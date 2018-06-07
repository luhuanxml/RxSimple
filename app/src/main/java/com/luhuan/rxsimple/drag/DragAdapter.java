package com.luhuan.rxsimple.drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.luhuan.rxsimple.R;
import com.luhuan.rxsimple.utils.GlideApp;

import java.util.LinkedList;

public class DragAdapter extends RecyclerView.Adapter<DragAdapter.Holder> {

    public static final int NORMAL = 111;
    public static final int ADD_BUTTON = 222;
    private Context mContext;
    private LinkedList<ImageItem> mList;
    private OnDragClickListener onDragClickListener;
    private RequestOptions requestOptions = new RequestOptions().centerCrop();

    public DragAdapter(Context context, LinkedList<ImageItem> list) {
        mContext = context;
        mList = list;
    }

    //item点击事件设置回调
    public void setOnDragClickListener(OnDragClickListener onDragClickListener) {
        this.onDragClickListener = onDragClickListener;
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
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        ImageItem imageItem = mList.get(position);
        if (position != mList.size() - 1) {
            //Glide.with(mContext).load(imageItem.getPath()).apply(requestOptions).into(holder.squareView);
            GlideApp.loadCircle(imageItem.getPath()).into(holder.squareView);
        } else {
            Glide.with(mContext).load(R.mipmap.ic_launcher).apply(requestOptions).into(holder.squareView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getItemViewType() == ADD_BUTTON) {
                    onDragClickListener.onClick();
                }
                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public interface OnDragClickListener {
        void onClick();
    }

    class Holder extends RecyclerView.ViewHolder {
        SquareView squareView;

        Holder(View itemView) {
            super(itemView);
            squareView = (SquareView) itemView;
        }
    }
}

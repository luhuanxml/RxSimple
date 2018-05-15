package com.luhuan.rxsimple.drag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.caimuhao.rxpicker.utils.RxPickerImageLoader;
import com.luhuan.rxsimple.R;
import com.luhuan.rxsimple.constraint.CustomItemDecoration;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class DragActivity extends AppCompatActivity implements DragTouchCallBack.DragListener {

    LinkedList<ImageItem> mImageItems;

    RecyclerView dragRecyclerView;

    TextView dragDeleteView;

    DragAdapter dragAdapter;

    DragActivity mActivity;

    private final int spanCount=3;
    private ImageItem imageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        dragRecyclerView=findViewById(R.id.drag_recycler_view);
        dragDeleteView=findViewById(R.id.drag_delete_view);
        mActivity=this;
        imageItem = new ImageItem();
        imageItem.setName("ADD_BUTTON");
        mImageItems=new LinkedList<>();
        mImageItems.add(imageItem);
        dragAdapter=new DragAdapter(mActivity,mImageItems);
        dragRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,spanCount));
        dragRecyclerView.setAdapter(dragAdapter);
        CustomItemDecoration customItemDecoration=new CustomItemDecoration();
        dragRecyclerView.addItemDecoration(customItemDecoration);
        DragTouchCallBack dragTouchCallBack=new DragTouchCallBack(mActivity);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(dragTouchCallBack);
        itemTouchHelper.attachToRecyclerView(dragRecyclerView);
        dragAdapter.setOnDragClickListener(new DragAdapter.OnDragClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick() {
                if (mImageItems.size()==10){
                    Toast.makeText(mActivity, "最多添加9张图片", Toast.LENGTH_SHORT).show();
                }else {
                    RxPicker.of().single(false).camera(true)
                            .limit(3,10-mImageItems.size()).start(mActivity)
                            .subscribe(new Consumer<List<ImageItem>>() {
                                @Override
                                public void accept(List<ImageItem> imageItems) throws Exception {
                                    mImageItems.removeLast();
                                    mImageItems.addAll(imageItems);
                                    mImageItems.addLast(imageItem);
                                    dragAdapter.refresh(mImageItems);
                                }
                            });
                }
            }

            @Override
            public void onLongClick() {

            }
        });
    }

    @Override
    public void onMoveResult(int fromPosition, int toPosition) {
        ImageItem imageItem=mImageItems.remove(fromPosition);
        mImageItems.add(toPosition,imageItem);
    }

    int i=0;
    @Override
    public void onDeleteResult(int deletePosition) {
        Log.d("deletePosition", "onDeleteResult: "+i++);
        mImageItems.remove(deletePosition);
    }

    @Override
    public void onDragging(boolean isDragging, RecyclerView.ViewHolder viewHolder) {
        if (isDragging){
            viewHolder.itemView.setAlpha(0.5f);
            dragDeleteView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.itemView.setAlpha(1f);
            dragDeleteView.setVisibility(View.INVISIBLE);
        }
    }
}

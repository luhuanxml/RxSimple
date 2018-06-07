package com.luhuan.rxsimple.drag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.luhuan.rxsimple.R;
import com.luhuan.rxsimple.constraint.CustomItemDecoration;
import com.luhuan.rxsimple.utils.ToolKt;

import java.util.LinkedList;

public class DragActivity extends AppCompatActivity implements DragTouchCallBack.DragListener {
    LinkedList<ImageItem> mImageItems;

    RecyclerView dragRecyclerView;

    TextView dragDeleteView;

    DragAdapter dragAdapter;

    DragActivity mActivity;

    private final int spanCount=3;
    private ImageItem imageItem;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        int i= ToolKt.getDp(10);
        ToolKt.log("DP",String.valueOf(i));
        String b=getIntent().getStringExtra("aaa");
        String d=getIntent().getStringExtra("ccc");
        int f=getIntent().getIntExtra("eee",0);
        ToolKt.log("DP",b+d+f);
        dragRecyclerView=findViewById(R.id.drag_recycler_view);
        dragDeleteView=findViewById(R.id.drag_delete_view);
        mActivity=this;
        imageItem = new ImageItem();
        imageItem.setName("ADD_BUTTON");
        mImageItems=new LinkedList<>();
        mImageItems.add(imageItem);
        dragAdapter=new DragAdapter(mActivity,mImageItems);
        dragRecyclerView.setHasFixedSize(true);
        dragRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,spanCount));
        dragRecyclerView.setAdapter(dragAdapter);
        CustomItemDecoration customItemDecoration=new CustomItemDecoration();
        dragRecyclerView.addItemDecoration(customItemDecoration);
        DragTouchCallBack dragTouchCallBack=new DragTouchCallBack(mActivity);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(dragTouchCallBack);
        itemTouchHelper.attachToRecyclerView(dragRecyclerView);
        dragAdapter.setOnDragClickListener(() -> {
            if (mImageItems.size()==10){
                Toast.makeText(mActivity, "最多添加9张图片", Toast.LENGTH_SHORT).show();
            }else {
                RxPicker.of().single(false).camera(true)
                        .limit(1,10-mImageItems.size()).start(mActivity)
                        .subscribe(imageItems -> {
                            mImageItems.removeLast();
                            mImageItems.addAll(imageItems);
                            mImageItems.addLast(imageItem);
                            dragAdapter.refresh(mImageItems);
                        });
            }
        });
    }

    @Override
    public void onMoveResult(int fromPosition, int toPosition) {
        ImageItem imageItem=mImageItems.remove(fromPosition);
        mImageItems.add(toPosition,imageItem);
    }

    @Override
    public void onDeleteResult(int deletePosition) {
        mImageItems.remove(deletePosition);
    }

    @Override
    public void onDragging() {
            dragDeleteView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDragged() {
            dragDeleteView.setVisibility(View.INVISIBLE);
    }
}

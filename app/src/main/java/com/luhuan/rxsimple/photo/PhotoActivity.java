package com.luhuan.rxsimple.photo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.luhuan.rxsimple.R;
import com.luhuan.rxsimple.constraint.CustomItemDecoration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements CallbackItemTouch{

    RecyclerView recyclerPhoto;
    PhotoAdapter photoAdapter;

    List<Integer> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
//        list.add(8);
//        list.add(9);
        recyclerPhoto=findViewById(R.id.photo_wall);
        recyclerPhoto.setLayoutManager(new GridLayoutManager(this,3));
        photoAdapter=new PhotoAdapter(list);
        recyclerPhoto.setAdapter(photoAdapter);
        recyclerPhoto.addItemDecoration(new CustomItemDecoration());
        ItemTouchHelper.Callback callback = new PhotoItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerPhoto);
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        photoAdapter.notifyItemMoved(oldPosition, newPosition);
        list.add(newPosition,list.remove(oldPosition));
    }

    @Override
    public void itemRefreshOnMoved(int oldPosition, int newPosition) {
//        photoAdapter.notifyItemChanged(oldPosition);
//        photoAdapter.notifyItemChanged(newPosition);

        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).toString());
        }
        System.out.println("");
    }
}

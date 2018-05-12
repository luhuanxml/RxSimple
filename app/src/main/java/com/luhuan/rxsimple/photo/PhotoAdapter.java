package com.luhuan.rxsimple.photo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.Holder> {
    private List<Integer> list;

    public PhotoAdapter(List<Integer> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                300));
        textView.setBackgroundColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        return new Holder(textView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final int mPosition = position;
        if (mPosition < (list.size() - 1)) {
            holder.textView.setBackgroundColor(Color.RED);
        }else {
            holder.textView.setBackgroundColor(Color.GRAY);
        }
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public boolean onLongClick(View view) {
                if (mPosition < list.size() - 1) {
                    Vibrator vibrator = (Vibrator) holder.textView.getContext().getSystemService(Service
                            .VIBRATOR_SERVICE);
                    vibrator.vibrate(200);
                }
                return false;
            }
        });
        holder.textView.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder implements SelectViewHolderHelper {
        TextView textView;

        Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        @Override
        public void itemSelectedColor() {
            textView.setBackgroundColor(Color.GRAY);
        }

        @Override
        public void itemClearColor() {
            textView.setBackgroundColor(Color.RED);
        }
    }
}

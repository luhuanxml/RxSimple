package com.luhuan.rxsimple.drag;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.luhuan.rxsimple.utils.Resolution;

import io.reactivex.annotations.NonNull;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

public class DragTouchCallBack extends ItemTouchHelper.Callback {

    private DragListener dragListener;
    private boolean isExChange = false;

    //拖拽后手抬起来
    private boolean handUp = false;

    public DragTouchCallBack(@NonNull DragListener dragListener) {
        this.dragListener = dragListener;
    }

    //允许长按拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //不允许滑动事件
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        if (viewHolder.getItemViewType() == DragAdapter.ADD_BUTTON) {
            dragFlags = 0;
        } else {
            dragFlags = UP | DOWN | START | END;
        }
        return makeFlag(ACTION_STATE_DRAG, dragFlags);
    }

    //动作完成后才会走onChildDraw 所以在这里设置抬手动作状态为true
    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        handUp = true;
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (ACTION_STATE_DRAG == actionState) {
            viewHolder.itemView.setAlpha(0.5f);
            dragListener.onDragging();
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(1f);
        //拖拽结束如果是删除了则不在这里处理，如果是拖动交换位置则在这里刷新换位后的item position
        //解决交换位置后position没有改变的情况
        if (isExChange){
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        dragListener.onDragged();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Log.d("Y", "onChildDraw: " + dY);
        if (dY > recyclerView.getHeight() - viewHolder.itemView.getBottom() - Resolution.dip2px
                (recyclerView.getContext(), 50)) {
            if (handUp) {
                //删除完成前隐藏
                if (viewHolder.itemView.getVisibility() == View.VISIBLE) {
                    viewHolder.itemView.setVisibility(View.INVISIBLE);
                }
                recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                recyclerView.getAdapter().notifyItemRangeChanged(viewHolder.getAdapterPosition(),
                        recyclerView.getChildCount());
                dragListener.onDeleteResult(viewHolder.getLayoutPosition());
                isExChange = false;
            }
        } else {
            //如果没有到达这个数值原路返回或者是交换位置
            if (handUp) {
                isExChange = true;
            }
        }
        handUp = false;//拖拽删除无论有没有删除，抬手动作设置为false初始值
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (target.getItemViewType() != DragAdapter.ADD_BUTTON) {
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            dragListener.onMoveResult(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 拖动后结果回调
     */
    interface DragListener {

        /**
         * 互换位置结果同步LinkedList
         *
         * @param fromPosition from
         * @param toPosition   to
         */
        void onMoveResult(int fromPosition, int toPosition);

        /**
         * 删除后结果同步LinkedList
         *
         * @param deletePosition delete
         */
        void onDeleteResult(int deletePosition);

        /**
         * 正在拖拽状态的回调
         */
        void onDragging();

        /**
         * 拖拽已经结束回调
         */
        void onDragged();
    }
}

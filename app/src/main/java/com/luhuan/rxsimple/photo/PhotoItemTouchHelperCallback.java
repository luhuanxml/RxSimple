package com.luhuan.rxsimple.photo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.*;

/**
 * Created by luhuanxml on 12/01/2016.
 * recyclerView item拖动回调
 */
public class PhotoItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private CallbackItemTouch callbackItemTouch; // interface
    private int lastPosition;

    public PhotoItemTouchHelperCallback(CallbackItemTouch callbackItemTouch) {
        this.callbackItemTouch = callbackItemTouch;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false; // swiped disabled
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        lastPosition = recyclerView.getChildCount() - 1;
        int dragFlags;
        if (viewHolder.getAdapterPosition() == lastPosition) {
            dragFlags = 0;
        } else {
            dragFlags = UP | DOWN | START | END;
        }
        return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, dragFlags); // as parameter, action drag and flags drag
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof SelectViewHolderHelper) {
                SelectViewHolderHelper selectViewHolderHelper = (SelectViewHolderHelper) viewHolder;
                if (viewHolder.getAdapterPosition() < lastPosition)
                    selectViewHolderHelper.itemSelectedColor();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof SelectViewHolderHelper) {
            SelectViewHolderHelper selectViewHolderHelper = (SelectViewHolderHelper) viewHolder;
            if (viewHolder.getAdapterPosition() < lastPosition) {
                selectViewHolderHelper.itemClearColor();
            }
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int oldPosition = viewHolder.getAdapterPosition();
        int newPosition = target.getAdapterPosition();
        if (newPosition < recyclerView.getChildCount() - 1) {
            callbackItemTouch.itemTouchOnMove(oldPosition, newPosition); // information to the interface
        }
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        callbackItemTouch.itemRefreshOnMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // swiped disabled
    }
}

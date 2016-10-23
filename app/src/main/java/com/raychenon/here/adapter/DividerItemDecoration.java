package com.raychenon.here.adapter;

import com.raychenon.here.R;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Rect;

import android.graphics.drawable.Drawable;

import android.support.annotation.IntRange;

import android.support.v4.content.ContextCompat;

import android.support.v7.widget.RecyclerView;

import android.view.View;

/**
 * @author  Raymond Chenon
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int margin;

    /**
     * @param  context
     * @param  margin   desirable margin size in px between the views in the recyclerView
     */
    public DividerItemDecoration(final Context context,
            @IntRange(from = 0) final int margin) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        this.margin = margin;
    }

    @Override
    public void onDrawOver(final Canvas c, final RecyclerView parent, final RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * Set different margins for the items inside the recyclerView: no top margin for the first row and no left margin
     * for the first column.
     */
    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent,
            final RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        // set top margin to all
        outRect.top = margin;

        // set bottom margin to all
        outRect.bottom = margin;

    }
}

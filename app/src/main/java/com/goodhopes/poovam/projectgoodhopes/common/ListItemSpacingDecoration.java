package com.goodhopes.poovam.projectgoodhopes.common;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by poovam on 3/12/16.
 * recycler view decorator
 */

public class ListItemSpacingDecoration extends RecyclerView.ItemDecoration {


        private int spacing;
        private boolean includeEdge;

        public ListItemSpacingDecoration( int spacing, boolean includeEdge) {

            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);

            outRect.top = spacing;
            outRect.bottom = spacing;
            outRect.right = spacing;
            outRect.left = spacing;
        }

}


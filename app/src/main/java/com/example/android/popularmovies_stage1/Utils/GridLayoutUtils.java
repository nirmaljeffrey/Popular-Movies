package com.example.android.popularmovies_stage1.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
/**
 * These utilities will be used to create column for grids  based on the screen size.
 */
public class GridLayoutUtils {
    public static int calculateNumberOfColumns(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth= displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth/180);
    }
}

package com.example.android.popularmovies_stage1.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
/**
 * The GridLayoutUtils class will be used to create column for grids  based on the screen size.
 */
public final class GridLayoutUtils {
    //Constructor is made private, so this class will not be instantiated
    private GridLayoutUtils(){}
    public static int calculateNumberOfColumns(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth= displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth/180);
    }
}

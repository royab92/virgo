package com.example.rb.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.internal.BaselineLayout;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper extends BottomNavigationView{

    public BottomNavigationViewHelper(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public static void disableShiftMode(BottomNavigationView view, Context context) {
        BottomNavigationItemView item;
        View itemTitle;
       final ViewGroup bottomMenu = (ViewGroup)view.getChildAt(0);
      //  final int bottomMenuChildCount = bottomMenu.getChildCount();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile(FaNum).ttf");
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                 item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
              //  item.setChecked(item.getItemData().isChecked());
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to changevalue of shift mode", e);
        }
        for(int i=0; i<5; i++){
            item = (BottomNavigationItemView)bottomMenu.getChildAt(i);
            //every BottomNavigationItemView has two children, first is an itemIcon and second is an itemTitle
            itemTitle = item.getChildAt(1);
            //every itemTitle has two children, first is a smallLabel and second is a largeLabel. these two are type of AppCompatTextView
            ((TextView)((BaselineLayout) itemTitle).getChildAt(0)).setTypeface(tf, Typeface.BOLD);
            ((TextView)((BaselineLayout) itemTitle).getChildAt(1)).setTypeface(tf, Typeface.BOLD);
        }
    }
}

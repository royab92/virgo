package com.example.rb.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bahrampashootan on 9/9/2018 AD.
 */

public class DanaFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dana,null);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        Typeface tf = Typeface.createFromAsset(this.getActivity().getAssets(), "IRANSansMobile(FaNum).ttf");
        TextView w1=(TextView)view.findViewById(R.id.topbar);
        w1.setTypeface(tf);
      //  FragmentManager manager = getFragmentManager();
        Fragment fragment1=new Tab2Fragment();
        Fragment fragment2=new TabFragment();
      /*  FragmentTransaction transaction = manager.beginTransaction();
        String tag1 = "tab2";
        String tag2 = "tab";
        transaction.addToBackStack( tag1 );
        transaction.addToBackStack( tag2 );
        transaction.commit();*/
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
         adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(fragment1, "هم رده من");
        adapter.addFragment(fragment2, "برترین ها");
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(tf);
                }
            }
        }
    }


}

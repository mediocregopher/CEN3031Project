package edu.ufl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LevelSelect extends FragmentActivity {
    static final int NUM_WORLDS = 3;

    LSAdapter lsAdapter;

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelselect);

        lsAdapter = new LSAdapter(getSupportFragmentManager());

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(lsAdapter);
    }

    public static class LSAdapter extends FragmentPagerAdapter {
        public LSAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_WORLDS;
        }

        @Override
        public Fragment getItem(int position) {
            return World.newInstance(position);
        }
    }

    public static class World extends Fragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static World newInstance(int num) {
            World f = new World();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.world_pager, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView)tv).setText("World" + (mNum+1));
            
            View gv = v.findViewById(R.id.levelgrid);
            List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
            
            for(int i=0;i<NUM_WORLDS;i++){
                HashMap<String, String> hm = new HashMap<String,String>();
                hm.put("lvl", "LVL-" + (i+1));
                aList.add(hm);
            }
     
            // Keys used in Hashmap
            String[] from = {"lvl"};
     
            // Ids of views in listview_layout
            int[] to = { R.id.lvl};
            ((GridView)gv).setAdapter(new SimpleAdapter(container.getContext(), aList, R.layout.level_gridlayout, from, to));
            ((GridView)gv).setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                        int position, long id) {
                    try{
                        Class<?> myClass = Class.forName("edu.ufl.thegame");
                        Intent intent = new Intent(getActivity(), myClass);
                        startActivity(intent);
                    }
                    catch(ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }
            });
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }


    }
}
package edu.ufl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.TextView;

public class LevelSelect extends FragmentActivity {
    static final int[][] LEVEL_LOOKUP = new int[][] {
                        {R.raw.level1_1,R.raw.level1_2,R.raw.level1_3,R.raw.level1_4,R.raw.level1_5},
                        {R.raw.level2_1,R.raw.level2_2,R.raw.level2_3,R.raw.level2_4,R.raw.level2_5},
                        {R.raw.level3_1,R.raw.level3_2,R.raw.level3_3}};
    static final int NUM_WORLDS = LEVEL_LOOKUP.length;
    static final int LVL_PER_WORLD = LEVEL_LOOKUP[0].length;

    LSAdapter lsAdapter;

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelselect);

        lsAdapter = new LSAdapter(getSupportFragmentManager());

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(lsAdapter);
        ResourceManager.init(this);
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
        int worldNum;

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
            worldNum = getArguments() != null ? getArguments().getInt("num") + 1 : 0;
        }
        
        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences settings = ResourceManager.getPreferences();
            int lvlComp = settings.getInt("levelCompleted", 0);
            if (lvlComp/5+1 == worldNum) {
                try {
                    int position = lvlComp%LVL_PER_WORLD;
                    GridView gv = (GridView) getView().findViewById(R.id.levelgrid);
                    ImageView iv = (ImageView) gv.getChildAt(position).findViewById(R.id.lvl_btn);
                    iv.setImageResource(R.drawable.levelbutton);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.world_pager, container, false);
            TextView tv = (TextView) v.findViewById(R.id.text);
            tv.setText("World" + worldNum);
            Typeface font = Typeface.createFromAsset(ResourceManager.getAssetManager(), "smb.ttf");  
            tv.setTypeface(font);  
            
            View gv = v.findViewById(R.id.levelgrid);
            List<HashMap<String,Object>> aList = new ArrayList<HashMap<String,Object>>();
            SharedPreferences settings = ResourceManager.getPreferences();
            int lvlsComp = settings.getInt("levelCompleted", 0);
            for(int i=0;i<LVL_PER_WORLD;i++){
                HashMap<String, Object> hm = new HashMap<String,Object>();
                hm.put("lvl", "LVL " + (worldNum) + "-" + (i+1));
                if (worldNum*i <= lvlsComp) {
                    hm.put("img", R.drawable.levelbutton);
                } else {
                    hm.put("img", R.drawable.levelbutton_lock);
                }
                aList.add(hm);
            }
     
            // Keys used in Hashmap
            String[] from = {"lvl", "img"};
     
            // Ids of views in listview_layout
            int[] to = { R.id.lvl, R.id.lvl_btn};
            ((GridView)gv).setAdapter(new mSimpleAdapter(container.getContext(), aList, R.layout.level_gridlayout, from, to));
            ((GridView)gv).setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                        int position, long id) {
                    try{
                        Class<?> myClass = Class.forName("edu.ufl.thegame");
                        Intent intent = new Intent(getActivity(), myClass);
                        intent.putExtra("lvl", LEVEL_LOOKUP[worldNum-1][position]);
                        GameLog.d("LevelSelect", "Chose level: " + worldNum + "-" + (position+1));
                        SharedPreferences settings = ResourceManager.getPreferences();
                        int levelsCompleted = settings.getInt("levelCompleted", 0);
                        if (LVL_PER_WORLD*(worldNum-1)+position <= levelsCompleted) {
                            startActivity(intent);
                        }
                        
                    }
                    catch(ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }
            });
            return v;
        }


    }
}

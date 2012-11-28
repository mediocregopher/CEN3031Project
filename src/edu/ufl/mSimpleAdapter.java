package edu.ufl;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class mSimpleAdapter extends SimpleAdapter {


    public mSimpleAdapter(Context context, List<? extends Map<String, ?>> data,
            int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        ResourceManager.init(context);
    }

    @Override
    public void setViewText(TextView v, String text) {
        v.setText(text);
        Typeface font = Typeface.createFromAsset(ResourceManager.getAssetManager(), "smb.ttf");  
        v.setTypeface(font);
    }
}

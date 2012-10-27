package edu.ufl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import java.util.HashMap;
import java.util.ArrayList;
import org.json.*;

public class Sprite {

    public static enum SpriteType {
        ALBERT,
        ALBERT_WALKING,
        ALBERT_SPRINTING,
        ALBERT_DEAD
    }

    private final static HashMap<SpriteType,SpriteSpec> spriteSpecs = new HashMap<SpriteType,SpriteSpec>() {{
        put( SpriteType.ALBERT,           new SpriteSpec( R.drawable.albert,
                                                          R.raw.albert_sprite ));
        put( SpriteType.ALBERT_WALKING,   new SpriteSpec( R.drawable.albert_walking,
                                                          R.raw.albert_walking_sprite ));
        put( SpriteType.ALBERT_SPRINTING, new SpriteSpec( R.drawable.albert_walking,
                                                          R.raw.albert_sprinting_sprite ));
        put( SpriteType.ALBERT_DEAD,      new SpriteSpec( R.drawable.albert_dead,
                                                          R.raw.albert_dead_sprite ));
    }};

    private Bitmap bitmap;
    private Bitmap fbitmap;
    private SpriteType type;
    private float width;
    private float height;

    private boolean loop;
    private boolean flipped = false;
    private ArrayList<Integer> framesper;

    private int curr = 0;
    private int currCount;
    private int numFrames;
    private RectF mask;

    Sprite(SpriteType type) {
        this.type = type;

        SpriteSpec spec = Sprite.spriteSpecs.get(type);

        JSONObject sjson = JSON.fileAsObject( spec.jsonid );
        this.bitmap = BitmapFactory.decodeResource( ResourceManager.getResources(),
                                                    spec.drawableid );
        this.fbitmap = Bitmap.createScaledBitmap(this.bitmap,-this.bitmap.getWidth(),this.bitmap.getHeight(),false);
        this.height = this.bitmap.getHeight();

        /* Get sprite width */
        this.width = 0;
        try { this.width = ResourceManager.dpToPx(sjson.getInt("spritewidth")); }
        catch (JSONException e) { GameLog.d("Sprite","Error getting spritewidth"); }

        /* Get loop (default false) */
        try { this.loop = sjson.getBoolean("loop");  }
        catch (JSONException e) { this.loop = false; }

        /* Get framesper (default [1]) */
        try {
            JSONArray framesper_json = sjson.getJSONArray("framesper");
            this.framesper = new ArrayList<Integer>();
            for (int i = 0; i < framesper_json.length(); i++) {
                this.framesper.add((Integer)framesper_json.get(i));
            }
        }
        catch (JSONException e) {
            this.framesper = new ArrayList<Integer>();
            this.framesper.add(1);
        }

        this.currCount = this.framesper.get(0).intValue();
        this.mask = new RectF(0,0,this.width,this.height);
        this.numFrames = this.framesper.size();
    }

    public float getWidth()  { return this.width;  }
    public float getHeight() { return this.height; }
    public boolean getFlipped() { return this.flipped; }
    public SpriteType getType() { return this.type;    }


    public void flip() { this.flipped = !this.flipped; }

    /* These only work if the original sprite is "facing" right" */
    public void faceRight() { if (this.flipped)  this.flip(); }
    public void faceLeft()  { if (!this.flipped) this.flip(); }

    public void update() {
        /* If we're done running this frame of the sprite we need to figure out
           what to do */
        if (this.currCount == 0) {
            this.curr++;

            /* If we've gone through all the frames, check if we should loop */
            if (this.curr == this.numFrames) {
                if (this.loop) this.curr = 0;
                else this.curr--;
            }

            this.currCount = this.framesper.get(this.curr).intValue();
        }

        /* Position mask based on whether or not we're flipped */
        if (!this.flipped) this.mask.offsetTo(this.curr*this.width+1,0);
        else               this.mask.offsetTo( (this.numFrames - this.curr - 1)*this.width+1, 0);
        this.currCount--;
    }

    public void draw(RectF rectf, Canvas canvas, Camera camera) {
        if (this.flipped) camera.draw(this.mask,rectf,this.fbitmap,canvas);
        else              camera.draw(this.mask,rectf,this.bitmap, canvas);
    }

    /* 
     * God I hate java. This should be a pair or a tuple,
     * but the java gods didn't think those were descriptive enough.
     * so now we're stuck making classes for two fucking variable
     * structures 
     */
    private static class SpriteSpec {
        public final int drawableid;  
        public final int jsonid;  
        public SpriteSpec(int o, int t) {
            this.drawableid = o;      
            this.jsonid = t;      
        }
    }

}


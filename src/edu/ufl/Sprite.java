package edu.ufl;

import android.graphics.Bitmap;
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
        ALBERT_DEAD,
        ALBERT_FALLING,
        ALBERT_LASER
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
        put( SpriteType.ALBERT_FALLING,   new SpriteSpec( R.drawable.albert_falling,
                                                          R.raw.albert_sprite ));
        put( SpriteType.ALBERT_LASER,     new SpriteSpec( R.drawable.albert_laser,
                                                          R.raw.albert_laser ));
    }};

    private Bitmap bitmap;
    private SpriteType type;
    private float width;
    private float height;

    private boolean loop;
    private boolean flipped = false;
    private ArrayList<Integer> framesper;
    private float offsetFacingLeft;
    private float offsetFacingRight;

    private int curr = 0;
    private int currCount;
    private int numFrames;
    private RectF mask;

    Sprite(SpriteType type) {
        this.type = type;

        SpriteSpec spec = Sprite.spriteSpecs.get(type);

        JSONObject sjson = JSON.fileAsObject( spec.jsonid );
        this.bitmap = ResourceManager.getBitmap(spec.drawableid);
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

        try { this.offsetFacingLeft = (float)(ResourceManager.dpToPx(sjson.getInt("offsetFacingLeft"))); }
        catch (JSONException e) { this.offsetFacingLeft = 0; }

        try { this.offsetFacingRight = (float)(ResourceManager.dpToPx(sjson.getInt("offsetFacingRight"))); }
        catch (JSONException e) { this.offsetFacingRight = 0; }

        this.currCount = this.framesper.get(0).intValue();
        this.mask = new RectF(0,0,this.width,this.height);
        this.numFrames = this.framesper.size();
    }

    /* Copy constructor */
    Sprite(Sprite s) {
        this.bitmap = s.bitmap;
        this.type = s.type;
        this.width = s.width;
        this.height = s.height;

        this.loop = s.loop;
        this.flipped = s.flipped;
        this.framesper = s.framesper;

        this.curr = s.curr;
        this.currCount = s.currCount;
        this.numFrames = s.numFrames;
        this.mask = new RectF(s.mask);
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
        this.mask.offsetTo(this.curr*this.width,0);
        this.currCount--;
    }

    public void draw(float x, float y, Canvas canvas, Camera camera) {
        if (!this.flipped) {
            RectF placement = new RectF(
                x + this.offsetFacingRight,
                y,
                x + this.offsetFacingRight + this.width,
                y + this.height
            );
            camera.draw(this.mask,placement,this.bitmap,canvas);
        }
        else {
            RectF placement = new RectF(
                x + this.offsetFacingLeft,
                y,
                x + this.offsetFacingLeft + this.width,
                y + this.height
            );
            camera.drawFlipped(this.mask,placement,this.bitmap, canvas);
        }
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


package edu.ufl;

import java.util.HashMap;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.AudioManager;
import android.content.Context;
import android.net.Uri;

/**
 * A static class used for storing and playing all audio in the game.
 * SoundManager uses a SoundPool for short audio files and a
 * MediaPlayer for long audio files (80KB+). File references are stored in HashMaps,
 * with each reference having its own ID in the proper map.
 */
public class SoundManager {
	
	static private SoundManager _instance;
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> soundPoolMap;
	private static AudioManager audioManager;
	private static Context context;
	private static MediaPlayer mediaPlayer;
	private static HashMap<Integer, Uri> mediaPlayerMap;
	
	private SoundManager()	{ }
	
	static synchronized public SoundManager getInstance() {
		if (_instance == null)
			_instance = new SoundManager();
		return _instance;
	}
	
	public static void initSounds(Context theContext) {
		context = theContext;
		soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
		soundPoolMap = new HashMap<Integer, Integer>();
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayerMap = new HashMap<Integer, Uri>();
	}
	
	public static void addSound(int index, int soundID) {
		soundPoolMap.put(index, soundPool.load(context, soundID, 1));
	}
	
	/**
	 * Adds short audio files to the soundPool map for use.
	 */
	public static void loadSounds() {
		addSound(1, R.raw.menu_beep);
		addSound(2, R.raw.boing_spring);
		addSound(3, R.raw.odd_bounce);
		addSound(4, R.raw.death);
		addSound(5, R.raw.laser);
	}
	
	/**
	 * 
	 * @param index - the key of the desired sound in the soundPoolMap 
	 * @param speed - used to control playback speed (0.5 = half, 2.0 = double)
	 * @param isLooping - set to true if sound loops, otherwise use false for single sounds
	 */
	public static void playSound(int index, float speed, boolean isLooping)	{
		float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if (isLooping)
			soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, -1, speed);
		else
			soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed);
		GameLog.d("SoundManager", "Play sound " + index);
	}
	
	public static void stopSound(int index)
	{
		soundPool.stop(soundPoolMap.get(index));
	}
		
	/**
	 * Creates a Uri object used to identify files for MediaPlayer
	 */
	public static void addMedia(int index, String path) {
		Uri media = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + path);
		mediaPlayerMap.put(index, media);
	}
	
	/**
	 * Adds longer audio files to mediaPlayer map for future use
	 */
	public static void loadMedia() {
		addMedia(1, "go_gators_live");
		addMedia(2, "orange_blue");
		addMedia(3, "go_gators_studio");
	}	
	
	/**
	 * playMedia(int index) sets a new data source for the mediaPlayer with Uri from mediaPlayerMap,
	 * prepares the mediaPlayer once set, and then starts it.
	 * 
	 * @param index - the key of the desired audio file in the mediaPlayerMap
	 */
	public static void playMedia(int index) {
		try {
		    mediaPlayer.reset();
			mediaPlayer.setDataSource(context, mediaPlayerMap.get(index));
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch(IOException ex) {
			GameLog.d("SoundManager", "Media file not found");
		}
		catch(IllegalArgumentException ex) {
			GameLog.d("SoundManager", "Unable to play media file");
		}
	}
	
	/**
	 * Pauses playback of the mediaPlayer.
	 */
	public static void pauseMedia() {
		mediaPlayer.pause();
	}
	
	/**
	 * Resumes playing from where the mediaPlayer was paused.
	 */
	public static void resumeMedia() {
		mediaPlayer.start();
	}
	
	/**
	 * Resets the mediaPlayer so that a new file can be loaded into the player.
	 */
	public static void resetMedia() {
		mediaPlayer.reset();
	}
		
	public static void cleanup() {
		soundPool.release();
		soundPool = null;
		soundPoolMap.clear();
		mediaPlayer.release();
		mediaPlayer = null;
		mediaPlayerMap.clear();
		audioManager.unloadSoundEffects();
		_instance = null;
	}
}

package tjuhot.solapop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameMain extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	private static final String DB_FILTER = "GameMain";
	private Music mMusic;
	private static final String STR_RAT_PATH[] = {
		"keng",
		"rat1",
		"rat2",
		"rat3",
		"rat4",
		"rat5",
		"rat6"
	};
	public static final String STR_HITRAT = "hitrat";
	
	List<TextureRegion> mRatTextureRegions;
	List<Texture>       mRatTextures;
	
	List<TextureRegion> mHitRatTextureRegions;
	List<Texture>       mHitRatTextures;
	Camera mCamera;

	void debug(String msg) {
		Log.d(DB_FILTER, msg);
	}

	public void onLoadComplete() {

	}

	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(
				new EngineOptions(true, ScreenOrientation.LANDSCAPE,
						new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
						mCamera).setNeedsSound(true).setNeedsMusic(true));
	}

	public void onLoadResources() {
		loadRatTexture();
		loadHitRatTexture();
		loadHitMusic();
	}
	void loadHitRatTexture(){
		mHitRatTextureRegions = new ArrayList<TextureRegion>();
		mHitRatTextures = new ArrayList<Texture>();
		for(int i=1;i<STR_RAT_PATH.length;++i){
			Texture t = new Texture(128, 128);
			TextureRegion temp = TextureRegionFactory.createFromAsset(t
					, getApplicationContext(), String.format("gfx/%s%d.png", STR_HITRAT,i),0,0);
			mHitRatTextureRegions.add(temp);
			mHitRatTextures.add(t);
			this.mEngine.getTextureManager().loadTexture(t);
		}
	}
	void loadRatTexture(){
		mRatTextureRegions = new ArrayList<TextureRegion>();
		mRatTextures = new ArrayList<Texture>();
		for(int i=0;i<STR_RAT_PATH.length;++i){
			Texture tempt = new Texture(128, 128);
			TextureRegion temp = TextureRegionFactory.createFromAsset(tempt
					, getApplicationContext(), String.format("gfx/%s.png", STR_RAT_PATH[i]),0,0);
			mRatTextureRegions.add(temp);
			mRatTextures.add(tempt);
			this.mEngine.getTextureManager().loadTexture(tempt);
			debug(String.format("gfx/%s.png", STR_RAT_PATH[i]));
		}
		debug(String.format("RatTexture size = %d", mRatTextureRegions.size()));
	}
	void loadHitMusic()
	{
		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), 
					this, "kick_sound.mp3");
//			this.mMusic.setLooping(true);
		} catch (final IOException e) {
			debug("HitMusic load Error");
		}
	}
	
	public Scene onLoadScene() {
		debug("OnLoadScense");
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(1);
		final Scenario sc = new Scenario(mRatTextureRegions, mHitRatTextureRegions,mMusic);
		scene.getLastChild().attachChild(sc);
		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				float x = pSceneTouchEvent.getX();
				float y = pSceneTouchEvent.getY();
				sc.onClick((int)x, (int)y);
				return false;
			}
		});
		sc.addRat(0, 0);
		sc.addRat(250, 250);
		sc.addRat(400, 400);
		scene.registerUpdateHandler(new TimerHandler( 0.2f , true,new ITimerCallback() {
			public void onTimePassed(TimerHandler pTimerHandler) {
				sc.step();
			}
		}));
		return scene;    
	}

}
package tjuhot.solapop;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;
import android.os.Bundle;

public class GameMain extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	Camera mCamera;

	public void onLoadComplete() {

	}

	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true,ScreenOrientation.LANDSCAPE
				,new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera)
				.setNeedsSound(true).setNeedsMusic(true));
	}

	public void onLoadResources() {
		// TODO Auto-generated method stub

	}

	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		return null;
	}

}
package tjuhot.reyoung.testandengine;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.engine.options.*;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

public class Main extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private Texture mFontTexture;
	private Font mFont;

	
	
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	public void onLoadResources() {
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.BLACK);

		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
	}

	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(1);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		final Text textCenter = new Text(100, 60, this.mFont, "Hello AndEngine!\nYou can even have multilined text!", HorizontalAlign.CENTER);
		final Text textLeft = new Text(100, 200, this.mFont, "Also left aligned!\nLorem ipsum dolor sit amat...", HorizontalAlign.LEFT);
		final Text textRight = new Text(100, 340, this.mFont, "And right aligned!\nLorem ipsum dolor sit amat...", HorizontalAlign.RIGHT);

		scene.getLastChild().attachChild(textCenter);
		scene.getLastChild().attachChild(textLeft);
		scene.getLastChild().attachChild(textRight);

		return scene;
	}

	public void onLoadComplete() {

	}
}
package tjuhot.solapop;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import tjuhot.solapop.GameMain;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class GameMenu extends BaseGameActivity  implements IOnMenuItemClickListener {

	private static final int CAMERA_WIDTH = 512;
	private static final int CAMERA_HEIGHT = 300;
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 220;
	protected static final int MENU_OK = 0;
	protected static final int MENU_RESET = MENU_OK + 1;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	



	protected Camera mCamera;

	protected Scene mMainScene;

	private Texture mTexture;

	private TextureRegion mFaceTextureRegion;

	protected MenuScene mMenuScene;

	private Texture mMenuTexture;
	protected TextureRegion mMenuResetTextureRegion;
	protected TextureRegion mMenuQuitTextureRegion;
	protected TextureRegion mMenuOKTextureRegion;


	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera));
	}


	public void onLoadResources() {
		this.mTexture = new Texture(1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mFaceTextureRegion = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "gfx/background.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(this.mTexture);
		
		this.mMenuTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuQuitTextureRegion = TextureRegionFactory.createFromAsset(
				this.mMenuTexture, this, "gfx/menu_quit.png", 0, 0);
		
		this.mMenuResetTextureRegion = TextureRegionFactory.createFromAsset(
				this.mMenuTexture, this, "gfx/menu_reset.png", 0, 0);

		this.mMenuOKTextureRegion = TextureRegionFactory.createFromAsset(
				this.mMenuTexture, this, "gfx/menu_ok.png", 0, 50);

		this.mEngine.getTextureManager().loadTexture(this.mMenuTexture);		
	}


	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

//		this.createMenuScene();
//		Log.e("TAG", "Step1");
		/* Just a simple scene with an animated face flying around. */
		this.mMainScene = new Scene(1);
		this.mMainScene.setBackground(new ColorBackground(0.09804f, 0.6274f,
				0.8784f));

		
		final Sprite face = new Sprite(0,0, this.mFaceTextureRegion);
//		face.registerEntityModifier(new MoveModifier(30, 0, CAMERA_WIDTH
//				- face.getWidth(), 0, CAMERA_HEIGHT - face.getHeight()));
		this.mMainScene.getLastChild().attachChild(face);
		
		final Sprite button1 = new Sprite(BUTTON_WIDTH,BUTTON_HEIGHT, this.mMenuOKTextureRegion);
		this.mMainScene.getLastChild().attachChild(button1);
		mMainScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				float x = pSceneTouchEvent.getX();
				float y = pSceneTouchEvent.getY();
				if(x>BUTTON_WIDTH&&x<(BUTTON_WIDTH+220)&&y>BUTTON_HEIGHT&&y<(BUTTON_HEIGHT+50)){
					Intent intent= new Intent(GameMenu.this,FileSelector.class);
					startActivity(intent);
				}
				
				return false;
			}
		});
		
		
	
		

		return this.mMainScene;
	}


	public void onLoadComplete() {

	}


	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_MENU
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if (this.mMainScene.hasChildScene()) {
				/* Remove the menu and reset it. */
				this.mMenuScene.back();
			} else {
				/* Attach the menu. */
				this.mMainScene.setChildScene(this.mMenuScene, false, true,
						true);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}


	public boolean onMenuItemClicked(final MenuScene pMenuScene,
			final IMenuItem pMenuItem, final float pMenuItemLocalX,
			final float pMenuItemLocalY) {
		
		Log.e("TAG","press");
		if(pMenuItemLocalX>50&&pMenuItemLocalY>50&&pMenuItemLocalX<100&&pMenuItemLocalY<100){
			Intent intent= new Intent(GameMenu.this,GameMain.class);
			startActivity(intent);
		}
		
		switch (pMenuItem.getID()) {
		case MENU_OK:
			Intent intent= new Intent(GameMenu.this,GameMain.class);
			startActivity(intent);
		case MENU_RESET:
			/* Restart the animation. */
			this.mMainScene.reset();

			/* Remove the menu and reset it. */
			this.mMainScene.clearChildScene();
			this.mMenuScene.reset();
			return true;
		case MENU_QUIT:
			/* End Activity. */
			this.finish();
			return true;
		default:
			return false;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

//	protected void createMenuScene() {
//		this.mMenuScene = new MenuScene(this.mCamera);
//
//		final SpriteMenuItem okMenuItem = new SpriteMenuItem(MENU_OK,
//				this.mMenuOKTextureRegion);
//		okMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA,
//				GL10.GL_ONE_MINUS_SRC_ALPHA);
//		this.mMenuScene.addMenuItem(okMenuItem);
//		
//		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(MENU_RESET,
//				this.mMenuResetTextureRegion);
//		resetMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA,
//				GL10.GL_ONE_MINUS_SRC_ALPHA);
//		this.mMenuScene.addMenuItem(resetMenuItem);
//		
//		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT,
//				this.mMenuQuitTextureRegion);
//		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA,
//				GL10.GL_ONE_MINUS_SRC_ALPHA);
//		this.mMenuScene.addMenuItem(quitMenuItem);
//		
//		this.mMenuScene.buildAnimations();
//
//		this.mMenuScene.setBackgroundEnabled(false);
//
//		this.mMenuScene.setOnMenuItemClickListener(this);
//	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

package tjuhot.solapop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Context;
import android.graphics.Point;

public class LevelStub implements ILevel {

	Context m_context;
	Engine	m_engine;
	public LevelStub(Context app,Engine eng){
		m_context = app;
		m_engine = eng;
	}
	public TextureRegion getBackground() {
		Texture t = new Texture(2048, 1024); 
		TextureRegion retv =  TextureRegionFactory.createFromAsset(t, m_context
				, "stub/bg.png", 0, 0);
		m_engine.getTextureManager().loadTexture(t);
		return retv;
	}

	public Music getMusic() {
		try {
			Music retv =  MusicFactory.createMusicFromAsset(m_engine.getMusicManager()
					, m_context, "stub/bgm.mp3");
			assert(retv!=null);
			return retv;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public List<Point> getRats() {
		List<Point> retv = new ArrayList<Point>();
		retv.add(new Point(512, 300));
		retv.add(new Point(512+150,300+60));
		retv.add(new Point(512+150+160,300+60));
		retv.add(new Point(512+60,300+150));
		return retv;
	}
	public List<Long> getBeats() {
		List<Long > retv= new ArrayList<Long>();
		Random r = new Random(17);
		for(int i=0;i<1000;++i){
			long l = r.nextLong()%(300*1000);
			retv.add(l>0?new Long(l):new Long(-l));
		}
		Collections.sort(retv);
		return retv;
	}
	public int getRatSize() {
		return 4;
	}

}

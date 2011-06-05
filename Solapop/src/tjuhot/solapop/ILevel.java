package tjuhot.solapop;

import java.util.List;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.Point;

public interface ILevel {
	public List<Point>	 getRats();
	public TextureRegion getBackground();
	public Music		 getMusic();
	public List<Long>	getBeats();
	public int			getRatSize();
}

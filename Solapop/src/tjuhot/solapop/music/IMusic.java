package tjuhot.solapop.music;

import org.anddev.andengine.audio.music.Music;

/**
*
* @author YQ
*/
public interface IMusic {
   public long BeatTime(int beatIndex);
   public String getMusic();
   public int beatSize();
}
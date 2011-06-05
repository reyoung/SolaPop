package tjuhot.solapop.music;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlpFileReader implements IMusic{

    private String slpFileNameString = null;
    private String titleString = null;
    private String artistString = null;
    private String musicString = null;
    private Double offset = null;
    private Double time = null;
    private Double[][] bpm = null;
    private List<slpAttribute<Integer, Boolean>> slpNoteList = new ArrayList<slpAttribute<Integer, Boolean>>();
    private List<beatAttribute<Integer, Double>> beatList = new ArrayList<beatAttribute<Integer, Double>>();

    public SlpFileReader(String slpFileName) {
        this.slpFileNameString = slpFileName;
        File file = new File(slpFileNameString);
        List list = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
        } catch (IOException e) {
        }
        int count = 1;
        for (int i = 0; i < list.size(); i++) {
            String conf = list.get(i).toString();
            if (conf.startsWith("#TITLE")) {
                String[] subcom = conf.split(":");
                String[] substr = subcom[1].split(";");
                titleString = substr[0];
            } else if (conf.startsWith("#ARTIST")) {
                String[] subcom = conf.split(":");
                String[] substr = subcom[1].split(";");
                artistString = substr[0];
            } else if (conf.startsWith("#MUSIC")) {
                String[] subcom = conf.split(":");
                String[] substr = subcom[1].split(";");
                musicString = substr[0];
            } else if (conf.startsWith("#OFFSET")) {
                String[] subcom = conf.split(":");
                String[] substr = subcom[1].split(";");
                if (substr[0].startsWith("-")) {
                    substr = substr[0].split("-");
                    offset = -1 * Double.parseDouble(substr[1]);
                } else {
                    offset = Double.parseDouble(substr[0]);
                }
            } else if (conf.startsWith("#TIME")) {
                String[] subcom = conf.split(":");
                String[] substr = subcom[1].split(";");
                time = Double.parseDouble(substr[0]);
            } else if (conf.startsWith("#BPMS")) {
                String[] subcom = conf.split(":");
                String[] substr = subcom[1].split(";");
                String[] subpau = substr[0].split(",");
                bpm = new Double[2][2];
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 2; j++) {
                        bpm[k][j] = -1.0;
                    }
                }
                int N = 2;
                if (subpau.length < 2) {
                    N = subpau.length;
                }
                for (int i1 = 0; i1 < N; i1++) {
                    if (!subpau[i1].equals("")) {
                        String[] subequ = subpau[i1].split("=");
                        bpm[i1][0] = Double.parseDouble(subequ[0]);
                        bpm[i1][1] = Double.parseDouble(subequ[1]);
                    }
                }
            } else if (conf.startsWith("0") || conf.startsWith("1")) {
                slpAttribute<Integer, Boolean> slpAttribute = new slpAttribute<Integer, Boolean>();
                if (conf.equals("0000")) {
                    slpAttribute.BeatOrNo = false;
                } else {
                    slpAttribute.BeatOrNo = true;
                }
                //System.out.println(slpAttribute.BeatOrNo);
                slpAttribute.index = count;
                //System.out.println(slpAttribute.index);
                slpNoteList.add(slpAttribute);
                count++;
            }
        }
        int beatCount = 0;
        for (int i = 0; i < slpNoteList.size(); ++i) {
            beatAttribute<Integer, Double> beatAttribute = new beatAttribute<Integer, Double>();
            if (slpNoteList.get(i).BeatOrNo == true) {
                beatAttribute.index = beatCount;
                if (Double.parseDouble(String.valueOf(bpm[1][0])) == -1.0) {
                    beatAttribute.Time = offset + (slpNoteList.get(i).index-1) * 60 / bpm[0][1];
                    //System.out.println(beatAttribute.Time);
                } else {
                    //System.out.println("F2");
                    if ((slpNoteList.get(i).index-1) < bpm[1][0].intValue()) {
                        beatAttribute.Time = offset + (slpNoteList.get(i).index-1) * 60 / bpm[0][1];
                    } else {
                        beatAttribute.Time = bpm[1][0].intValue() * 60 / bpm[0][1]
                                + (((slpNoteList.get(i).index-1) - bpm[1][0].intValue()) * 60 / bpm[1][1]);
                    }
                }
                beatList.add(beatAttribute);
                beatCount++;
            }
        }
    }

    public String getTitle() {
        return titleString;
    }

    public String getArtist() {
        return artistString;
    }

    public String getMusic() {
        return musicString;
    }

    public long getTime() {
        time = time * 1000;
        //System.out.println(time.toString());
        String substr = time.toString();
        for (int i = 0; i < substr.length(); i++) {
            if (substr.charAt(i) == '.') {
                substr = substr.substring(0, i);
                break;
            }
        }
        return Long.parseLong(substr);
    }

    public long BeatTime(int beatIndex) {
        for (int i = 0; i < beatList.size(); ++i) {
            beatAttribute<Integer, Double> rightSlpAttribute = beatList.get(i);
            if (rightSlpAttribute.index.equals(beatIndex)) {
                Double temptime = rightSlpAttribute.Time * 1000;
                String substring = temptime.toString();
                //System.out.println(substring);
                for (int j = 0; j < substring.length(); j++) {
                    //System.out.println(substring.charAt(j) );
                    if (substring.charAt(j) == '.') {
                        //System.out.println("Flag!");
                        substring = substring.substring(0, j);
                        break;
                    }
                }
                return Long.parseLong(substring);
            }
        }
        return -1000;
    }

    public int beatSize() {
        return beatList.size();
    }
}
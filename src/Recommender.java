import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Jama.FloatMatrix;


public class Recommender {
	public static ArrayList<String> userIndexToName=new ArrayList<String>();
	public static ArrayList<String> videoIndexToName=new ArrayList<String>();
	public static ArrayList<String> channelIndexToName=new ArrayList<String>();
	
	public static HashMap<String, Integer> userNameToIndex = new HashMap<String, Integer>();
	public static HashMap<String, Integer> videoNameToIndex = new HashMap<String, Integer>();
	public static HashMap<String, Integer> channelNameToIndex = new HashMap<String, Integer>();
	
	public static void recommend(String modelfile,int userIndex, int recommendedNum) throws IOException, ClassNotFoundException{
		userIndexToName=Input.indexToName("user.csv");
		videoIndexToName=Input.indexToName("video.csv");
		channelIndexToName=Input.indexToName("subchannel.csv");
		
		userNameToIndex=Input.IdList_input("user.csv");
		videoNameToIndex=Input.IdList_input("video.csv");
		channelNameToIndex=Input.IdList_input("subchannel.csv");
		
		FileInputStream fis = new FileInputStream(modelfile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		float uiMat[][]=(float[][])ois.readObject();
		ois.close();
		
		ArrayList<Score> User=new ArrayList<Score>();
		for(int i=0;i<uiMat[0].length;i++)
			User.add(new Score(uiMat[userIndex][i],userIndex,i));
		
		ScoreComparator comp = new ScoreComparator();
		Collections.sort(User,comp);
		
		for(int i=0;i<recommendedNum;i++){		
			int user=User.get(i).userId;
			int item=User.get(i).videoId;
			System.out.println((i+1)+". User_ID: "+userIndexToName.get(user)+" Video_ID: "+videoIndexToName.get(item));
		}
					
	}

}

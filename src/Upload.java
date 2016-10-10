import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import Jama.FloatMatrix;

public class Upload {
	public static int no_of_users = 0;
	public static int no_of_videos = 0;
	public static int no_of_channels = 0;	//channels that users subscribe

	public static HashMap<String, Integer> userIdList = new HashMap<String, Integer>();
	public static HashMap<String, Integer> videoIdList = new HashMap<String, Integer>();
	public static HashMap<String, Integer> channelIdList = new HashMap<String, Integer>();
	
	public static float uiMat[][];
	public static float ucMat[][];
	public static float indicator80[][];	// indicator matrix that means training data
	public static float indicator20[][];	// indicator matrix that means test data

	public static void upload() throws IOException {
		userIdList = Input.IdList_input("user.csv");
		videoIdList = Input.IdList_input("video.csv");
		channelIdList = Input.IdList_input("subchannel.csv");

		no_of_users = userIdList.size();
		no_of_videos = videoIdList.size();
		no_of_channels = channelIdList.size();

		uiMat = new float[no_of_users][no_of_videos];	//user-item matrix
		ucMat = new float[no_of_users][no_of_channels];	//user-subscribed channel matrix
		indicator80 = new float[no_of_users][no_of_videos];
		indicator20 = new float[no_of_users][no_of_videos];

		Input.uiMatScoring("1LikeList.csv", "2Comment.csv", "3Upload.csv");
		Input.ucMatScoring("4subscription.csv");
		Input.makeIndicator();
		
		float predicted_uiMat[][]=CollectiveMF.Learning().getArray();	//predicted Scores
		fileCreate(predicted_uiMat,"result");
		
	} 
	static void fileCreate(float[][] Mat, String dataName) {
	      FileOutputStream fos = null;
	      ObjectOutputStream oos = null;
	      
	      try {
	         fos = new FileOutputStream(dataName + ".dat");
	         oos = new ObjectOutputStream(fos);
	         oos.writeObject(Mat);
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally { 
	         if (fos != null)
	            try {
	               fos.close();
	            } catch (IOException e) {
	            }
	         if (oos != null)
	            try {
	               oos.close();
	            } catch (IOException e) {
	            }
	      }
	   }
}

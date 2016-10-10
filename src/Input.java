import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Input {
	static HashMap<String, Integer> IdList_input(String filename) throws IOException {
		HashMap<String, Integer> Map = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(filename)); 
																			
		String line = new String();
		String token[];

		int i = 0;
		while ((line = br.readLine()) != null) {
			token = line.split(";");
			Map.put(token[0], i);
			i++;
		}

		br.close();
		return Map;
	}
	static ArrayList<String> indexToName(String filename) throws IOException{
		ArrayList<String> lst= new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(filename)); 
		String line = new String();
		String token[];
		
		while((line=br.readLine())!=null){
			token=line.split(";");
			lst.add(token[0]);
		}
		
		br.close();
		return lst;
	}
	
	
	static void uiMatScoring(String likeList,String comment,String upload) throws IOException{
		String line = new String();
		String token[];
		
		/*for likeList*/
		BufferedReader br = new BufferedReader(new FileReader(likeList)); 
		br.readLine();	// delete first line - first line indicates form of text: channelId;videoId
		while((line = br.readLine()) != null){
			token=line.split(";");
			int userId=Upload.userIdList.get(token[0]);
			int videoId=Upload.videoIdList.get(token[1]);
			Upload.uiMat[userId][videoId]+=1;
		}
		br.close();
		
		/*for comment*/
		br = new BufferedReader(new FileReader(comment));
		br.readLine(); //delete first line - first line indicates form of text: commenter;videoId
		while((line = br.readLine()) != null){
			token=line.split(";");
			int userId=Upload.userIdList.get(token[0]);
			int videoId=Upload.videoIdList.get(token[1]);
			Upload.uiMat[userId][videoId]+=1;
		}
		br.close();
		
		/*for upload*/
		br = new BufferedReader(new FileReader(upload));
		br.readLine(); //delete first line - first line indicates form of text: uploader;videoId
		while((line = br.readLine()) != null){
			token=line.split(";");
			int userId=Upload.userIdList.get(token[1]);
			int videoId=Upload.videoIdList.get(token[0]);
			Upload.uiMat[userId][videoId]+=1;
		}
		br.close();
	
	}
	
	static void ucMatScoring(String subscription) throws IOException{

		String line = new String();
		String token[];
		
		/*for subscription*/
		BufferedReader br = new BufferedReader(new FileReader(subscription)); 
		br.readLine();	// delete first line - first line indicates form of text: userId;subscription
		while((line = br.readLine()) != null){
			token=line.split(";");
			int userId=Upload.userIdList.get(token[0]);
			int channelId=Upload.channelIdList.get(token[1]);
			Upload.uiMat[userId][channelId]+=1;
		}
		br.close();
	}
	
	static void makeIndicator(){
		int random;
		ArrayList<Score> ratedList;
		
		for(int i=0;i<Upload.no_of_users;i++){
			ratedList = new ArrayList<Score>();
			for(int j=0;j<Upload.no_of_videos;j++){
				if(Upload.uiMat[i][j]!=0){
					ratedList.add(new Score(Upload.uiMat[i][j],i,j));
					Upload.indicator80[i][j]=1;
				}
			}
			
			int tmp=(int)Math.ceil((double)ratedList.size()*0.2);
			for(int j=0;j<tmp;j++){
				random = (int)(Math.random()*tmp);
				Upload.indicator20[ratedList.get(random).userId][ratedList.get(random).videoId]=1;
				Upload.indicator80[ratedList.get(random).userId][ratedList.get(random).videoId]=0;
			}
		}
		
	}
}

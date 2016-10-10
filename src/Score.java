public class Score {
	float rate = -100;	//score
	int userId;	//index of user
	int videoId;	//index of video
	
	public Score(){}
	public Score(float rate,int userId,int videoId){
		this.rate=rate;
		this.userId=userId;
		this.videoId=videoId;
	}
	public Score(int userId,int videoId){
		this.userId=userId;
		this.videoId=videoId;
	}
}
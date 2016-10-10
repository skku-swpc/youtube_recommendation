
1. Create Model

	In Upload.upload() function, you can input your data. Data should be contained in file that stored the code.

	you make files that LikeList.csv, Comment.csv, Upload.csv, Subscription.csv.
	In each file, data format is like this.
	1) LikeList.csv
	Userid, VideoId

	2) Comment.csv
	Commenter, VideoId

	3) Upload.csv
	uploader, VideoId

	4) Subscription.csv
	Channelid, Subscription

2. Recommend

	In Recommend.recommend () function, you can input 3 value about (modelfile, userIndex,recommendedNum)

	In folder, you can give 3 csv files that are made by Create Model function.
	User.csv
	Video.csv
	Subchannel.csv

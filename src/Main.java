import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import Jama.FloatMatrix;


public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {		
		//Upload.upload();
		Recommender.recommend("result.dat", 1, 3);	//Recommender.recommend(model data, user_index, no_of_recommended video)
	}

}

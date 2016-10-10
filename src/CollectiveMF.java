import Jama.FloatMatrix;



public class CollectiveMF {
	public static float ETA=0.5f;
	public static int T = 200;
	public static float a = 0.1f;
	public static float b = 0.1f;
	public static int latent = 3;
	
	public static FloatMatrix I;
	public static FloatMatrix UIMAT;
	public static FloatMatrix UCMAT;
	
	static float normF_square(FloatMatrix Mat, int row, int col) {
		float ans = 0;
		float temp = 0;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				temp = Mat.get(i, j);
				ans += temp * temp;
			}
		return ans;
	}
	
	static float errorFunction(FloatMatrix X, FloatMatrix Y, FloatMatrix Z) {
		float term1 = normF_square(I.arrayTimes(UIMAT.minus(X.times(Y.transpose()))), Upload.no_of_users, Upload.no_of_videos) / 2;
		float term2 = a / 2 * normF_square(UCMAT.minus(X.times(Z.transpose())), Upload.no_of_users, Upload.no_of_channels);
		float term3 = b / 2 * (normF_square(X, Upload.no_of_users, latent) + normF_square(Y, Upload.no_of_videos, latent) + normF_square(Z, Upload.no_of_channels, latent));
		return term1 + term2 + term3;
	}
	
	static FloatMatrix deltaERX(FloatMatrix X, FloatMatrix Y, FloatMatrix Z) {
		FloatMatrix delta = (I.arrayTimes(X.times(Y.transpose()).minus(UIMAT))).times(Y);
		delta = delta.plus(((X.times(Z.transpose()).minus(UCMAT)).times(Z).times(a)));
		delta = delta.plus(X.times(b));
		return delta;
	}

	static FloatMatrix deltaERY(FloatMatrix X, FloatMatrix Y) {
		FloatMatrix delta = (I.arrayTimes(X.times(Y.transpose()).minus(UIMAT))).transpose().times(X);
		delta = delta.plus(Y.times(b));
		return delta;
	}

	static FloatMatrix deltaERZ(FloatMatrix X, FloatMatrix Z) {
		FloatMatrix delta = (((X.times(Z.transpose()).minus(UCMAT))).transpose().times(X).times(a));
		delta = delta.plus(Z.times(b));
		return delta;
	}
	static FloatMatrix initialize(int row, int col) {

		float[][] arr = new float[row][col];

		// range of (0,1)
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				arr[i][j] = (float) Math.random();
				while (arr[i][j] == 0)
					arr[i][j] = (float) Math.random();
			}
		}

		return new FloatMatrix(arr);
	}
	
	static FloatMatrix Learning(){
		// initialization
				FloatMatrix X = initialize(Upload.no_of_users, latent);
				FloatMatrix Y = initialize(Upload.no_of_videos, latent);
				FloatMatrix Z = initialize(Upload.no_of_channels, latent);

				//Gradient Descent Method
				int t = 0;
				float ER_t = Float.MAX_VALUE;
				float ER_t1 = errorFunction(X, Y, Z);

				FloatMatrix deltaX;
				FloatMatrix deltaY;
				FloatMatrix deltaZ;
				System.out.println("start");
				while (t < 200 && ER_t - ER_t1 > ETA) {
					System.out.println(t);
					deltaX = deltaERX(X, Y, Z);
					deltaY = deltaERY(X, Y);
					deltaZ = deltaERZ(X, Z);
					float gamma = 1;

					while (errorFunction(X.minus(deltaX.times(gamma)), Y.minus(deltaY.times(gamma)), Z.minus(deltaZ.times(gamma))) > errorFunction(X, Y, Z)) {
						gamma /= 2;
						System.out.println("second-while");
					}
					ER_t = errorFunction(X, Y, Z);
					X = X.minus(deltaX.times(gamma));
					Y = Y.minus(deltaY.times(gamma));
					Z = Z.minus(deltaZ.times(gamma));
					t = t + 1;
					ER_t1 = errorFunction(X, Y, Z);
					System.out.println("ERt-ERt1:"+(ER_t-ER_t1));
				}

				return X.times(Y.transpose());
	}


}

//package lab2;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Random;
//
//import java.io.File;
//import java.lang.reflect.Method;
//
//import jxl.Workbook;
//import jxl.write.Label;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//
//public class RunningTimeSurvey {
//	// task name function name run times upper
//	static String[][] taskList = { { "LinearTimeTest", "linearTime", "10000000" },
//			{ "LinearTimeTest", "linearTimeCollections", "10000000" },
//			 { "NlognTimeTest", "NlognTime", "1000000"},
//			 { "QuadraticTimeTest", "QuadraticTime", "100000"},
//			 { "CubicTimeTest", "CubicTime", "1000"},
//			 { "ExponentialTimeTest", "ExponentialTime", "10"},
//			 { "FactorialTimeTest", "FactorialTime", "10" }
//	};
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String osName = System.getProperty("os.name");
//		System.out.println(osName);
//		try {
//			File xlsFile = new File("RunningTimeSurvey.xls");
//			// Create a workbook
//			WritableWorkbook workbook;
//			workbook = Workbook.createWorkbook(xlsFile);
//
//			// Create a worksheet
//			WritableSheet sheet = workbook.createSheet("RunningTime", 0);
//			// the first row
//			for (int j = 1, n = 1; j <= 8; j++) {
//				n = 10 * n;
//				sheet.addCell(new Label(j + 1, 0, "n = " + n));
//			}
//			for (int i = 0; i < taskList.length; i++) {
//				String[] taskInfo = taskList[i];
//
//				Class<?> me = Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());
//				Method method = me.getMethod(taskInfo[1], int.class);
//				int upper = Integer.parseInt(taskInfo[2]);
//				sheet.addCell(new Label(0, i + 1, taskInfo[0]));
//				sheet.addCell(new Label(1, i + 1, taskInfo[1]));
//
//				for (int j = 1, n = 1; Math.pow(10, j) <= upper; j++) {
//					n = 10 * n;
//					Long time = (Long) method.invoke(null, n);
//					// 向工作表中添加数据
//					sheet.addCell(new Label(j + 1, i + 1, time.toString()));
//				}
//
//			}
//			workbook.write();
//			workbook.close();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public static long linearTimeCollections(int n) {
//		ArrayList<Long> arrayList = new ArrayList<Long>(n);
//		generateArrayList(n, arrayList);
//		long timeStart = System.currentTimeMillis();
//		getMax(n, arrayList);
//		long timeEnd = System.currentTimeMillis();
//		long timeCost = timeEnd - timeStart;
//		return timeCost;
//	}
//
//	public static long linearTime(int n) {
//		long[] list = new long[n];
//		generateList(n, list);
//		long timeStart = System.currentTimeMillis();
//		getMax(n, list);
//		long timeEnd = System.currentTimeMillis();
//		long timeCost = timeEnd - timeStart;
//		return timeCost;
//	}
//
//	public static long getMax(long n, long[] list) {
//		long max = list[0];
//		for (int i = 1; i < n; i++) {
//			if (list[i] > max) {
//				max = list[i];
//			}
//		}
//		return max;
//	}
//
//	public static void generateList(int n, long[] list) {
//		for (int i = 0; i < n; i++) {
//			list[i] = i;
//		}
//		// shuffle
//		Random rnd = new Random();
//		for (int i = list.length; i > 1; i--) {
//			int j = rnd.nextInt(i);
//			long temp = list[j];
//			list[j] = list[i - 1];
//			list[i - 1] = temp;
//		}
//		Collections.shuffle(Collections.singletonList(list));
//	}
//
//	public static void generateArrayList(int n, ArrayList<Long> arrayList) {
//		for (long i = 0; i < n; i++) {
//			arrayList.add(i);
//		}
//		// shuffle
//		Collections.shuffle(arrayList);
//	}
//
//	public static long getMax(long n, ArrayList<Long> arrayList) {
//		long max = arrayList.get(0);
//		for (int i = 1; i < n; i++) {
//			if (arrayList.get(i) > max) {
//				max = arrayList.get(i);
//			}
//		}
//		return max;
//	}
//
//	public static long NlognTime(int n) {
//		//TODO:generate you test input data here
//		long[] list = new long[n];
//		generateList(n, list);
//		long timeStart = System.currentTimeMillis();
//		//TODO: write a algorithm
//		mergeSort(list,n);
//		long timeEnd = System.currentTimeMillis();
//		long timeCost = timeEnd - timeStart;
//		return timeCost;
//	}
//
//	private static void mergeSort(long[] data, long n){
//
//		if (n > 1){
//			long[] A,B;
//			A = Arrays.copyOfRange(data,0, (int) (n / 2));
//			B = Arrays.copyOfRange(data,(int)n / 2,(int)n);
//			mergeSort(A,n / 2);
//			mergeSort(B,n - n / 2);
//			long[] nums = merge(A,(int)n / 2,B, (int) ((int)n - n / 2));
//			int k = 0;
//			for (long num : nums) {
//				data[k] = num;
//				k += 1;
//			}
//
//		}
//
//	}
//
//	private static long[] merge(long[] A,int A_size, long[] B, int B_size){
//		long[] num = new long[A_size + B_size];
//		int i = 0,j = 0;
//		for (int k = 0;k < num.length;k++){
//			if (i < A.length && j < B.length && A[i] <= B[j]){
//				num[k] = A[i];
//				i += 1;
//			}
//			else if (i < A.length && j < B.length && A[i] > B[j]){
//				num[k] = B[j];
//				j += 1;
//			}
//			else if (i < A.length || j < B.length){
//				if (i >= A.length){
//					num[k] = B[j];
//					j += 1;
//				}
//				else if (j >= B.length){
//					num[k] = A[i];
//					i += 1;
//				}
//
//			}
//		}
//		return num;
//	}
//
//
//	public static void generateDotList(int n, long[][] list) {
//		for (int i = 0; i < n; i++) {
//			for (int j = 0;j < 2;j++){
//				list[i][j] = i;
//			}
//		}
//		// shuffle
//		Random rnd = new Random();
//		for (int i = list.length; i > 1; i--) {
//			for (int k = 0;k < 2;k++){
//				int j = rnd.nextInt(i);
//				long temp = list[j][k];
//				list[j][k] = list[i - 1][k];
//				list[i - 1][k] = temp;
//			}
//		}
//	}
//
//	public static long QuadraticTime(int n){
//		//TODO:generate you test input data here
//		long[][] list = new long[n][2];
//		generateDotList(n,list);
//		long timeStart = System.currentTimeMillis();
//		//TODO: write a algorithm
//		getMin(n,list);
//		long timeEnd = System.currentTimeMillis();
//		long timeCost = timeEnd - timeStart;
//		return timeCost;
//	}
//
//	public static long getMin(long n, long[][] list){
//		long min = (list[0][0] - list[1][0]) * (list[0][0] - list[1][0])
//				 + (list[0][1] - list[1][1]) * (list[0][1] - list[1][1]);
//		for (int i = 1;i < n;i++){
//			for (int j = i + 1;j < n;j++){
//				long d = (list[i][0] - list[j][0]) * (list[i][0] - list[j][0])
//						+ (list[i][1] - list[j][1]) * (list[i][1] - list[j][1]);
//				if (d < min)
//					min = d;
//			}
//		}
//		return min;
//	}
//
//	public static long CubicTime(int n){
//		//TODO:generate you test input data here
//		long timeStart = System.currentTimeMillis();
//		//TODO: write a algorithm
//		n3Add(n);
//		long timeEnd = System.currentTimeMillis();
//		long timeCost = timeEnd - timeStart;
//		return timeCost;
//	}
//
//	public static long n3Add(int n){
//		long sum = 0;
//		for (int i = 0;i < n;i++){
//			for (int j = 0;j < n;j++){
//				for (int k = 0;k < n;k++){
//					sum += 2;
//				}
//			}
//		}
//		return sum;
//	}
//
//	public static long ExponentialTime(int n){
//		//TODO:generate you test input data here
//		long max = (long) Math.pow(2,n);
//		long sum = 0;
//		long timeStart = System.currentTimeMillis();
//		//TODO: write a algorithm
//		for (int i = 0;i < max;i++){
//			sum += 1;
//		}
//		long timeEnd = System.currentTimeMillis();
//		long timeCost = timeEnd - timeStart;
//		return timeCost;
//	}
//
////	public static long BruceForceFactorial(int n) {
//	public static long FactorialTime(int n) {
//		// to generate you test input data
//		long timeStart = System.currentTimeMillis();
//		// to write a algorithm
//		Factorial(n);
//		long timeEnd = System.currentTimeMillis();
//		long timeCost = timeEnd - timeStart;
//		return timeCost;
//	}
//
//	public static long Factorial(int n) {
//		if (n == 1)
//			return 1;
//		else {
//			long sum = 0;
//			for (int i = 0; i < n; i++) {
//				sum += Factorial(n - 1);
//			}
//			return sum;
//		}
//
//	}
//
//}

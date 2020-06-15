//package lab11;
//
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Random;
//
//import static org.testng.Assert.assertEquals;
//
//public class P2_FaFaTaTest {
//	private static final InputStream stdIn = System.in;
//	private static final PrintStream stdOut = System.out;
//
//	private static ByteArrayInputStream testIn;
//	private static ByteArrayOutputStream testOut;
//
//	private static final boolean check = true;
//	//	private static final boolean check = false;
////	private static final boolean showData = true;
//		private static final boolean showData = false;
//
//	private static final String dataName = "Data2";
//
//	private static String testName;
//	{
//	    testName = this.getClass().getName();
//	    testName = testName.substring(0, testName.length()-4);
//	}
//
//	@DataProvider(name = "Data1")
//	public Object[][] DataProviders() {
//	    Object[][] obj = new Object[][] {
//	            {
//	                    "4 11\n" +
//			                    "4 5 1 4\n",
//	                    "0\n" +
//			                    "1\n" +
//			                    "0\n" +
//			                    "1\n" +
//			                    "4\n" +
//			                    "6\n" +
//			                    "0\n" +
//			                    "0\n" +
//			                    "0\n" +
//			                    "4\n" +
//			                    "0\n"
//	            },
//	    };
//	    return obj;
//	}
//
//	@DataProvider(name = "Data2")
//	public Object[][] DataProviders2() {
//		Object[][] obj = new Object[1][2];
//		StringBuilder sb = new StringBuilder();
//		Random rand = new Random();
//
//		int n = 100;
//		int aMax = 10_0000_0000;
//		int p = 49999;
//
//		int[] arrA = new int[n];
//
//		sb.append(n).append(" ").append(p).append("\n");
//		for (int i = 0; i < n; i++) {
//			arrA[i] = rand.nextInt(aMax + 1);
//			sb.append(arrA[i]).append(" ");
//		}
//
//		StringBuilder sb2 = new StringBuilder();
//		if (check) {
//			int[] cnt = new int[p];
//			for (int i = 0; i < n; i++) {
//				for (int j = 0; j < n; j++) {
//					int s = (int) (((long) arrA[i] * arrA[j]) % p);
//					++ cnt[s];
//				}
//			}
//
//			for (int i = 0; i < p; i++) {
//				sb2.append(cnt[i]).append("\n");
//			}
//		}
//
//		obj[0][0] = sb.toString();
//		obj[0][1] = sb2.toString();
//		return obj;
//	}
//
//	@Test(dataProvider = dataName)
//	public void testMain(String data, String res) {
//		if (showData) {
//			System.out.println(">>> data\n" + data);
//			System.out.println(">>> res\n" + res);
//		}
//
//		testOut = new ByteArrayOutputStream();
//		System.setOut(new PrintStream(testOut));
//
//		testIn = new ByteArrayInputStream(data.getBytes());
//		System.setIn(testIn);
//
//		long time = -1;
//
//		try {
//			Class cls = Class.forName(testName);
//			Method m = cls.getMethod("main", String[].class);
//			time = System.currentTimeMillis();
//			m.invoke(null, (Object) new String[0]);
//			time = System.currentTimeMillis() - time;
//			if (check) {
//				assertEquals(
//						testOut.toString().replaceAll("\r", ""),
//						res);
//			}
//		} catch (ClassNotFoundException
//				| NoSuchMethodException
//				| InvocationTargetException
//				| IllegalAccessException e) {
//			e.printStackTrace();
//		} finally {
//			System.setIn(stdIn);
//			System.setOut(stdOut);
//		}
//
//		System.out.println(">>> finish in " + time + " ms");
//	}
//}
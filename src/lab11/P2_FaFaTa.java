package lab11;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class P2_FaFaTa {
	private static int n, p, pr;
	private static int[] arrA, arrB, arrC, arrS, expToRem, remToExp;

	public static void main(String[] args) throws Exception {
		Reader in = new Reader();
		PrintWriter out = new PrintWriter(System.out);

	    n = in.nextInt();
	    p = in.nextInt();

		arrA = new int[n];
		for (int i = 0; i < n; i++) {
			arrA[i] = in.nextInt();
		}
		
		pr = getPrimitiveRoot();

		System.out.println(pr);
		expToRem = new int[p];
		remToExp = new int[p];
		generateRef();

		arrB = new int[p];
		for (int i = 0; i < n; i++) {
			int exp = remToExp[arrA[i] % p];
			arrB[exp] += 1;
		}

		arrC = polyMulti();

		arrS = new int [p];
		for (int i = 0, e = 2; e <= 2 * p - 2; i++, e++) {
			int tmp = e % (p - 1);
			if (tmp == 0) tmp = p - 1;
			int r = expToRem[tmp];
			arrS[r] += arrC[i];
		}

		arrS[0] = 2 * n * arrB[0] - arrB[0] * arrB[0];

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < p; i++) {
			sb.append(arrS[i]).append("\n");
		}
		out.print(sb.toString());

		out.close();
	}
	
	private static int getPrimitiveRoot() throws Exception {
		next:
		for (int i = 2; i < p; i++) {
			int power = i;
			for (int j = 2; j < p - 1; j++) {
				power = (power * i) % p;
				if (power == 1) continue next;
			}
			power = (power * i) % p;
			if (power == 1) return i;
		}
		throw new Exception("Prime number error");
	}

	private static void generateRef() {
		int power = pr;
		for (int i = 1; i < p; i++, power = (power * pr) % p) {
			expToRem[i] = power;
			remToExp[power] = i;
		}
	}

	private static int[] polyMulti() {
		int fixLen = 1;
		while (fixLen < p - 1) fixLen *= 2;
		fixLen *= 2;
		int[] arr = new int[fixLen];
		System.arraycopy(arrB, 1, arr, 0, p - 1);

		Complex[] ps = fft(arr);

		// multi
		for (int i = 0; i < fixLen; i++) {
			ps[i] = ps[i].multi(ps[i]);
		}

		ps = ifft(ps, true);

		for (int i = 0; i < fixLen; i++) {
			arr[i] = (int) Math.round(ps[i].a);
		}

		return arr;
	}

	private static Complex[] fft(int[] arr) {
		int n = arr.length;
		if (n == 1) return new Complex[]{new Complex(arr[0], 0)};

		int[] a1 = new int[n / 2];
		int[] a2 = new int[n / 2];
		for (int i = 0; i < n / 2; i++) {
			a1[i] = arr[2 * i];
			a2[i] = arr[2 * i + 1];
		}

		Complex[] es = fft(a1);
		Complex[] ds = fft(a2);
		Complex[] ret = new Complex[n];

		for (int i = 0; i < n / 2; i++) {
			double tmp = -2.0 * Math.PI * i / n;
			Complex w = new Complex(Math.cos(tmp), Math.sin(tmp));
			w = w.multi(ds[i]);
			ret[i] = es[i].add(w);
			ret[i + n / 2] = es[i].sub(w);
		}

		return ret;
	}

	private static Complex[] ifft(Complex[] arr, boolean isTop) {
		int n = arr.length;
		if (n == 1) return new Complex[]{arr[0]};

		Complex[] a1 = new Complex[n / 2];
		Complex[] a2 = new Complex[n / 2];
		for (int i = 0; i < n / 2; i++) {
			a1[i] = arr[2 * i];
			a2[i] = arr[2 * i + 1];
		}

		Complex[] es = ifft(a1, false);
		Complex[] ds = ifft(a2, false);
		Complex[] ret = new Complex[n];

		for (int i = 0; i < n / 2; i++) {
			double tmp = 2.0 * Math.PI * i / n;
			Complex w = new Complex(Math.cos(tmp), Math.sin(tmp));
			w = w.multi(ds[i]);
			ret[i] = es[i].add(w);
			ret[i + n / 2] = es[i].sub(w);
		}

		if (isTop) {
			for (int i = 0; i < n; i++) {
				ret[i].a /= n;
			}
		}

		return ret;
	}

	private static class Complex {
		double a, b;

		public Complex(double a, double b) {
			this.a = a;
			this.b = b;
		}

		public Complex add(Complex o) {
			return new Complex(a + o.a, b + o.b);
		}

		public Complex sub(Complex o) {
			return new Complex(a - o.a, b - o.b);
		}

		public Complex multi(Complex o) {
			return new Complex(a * o.a - b * o.b, a * o.b + b * o.a);
		}

		@Override
		public String toString() {
			return String.format("(%.2f,%.2f)", a, b);
		}
	}

	private static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;

		public Reader()
		{
			din = new DataInputStream(System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public Reader(String file_name) throws IOException
		{
			din = new DataInputStream(new FileInputStream(file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public String readLine() throws IOException
		{
			byte[] buf = new byte[64]; // line length
			int cnt = 0, c;
			while ((c = read()) != -1)
			{
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, cnt);
		}

		public int nextInt() throws IOException
		{
			int ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do
			{
				ret = ret * 10 + c - '0';
			}  while ((c = read()) >= '0' && c <= '9');

			if (neg)
				return -ret;
			return ret;
		}

		public long nextLong() throws IOException
		{
			long ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			}
			while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}

		public double nextDouble() throws IOException
		{
			double ret = 0, div = 1;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();

			do {
				ret = ret * 10 + c - '0';
			}
			while ((c = read()) >= '0' && c <= '9');

			if (c == '.')
			{
				while ((c = read()) >= '0' && c <= '9')
				{
					ret += (c - '0') / (div *= 10);
				}
			}

			if (neg)
				return -ret;
			return ret;
		}

		private void fillBuffer() throws IOException
		{
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException
		{
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException
		{
			if (din == null)
				return;
			din.close();
		}
	}
}

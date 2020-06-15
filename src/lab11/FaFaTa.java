package lab11;
//快读模板1：更快，但没有next()用于读字符串

import java.io.*;

public class FaFaTa {

    static long[] primeFactor;
    static int[] e_r,r_e;
    static Complex[] B,C;
    static Complex w = new Complex(0,0);
    static double pi = Math.PI;
    static int[] outs;

    public static void main(String[] args) throws IOException {
        Reader sc=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        int n = sc.nextInt();
        int p = sc.nextInt();
        long[] A = new long[n];
        for (int i = 0;i < n;i++){
            A[i] = sc.nextLong();
        }
        gets(p - 1);
        long primitiveRoot = is_P(p);
        e_r = new int[p];
        r_e = new int[p];
        for (int i = 1;i < p;i++){
            int re = (int) (modPow(primitiveRoot,i,p));
            e_r[i] = re;
            r_e[re] = i;
        }
        int  len = 1;
        while (len <= p)
            len*=2;
        len*=2;
        B = new Complex[p];
        C = new Complex[len];
        for (int i = 0;i < len;i++){
            if (i < p)
                B[i] = new Complex(0,0);
            C[i] = new Complex(0,0);

        }
        for (int i = 0;i < n;i++){
            int t = r_e[(int)A[i]%p];
            B[t].real += 1;
        }
        int index = 0;
        for (int i = 1;i < B.length;i++){
            C[index].real = B[i].real;
            C[index].imaginary = B[i].imaginary;
            index++;
        }
//        for (Complex i : C){
//            System.out.println(i.real + " " + i.imaginary);
//        }
//        System.out.println();
        C = PM(C);

        outs = new int[p];

        int iin = 0;
        for (int e = 2;e <= 2*p-2;e++){
            int t = e % (p - 1);
            if (t == 0)
                t = p - 1;
            int r = e_r[t];
            outs[r] += (int)(Math.round(C[iin].real));
            iin++;
        }

        outs[0] = (int)(2 * n * B[0].real - B[0].real * B[0].real);


        StringBuilder sb = new StringBuilder();
        for (int i: outs) {
            sb.append(i).append("\r\n");
        }
        out.print(sb.toString());

        out.close();
    }

    public static Complex[] PM(Complex[] B){
        B = FFT(B);
        dot(B);
        B = iFFT(B,true);
        return B;
    }

    static void dot(Complex[] Bx){

        for (int i = 0; i < Bx.length; i++){
            Bx[i] = mul(Bx[i], Bx[i]);
        }
    }

    static Complex[] iFFT(Complex[] B,boolean top){
        int n = B.length;
        if (n==1)
            return new Complex[]{B[0]};
        Complex[] odd = new Complex[n/2];
        Complex[] even = new Complex[n/2];
        Complex[] re = new Complex[n];
        int a = 0,b = 0;
        for (int i = 0;i < n;i++){
            if (i % 2 == 0) {
                even[a] = B[i];
                a++;
            }
            else {
                odd[b] = B[i];
                b++;
            }
        }

        Complex[] e = iFFT(even,false);
        Complex[] d = iFFT(odd,false);

        for (int k = 0;k < n/2;k++){
            double v = (2*pi*k)/n;
            w.real = Math.cos(v);
            w.imaginary = Math.sin(v);

            re[k] = add(e[k],mul(w,d[k]));
            re[k+n/2] = minus(e[k],mul(w,d[k]));
        }
        if (top){
            for (int i = 0;i < n;i++){
                re[i].real /= n;
            }
        }

        return re;

    }

    static Complex[] FFT(Complex[] B){
        int n = B.length;
        if (n==1)
            return new Complex[]{B[0]};
        Complex[] odd = new Complex[n/2];
        Complex[] even = new Complex[n/2];
        Complex[] re = new Complex[n];
        int a = 0,b = 0;
        for (int i = 0;i < n;i++){
            if (i % 2 == 0) {
                even[a] = B[i];
                a++;
            }
            else {
                odd[b] = B[i];
                b++;
            }
        }

        Complex[] e = FFT(even);
        Complex[] d = FFT(odd);

        for (int k = 0;k < n/2;k++){
            double v = -1*(2.0*pi*k)/n;
            w.real = Math.cos(v);
            w.imaginary = Math.sin(v);

            re[k] = add(e[k],mul(w,d[k]));
            re[k+n/2] = minus(e[k],mul(w,d[k]));
        }

        return re;

    }


    public static class Complex {
        double real;
        double imaginary;

        Complex(double real,double imaginary){
            this.imaginary = imaginary;
            this.real = real;
        }

    }
    public static Complex add(Complex a,Complex b){
        return new Complex(a.real+b.real,a.imaginary+b.imaginary);
    }

    public static Complex minus(Complex a,Complex b){
        return new Complex(a.real-b.real,a.imaginary-b.imaginary);
    }

    public static Complex mul(Complex a,Complex b){
        return new Complex((a.real*b.real - a.imaginary*b.imaginary),
                a.imaginary*b.real + a.real*b.imaginary);
    }

    public static Complex div(Complex a,int b){
        return new Complex(a.real/b,a.imaginary/b);
    }


    public static void gets(long n){
        int flag = 0;
        long[] str = new long[50];
        for (long i = 2; i <= n; i++) {
            if (n % i == 0) {
                str[flag] = i;
                flag++;
                n = n / i;
                i--;
            }
        }
        primeFactor = new long[flag];

        for (int i = 0;i < flag;i++){
            primeFactor[i] = str[i];
        }
    }

    static long is_P(long P){

        boolean ta;
        for (long i = 2;i < P;i++){
            ta = true;
            for (long l : primeFactor) {
                if (pow(i, (P - 1) / l) % P == 1) {
                    ta = false;
                    break;
                }
            }
            if (ta)
                return i;
        }
        return 1;
    }


    static long modPow(long a,long n,long mod){
        long re = 1;
        for (int i = 0;i < n;i++){
            re*=a;
            re%=mod;
        }
        return re%mod;
    }


    static long pow(long a,long n){
        long re = 1;
        for (int i = 0;i < n;i++){
            re*=a;
        }
        return re;
    }

    static class Reader
    {
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

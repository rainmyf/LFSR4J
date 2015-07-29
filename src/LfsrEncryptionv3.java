import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/***
 * LFSR加解密，包含JK触发器及GEFFE触发器
 * @author 毛宇锋
 * @version 1.0
 * */

public class LfsrEncryptionv3 {
	static int []buf1=new int[200];
	static int []buf2=new int[200];
	static int []buf3=new int[200];
	static int []a=new int[200];
	static int []b=new int[200];
	static Scanner sc=new Scanner(System.in);
	static void initArray()
	{
		buf1[0]=1;buf1[1]=0;buf1[2]=1;buf1[3]=0;buf1[4]=0;//初始化buf1数组为10100
		buf1[0]=0;buf1[1]=0;buf1[2]=1;buf1[3]=0;buf1[4]=1;//初始化buf2数组为00101
		buf1[0]=0;buf1[1]=1;buf1[2]=0;buf1[3]=1;buf1[4]=0;//初始化buf3数组为01010
		initKey(buf1);
		initKey(buf2);
		initKey(buf3);
	}
	static void initKey(int []key) //密钥生成器
	{
		for(int k=5;k<key.length;++k)
			key[k]=(key[k-2]+key[k-5])%2;
	}
	static String fileReadUtil() //从系统中读文件操作
	{
		String str = null;
		try {
			System.out.println("请输入文件名(绝对路径请用\\)：");
			String fileName=sc.next();
			FileReader fr=new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
			str=br.readLine();
			String strr=br.readLine();
			while(strr!=null)
			{
				str+=strr;
				strr=br.readLine();
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	static void geffe()//生成geffee随机序列
	{
		for (int i = 0; i < buf1.length; i++) {
			if(buf2[i]==1)
				a[i]=buf1[i];
			else
				a[i]=buf3[i];
		}
	}
	static void jkTrigger()
	{
		for(int j=1;j<buf2.length;j++)
		{
			if(b[j-1]==0)
				b[j]=buf1[j];
			else
				if(~buf2[j]>0)
					b[j]=1;
				else 
					b[j]=0;
		}
	}
	static void lfsrEncryption() //加密过程
	{
		int i=0;
		boolean flag=false;
		int choose=-1;
		while(!flag)
		{
			System.out.println("请选择从文件中读取明文还是手动输入明文(1/0)");
			choose=sc.nextInt();
			if(choose==1||choose==0)
				flag=true;
			else
				System.out.println("输入错误，请重新输入：");
		}
		String s=new String();
		String code=new String();
		if(choose==1)
		{
			s=fileReadUtil();
		}
		else
		{
		System.out.println("请输入明文C：");
		s=sc.next();
		}
		for (int counter = 0; counter < s.length(); counter++) {
			char c=s.charAt(counter);
			  int sum=0;
			     for(int j=0;j<8;++j)
			     {
			    	 sum+=Math.pow(2,7-j)*b[(i+j)%200];
			     if(i+j>199)
				    i=(i+j-1)%198+1;
			     else
				    i=i+8;
			     }
			     code+=(char)((int)c^sum);
		}
		System.out.println("利用Gette生成的随机序列加密后的密文e为：");
		System.out.println(code);
		code=new String();
		for (int counter = 0; counter < s.length(); counter++) {
			char c=s.charAt(counter);
			  int sum=0;
			     for(int j=0;j<8;++j)
			     {
			    	 sum+=Math.pow(2,7-j)*b[(i+j)%200];
			     if(i+j>200)
				    i=(i+j-1)%199+1;
			     else
				    i=i+8;
			     }
			     code+=(char)((int)c^sum);
		}
		System.out.println("利用JK触发器生成的随机序列加密后的密文e为：");
		System.out.println(code);
		}
	static void lfsrDecryption()//解密过程
	{
		int i=0;
		boolean flag=false;
		int choose=-1;
		while(!flag)
		{
			System.out.println("请选择从文件中读取密文还是手动输入密文(1/0)");
			choose=sc.nextInt();
			if(choose==1||choose==0)
				flag=true;
			else
				System.out.println("输入错误，请重新输入：");
		}
		String s=new String();
		String code=new String();
		if(choose==1)
		{
			code=fileReadUtil();
		}
		else
		{
		System.out.println("请输入密文e：");
		code=sc.next();
		}
		flag=false;
		while(!flag)
		{
			System.out.println("请选择用Gette密钥流解密还是用JK密钥流解密(1/0):");
			choose=sc.nextInt();
			if(choose==1||choose==0)
				flag=true;
			else
				System.out.println("输入错误，请重新输入：");
		}
		if(choose==1)
		{
			for (int counter = 0; counter<code.length(); counter++) {
				int sum=0;
				char c=code.charAt(counter);
				for(int j=0;j<8;++j)
					{
						sum+=Math.pow(2,7-j)*a[(i+j)%200];
						if(i+j>200)
							i=(i+j-1)%199+1;
						else
							i=i+8;
					}
					s+=(char)((int)c^sum);
			}
		}
		else if(choose==0)
		{
			for (int counter = 0; counter<code.length(); counter++) {
				int sum=0;
				char c=code.charAt(counter);
				for(int j=0;j<8;++j)
					{
						sum+=Math.pow(2,7-j)*b[(i+j)];
						if(i+j>200)
							i=(i+j-1)%199+1;
						else
							i=i+8;
					}
					s+=(char)((int)c^sum);
			}
		}
		System.out.println("解密后的明文C为：");
		System.out.println(s);
	}
	public static void main(String[] args) {
		int choose = -1;
		boolean flag=false;
		initArray();
		geffe();
		jkTrigger();
		while(!flag)
		{
			System.out.println("请选择加密/解密(1/0)");
			choose=sc.nextInt();
			if(choose==1||choose==0)
				flag=true;
			else
				System.out.println("输入错误，请重新输入：");
		}
		if(choose==1)
		lfsrEncryption();
		else
		lfsrDecryption();
		main(null);
	}
}

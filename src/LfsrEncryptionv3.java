import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/***
 * LFSR�ӽ��ܣ�����JK��������GEFFE������
 * @author ë���
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
		buf1[0]=1;buf1[1]=0;buf1[2]=1;buf1[3]=0;buf1[4]=0;//��ʼ��buf1����Ϊ10100
		buf1[0]=0;buf1[1]=0;buf1[2]=1;buf1[3]=0;buf1[4]=1;//��ʼ��buf2����Ϊ00101
		buf1[0]=0;buf1[1]=1;buf1[2]=0;buf1[3]=1;buf1[4]=0;//��ʼ��buf3����Ϊ01010
		initKey(buf1);
		initKey(buf2);
		initKey(buf3);
	}
	static void initKey(int []key) //��Կ������
	{
		for(int k=5;k<key.length;++k)
			key[k]=(key[k-2]+key[k-5])%2;
	}
	static String fileReadUtil() //��ϵͳ�ж��ļ�����
	{
		String str = null;
		try {
			System.out.println("�������ļ���(����·������\\)��");
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
	static void geffe()//����geffee�������
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
	static void lfsrEncryption() //���ܹ���
	{
		int i=0;
		boolean flag=false;
		int choose=-1;
		while(!flag)
		{
			System.out.println("��ѡ����ļ��ж�ȡ���Ļ����ֶ���������(1/0)");
			choose=sc.nextInt();
			if(choose==1||choose==0)
				flag=true;
			else
				System.out.println("����������������룺");
		}
		String s=new String();
		String code=new String();
		if(choose==1)
		{
			s=fileReadUtil();
		}
		else
		{
		System.out.println("����������C��");
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
		System.out.println("����Gette���ɵ�������м��ܺ������eΪ��");
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
		System.out.println("����JK���������ɵ�������м��ܺ������eΪ��");
		System.out.println(code);
		}
	static void lfsrDecryption()//���ܹ���
	{
		int i=0;
		boolean flag=false;
		int choose=-1;
		while(!flag)
		{
			System.out.println("��ѡ����ļ��ж�ȡ���Ļ����ֶ���������(1/0)");
			choose=sc.nextInt();
			if(choose==1||choose==0)
				flag=true;
			else
				System.out.println("����������������룺");
		}
		String s=new String();
		String code=new String();
		if(choose==1)
		{
			code=fileReadUtil();
		}
		else
		{
		System.out.println("����������e��");
		code=sc.next();
		}
		flag=false;
		while(!flag)
		{
			System.out.println("��ѡ����Gette��Կ�����ܻ�����JK��Կ������(1/0):");
			choose=sc.nextInt();
			if(choose==1||choose==0)
				flag=true;
			else
				System.out.println("����������������룺");
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
		System.out.println("���ܺ������CΪ��");
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
			System.out.println("��ѡ�����/����(1/0)");
			choose=sc.nextInt();
			if(choose==1||choose==0)
				flag=true;
			else
				System.out.println("����������������룺");
		}
		if(choose==1)
		lfsrEncryption();
		else
		lfsrDecryption();
		main(null);
	}
}

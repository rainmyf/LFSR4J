import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class LfsrEncryption {
private static int[] key=new int[32];
private static Scanner sc=new Scanner(System.in);
static void initKey() //��Կ������
{
	for(int k=5;k<31;++k)
		key[k]=(key[k-2]+key[k-5])%2;
}
static void lfsrEncryption() //���ܹ���
{
	System.out.println("��������Կ(<32)");
	int myKey=sc.nextInt();
	for(int i=4;i>=0;i--)   //����Կ��Ϊ��Կ�����ǰ��λ������key������
	{
		key[i]=myKey%2;
		myKey/=2;
	}
	initKey();
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
		    	 sum+=Math.pow(2,7-j)*key[(i+j)%31];
		     if(i+j>32)
			    i=(i+j-1)%31+1;
		     else
			    i=i+8;
		     }
		     code+=(char)((int)c^sum);
	}
	System.out.println("���ܺ������eΪ��");
	System.out.println(code);
	}
static void lfsrDecryption()//���ܹ���
{
	System.out.println("��������Կ(<32)");
	int myKey=sc.nextInt();
	for(int i=4;i>=0;i--)   //����Կ��Ϊ��Կ�����ǰ��λ������key������
	{
		key[i]=myKey%2;
		myKey/=2;
	}
	initKey();
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
	for (int counter = 0; counter<code.length(); counter++) {
		  int sum=0;
		  char c=code.charAt(counter);
		    for(int j=0;j<8;++j)
		    {
			    sum+=Math.pow(2,7-j)*key[(i+j)%31];
		    if(i+j>32)
			i=(i+j-1)%31+1;
		    else
			   i=i+8;
		    }
		     s+=(char)((int)c^sum);
	}
	System.out.println("���ܺ������CΪ��");
	System.out.println(s);

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
public static void main(String[] args) {
	int choose = -1;
	boolean flag=false;
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

import java.util.Scanner;
/**
 * LFSR加解密，没有JK触发器和GEFFE触发器
 * @author 毛宇锋
 * @version 1.0
 * */

public class lfsrEncryptionv2 {
	static char fn_feedback(char n,char c,char curr_state)
{
		char t = (char) (c & curr_state);
		char s = (char) (t & (char)128);//get first bit
		for(int i =1; i< n; i++)
		{
			s = (char) (s ^ ( (t << i) & (char)128 ));
		}
		return (char)((curr_state << 1) | (s >> (n-1))); //return next state
}

	static void lfsr_output_byte(int n,char c,char init_state,char output_bytes[],int byte_length)
{
	char next_state = init_state;
	for(int i = 0; i < byte_length; i++)
	{
		char temp = (char)0;
		for(int j=0; j< 8; j++)
		{
			temp = (char) (temp | ( (next_state & (char)128) >> j ));
			next_state = fn_feedback((char)n, c, next_state);
}
output_bytes[i] = temp;
System.out.println("lfsr_output_byte is called.");
}
}

//将一个字符按位输出
	static void output_binary(char c)
{
		for(int i=0; i< 8; i++)
		{
			if( (((int)c<< 1) & 128) ==1) 
		System.out.print("1");
	else
		System.out.print("0");
		}
		System.out.println();
}

	static void Geffe(char buf[],char buf1[],char buf2[],char b[])
{
	for(int i=0; i< 10; i++)
	{   
		b[i]=(char) (buf[i]*buf1[i]+buf2[i]*buf1[i]+buf2[i]);
		output_binary( (char)b[i]);
		}
	System.out.println("Geffe is called");
}
static void JK(char buf[],char buf1[],char c[])
{
	for(int i=1; i< 11; i++)
	{   
		c[i]=(char) (((int)buf[i]+(int)buf1[i]+1)*(int)c[i-1]+(int)buf[i]);
		output_binary( (char)c[i]);
		}
}
static void cypt(char b[])
{
	char []cypher=new char[10];
	char []cyph=new char[10];
	System.out.println("input the cyphertext:");
	Scanner sc=new Scanner(System.in);
	cypher=sc.next().toCharArray();
	int j=0;
	for(;cypher[j]!='\0';j++)
	{
		cyph[j]=(char) (cypher[j]^b[j]);
		System.out.print(cyph[j]);
		}
	System.out.println();
	for(int k=0;k<j;k++)
	{
		cypher[k]=(char) (cyph[k]^b[k]);
		System.out.print(cyph[k]);
		}
	System.out.println();
	}
public static void main(String[] args) {
	char []buf=new char[10];
	char []buf1=new char[10];
	char []buf2=new char[10];
	char []b=new char[100];
	char []c=new char[100];
//  函数f       初始状态152
	lfsr_output_byte(5,(char)144,(char)152, buf, 10);
	lfsr_output_byte(5,(char)44,(char)152, buf1, 10);
	lfsr_output_byte(5,(char)24,(char)152, buf2, 10);
	Geffe(buf,buf1,buf2,b);
	c[0]=0;
	JK(buf,buf1,c);
	System.out.println();
	System.out.println("Geffe operate:\n");
	cypt(b);
	System.out.println();
	System.out.println("J-K operate:\n");
	cypt(c);
}

}

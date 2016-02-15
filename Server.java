import java.net.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import java.sql.*;
public class Server {
	 
public static void main (String [] args ) throws Exception {

//this class is to create the server side socket that acts as the receiver of the data packet after merging the splits
        int bytesRead=0, lim=0;
        JFrame frame=new JFrame();
        int[] result=new int[20];
        String database,sql,nodeid="client";
        Statement stmt;
        int i=0,pc=0,tc=0,flag = 0,m,flag1=0,n=0,o;
        int currenttot;
        Connection conn;
        Rate r=new Rate();
        lim=r.rateSet();
        System.out.println("Rate limit approved: "+lim);
        int j,k=0,l=0;
        ServerSocket socket1 = new ServerSocket(15117);
        Socket soc=socket1.accept();
        byte[] bytearray=new byte[1024356];
        InputStream is=soc.getInputStream();
        FileOutputStream fos=new FileOutputStream("G:\\sem7\\cns\\copy.docx");
        BufferedOutputStream bos=new BufferedOutputStream(fos);
        bytesRead=is.read(bytearray, 0,bytearray.length);
        currenttot=bytesRead;
        System.out.println("completed...");
        do{
        	bytesRead=is.read(bytearray, 0, (bytearray.length-currenttot));
        	if(bytesRead>= 0)
        		currenttot+=bytesRead;
        	
        }while(bytesRead>-1);
        String str1="",str2="",str3="",str4="Packet flood attack";
        String str5="Replica flood attack",str6="Unauthorised node";
        String str7="Count Reuse",str8="",str9="";
        
        String str=new String(bytearray);
        
        bos.write(bytearray,0,bytearray.length);
        System.out.println(str);
        System.out.println("file transfer completed successfully....");
        
        for(j=0;j<str.length();j++)
        {
        		if(str.charAt(j)=='$')
        		{   
        			while(str.charAt(j+1)!='$')
        			{
        				j++;
        				str1=str1+(str.charAt(j));
        			}
        			flag=1;
        			System.out.println("Packet Count Received: "+str1);
        			break;
        		}
        }
        if(flag==1)
        {
        	//System.out.println("j "+j);
        	//k=j;
        	//System.out.println("k "+k);
        	//System.out.println("k content "+str.charAt(k));
        	for(k=j;k<str.length();k++)
        	{
        		
             if(str.charAt(k)=='$')
             {
            	 while(str.charAt(k+1)!='$')
     			{
     				k++;
     				str2=str2+(str.charAt(k));
     			}
            	 flag=2;
            	 System.out.println("Transmission count received: "+str2);
            	break;
             }
        	} 
        }
        if(flag==2)
        {
        	for(l=k;l<str.length();l++)
        	{
        		
             if(str.charAt(l)=='$')
             {
            	 while(str.charAt(l+1)!='$')
     			{
     				l++;
     				str3=str3+(str.charAt(l));
     			}
            	 flag=3;
            	 System.out.println("Node id received: "+str3);
            	break;
             }
        	} 
        }
        if(flag==3)
        {
        	for(n=l;n<str.length();n++)
        	{
        		
             if(str.charAt(n)=='$')
             {
            	 while(str.charAt(n+1)!='$')
     			{
     				n++;
     				str8=str8+(str.charAt(n));
     			}
            	 flag=4;
            	 System.out.println("Address: "+str8);
            	break;
             }
        	} 
        }
        if(flag==4)
        {
        	for(o=n;o<str.length();o++)
        	{
        		
             if(str.charAt(o)=='$')
             {
            	 while(str.charAt(o+1)!='$')
     			{
     				o++;
     				str9=str9+(str.charAt(o));
     			}
            	 System.out.println("Timestamp: "+str9);
            	break;
             }
        	} 
        }
        pc=Integer.parseInt(str1);
        tc=Integer.parseInt(str2);
        //System.out.println("Packet count received: "+pc);
        //System.out.println("Transmission count received: "+tc);
        if(str3.equalsIgnoreCase(nodeid))
        {
        	System.out.println("Signature verification successful");
        
           if(pc>lim && tc>lim)
           {
        	   database="jdbc:mysql://localhost:3306/flood";
        	   conn = DriverManager.getConnection( database, "root", "sree" );
        	   sql="insert into blacklist values('"+str3+"','"+str5+"')";
        	   stmt = conn.createStatement();
        	   stmt.executeUpdate(sql);
        	   JOptionPane.showMessageDialog(frame,"Replica Flood Attack","ATTACKER DETECTED",JOptionPane.WARNING_MESSAGE);
        	  
        	   System.out.println("Replica Flood Attack");
           }
           else if(pc>lim)
           {
        		database="jdbc:mysql://localhost:3306/flood";
        		conn = DriverManager.getConnection( database, "root", "sree" );
        		sql="insert into blacklist values('"+str3+"','"+str4+"')";
        		stmt = conn.createStatement();
        		stmt.executeUpdate(sql);
        		JOptionPane.showMessageDialog(frame,"Packet Flood Attack","ATTACKER DETECTED",JOptionPane.WARNING_MESSAGE);
        		System.out.println("Packet flood Attack");
           }
           else
           {
        	   System.out.println("INCONSISTENCY CHECK");
        	   database="jdbc:mysql://localhost:3306/flood";
        	   conn = DriverManager.getConnection( database, "root", "sree" );
        	   sql="select * from reuse;";
        	   stmt = conn.createStatement();
        	   ResultSet rs=stmt.executeQuery(sql);
        	   while(rs.next())
        	   {
        		   result[i]=rs.getInt(1);
        		   i++;
        	   }
        	   for(m=0;m<i;m++)
        	   {
        		   if(result[m]==pc || result[m]==tc)
        		   {
        			   System.out.println("Inconsistent Count detected");
        			   sql="insert into blacklist values('"+str3+"','"+str7+"')";
                	   stmt = conn.createStatement();
                	   stmt.executeUpdate(sql);
                	   JOptionPane.showMessageDialog(frame,"Count Reuse","ATTACKER DETECTED",JOptionPane.WARNING_MESSAGE);
                	   System.out.println("Count Reuse");
        			  flag1=1;
        		   }
        	   }
        		  if(flag1==0)
        		   {
        			   sql="insert into reuse values('"+pc+"')";
                	   stmt = conn.createStatement();
                	   stmt.executeUpdate(sql);
                	   //sql="insert into reuse values('"+tc+"')";
                	   //stmt = conn.createStatement();
                	   //stmt.executeUpdate(sql);
                	   //System.out.println("Packets received, authorised user");
                	   //System.out.println("Pclaim and Tclaim stored");
                	   JOptionPane.showMessageDialog(frame,"Packets received, authorised user\nPclaim and Tclaim stored");
                	   System.out.println("Authorised user\npackets received successfully");             	           		   
        		   }
           }
           
        }
        else
        {
        	System.out.println("Signature verification failed");
        	database="jdbc:mysql://localhost:3306/flood";
    		conn = DriverManager.getConnection( database, "root", "sree" );
    		sql="insert into blacklist values('"+str3+"','"+str6+"')";
    		stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
        }
   } 
}

class Rate{
	int lim=01;
	int ch;
	String str="";
	public int rateSet()
	{
		Scanner s=new Scanner(System.in);
		try{
			do{
				System.out.println("Menu:\n1.set limit\n2.exit");
				System.out.println("enter choice?");
				ch=s.nextInt();
				switch(ch)
				{
					case 1: System.out.println("Enter the limit value needed");
					lim=s.nextInt();
					if(lim>10)
					{
						System.out.println("too high value!enter value less than 10");
					}
	
					break;
					case 2:break;
				}
			}while(ch==1);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return lim;
	}
}




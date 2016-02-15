import java.net.*;
import resources.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;

import javax.swing.*;

public class Client extends JPanel implements ActionListener{

//this class is to create the client side socket in the network that sends data into the network

     File filename;
     int splitcount;
     JFileChooser chooser;
     String choosertitle,data;
     Socket sock;
     NodeCreate head,nc;
     PrintWriter out,out1;
     Linked ll,l2;
     int pc,tc;
     long s1;
     static JFrame frame;
     JButton or=new JButton("Choose File");
    public Client() {
    	pc=0;
    	tc=0;
    	data="";
    	head=null;
    	or.addActionListener(this);
      add(or);
     }
     public static void main (String [] args ) throws IOException {
          
            JFrame frame = new JFrame("");
            Client panel=new Client();
            frame.getContentPane().add(panel,"Center");
    	    frame.setSize(200,200);
    	    frame.setVisible(true);  
            }
    public  void actionPerformed(ActionEvent e) {
    	 int ch;
   	 if(e.getActionCommand().equals("Choose File"))
   	 { try
   		 {
   		 sock = new Socket("localhost",15117);
   		 chooser = new JFileChooser(); 
   		 chooser.setCurrentDirectory(new java.io.File("."));
   		 chooser.setSize(20, 20);
   		 chooser.setDialogTitle(choosertitle);
   		 chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
       
   		 chooser.setAcceptAllFileFilterUsed(false);  
   		 if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
   		 { 
   			 System.out.println("getCurrentDirectory(): "+chooser.getCurrentDirectory());
   			 System.out.println("getSelectedFile() : "+chooser.getSelectedFile());
   			 filename=new File(chooser.getCurrentDirectory().getAbsolutePath()+"\\"+chooser.getSelectedFile().getName());
   			 System.out.println(chooser.getCurrentDirectory().getAbsolutePath()+chooser.getSelectedFile().getName());
         }
   		 else 
   		 {
         	System.out.println("No Selection ");
         }
         //System.out.println("hello");
         FileSplit fs=new FileSplit();
         String[] splits=new String[100];
         splits=fs.splitFile(filename);
         String str="";
         //System.out.println("hello");
         splitcount=fs.packetCount();
         do{
        	 //byte[] hi=new byte[10];
        	 System.out.println("Enter Choice:\n1.Packet Flood Attack\n2.Replica Flood Attack\n3. Exit");
        	 Scanner s=new Scanner(System.in);
        	 ch=s.nextInt();
        	 switch(ch)
        	 {
        	 	case 1: Linked ll=new Linked();
        	 			byte[] hi={0};
        	 			byte[] bytearray=new byte[1023456];
        	 			int bytesRead;
        	 			String id="";
        	 			OutputStream os=sock.getOutputStream();
        	 			for(int j=0;j<splitcount;j++)
        	 			{
        	 				System.out.println(splits[j]);
        	 				File transferFile = new File (splits[j]);
        	 				FileInputStream fstream = new FileInputStream(transferFile);
        	 				BufferedInputStream bis = new BufferedInputStream(fstream);
        	 				bytesRead=bis.read(bytearray,0,bytearray.length);
        	 				NodeCreate nc=new NodeCreate(splits[j],0);
        	 				if(head==null)
        	 				{
        	 					head=nc; 
        	 				}
        	 				ll.insert(nc);
        	 				pc=ll.getpktCount(nc);
            	 			tc=ll.gettransCount(nc);
            	 			id=ll.getNodeid(nc,1);
            	 			s1=ll.getTimestamp(nc,0);
        	 				System.out.println("Packet count: "+pc+"\nTransmission count:"+tc);
            	 			
        	 			}	
        	 			str=str+sock.getInetAddress();
        	 			data="$"+pc+"$"+tc+"$"+id+"$"+str+"$"+s1+"$";
        	 			System.out.println(data);
        	 			byte[] b1=new byte[data.length()];
        	 			b1=data.getBytes();
        	 			System.out.println(b1);
        	 			hi=new byte[b1.length+bytearray.length];
        	 			System.arraycopy(bytearray, 0,hi,0,bytearray.length);
        	 			System.arraycopy(b1,0,hi,bytearray.length,b1.length);
        	 			
    	 				//System.out.println("PACKET: "+hi);
        	 			os.write(hi, 0, hi.length);
      	 			    
        	 			//ll.display(head,0);
        	 			//System.out.println("\nSending Files...");
        	 			//System.out.println("\nFile transfer complete");
        	 			break;
        	 	case 2: Linked l2=new Linked();
	 			byte[] hi1={0};
	 			byte[] bytearray1=new byte[1023456];
	 			int bytesRead1;
	 			String id1="";
	 			int j=0;
	 			OutputStream os1=sock.getOutputStream();
	 			while(j<splitcount)
	 			{
	 				System.out.println(splits[0]);
	 				File transferFile = new File (splits[0]);
	 				FileInputStream fstream = new FileInputStream(transferFile);
	 				BufferedInputStream bis = new BufferedInputStream(fstream);
	 				bytesRead=bis.read(bytearray1,0,bytearray1.length);
	 				NodeCreate nc=new NodeCreate(splits[0],1);
	 				if(head==null)
	 				{
	 					head=nc; 
	 				}
	 				l2.insert(nc);
	 				pc=l2.getpktCount(nc);
    	 			tc=l2.gettransCount(nc);
    	 			id1=l2.getNodeid(nc,0);
    	 			s1=l2.getTimestamp(nc,0);
	 				System.out.println("Packet count: "+pc+"\nTransmission count:"+tc);
	 				j++;
    	 			
	 			}	
	 			str=str+sock.getInetAddress();
	 			data="$"+pc+"$"+tc+"$"+id1+"$"+str+"$"+s1+"$";
	 			System.out.println(data);
	 			byte[] b2=new byte[data.length()];
	 			b2=data.getBytes();
	 			System.out.println(b2);
	 			hi1=new byte[b2.length+bytearray1.length];
	 			System.arraycopy(bytearray1, 0,hi1,0,bytearray1.length);
	 			System.arraycopy(b2,0,hi1,bytearray1.length,b2.length);
	 			os1.write(hi1, 0, hi1.length);
	 			break;		
        	 	case 3: 
        	 			break;
        	 }
       }while(ch>2);
   		 }
   	 catch(Exception e1)
   	 {
   		 e1.printStackTrace();
   	 }
   	 
   	 finally
   	 {
   		 try
   		 {
   			 sock.close();
   		 }
   		 catch(IOException e2)
   		 {
   			 e2.printStackTrace();
   		 }
   	 }
        
   	 }
     }
}

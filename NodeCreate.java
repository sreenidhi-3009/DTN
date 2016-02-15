package resources;
import java.util.Date;

public class NodeCreate {
	public String path;
	public PClaim pc;
	TClaim tc;
	public int flag;
	public NodeCreate next;
	public NodeCreate(String path1,int flag)
	{
		path=path1;
	    pc=new PClaim();
		tc=new TClaim();
		next=null;
		this.flag=flag;
	}

}
class PClaim {
	
String nodeid;
public int cp;
Date d1;
long s;
public PClaim()
{
	cp=0;
	d1=new Date();
	s=d1.getTime();
	nodeid="client";
	
}
}

class TClaim {
	
String src,dest;
int ct;
Date d2;
long s;
public TClaim()
{
	src="client";
	dest="server";
	ct=0;
	d2=new Date();
	s=d2.getTime();
}
}


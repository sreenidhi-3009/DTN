package resources;
public class Linked{

//this class adds all the packets in a linked list format
	
	NodeCreate head;
	int i,j;
	public Linked()
	{
		head=null;
		i=0;
		j=0;
	}
	public void insert(NodeCreate node)
	{
		NodeCreate p;
		if(node.flag==0 && node.pc.nodeid=="client")
		{
			node.pc.cp=i;
			i++;
		}
		else if(node.flag==1 && node.tc.src=="client" && node.tc.dest=="server"){
			node.pc.cp=j;
			node.tc.ct=j;
			j++;
		}
	if(head==null)
	{
		head=node;
		
		head.next=null;
	}
	else{
		p=head;
		while(p.next!=null)
		{
			p=p.next;
			
		}
		p.next=node;
		p.next.pc.cp++;
		p.next.tc.ct++;
	}
	}
	public void display(NodeCreate node,int flag)
	{
		NodeCreate p;
		if(node==null)
		{
			System.out.println("empty");
		}
		else
		{
			p=node;
			while(p!=null)
			{
				if(flag==0)
				{
				System.out.println("File path: "+p.path);
				System.out.println("Source: "+p.pc.nodeid);
				p=p.next;
				}
				else if(flag==1)
				{
					System.out.println("File path: "+p.path);
					System.out.println("Source: "+p.tc.src);
					System.out.println("Destination: "+p.tc.dest);
					p=p.next;
				}
			}
		}
	}
	public int getpktCount(NodeCreate node)
	{
          
          return node.pc.cp;
	}
	public int gettransCount(NodeCreate node)
	{
		
        return node.tc.ct;
		
	}
	public String getNodeid(NodeCreate node,int flag)
	{
		if(flag==1)
		{
		return node.pc.nodeid;
		}
		else
		{
			return node.tc.src;
		}
	}
	public long getTimestamp(NodeCreate node,int flag)
	{
		if(flag==0)
			return node.pc.s;
		else
			return node.tc.s;
	}
	

}

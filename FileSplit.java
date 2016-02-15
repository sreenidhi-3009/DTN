package resources;
import java.io.*;


public class FileSplit {

//this class does the file manipulation to split it into packets of fixed size to send into the network

	int i;
	public FileSplit(){
		i=0;
	}
    public String[] splitFile(File f) throws IOException {
    	String[] arr=new String[100];
    	String s;
    	
        BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f));
        FileOutputStream out;
        String name = f.getName();
        System.out.println(name);
        System.out.println(f.getParent());
        int partCounter = 1;
        int sizeOfFiles = 1024;
        byte[] buffer = new byte[sizeOfFiles];
        int tmp = 0;
        while ((tmp = bis.read(buffer)) > 0) {
        	s=f.getParent()+"\\"+name+"."+String.format("%03d", partCounter++);
            File newFile=new File(s);
            
            newFile.createNewFile();
            	out = new FileOutputStream(newFile);
                out.write(buffer,0,tmp);
                out.close();
            	arr[i]=s;
            	i++;
            
        }
            return  arr;
        }
    public int packetCount()
    {
    	System.out.println("Total packets generated: "+i);
    	return i;
    }

}

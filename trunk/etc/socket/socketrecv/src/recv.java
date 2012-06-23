import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class recv {
	public static void main(String[] args) 
	{
		
		try
		{
			System.out.println("create sock");
			ServerSocket svsock = new ServerSocket(5000);
			for (int i=0;;i++) {
				System.out.println("accept");
				Socket sock = svsock.accept();
				System.out.println("buffer read");
				int j = 0;
				FileOutputStream outFile = null;
				try {
					outFile = new FileOutputStream("test/xyz" + i + ".dat");
					System.out.println("test/xyz" + i + ".dat");
					j++;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				InputStream is = sock.getInputStream();
				byte[] byteBuffer = new byte[10*1024];
				

				int allsize = 0;
				while(sock.isConnected()) {
					int size = is.read(byteBuffer);
					if (size == -1){
						break;
					} else {
						outFile.write(byteBuffer, 0, size);
					}
					allsize += size;
					//System.out.println("wd" + size);
				}
				System.out.println("close size=" + allsize);
				outFile.close();
				sock.close();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		System.out.println("endmain");
	}

}

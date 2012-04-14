import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
				FileWriter outFile = null;
				try {
					outFile = new FileWriter("xyz" + i + ".mp4");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("accept");
				Socket sock = svsock.accept();
				System.out.println("buffer read");
				BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				BufferedWriter wt = new BufferedWriter(outFile);
			
				while(sock.isConnected()) {
					System.out.println("r");
					int line = in.read();
					if (line == -1) {
						System.out.println("b");
						break;
					}
					System.out.println("w");
					wt.write(line);
					System.out.println("wd");
				}
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

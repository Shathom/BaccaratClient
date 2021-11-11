import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {
	Socket socketClient;
	String ipAddress;
	int portNumber;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;
	
	Client(Consumer<Serializable> call, int portNum, String ip){
		this.portNumber = portNum;
		this.ipAddress = ip;
		callback = call;
	}
	
	public void run() {
		
		try {
			socketClient = new Socket(ipAddress,portNumber);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
				BaccaratInfo clientInfo = (BaccaratInfo)in.readObject();
			}
			catch(Exception e) {
				
			}
		}
	
    }
	
	public void send(BaccaratInfo clientInfo) {
		
		try {
			out.writeObject(clientInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}

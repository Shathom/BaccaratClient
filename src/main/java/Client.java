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
	public BaccaratInfo clientInfo;
	boolean fresh = false;
	
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
		catch(Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
			 
			try {
				this.clientInfo = (BaccaratInfo)in.readObject();
				callback.accept(clientInfo);
				this.fresh = true;
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	
    }
	
	public BaccaratInfo getBaccaratInfo() {
		while (!this.fresh) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.fresh = false;
		return this.clientInfo;
		
	}
	
	public void send(BaccaratInfo clientInfo) {
		
		try {
			out.writeObject(clientInfo);
			System.out.println("information sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}

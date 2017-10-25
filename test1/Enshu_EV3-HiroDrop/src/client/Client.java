package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client{
	private Socket s = null;
	private BufferedReader br = null;

	public static void main(String[] args) throws IOException, InterruptedException{
		Client client = new Client();
		while(true){
			client.print();
		}
	}
	
	public Client(){
		try{
			s = new Socket("10.0.1.1", 6000);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}catch(IOException e){
			System.err.println("Failed to connect: " + e);
			System.exit(1);
		}
		System.out.println("connected");
	}
	
	public void print() throws IOException{
		String line = br.readLine();
		if(line != null) System.out.println(line);
	}
}
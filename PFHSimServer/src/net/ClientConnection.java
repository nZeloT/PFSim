package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import net.shared.ClientMessage;
import net.shared.ServerMessage;

public class ClientConnection {
	
	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private boolean closed = true;
	
	public ClientConnection(Socket s) {
		try {
			in = new ObjectInputStream(s.getInputStream());
			out = new ObjectOutputStream(s.getOutputStream());
			closed = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientMessage doRoundTrip(ServerMessage clnt){
		ClientMessage serv = null;
		if(!closed){
			try {
				out.writeObject(clnt);
				serv = (ClientMessage)in.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return serv;
	}

	public void close(){
		try {
			closed = true;
			in.close();
			out.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
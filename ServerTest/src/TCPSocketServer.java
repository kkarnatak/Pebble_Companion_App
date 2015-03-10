//package vc.server.tcpsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TCPSocketServer implements Runnable {
	
	private Set<TCPClientHandler> clients = Collections.synchronizedSet(new HashSet<TCPClientHandler>());
	
	private final int tcpPort;
	
	private boolean running = true;
	private ServerSocket server=null;

	public TCPSocketServer(int tcpPort) {
		this.tcpPort = tcpPort;
		new Thread(this).start();
	}

	@Override
	public void run() {
		
		if (server == null) {
			try {
				server = new ServerSocket(tcpPort);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		while(running) {
			try {
				Socket s = server.accept();
				TCPClientHandler clientHandler = new TCPClientHandler(s);
				clients.add(clientHandler);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}

	public void sendAlarmEvent(String string) {
		for(TCPClientHandler client : clients) {
			client.sendMessage(string);
		}
	}

}

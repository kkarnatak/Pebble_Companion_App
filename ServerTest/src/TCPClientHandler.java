//package vc.server.tcpsocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClientHandler {
	
	private Socket s;
	
	private OutputStream os=null;

	public TCPClientHandler(Socket s) {
		this.s = s;
		try {
			this.os = s.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String string) {
		byte buf[] = packMessage(string);
		try {
			os.write(buf);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private byte[] packMessage(String string) {
		
		int size = 7+string.length();
		
		byte buf[] = new byte[size];
		buf[0] = (byte)0;
		
		// Size
		buf[1] = (byte)0;
		buf[2] = (byte)size;
		
		// Message type
		buf[3] = 'A';
		buf[4] = 'M';
		
		// Format
		buf[5] = 'T';
		
		for(int i=0;i<string.length();i++) {
			buf[6+i] = (byte)string.charAt(i);
		}		
		
		buf[buf.length-1] = (byte)0xff;
		

		return buf;
	}

}

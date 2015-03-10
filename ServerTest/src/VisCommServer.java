

import java.io.File;

/*import vc.server.tcpsocket.TCPSocketServer;
import vc.server.websocket.WebSocketServer;
import fi.iki.elonen.SimpleWebServer;*/

public class VisCommServer {
	
	public static final int HTTP_PORT = 80;
	public static final int WS_PORT = 2000;
	public static final int TCP_PORT = 2001;

	public static void main(String[] args) {
/*		SimpleWebServer ws = new SimpleWebServer(null, HTTP_PORT, new File("wwwroot"), true);
		
		WebSocketServer wss = new WebSocketServer(WS_PORT);*/
		
		TCPSocketServer tss = new TCPSocketServer(TCP_PORT);
		
		boolean running = true;
		int count = 0;
		while(running) {
			String message = "Message "+count;
			System.out.println("Sending message to clients: "+message); 
		//	wss.sendAlarmEvent(message);
			tss.sendAlarmEvent(message);
			
			count++;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

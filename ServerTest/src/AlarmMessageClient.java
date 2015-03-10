import java.io.IOException;
import java.net.Socket;


public class AlarmMessageClient {
	
	public static final String HOST = "localhost";
	public static final int TCP_PORT = 2001;

	public static void main(String[] args) {
		try {
			Socket s = new Socket(HOST, TCP_PORT);
			SocketWrapper wrapper = new SocketWrapper(s);
			
			// Register
			String message = "<subscription client=\"AlarmMessageClient\"><topic name=\"AlarmMessage\"/></subscription>";
			s.getOutputStream().write(message.getBytes());
			
			// Wait for messages
			boolean running = true;
			while(running) {
				String event = getTextEvent(wrapper);
				System.out.println("Event received: "+event);
			}
			
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
	}

	private static String getTextEvent(SocketWrapper wrapper) throws NullPointerException, IOException {
		while(true) {
			String[] message = wrapper.getMessage();
			if("T".equals(message[0])) {
				if("AM".equals(message[1])) {
					return message[2];
				}
			}
		}
	}
}

package com.example.testapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class SocketWrapper {
	
	private final Socket s;
    static int num=0;
	private InputStream is=null;
	
	private String buffer = "";

	public SocketWrapper(Socket s) {
		this.s = s;
		try {
			this.is = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getMessage() throws IOException, NullPointerException {
		byte[] buf = new byte[1024];
		
		while (true) {
			String[] msg = fetchMessageFromBuffer();

			if (msg != null) {
                System.out.println("KKLOG : Inside MSG IF condition");
				return msg;
			}		

			int numBytesRead = is.read(buf);
			if (numBytesRead != -1) {

				buffer = buffer + new String(buf, 0, numBytesRead);
			}
		}
	}

	private String[] fetchMessageFromBuffer() {
		int begin = buffer.indexOf(0x00);
		int end = buffer.indexOf(0xff);

		if(begin!=-1 && end!=-1) {

			System.out.println("KKLOG : Inside if condition");
			if(begin!=0) {
				System.out.println("We have bogus data at the beginning of the buffer: "+buffer.substring(0, begin));
			}
		
			String message = buffer.substring(begin, end);
			if((end+1)<buffer.length()) {
				buffer = buffer.substring(end+1);
			} else {
				buffer = "";
			}
			
			String[] arr = new String[3];
			
			// Format
			arr[0] = message.substring(5, 6);
			
			// Message topic
			arr[1] = message.substring(3, 5);
			
			arr[2] = message.substring(6);
			
			return arr;
		}
		System.out.println("KKLOG : Outside if condition");
		System.out.println("KKLOG :  Begin = " + begin + " End = " + end);

        String[] aa = new String[3];
        if(num%2 == 0) {
            aa[0]="T";
            aa[1]="AM";
            aa[2]=" Dummy Log Msg";
            //aa = {"T", "AM", "KKEVENT MSG"};
        }
        else
        {
            aa[0]="AM";
            aa[1]="T";
            aa[2]=" Dummy Log Msg 2";
        }

        num++;

		
		return aa;
	}

}

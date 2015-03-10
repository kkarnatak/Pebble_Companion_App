package com.example.testapp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class AlarmMessageClient {
	/*
	public static final String HOST = "localhost";
	public static final int TCP_PORT = 2001;

	public static void main(String[] args) {
		try {
			Socket s = new Socket(HOST, TCP_PORT);
			SocketWrapper wrapper = new SocketWrapper(s);
			
			
			// Register
			String message ="<subscription client= \"AlarmMessageClient\" ><topic name=\"AlarmMessage\"/></subscription>";
         s.getOutputStream().write(message.getBytes());
         s.getOutputStream().flush();
         System.out.println("one");

			// Wait for mSystem.out.println("KKLOG : Inside if condition");essages
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
		
	}*/

	/*public static String getTextEvent(SocketWrapper wrapper) throws NullPointerException, IOException {
		while(true) {

			String[] message = wrapper.getMessage();
			System.out.println("two");

			if("T".equals(message[0])) {
				System.out.println("3");
				if("AM".equals(message[1])) {
					return message[2];
				}
			}
		}
	}*/

    public static String[] getTextEvent(SocketWrapper wrapper) throws NullPointerException, IOException {
        while(true) {

            String[] message = wrapper.getMessage();
            System.out.println("two");

           /* if("T".equals(message[0])) {
                System.out.println("3");
                if("AM".equals(message[1])) {
                    return message[2];
                }
            }*/
            return message;
        }
    }
	
	private static void printMsg(Socket s){
		boolean end=false;
		try{
			
			Byte[] messageByte = new Byte[1000];
			Byte[] byteArr = new Byte[100];
			DataInputStream in= new DataInputStream(s.getInputStream());
			
			while (!end){
				
				
			}

		}
		catch(Exception e){
			
			
		}
		
	}
	
}

package com.messaging_application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {

	public static int port = 123;

	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	public Server() throws IOException {
		serverSocket = new ServerSocket(port);
		while (true) {
			init();
			messaging();
			close();
		}
	}

	private void init() throws IOException {
		socket = serverSocket.accept();
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream());
		output.flush();
	}

	private void messaging() throws IOException {
		new Thread(this).start();
		String message = null;
		do {
			message = input.readLine();
			System.out.println(message);
		} while (!message.equalsIgnoreCase("Client: END"));
	}

	private void close() throws IOException {
		socket.close();
		input.close();
		output.close();
		System.exit(1); 
	}

	public void run() {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			String message = scanner.nextLine();
			output.println("Server: " + message + "\n");
			output.flush();
		}
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}

}

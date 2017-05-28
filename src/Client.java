import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	public Client() throws UnknownHostException, IOException {
		init();
		messaging();
		close();
	}

	private void init() throws UnknownHostException, IOException {
		socket = new Socket("localhost", Server.port);
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
		} while (!message.equalsIgnoreCase("Server: END"));
	}

	private void close() throws IOException {
		input.close();
		output.close();
		socket.close();
		System.exit(1); 
	}

	public void run() {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			String message = scanner.nextLine();
			output.println("Client: " + message);
			output.flush();
		}
	}

	public static void main(String args[]) throws UnknownHostException, IOException {
		new Client();
	}
}

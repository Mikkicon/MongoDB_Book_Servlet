package http.server;

import http.server.processors.Processor;
import http.server.processors.ServletProcessor;
import http.server.processors.StaticResourceProcessor;
import http.server.servlet.AbstractServletsMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

	/**
	 * WEB_ROOT is the directory where our HTML and other files reside. For this
	 * package, WEB_ROOT is the "webroot" directory under the working directory.
	 * The working directory is the location in the file system from where the
	 * java command was invoked.
	 */
	private boolean shutdown = false;
	private final AbstractServletsMap servletsMap;

	public HttpServer(AbstractServletsMap servletsMap) {
		this.servletsMap = servletsMap;
	}

	public void await() throws IOException {
		ServerSocket serverSocket = null;
		int port = 8889;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Server is waiting for request at port: " + port);
		servletsMap.callInit();
		// Loop waiting for a request
		while (!shutdown) {
			try {
				final Socket socket = serverSocket.accept();
				try {
					shutdown = processRequest(socket);
					System.out.println(this);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		servletsMap.callDestroy();
		serverSocket.close();
	}

	private boolean processRequest(Socket socket) throws IOException {
		InputStream input = socket.getInputStream();
		OutputStream output = socket.getOutputStream();

		Request request = new HttpRequest(input);
		System.out.println(request.getRequestAsText());
		Response response = new HttpResponse(output);

		String uri = request.getURI();

		if (uri.equals("/shutdown"))
			return !shutdown;

		Processor processor = selectProcessor(uri);
		processor.process(request, response);
		socket.close();

		return shutdown;
	}

	private boolean isNull(String uri) {
		return uri == null;
	}

	private Processor selectProcessor(String uri) {
		Processor processor;
		// check if this is a request for a servlet or a static resource
		// a request for a servlet begins with "/servlet/"
		if (uri.startsWith("/servlet/")) {
			processor = new ServletProcessor(servletsMap);
		} else {
			processor = new StaticResourceProcessor();
		}
		return processor;
	}
}

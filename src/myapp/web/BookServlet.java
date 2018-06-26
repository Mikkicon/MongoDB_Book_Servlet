package myapp.web;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import http.server.Request;
import http.server.Response;
import http.server.servlet.AbstractServlet;
import org.bson.Document;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class BookServlet extends AbstractServlet {

    private MongoCollection<Document> bookCollection;

    private void initDB() throws UnknownHostException {

    			ServerAddress serverAddress = new ServerAddress("localhost",
    			27017);


    			
    			//MY LABA
        MongoClient mongoClient = new MongoClient(serverAddress);
        MongoDatabase database = mongoClient.getDatabase("Books");
        bookCollection = database.getCollection("books");
        if (bookCollection == null)
           database.createCollection("books",  new CreateCollectionOptions().capped(true).sizeInBytes(0x150000));
    }

    private void createBook(Book book) {
        Document doc = book.toDocument();
        bookCollection.insertOne(doc);
    }

    private List<Book> getBooks() {
        List<Book> books = new ArrayList<Book>();
        FindIterable<Document> cursor = bookCollection.find();
        for (Document doc : cursor)
            books.add(Book.fromDocument(doc));
        return books;
    }

    @Override
    public void init() {
        System.out.println("---from Servlet init---");
        try {
            initDB();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void service(Request request, Response response) throws IOException {
        if(request.getType().equals("POST"))
            doPost(request, response);
        else if (request.getType().equals("GET"))
            doGet(request, response);
    }

    protected void doPost(Request request, Response response) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html\r\n");
            out.println("<html><head><head/><body>");
            out.print("<h2>Books</h2>");
            out.print("<table>");

            String name = request.getParameter("name");
            String author = request.getParameter("author");
            String language = request.getParameter("language");
            String year = request.getParameter("year");

            if (name != null && author != null && language != null && year != null)
                createBook(new Book(name, author, language, Integer.parseInt(year)));

            

            out.print("<form action='servlet/book' method='get'>");
            out.print("<label>Name <input type='text' name='name'></label><br>");
            out.print("<label>year of post <input type='text' name='year'></label><br>");
            out.print("<label>language <input type='text' name='language'></label><br>");
            out.print("<label>author <input type='text' name='author'></label><br>");
            out.print("<input type='submit' value='Submit'>");
            out.print("</form>");
            
            out.print("<tr><td><p>  Name </p></td><td><p>  Author </p></td><td><p>  Language </p></td><td><p> Year </p></td></tr>");

            
            
            for (Book book : getBooks())
                out.print("<tr><td><p> " + book.getName() + "</p></td><td><p> " + book.getAuthor() + "</p></td><td><p> " + book.getLanguage() + "</p></td><td><p> " + book.getYear() + "</p></td>");
            out.print("</table>");
            out.println("</body></html>");
        }
    }

    protected void doGet(Request request, Response response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("---from Servlet destroy---");
    }

}

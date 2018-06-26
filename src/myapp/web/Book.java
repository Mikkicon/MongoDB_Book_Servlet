package myapp.web;

import org.bson.Document;
import java.io.Serializable;

public class Book implements Serializable{
    private String name;
    private String author;
    private String language;
    private int year;

    public  Book(String name, String author, String language, int year) {
        this.author = author;
        this.language = language;
        this.name = name;
        this.year = year;
    }

    public String getName()
    {
        return name;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getLanguage()
    {
        return language;
    }

    public int getYear()
    {
        return year;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("name", name);
        document.append("year", year);
        document.append("language", language);
        document.append("author", author);
        return document;
    }

    public static Book fromDocument(Document document) {
        return new Book((String)document.get("name"), (String)document.get("author"), (String)document.get("language"), (Integer)document.get("year"));
    }
}
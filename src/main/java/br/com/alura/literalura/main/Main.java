package br.com.alura.literalura.main;

import br.com.alura.literalura.model.Author;
import br.com.alura.literalura.model.Books;
import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import br.com.alura.literalura.service.ApiService;
import br.com.alura.literalura.service.BooksList;
import br.com.alura.literalura.service.DataConversion;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private Scanner reading = new Scanner(System.in);
    private static ApiService apiService = new ApiService();
    private static DataConversion dataConversion = new DataConversion();
    private static final String ADRESS = "https://gutendex.com/books/";
    private List<Books> registredBooks = new ArrayList<>();
    private List<Author> registredAuthors = new ArrayList<>();
    private BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Main(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        while (true) {
            String menu = "Choose one option:\n" +
                    "1 - Search books by name\n" +
                    "2 - Show registrated books\n" +
                    "3 - Show registrated authors\n" +
                    "4 - Show living authors by some year\n" +
                    "5 - Show books by some language\n" +
                    "0 - Exit";

            System.out.println(menu);
            var choice = reading.nextInt();
            reading.nextLine();

            switch (choice){
                case 1:
                    findBookByTitle();
                    break;
                case 2:
                    showRegistredBooks();
                    break;
                case 3:
                    showRegistredAuthors();
                    break;
                case 4:
                    showLivingAuthorsBySomeYear();
                    break;
                case 5:
                    showBooksBySomeLanguage();
                    break;
                case 0:
                    System.out.println("Leaving the aplication...");
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    private BooksList getDadosLivro() {
        System.out.println("Type the book name: ");
        String chosenBook = reading.nextLine();
        String url = ADRESS + "?search=" + chosenBook.replace(" ", "%20");
        String json = apiService.getData(url);
        return dataConversion.getData(json, BooksList.class);
    }

    public void findBookByTitle() {
        BooksList booksList = getDadosLivro();

        if (booksList != null && booksList.getResults() != null && !booksList.getResults().isEmpty()) {
            Books firstBook = booksList.getResults().get(0);
            System.out.println("First book found: " + firstBook.getTitle());
            bookRepository.save(firstBook);

            System.out.println("Title: " + firstBook.getTitle());
            for (Author author : firstBook.getAuthors()) {
                System.out.println("Author Name: " + author.getName());
            }
            System.out.println("Language: " + firstBook.getLanguage());
            System.out.println("Download Count: " + firstBook.getDownloadCount());
        } else {
            System.out.println("No books found...");
        }
    }

    public void showRegistredBooks() {
        registredBooks = bookRepository.findAll();
        if (registredBooks.isEmpty()) {
            System.out.println("No books registered.");
        } else {
            for (Books book : registredBooks) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Language: " + String.join(", ", book.getLanguage()));
                System.out.println("Download Count: " + book.getDownloadCount());
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            }
        }
    }

    public void showRegistredAuthors() {
        registredAuthors = authorRepository.findAll();
        if (registredAuthors.isEmpty()) {
            System.out.println("No authors registered.");
        } else {
            for (Author author : registredAuthors) {
                System.out.println("Author Name: " + author.getName());
                System.out.println("Author's Birth: " + author.getBirthYear());
                System.out.println("Author's Death: " + author.getDeathYear());
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            }
        }
    }

    public void showLivingAuthorsBySomeYear() {
        System.out.println("Choose the year: ");
        int year = reading.nextInt();
        reading.nextLine();

        List<Author> livingAuthors = authorRepository.findLivingAuthorsByYear(year);

        if (livingAuthors.isEmpty()) {
            System.out.println("No living authors found in " + year);
        } else {
            for (Author author : livingAuthors) {
                System.out.println("Author Name: " + author.getName());
                System.out.println("Author's Birth: " + author.getBirthYear());
                System.out.println("Author's Death: " + author.getDeathYear());
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            }
        }
    }

    public void showBooksBySomeLanguage() {
        System.out.println("""
                            Enter the language: 
                            EN - English
                            ES - Spanish
                            PT - Portuguese
                            FR - French
                            """);
        String language = reading.nextLine();
        language = language.toLowerCase(Locale.ROOT);

        List<Books> booksByLanguage = bookRepository.findByLanguageContaining(language);

        if (booksByLanguage.isEmpty()) {
            System.out.println("No books found for the language: " + language);
        } else {
            for (Books book : booksByLanguage) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Language: " + book.getLanguage());
                System.out.println("Download Count: " + book.getDownloadCount());
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            }
        }
    }

}
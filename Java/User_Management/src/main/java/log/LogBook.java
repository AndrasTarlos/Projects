package log;

import lombok.Getter;
import lombok.Setter;
import utils.DatahandlerJSON;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Vector;

/**
 * <h1>LogBook</h1>
 * @author: Tarlos Andras
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * The model class of logbook. Every change is saved in this class in
 * form of a Vector String.
 */
@Getter
@Setter
public class LogBook {
    private final Vector<String> entries = new Vector<>();
    private static LogBook instance;
    private File file;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean fileWritingEnabled = false;

    private static final URI logbookPath;

    static {
        try {
            logbookPath = Objects.requireNonNull(DatahandlerJSON.class.getResource("../DATA/logbook.log")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Advanced constructor
     */
    private LogBook() {
        file = new File(logbookPath);
        try {
            if (!file.createNewFile()) {
                reader = new BufferedReader(new FileReader(file));
                readFile();
                reader.close();
            }

            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GetInstance method for the Singleton design pattern
     * @return LogBook instance
     */
    public static LogBook getLogBookInstance() {
        if (instance == null) {
            instance = new LogBook();
        }
        return instance;
    }

    /**
     * Adds an entry to the log book
     * @param entry a String value
     * @throws IOException if an error occurs
     */
    public void addEntry(String entry) throws IOException {
        entries.add(entry);
        if (fileWritingEnabled) {
            writeFile(entry);
        }
    }

    /**
     * Get the entry at index x in the log book
     * @param index int
     * @return String
     */
    public String getEntry(Integer index) {
        return entries.get(index);
    }

    /**
     * Get the size of
     * @return entries
     */
    public int getSize() {
        return entries.size();
    }

    /**
     * Close the log book
     * @throws IOException if an error occurs
     */
    public void logbookClose() throws IOException {
        writer.close();
    }

    /**
     * Print the content of the log book
     */
    public void printLog() {
        for (String e: entries) {
            System.out.println(e);
        }
    }

    /**
     *
     * @param entry String
     * @throws IOException if an error occurs
     */
    private void writeFile(String entry) throws IOException {
        writer.append(entry);
        writer.newLine();
        writer.flush();
    }

    /**
     * Read the logbook.log file
     * @throws IOException if an error occurs
     */
    private void readFile() throws IOException {
        while (reader.ready()) {
            entries.add(reader.readLine());
        }
        fileWritingEnabled = true;
    }
}

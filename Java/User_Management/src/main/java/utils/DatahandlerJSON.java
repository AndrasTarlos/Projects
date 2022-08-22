package utils;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import company.Company;
import fascades.Fascade;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <h1>DatahandlerJSON</h1>
 * @author: Tarlos Andras
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * This class writes and reads the JSON files
 * companyJSON and fascadeJSON.
 */
public class DatahandlerJSON {
    private static final URI companyJsonPath;
    private static final URI fascadeJsonPath;

    private static DatahandlerJSON instance;

    // Reads the URI path of the needed files
    static {
        try {
            companyJsonPath = Objects.requireNonNull(DatahandlerJSON.class.getResource("../DATA/companyJSON.json")).toURI();
            fascadeJsonPath = Objects.requireNonNull(DatahandlerJSON.class.getResource("../DATA/fascadeJSON.json")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Basic constructor
     */
    public DatahandlerJSON() {

    }

    /**
     * GetInstance method for the Singleton design pattern
     * @return DatahandlerJSON object
     */
    public static DatahandlerJSON getDatahandlerJSONInstance() {
        if (instance == null) {
            instance = new DatahandlerJSON();
        }
        return instance;
    }

    /**
     * Reads the companyJSON.json file and convert it with ObjectMapper
     * to Objects
     * @return Company object
     */
    public Company readCompanyJSON() {
        Company[] company;
        try {
            String path = Paths.get(companyJsonPath).toString();
            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper = new ObjectMapper();
            company = objectMapper.readValue(jsonData, Company[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return company[0];
    }

    /**
     * Write to the companyJSON.json file (save changes made in the GUI)
     * @param company a Company object
     */
    protected void writeCompanyJSON(Company company) {
        List<Company> list = new ArrayList<>();
        list.add(company);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        try {
            fileOutputStream = new FileOutputStream(Paths.get(companyJsonPath).toString());
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, list);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads the companyJSON.json file and convert it with ObjectMapper
     * to Objects
     * @return Fascade object
     */
    public Fascade readFascadeJSON() {
        Fascade[] fascade;
        try {
            String path = Paths.get(fascadeJsonPath).toString();
            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper = new ObjectMapper();
            fascade = objectMapper.readValue(jsonData, Fascade[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fascade[0];
    }

    /**
     * Write to the fascadeJSON.json file (save changes made in the GUI)
     * @param fascade a Fascade object
     */
    protected void writeFascadeJSON(Fascade fascade) {
        List<Fascade> list = new ArrayList<>();
        list.add(fascade);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        try {
            fileOutputStream = new FileOutputStream(Paths.get(fascadeJsonPath).toString());
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, list);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

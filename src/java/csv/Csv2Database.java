package csv;

import ejd.JdbcHelper;
import java.io.*;
import java.util.*;

public class Csv2Database {

    private ArrayList<Person> parsedPersons;
    private ArrayList<Person> addedPersons;
    JdbcHelper jdbc;

    public Csv2Database() {
        parsedPersons = new ArrayList<Person>();
        addedPersons = new ArrayList<Person>();
        jdbc = new JdbcHelper();
    }

    public ArrayList<Person> readCsv(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
        String line;
            while((line = reader.readLine()) != null)
            {
                //TODO parse string to Person
                parsedPersons.add(Person.toPerson(line));
            }
        return parsedPersons;
    }

    public ArrayList<Person> addPersons(ArrayList<Person> persons) throws IOException {
        return addedPersons;
    }
}

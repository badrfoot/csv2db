package csv;

import ejd.JdbcHelper;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Csv2Database {

    private static final String _db = "jdbc:mysql://localhost/ejd";
    private static final String _user = "root";
    private static final String _pass = "not4you";

    private ArrayList<Person> parsedPersons;
    private ArrayList<Person> addedPersons;
    JdbcHelper jdbc;

    public Csv2Database() {
        parsedPersons = new ArrayList<Person>();
        addedPersons = new ArrayList<Person>();
        jdbc = new JdbcHelper();
    }

    public ArrayList<Person> readCsv(InputStream is) throws IOException {

        //reset before process
        parsedPersons.clear();

        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        BufferedReader reader = null;
        Person a = new Person();
        try {
            reader = new BufferedReader(new InputStreamReader(is));

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            //if(reader != null)
            reader.close();

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        int count = lines.size();
        for (int i = 1; i < count; i++) {
            // split
            String[] tokens = lines.get(i).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            System.out.println(i + ": " + lines.get(i));

            // number of columns must be 11
            if (tokens.length != 11) {
                continue;
            }

            // trim " off each token
            for (int j = 0; j < tokens.length; j++) {
                tokens[j] = tokens[j].replaceAll("^\"|\"$", "");
            }

            // add to the array
            Person person = new Person();

            person.setFirstName(tokens[0]);
            person.setLastName(tokens[1]);
            person.setCompanyName(tokens[2]);
            person.setAddress(tokens[3]);
            person.setCity(tokens[4]);
            person.setProvince(tokens[5]);
            person.setPostal(tokens[6]);
            person.setPhone1(tokens[7]);
            person.setPhone2(tokens[8]);
            person.setEmail(tokens[9]);
            person.setWeb(tokens[10]);

            parsedPersons.add(person);
        }
        // DEBUG
        System.out.println(parsedPersons.size());
        return parsedPersons;
    }

    public ArrayList<Person> addPersons(ArrayList<Person> persons) throws IOException {
        addedPersons.clear();
        ArrayList<Person> existingPersons = getPersonsFromDatabase();
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            if (isPersonExist(existingPersons, p)) {
                continue;
            }

            jdbc.connect(_db, _user, _pass);

            String sql = "insert into Person(firstName, lastName, companyName, address, city, province, postal, phone1, phone2, email, web)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";
            ArrayList<Object> params = new ArrayList<Object>();

            params.add(p.getFirstName());
            params.add(p.getLastName());
            params.add(p.getCompanyName());
            params.add(p.getAddress());
            params.add(p.getCity());
            params.add(p.getProvince());
            params.add(p.getPostal());
            params.add(p.getPhone1());
            params.add(p.getPhone2());
            params.add(p.getEmail());
            params.add(p.getWeb());

            int result = jdbc.update(sql, params);
            if (result != -1) {
                addedPersons.add(p);
            }
        }

        jdbc.disconnect();
        return addedPersons;
    }

    public ArrayList<Person> getPersonsFromDatabase() {
        ArrayList<Person> persons = new ArrayList<Person>();

        jdbc.connect(_db, _user, _pass);

        String sql = "select * from Person";

        try {
            ResultSet res = jdbc.query(sql);

            while (res.next()) {
                Person a = new Person();
                a.setFirstName(res.getString(2));
                a.setLastName(res.getString(3));
                a.setCompanyName(res.getString(4));
                a.setAddress(res.getString(5));
                a.setCity(res.getString(6));
                a.setProvince(res.getString(7));
                a.setPostal(res.getString(8));
                a.setPhone1(res.getString(9));
                a.setPhone2(res.getString(10));
                a.setEmail(res.getString(11));
                a.setWeb(res.getString(12));

                persons.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + ": " + e.getMessage());
        }

        jdbc.disconnect();

        return persons;
    }

    private boolean isPersonExist(ArrayList<Person> Ps, Person p) {

        for (int i = 0; i < Ps.size(); i++) {
            Person p2 = Ps.get(i);
            if (p.equals(p2)) {
                return true;
            }
        }
        return false;
    }
}
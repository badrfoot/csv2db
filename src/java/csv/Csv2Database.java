package csv;

import ejd.JdbcHelper;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Csv2Database {
    
    private static final String _db = "jdbc:mysql:localhost:3306/ejd";
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
        try{
            reader = new BufferedReader(new InputStreamReader(is));
            
            while((line = reader.readLine()) != null)
            {
                lines.add(line);
            }
            //if(reader != null)
            reader.close();
            System.out.println(lines.size());
            System.out.println(a);
        }catch(Exception e){
            System.out.println("[ERROR] " + e.getMessage());
        }
        
        int count = lines.size();
        for(int i=0;i<count;i++){
            a = a.toPerson(line);
            parsedPersons.add(a);
        }
        
        System.out.println(parsedPersons.size());
        return parsedPersons;
    }

    public ArrayList<Person> addPersons(ArrayList<Person> persons) throws IOException {
        addedPersons.clear();
        ArrayList<Person> existingPersons = getPersonsFromDatabase();
        for(int i=0; i<persons.size();i++){
            Person p = persons.get(i);
            if(isPersonExist(existingPersons, p))
                    continue;
            
            jdbc.connect(_db, _user, _pass);
            
            String sql = "insert into Person(firstName, lastName, companyName, address, city, province, postal, phone1, phone2, email, web)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";
            ArrayList<Object> params = new ArrayList<Object>();
            
            params.add(p.getFirstName());
            params.add(p.getLastName());
            params.add(p.getCompanyName());
            params.add(p.getAddress());
            params.add(p.getCity());
            params.add(p.getPostal());
            params.add(p.getPostal());
            params.add(p.getPhone1());
            params.add(p.getPhone2());
            params.add(p.getEmail());
            params.add(p.getWeb());
            
            int result = jdbc.update(sql, params);
            if(result != -1)
                addedPersons.add(p);
        }    
        
        jdbc.disconnect();
        return addedPersons;
    }
    
    public ArrayList<Person> getPersonsFromDatabase(){
        ArrayList<Person> persons = new ArrayList<Person>();
        
        jdbc.connect(_db, _user, _pass);
        
        String sql = "select * from Person";
        
        try{
            ResultSet res = jdbc.query(sql);
            while(res.next()){
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
        }catch(SQLException e){
            System.out.println(e.getSQLState() + ": " + e.getMessage());
        }
        
        jdbc.disconnect();
        
        return persons;
    }
    
    private boolean isPersonExist(ArrayList<Person> Ps, Person p){
        
        for(int i=0;i<Ps.size();i++){
            Person p2 = Ps.get(i);
            if(p.equals(p2))
        
        return true;
        }
    return false;
    }
}

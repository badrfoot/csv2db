package csv;

import java.util.*;

public class Person {

    private String firstName, lastName, companyName, address, city, province, postal, phone1, phone2, email, web;

    public Person() {
    }

    //adding constructor with all the parameters to create new person, should be useful
    public Person(String firstName, String lastName, String companyName, String address, String city, String province,
            String postal, String phone1, String phone2, String email, String web) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postal = postal;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.web = web;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public boolean equals(Person o) {
        if (!(o instanceof Person)) {
            return false;
        }
        if (this == o) {
            return true;
        }

        if (firstName.equals(o.getFirstName()) && lastName.equals(o.getLastName())
                && companyName.equals(o.getCompanyName()) && address.equals(o.getAddress())
                && city.equals(o.getCity()) && province.equals(o.getProvince())
                && postal.equals(o.getPostal()) && phone1.equals(o.getPhone1())
                && phone2.equals(o.getPhone2()) && email.equals(o.getEmail()) && web.equals(o.getWeb())) {
            return true;
        } else {
            return false;
        }
    }

    
    //in theory allows to parse string to the person object
    public Person toPerson(String s){
        ArrayList<String> a = null;
        String[] tokens = s.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for(String t : tokens) {
            a.add(t);
        }
        Person p = new Person(a.get(0), a.get(1), a.get(2), a.get(3), a.get(4), a.get(5),
        a.get(6), a.get(7), a.get(8), a.get(9), a.get(10));
        return p;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person)) // ignore null or non-Person obj
        {
            return false;
        }
        if (this == o) // ignore itself
        {
            return true;
        }
        // compare all member fields if they are equals
        Person p = (Person) o;
        if (firstName.equals(p.getFirstName()) && lastName.equals(p.getLastName())
                && companyName.equals(p.getLastName()) && address.equals(p.getLastName()) && city.equals(p.getLastName())
                && province.equals(p.getLastName()) && postal.equals(p.getLastName()) && phone1.equals(p.getLastName())
                && phone2.equals(p.getLastName()) && email.equals(p.getLastName()) && web.equals(p.getLastName())) {
            return true;
        } else {
            return false;
        }
    }
}

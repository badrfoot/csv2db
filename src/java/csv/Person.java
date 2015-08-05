/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csv;

/**
 *
 * @author bass0n
 */
public class Person {
    private String firstName, lastName, companyName, address, city, province, postal, phone1, phone2, email, web;

    public Person() {
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
    
    public boolean equals(Person o){
        if(!(o instanceof Person))
            return false;
        if (this==o)
            return true;
        
        if(firstName.equals(o.getFirstName()) && lastName.equals(o.getLastName()) &&
                companyName.equals(o.getCompanyName()) && address.equals(o.getAddress()) &&
                city.equals(o.getCity()) && province.equals(o.getProvince()) &&
                postal.equals(o.getPostal()) && phone1.equals(o.getPhone1()) &&
                phone2.equals(o.getPhone2()) && email.equals(o.getEmail()) && web.equals(o.getWeb()))
            return true;
            else
            return false;
        
    }
}

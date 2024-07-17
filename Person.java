// Person.java
public class Person {
    // Fields to store person information
    private String name;
    private  String surname;
    private  String email;

    // Constructor to creat a person object with initial details
    public Person(String name,String surname, String email){
        setName(name);
        setSurname(surname);
        setEmail(email);
    }

    // Getter and Setter for accessing person information
    public String getName(){
        return name;
    }

    public String getSurname() {

        return surname;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Print person information to the console
    public void printPersonInfo(){
        System.out.println("Name: "+ getName());
        System.out.println("Surname: "+ getSurname());
        System.out.println("Email: "+ getEmail());
    }
}
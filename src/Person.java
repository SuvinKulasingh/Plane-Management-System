public class Person {
    // initializing variables
     private String name, surname, email;

    public Person(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }


// all getters and setters for class person
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getSurname() {
        return surname;
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


    // method for print person information
    public void print_info(){
        System.out.println("Name: "+name);
        System.out.println("Surname: "+surname);
        System.out.println("Email: "+email);
    }

}

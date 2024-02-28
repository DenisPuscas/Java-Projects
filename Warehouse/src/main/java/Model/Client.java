package Model;

public class Client {
    /**
     * Id-ul clientului, se incrementeaza automat
     * la adaugarea unui client nou in baza de date
     * si nu poate fi modificat
     */
    private int id;
    /**
     * Numele poate sa fie format doar din litere
     * si sa contina cate un spatiu intre cuvinte
     */
    private String name;
    /**
     * Numarul de telefon este format doar din cifre
     * si poate incepe cu semnul +
     */
    private String phone;
    /**
     * Adresa de email trebuie sa respecte formatul
     * specific: nume@email.com.
     * Poate sa contina cifre dupa nume
     */
    private String email;
    /**
     * Adresa clientuli
     */
    private String address;

    public Client(){}

    public Client(int id, String name, String phone, String email, String address){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String toString(){
        return "client id:  " + id + ";\nname:  " + name + ";\nphone:  " + phone + ";\nemail:  " + email
                + ";\naddress:  " + address + ".\n";
    }
}

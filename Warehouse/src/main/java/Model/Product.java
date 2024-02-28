package Model;


public class Product {
    /**
     * Id-ul produsului, se incrementeaza automat
     * la adaugarea unui produs nou in baza de date
     * si nu poate fi modificat
     */
    private int id;
    /**
     * Numele poate sa fie format doar din litere
     * si sa contina cate un spatiu intre cuvinte
     */
    private String name;
    /**
     * Pretul este un float care nu poate sa
     * contina o valoare negativa
     */
    private float price;
    /**
     * Cantitatea produselor aflate in stoc, nu
     * poate contine o valoare negatica
     */
    private int quantity;

    public Product(){}

    public Product(int id, String name, float price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public float getPrice() {return price;}
    public void setPrice(float price) {this.price = price;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String toString(){
        return "product id:  " + id + ";\nname:  " + name + ";\nprice:  " + price + ";\nquantity:  " + quantity + ".\n";
    }

}

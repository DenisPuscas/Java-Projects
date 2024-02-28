package Model;

public class Order {
    /**
     * Id-ul comenzii, se incrementeaza automat
     * la adaugarea unei comenzi noi in baza de date
     * si nu poate fi modificat
     */
    private int id;
    /**
     * Id-ul clientului care a cumparat produsul respectiv
     */
    private int client_id;
    /**
     * Id-ul produsului care a fost cumparat de catre client
     */
    private int product_id;
    /**
     * Cantitatea produselor cumparate
     */
    private int quantity;
    /**
     * Pretul final rezultat in urma inmultirii cantitatii
     * produselor cumparate cu pretul unui singur produs
     */
    private float price;

    public Order(){}

    public Order(int id, int client_id, int product_id, int quantity, float price){
        this.id = id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getClient_id() {return client_id;}
    public void setClient_id(int client_id) {this.client_id = client_id;}
    public int getProduct_id() {return product_id;}
    public void setProduct_id(int product_id) {this.product_id = product_id;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public float getPrice() {return price;}
    public void setPrice(float price) {this.price = price;}

    public String toString(){
        return "id:  " + id + ";\nclient id:  " + client_id + ";\nproduct id:  " + product_id + ";\nquantity:  " + quantity + ";\nprice:  " + price + ".\n";
    }
}

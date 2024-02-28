package Model;

/**
 * Clasa Bill este o clasa imutabila.
 * Valorile atributelor nu se pot modifica
 * dupa ce obiectul a fost creat.
 *
 * @author Denis Puscas
 * @since 18-05-2023
 */
public class Bill {
    /**
     * Id-ul facturii, se incrementeaza automat
     * la adaugarea unei facturi noi in baza de date
     */
    private final int id;
    /**
     * Id-ul comenzii care a fost plasata
     * inainte de crearea facturii
     */
    private final int order_id;
    /**
     * Numele clientului care a facut comanda
     */
    private final String client_name;
    /**
     * Numarul de telefon al clientului necesar
     * contactarii acestuia in cazul unei probleme
     */
    private final String phone;
    /**
     * Numele produsului comandat
     */
    private final String product_name;
    /**
     * Cantitatea de produse comandate
     */
    private final int quantity;
    /**
     * Pretul unui produs de tipul celor comandate
     */
    private final float price;
    /**
     * Pretul total platit la final, rezultat din
     * inmultirea cantitatii cu pretul unui singur produs
     */
    private final float total;
    /**
     * Data si ora la care a fost plasata comanda
     */
    private final String date;

    public Bill(int id, int orderId, String clientName, String phone, String productName, int quantity, float price, float total, String date) {
        this.id = id;
        this.order_id = orderId;
        this.client_name = clientName;
        this.phone = phone;
        this.product_name = productName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.date = date;
    }

    public int getId() {return id;}
    public int getOrderId() {return order_id;}
    public String getClientName() {return client_name;}
    public String getPhone() {return phone;}
    public String getProductName() {return product_name;}
    public int getQuantity() {return quantity;}
    public float getPrice() {return price;}
    public float getTotal() {return total;}
    public String getDate() {return date;}
}

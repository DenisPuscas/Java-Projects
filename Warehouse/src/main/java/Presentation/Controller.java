package Presentation;

import BusinessLogic.BillBLL;
import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Model.Bill;
import Model.Client;
import Model.Order;
import Model.Product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clasa Controller gestioneaza ceea ce se intampla in interfata
 * si face legatura dinre clase.
 * Putem spune ca este creierul programului.
 *
 * @author Denis Puscas
 * @since 18-05-2023
 */
public class Controller {
    private final View view;
    private final ClientBLL clientBll;
    private final ProductBLL productBll;
    private final OrderBLL orderBll;
    private final BillBLL billBLL;

    public Controller(View view){
        this.view = view;
        this.clientBll = new ClientBLL();
        this.productBll = new ProductBLL();
        this.orderBll = new OrderBLL();
        this.billBLL = new BillBLL();
        view.addClientListener(e -> {view.setMainButton(0); view.mainButton();});
        view.addOrderListener(e -> {view.setMainButton(2); view.mainButton();});
        view.addProductListener(e -> {view.setMainButton(1); view.mainButton();});
        view.addBackListener(new BackListener());
        view.addAddListener(new AddListener());
        view.addEditListener(new EditListener());
        view.addDeleteListener(new DeleteListener());
        view.addFindListener(new FindListener());
        view.addViewAllListener(new ViewAllListener());
        view.addApplyListener(new ApplyListener());
        view.addBillListener(new BillListener());
    }

    /**
     * Ascultator pentru butonul back.
     */
    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.getLeftPanel().setSelected(0);
            view.getLeftPanel().setButtonsFalse();
            view.getLeftPanel().getReverseTimer().start();
            view.getRightPanel().setInputPanelFalse();
            view.getRightPanel().getReverseTimer().start();
            view.setTextFieldNull();
        }
    }

    /**
     * Ascultator pentru butonul add.
     */
    class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.getLeftPanel().setSelected(1);
            view.setAction(0);
            if (view.getMainButton() == 2){
                view.createContentPanel(clientBll.getTableData(), productBll.getTableData());
            } else {
                view.createContentPanel(null, null);
            }
        }
    }

    /**
     * Ascultator pentru butonul edit.
     */
    class EditListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.getLeftPanel().setSelected(2);
            view.setAction(1);
            view.createContentPanel(null, null);
        }
    }

    /**
     * Ascultator pentru butonul bill.
     */
    class BillListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.getLeftPanel().setSelected(1);
            view.setAction(5);
            view.createContentPanel(billBLL.getTableData(), null);
        }
    }

    /**
     * Ascultator pentru butonul delete.
     */
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.getLeftPanel().setSelected(3);
            view.setAction(2);
            view.createContentPanel(null, null);
            if (view.getAnswer() == 0){
                if (view.getMainButton() == 0){
                    clientBll.deleteClient(clientBll.findClientById(Integer.parseInt("" + view.getClientTable().getValueAt(
                            view.getClientTable().getSelectedRow(), 0))));
                } else if (view.getMainButton() == 1){
                    productBll.deleteProduct(productBll.findProductById(Integer.parseInt("" + view.getProductTable().getValueAt(
                            view.getProductTable().getSelectedRow(), 0))));
                } else if (view.getMainButton() == 2){
                    orderBll.deleteOrder(orderBll.findOrderById(Integer.parseInt(view.getOrderTable().getValueAt(
                            view.getOrderTable().getSelectedRow(), 0).toString())));
                }
            }
            view.getViewAllBtn().doClick();
        }
    }

    /**
     * Ascultator pentru butonul find.
     */
    class FindListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.getLeftPanel().setSelected(4);
            view.setAction(3);
            view.createContentPanel(null, null);
            if (view.getAnswer() == 0){
                if (view.getMainButton() == 0){
                    view.showClient(clientBll.findClientById(Integer.parseInt("" + view.getClientTable().getValueAt(
                            view.getClientTable().getSelectedRow(), 0))).toString());
                } else if (view.getMainButton() == 1){
                    view.showProduct(productBll.findProductById(Integer.parseInt("" + view.getProductTable().getValueAt(
                            view.getProductTable().getSelectedRow(), 0))).toString());
                } else if (view.getMainButton() == 2){
                    int clientId = Integer.parseInt(view.getOrderTable().getValueAt(view.getOrderTable().getSelectedRow(), 1).toString());
                    int productId = Integer.parseInt(view.getOrderTable().getValueAt(view.getOrderTable().getSelectedRow(), 2).toString());
                    Client c = clientBll.findClientById(clientId);
                    Product p = productBll.findProductById(productId);
                    view.showOrder("order id: " + view.getOrderTable().getValueAt(view.getOrderTable().getSelectedRow(), 0) +
                            "\n" + ((c == null)? ("client id: " + clientId) : ("\n" + c)) +
                            "\n" + ((p == null)? ("product id: " + productId) : p.toString()) +
                            "\nquantity: " + view.getOrderTable().getValueAt(view.getOrderTable().getSelectedRow(), 3) +
                            "\nprice: " + view.getOrderTable().getValueAt(view.getOrderTable().getSelectedRow(), 4));
                }
            }
            view.getViewAllBtn().doClick();
        }
    }

    /**
     * Ascultator pentru butonul view.
     */
    class ViewAllListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.getLeftPanel().setSelected(5);
            view.setAction(4);
            if (view.getMainButton() == 0){
                view.createContentPanel(clientBll.getTableData(), null);
            } else if (view.getMainButton() == 1){
                view.createContentPanel(productBll.getTableData(), null);
            } else if (view.getMainButton() == 2) {
                view.createContentPanel(orderBll.getTableData(), null);
            }
        }
    }

    /**
     * Ascultator pentru butonul apply.
     */
    class ApplyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (view.getMainButton() == 0){
                if (view.getAction() == 0){
                    try {
                        clientBll.insertClient(new Client(clientBll.findClientLastId() + 1,
                                view.getTextField2Text(), view.getTextField3Text(),
                                view.getTextField4Text(), view.getTextField5Text()));
                        view.getViewAllBtn().doClick();
                    } catch (Exception ex){
                        view.error(ex.getMessage());
                    }
                }else if(view.getAction() == 1){
                    try {
                        int id;
                        try {id = Integer.parseInt(view.getTextField1Text());
                        }catch (Exception ex){throw new Exception("This is not a valid id!");}

                        clientBll.updateClient(new Client(id, view.getTextField2Text(), view.getTextField3Text(),
                                view.getTextField4Text(), view.getTextField5Text()));
                        view.getViewAllBtn().doClick();
                    } catch (Exception ex){
                        view.error(ex.getMessage());
                    }
                }
            } else if (view.getMainButton() == 1){
                if (view.getAction() == 0){
                    try {
                        float price;
                        int quantity;

                        try {price = Float.parseFloat(view.getTextField3Text());
                        }catch (Exception ex){throw new Exception("This is not a valid price");}
                        try {quantity = Integer.parseInt(view.getTextField4Text());
                        }catch (Exception ex){throw new Exception("This is not a valid quantity");}

                        productBll.insertProduct(new Product(productBll.findProductLastId() + 1,
                                view.getTextField2Text(), price, quantity));
                        view.getViewAllBtn().doClick();
                    } catch (Exception ex) {
                        view.error(ex.getMessage());
                    }
                }else if(view.getAction() == 1){
                    try {
                        float price;
                        int id, quantity;

                        try {id = Integer.parseInt(view.getTextField1Text());
                        }catch (Exception ex){throw new Exception("This is not a valid id!");}
                        try {price = Float.parseFloat(view.getTextField3Text());
                        }catch (Exception ex){throw new Exception("This is not a valid price!");}
                        try {quantity = Integer.parseInt(view.getTextField4Text());
                        }catch (Exception ex){throw new Exception("This is not a valid quantity!");}

                        productBll.updateProduct(new Product(id, view.getTextField2Text(), price, quantity));
                        view.getViewAllBtn().doClick();
                    } catch (Exception ex) {
                        view.error(ex.getMessage());
                    }
                }
            } else if (view.getMainButton() == 2){
                try {
                    if (view.getClientTable().getSelectedRow() == -1) { throw new Exception("No client selected");}
                    if (view.getProductTable().getSelectedRow() == -1) { throw new Exception("No product selected");}

                    int orderId   = orderBll.findOrderLastId() + 1;
                    int quantity  = Integer.parseInt(view.getTextFieldShortText());
                    int clientId  = Integer.parseInt(view.getClientTable().getValueAt(view.getClientTable().getSelectedRow(), 0).toString());
                    int productId = Integer.parseInt(view.getProductTable().getValueAt(view.getProductTable().getSelectedRow(), 0).toString());
                    float price   = Float.parseFloat(view.getProductTable().getValueAt(view.getProductTable().getSelectedRow(), 2).toString());
                    String clientName  = view.getClientTable().getValueAt(view.getClientTable().getSelectedRow(), 1).toString();
                    String phone       = view.getClientTable().getValueAt(view.getClientTable().getSelectedRow(), 2).toString();
                    String productName = view.getProductTable().getValueAt(view.getProductTable().getSelectedRow(), 1).toString();

                    Product p = productBll.findProductById(productId);
                    if (quantity > p.getQuantity()){throw new NumberFormatException("There are not enough products!");}

                    orderBll.insertOrder(new Order(orderId, clientId, productId, quantity, price * quantity));
                    billBLL.insertBill(new Bill(billBLL.findBillLastId() + 1, orderId, clientName, phone, productName, quantity, price,
                            price * quantity, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss"))));
                    p.setQuantity(p.getQuantity() - quantity);
                    productBll.updateProduct(p);
                    view.getAddBtn().doClick();
                } catch (NumberFormatException nfe){
                    view.error(nfe.getMessage());
                } catch (Exception ex) {
                    view.warning(ex.getMessage());
                }

            }
        }
    }

    /**
     * Metoda main se ocupa cu crearea instantelor view si
     * controller si realizarea lagaturii dintre acestea.
     * @param args Nefolosit.
     * o eroare de acces la baza de date sau alte erori.
     */
    public static void main(String[] args) {
        View view = new View();
        new Controller(view);
    }


}

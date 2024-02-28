package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showConfirmDialog;

/**
 * Clasa View se ocupa de crearea interfetei si interactiunile cu aceasta
 *
 * @author Denis Puscas
 * @since 18-05-2023
 */
public class View extends JFrame {
    private final JButton clientBtn  = new JButton("Client");
    private final JButton orderBtn   = new JButton("Order");
    private final JButton productBtn = new JButton("Product");
    private final JButton addBtn     = new JButton("Add");
    private final JButton editBtn    = new JButton("Edit");
    private final JButton deleteBtn  = new JButton("Delete");
    private final JButton findBtn    = new JButton("Find");
    private final JButton viewAllBtn = new JButton("View all");
    private final JButton backBtn    = new JButton("Back");
    private final JButton applyBtn   = new JButton("Apply");
    private final JButton billBtn    = new JButton("Bill");
    private final JTextField textField1 = new JTextField(15);
    private final JTextField textField2 = new JTextField(15);
    private final JTextField textField3 = new JTextField(15);
    private final JTextField textField4 = new JTextField(15);
    private final JTextField textField5 = new JTextField(15);
    private final JTextField textFieldShort = new JTextField("1", 3);
    private final JLabel labelId       = new JLabel("ID:           ");
    private final JLabel labelName     = new JLabel("Name:     ");
    private final JLabel labelPhone    = new JLabel("Phone:    ");
    private final JLabel labelEmail    = new JLabel("Email:     ");
    private final JLabel labelAddress  = new JLabel("Address: ");
    private final JLabel labelPrice    = new JLabel("Price:      ");
    private final JLabel labelQuantity = new JLabel("Quantity: ");
    private final JLabel labelClients  = new JLabel("CLIENTS");
    private final JLabel labelProducts = new JLabel("PRODUCTS");
    private final JLabel labelOrders   = new JLabel("ORDERS");
    private final JLabel labelLogs     = new JLabel("LOGS");
    private final Color darkColor  = new Color(0x2b2b2b);
    private final Color midColor   = new Color(0x3C3F41);
    private final Color lightColor = new Color(0xbbbbbb);
    private final LeftPanel left;
    private final RightPanel right;
    private final JPanel contentPanel;
    private final Font fo2;
    private JTable clientTable;
    private JTable productTable;
    private JTable orderTable;
    private JTable billTable;
    private int mainButton = 0;
    private int action = 0;
    private int answer = 1;
    public View() {
        List<String> lst = new ArrayList<>(10);
        List<JButton> btnsList = new ArrayList<>();
        List<JLabel> labelList = new ArrayList<>();
        List<JTextField> textFieldList = new ArrayList<>();
        btnsList.add(addBtn);
        btnsList.add(editBtn);
        btnsList.add(findBtn);
        btnsList.add(backBtn);
        btnsList.add(billBtn);
        btnsList.add(orderBtn);
        btnsList.add(applyBtn);
        btnsList.add(clientBtn);
        btnsList.add(deleteBtn);
        btnsList.add(productBtn);
        btnsList.add(viewAllBtn);
        labelList.add(labelId);
        labelList.add(labelName);
        labelList.add(labelLogs);
        labelList.add(labelPhone);
        labelList.add(labelEmail);
        labelList.add(labelPrice);
        labelList.add(labelOrders);
        labelList.add(labelAddress);
        labelList.add(labelClients);
        labelList.add(labelQuantity);
        labelList.add(labelProducts);
        textFieldList.add(textField1);
        textFieldList.add(textField2);
        textFieldList.add(textField3);
        textFieldList.add(textField4);
        textFieldList.add(textField5);
        textFieldList.add(textFieldShort);
        textField1.setEditable(false);
        Font fo = new Font(clientBtn.getFont().toString(), Font.BOLD, 24);
        fo2 = new Font(clientBtn.getFont().toString(), Font.PLAIN, 16);

        for (JButton btn : btnsList) {
            btn.setFont(fo);
            btn.setBackground(lightColor);
            btn.setPreferredSize(new Dimension(130, 50));
        }
        for (JLabel lbl : labelList) {
            lbl.setFont(fo);
            lbl.setForeground(lightColor);
        }
        for (JTextField tf : textFieldList) {
            tf.setFont(fo);
            tf.setBackground(lightColor);
        }

        JPanel btnsPanel = new JPanel();
        btnsPanel.setPreferredSize(new Dimension(180, 335));
        btnsPanel.setVisible(false);
        btnsPanel.setBackground(midColor);
        btnsPanel.add(addBtn);
        btnsPanel.add(editBtn);
        btnsPanel.add(billBtn);
        btnsPanel.add(deleteBtn);
        btnsPanel.add(findBtn);
        btnsPanel.add(viewAllBtn);
        btnsPanel.add(backBtn);
        billBtn.setVisible(false);

        left = new LeftPanel();
        left.add(clientBtn);
        left.add(btnsPanel);

        JPanel rightButtons = new JPanel();
        rightButtons.add(orderBtn);
        rightButtons.add(Box.createHorizontalStrut(152));
        rightButtons.add(productBtn);
        rightButtons.setPreferredSize(new Dimension(430, 50));
        rightButtons.setBackground(darkColor);
        rightButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        contentPanel = new JPanel();
        contentPanel.setBackground(midColor);
        contentPanel.setPreferredSize(new Dimension(545, 490));
        contentPanel.setVisible(false);
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        right = new RightPanel();
        right.add(rightButtons);
        right.add(contentPanel);

        JPanel content = new JPanel();
        content.add(left);
        content.add(right);
        content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.setVisible(true);
        this.setContentPane(content);
        this.setSize(900, 600);
        this.setTitle("Warehouse");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableInitialization();
    }

    public void mainButton(){
        clientBtn.setVisible(false);
        orderBtn.setVisible(false);
        productBtn.setVisible(false);
        right.getComponent(0).setVisible(false);
        contentPanel.removeAll();
        if (mainButton == 2){
            editBtn.setVisible(false);
            billBtn.setVisible(true);
        }else {
            editBtn.setVisible(true);
            billBtn.setVisible(false);
        }
        left.getTimer().start();
        right.getTimer().start();
    }

    /**
     * Aceasta metoda este folosita pentru initializarea tabelelor.
     */
    private void tableInitialization(){
        clientTable = new JTable();
        clientTable.setRowSelectionAllowed(true);
        clientTable.getSelectionModel().addListSelectionListener(e -> {
            int row = clientTable.getSelectedRow();
            if (row != -1) {
                textField1.setText("" + clientTable.getValueAt(row, 0));
                textField2.setText("" + clientTable.getValueAt(row, 1));
                textField3.setText("" + clientTable.getValueAt(row, 2));
                textField4.setText("" + clientTable.getValueAt(row, 3));
                textField5.setText("" + clientTable.getValueAt(row, 4));
            }
        });
        clientTable.setPreferredSize(new Dimension(545, 430));
        clientTable.setFont(fo2);
        clientTable.setBackground(midColor);
        clientTable.setForeground(lightColor);
        clientTable.setRowHeight(20);

        productTable = new JTable();
        productTable.setRowSelectionAllowed(true);
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row != -1) {
                textField1.setText("" + productTable.getValueAt(row, 0));
                textField2.setText("" + productTable.getValueAt(row, 1));
                textField3.setText("" + productTable.getValueAt(row, 2));
                textField4.setText("" + productTable.getValueAt(row, 3));
            }
        });
        productTable.setPreferredSize(new Dimension(545, 430));
        productTable.setFont(fo2);
        productTable.setBackground(midColor);
        productTable.setForeground(lightColor);
        productTable.setRowHeight(20);

        orderTable = new JTable();
        orderTable.setRowSelectionAllowed(true);
        orderTable.setPreferredSize(new Dimension(545, 430));
        orderTable.setFont(fo2);
        orderTable.setBackground(midColor);
        orderTable.setForeground(lightColor);
        orderTable.setRowHeight(20);

        billTable = new JTable();
        billTable.setRowSelectionAllowed(true);
        billTable.getSelectionModel().addListSelectionListener(e -> {
            int row = billTable.getSelectedRow();
            if (row != -1) {
                showBill("bill_id:  " + billTable.getValueAt(billTable.getSelectedRow(), 0) +
                        "\norder_id:  " + billTable.getValueAt(billTable.getSelectedRow(), 1) +
                        "\nclient:  " + billTable.getValueAt(billTable.getSelectedRow(), 2) +
                        "\nphone:  " + billTable.getValueAt(billTable.getSelectedRow(), 3) +
                        "\nproduct:  " + billTable.getValueAt(billTable.getSelectedRow(), 4) +
                        "\nquantity:  " + billTable.getValueAt(billTable.getSelectedRow(), 5) +
                        "\nprice:  " + billTable.getValueAt(billTable.getSelectedRow(), 6) +
                        "\ntotal:  " + billTable.getValueAt(billTable.getSelectedRow(), 7) +
                        "\ndate:  " + billTable.getValueAt(billTable.getSelectedRow(), 8));
            }
        });
        billTable.setPreferredSize(new Dimension(545, 430));
        billTable.setFont(fo2);
        billTable.setBackground(midColor);
        billTable.setForeground(lightColor);
        billTable.setRowHeight(20);
    }

    /**
     * Creaza panourile care contin campurile de text si tabelele
     */
    public void createContentPanel(DefaultTableModel data1, DefaultTableModel data2){
        int nr = contentPanel.getComponentCount();
        if (nr > 0){
            for (int i = 0; i < nr; i++) {
                contentPanel.remove(0);
            }
        }
        if (mainButton == 0){
            if (action == 0 || action == 1){ // add, update client
                JPanel addClient1 = new JPanel();
                addClient1.add(labelId);
                addClient1.add(textField1);
                addClient1.setBackground(midColor);
                JPanel addClient2 = new JPanel();
                addClient2.add(labelName);
                addClient2.add(textField2);
                addClient2.setBackground(midColor);
                JPanel addClient3 = new JPanel();
                addClient3.add(labelPhone);
                addClient3.add(textField3);
                addClient3.setBackground(midColor);
                JPanel addClient4 = new JPanel();
                addClient4.add(labelEmail);
                addClient4.add(textField4);
                addClient4.setBackground(midColor);
                JPanel addClient5 = new JPanel();
                addClient5.add(labelAddress);
                addClient5.add(textField5);
                addClient5.setBackground(midColor);

                JPanel addClientPanel = new JPanel();
                if (action == 1)
                    addClientPanel.add(addClient1);
                addClientPanel.add(addClient2);
                addClientPanel.add(addClient3);
                addClientPanel.add(addClient4);
                addClientPanel.add(addClient5);
                addClientPanel.add(applyBtn);
                addClientPanel.setLayout(new BoxLayout(addClientPanel, BoxLayout.Y_AXIS));
                addClientPanel.setPreferredSize(new Dimension(545, 490));
                addClientPanel.setBackground(midColor);
                addClientPanel.setVisible(false);

                contentPanel.add(addClientPanel);
                addClientPanel.setVisible(true);
            } else if (action == 2) { // delete client
                if (clientTable.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(this,"No client selected","Alert",JOptionPane.WARNING_MESSAGE);
                } else {
                    answer = showConfirmDialog(this, "Are you sure you want to delete the client?");
                }
            } else if (action == 3) { // find client
                if (clientTable.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(this,"No client selected","Alert",JOptionPane.WARNING_MESSAGE);
                } else {
                    answer = 0;
                }
            }else if (action == 4){ // view all clients
                clientTable.setModel(data1);
                clientTable.getColumnModel().getColumn(0).setPreferredWidth(25);
                clientTable.getColumnModel().getColumn(1).setPreferredWidth(140);
                clientTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                clientTable.getColumnModel().getColumn(3).setPreferredWidth(160);
                clientTable.getColumnModel().getColumn(4).setPreferredWidth(130);

                JScrollPane scrollPane = new JScrollPane(clientTable);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrollPane.setPreferredSize(new Dimension(545, 450));
                scrollPane.setBackground(midColor);

                JPanel title = new JPanel();
                title.add(labelClients);
                title.setBackground(midColor);
                title.setPreferredSize(new Dimension(545, 40));
                title.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

                contentPanel.add(title);
                contentPanel.add(scrollPane);
                contentPanel.setVisible(false);
                contentPanel.setVisible(true);
            }
        }else if (mainButton == 1){
            if (action == 0 || action == 1){ // add, update product
                JPanel addProduct1 = new JPanel();
                addProduct1.add(labelId);
                addProduct1.add(textField1);
                addProduct1.setBackground(midColor);
                JPanel addProduct2 = new JPanel();
                addProduct2.add(labelName);
                addProduct2.add(textField2);
                addProduct2.setBackground(midColor);
                JPanel addProduct3 = new JPanel();
                addProduct3.add(labelPrice);
                addProduct3.add(textField3);
                addProduct3.setBackground(midColor);
                JPanel addProduct4 = new JPanel();
                addProduct4.add(labelQuantity);
                addProduct4.add(textField4);
                addProduct4.setBackground(midColor);

                JPanel addProductPanel = new JPanel();
                if (action == 1)
                    addProductPanel.add(addProduct1);
                addProductPanel.add(addProduct2);
                addProductPanel.add(addProduct3);
                addProductPanel.add(addProduct4);
                addProductPanel.add(applyBtn);
                addProductPanel.setLayout(new BoxLayout(addProductPanel, BoxLayout.Y_AXIS));
                addProductPanel.setPreferredSize(new Dimension(545, 490));
                addProductPanel.setBackground(midColor);
                addProductPanel.setVisible(false);

                contentPanel.add(addProductPanel);
                addProductPanel.setVisible(true);

            } else if (action == 2) { // delete product
                if (productTable.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(this,"No product selected","Alert",JOptionPane.WARNING_MESSAGE);
                } else {
                    answer = showConfirmDialog(this, "Are you sure you want to delete the product?");
                }
            } else if (action == 3) { // find client
                if (productTable.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(this,"No product selected","Alert",JOptionPane.WARNING_MESSAGE);
                } else {
                    answer = 0;
                }
            }else if (action == 4){
                productTable.setModel(data1);
                productTable.getColumnModel().getColumn(0).setPreferredWidth(45);
                productTable.getColumnModel().getColumn(1).setPreferredWidth(200);
                productTable.getColumnModel().getColumn(2).setPreferredWidth(180);
                productTable.getColumnModel().getColumn(3).setPreferredWidth(130);

                JScrollPane scrollPane = new JScrollPane(productTable);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrollPane.setPreferredSize(new Dimension(545, 450));
                scrollPane.setBackground(midColor);

                JPanel title = new JPanel();
                title.add(labelProducts);
                title.setBackground(midColor);
                title.setPreferredSize(new Dimension(545, 40));
                title.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

                contentPanel.add(title);
                contentPanel.add(scrollPane);
                contentPanel.setVisible(false);
                contentPanel.setVisible(true);
            }
        }else if(mainButton == 2){
            if (action == 0){
                clientTable.setModel(data1);
                clientTable.getColumnModel().getColumn(0).setPreferredWidth(25);
                clientTable.getColumnModel().getColumn(1).setPreferredWidth(140);
                clientTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                clientTable.getColumnModel().getColumn(3).setPreferredWidth(160);
                clientTable.getColumnModel().getColumn(4).setPreferredWidth(130);

                JScrollPane scrollPaneClients = new JScrollPane(clientTable);
                scrollPaneClients.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrollPaneClients.setPreferredSize(new Dimension(545, 150));
                scrollPaneClients.setBackground(midColor);

                JPanel titleClient = new JPanel();
                titleClient.add(labelClients);
                titleClient.setBackground(midColor);
                titleClient.setPreferredSize(new Dimension(545, 40));
                titleClient.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

                productTable.setModel(data2);
                productTable.getColumnModel().getColumn(0).setPreferredWidth(45);
                productTable.getColumnModel().getColumn(1).setPreferredWidth(200);
                productTable.getColumnModel().getColumn(2).setPreferredWidth(180);
                productTable.getColumnModel().getColumn(3).setPreferredWidth(130);

                JScrollPane scrollPaneProducts = new JScrollPane(productTable);
                scrollPaneProducts.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrollPaneProducts.setPreferredSize(new Dimension(545, 150));
                scrollPaneProducts.setBackground(midColor);

                JPanel titleProduct = new JPanel();
                titleProduct.add(labelProducts);
                titleProduct.setBackground(midColor);
                titleProduct.setPreferredSize(new Dimension(545, 40));
                titleProduct.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

                JPanel setQuantity = new JPanel();
                setQuantity.add(labelQuantity);
                setQuantity.add(textFieldShort);
                setQuantity.add(Box.createHorizontalStrut(200));
                setQuantity.add(applyBtn);
                setQuantity.setBackground(midColor);

                JPanel addOrderPanel = new JPanel();
                addOrderPanel.add(titleClient);
                addOrderPanel.add(scrollPaneClients);
                addOrderPanel.add(Box.createVerticalStrut(30));
                addOrderPanel.add(titleProduct);
                addOrderPanel.add(scrollPaneProducts);
                addOrderPanel.add(Box.createVerticalStrut(30));
                addOrderPanel.add(setQuantity);
                addOrderPanel.setLayout(new BoxLayout(addOrderPanel, BoxLayout.Y_AXIS));
                addOrderPanel.setPreferredSize(new Dimension(545, 490));
                addOrderPanel.setBackground(midColor);
                addOrderPanel.setVisible(false);

                contentPanel.add(addOrderPanel);
                addOrderPanel.setVisible(true);
            }else if (action == 2){ // delete order
                if (orderTable.getSelectedRow() == -1){
                    warning("No order selected");
                } else {
                    answer = showConfirmDialog(this, "Are you sure you want to delete the order?");
                }
            }else if (action == 3){ // delete order
                if (orderTable.getSelectedRow() == -1){
                    warning("No order selected");
                } else {
                    answer = 0;
                }
            }else if (action == 4){ // view all orders
//
                orderTable.setModel(data1);
                orderTable.getColumnModel().getColumn(0).setPreferredWidth(45);
                orderTable.getColumnModel().getColumn(1).setPreferredWidth(125);
                orderTable.getColumnModel().getColumn(2).setPreferredWidth(125);
                orderTable.getColumnModel().getColumn(3).setPreferredWidth(125);
                orderTable.getColumnModel().getColumn(4).setPreferredWidth(125);

                JScrollPane scrollPane = new JScrollPane(orderTable);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrollPane.setPreferredSize(new Dimension(545, 450));
                scrollPane.setBackground(midColor);

                JPanel title = new JPanel();
                title.add(labelOrders);
                title.setBackground(midColor);
                title.setPreferredSize(new Dimension(545, 40));
                title.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

                contentPanel.add(title);
                contentPanel.add(scrollPane);
                contentPanel.setVisible(false);
                contentPanel.setVisible(true);

            } else if(action == 5){ // view logs
                billTable.setModel(data1);
                billTable.getColumnModel().getColumn(0).setPreferredWidth(30);
                billTable.getColumnModel().getColumn(1).setPreferredWidth(30);
                billTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                billTable.getColumnModel().getColumn(3).setPreferredWidth(90);
                billTable.getColumnModel().getColumn(4).setPreferredWidth(80);
                billTable.getColumnModel().getColumn(5).setPreferredWidth(30);
                billTable.getColumnModel().getColumn(6).setPreferredWidth(50);
                billTable.getColumnModel().getColumn(7).setPreferredWidth(50);
                billTable.getColumnModel().getColumn(8).setPreferredWidth(85);

                JScrollPane scrollPane = new JScrollPane(billTable);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                scrollPane.setPreferredSize(new Dimension(545, 450));
                scrollPane.setBackground(midColor);

                JPanel title = new JPanel();
                title.add(labelLogs);
                title.setBackground(midColor);
                title.setPreferredSize(new Dimension(545, 40));
                title.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

                contentPanel.add(title);
                contentPanel.add(scrollPane);
                contentPanel.setVisible(false);
                contentPanel.setVisible(true);
            }
        }
    }

    public void setTextFieldNull(){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }

    /**
     * Clasa care se ocupa cu animatiile panoului din stanga ferestrei
     */
    public class LeftPanel extends JPanel{
        private int pointX = 92;
        private int pointY = 255;
        private int width = 110;
        private int height = 50;
        private final Timer timer;
        private final Timer reverseTimer;
        private int selected = 0;
        public LeftPanel(){
            setBackground(darkColor);
            setPreferredSize(new Dimension(295, 600));
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 255));
            timer = new Timer(15, new TimerListener());
            reverseTimer = new Timer(15, new ReverseTimerListener());
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(midColor);
            g.fillRoundRect(pointX, pointY, width, height, 20, 20);
            switch (selected) {
                case 1 -> g.fillPolygon(new int[]{240, 295, 295, 240}, new int[]{120, 100, 185, 165}, 4);
                case 2 -> g.fillPolygon(new int[]{240, 295, 295, 240}, new int[]{175, 155, 240, 220}, 4);
                case 3 -> g.fillPolygon(new int[]{240, 295, 295, 240}, new int[]{230, 210, 295, 275}, 4);
                case 4 -> g.fillPolygon(new int[]{240, 295, 295, 240}, new int[]{285, 265, 350, 330}, 4);
                case 5 -> g.fillPolygon(new int[]{240, 295, 295, 240}, new int[]{340, 320, 405, 385}, 4);
                default -> g.fillPolygon(new int[]{0}, new int[]{0}, 0);
            }
        }

        public class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (pointY > 92){
                    pointY-=4;
                    pointX--;
                    width+=2;
                    height+=8;
                    repaint();
                }else {
                    setLayout(new FlowLayout(FlowLayout.CENTER, 0, 113));
                    setVisibleButtonsTrue();
                    timer.stop();
                }
            }
        }
        public class ReverseTimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (pointY < 255){
                    pointY+=4;
                    pointX++;
                    width-=2;
                    height-=8;
                    repaint();
                }else {
                    clientBtn.setVisible(true);
                    orderBtn.setVisible(true);
                    productBtn.setVisible(true);
                    reverseTimer.stop();
                }
            }
        }
        public void setVisibleButtonsTrue(){this.getComponent(1).setVisible(true);}
        public void setButtonsFalse(){this.getComponent(1).setVisible(false);
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 255));}
        public Timer getTimer(){return timer;}
        public Timer getReverseTimer(){return reverseTimer;}
        public void setSelected(int n){selected = n; repaint();}

    }

    /**
     * Clasa care se ocupa cu animatiile panoului din drepta ferestrei
     */
    public class RightPanel extends JPanel{
        private int pointX1 = 85;
        private int pointY1 = 255;
        private int pointX2 = 377;
        private int pointY2 = 255;
        private int width = 130;
        private int height = 50;
        private final Timer timer;
        private final Timer reverseTimer;
        public RightPanel(){
            setBackground(darkColor);
            setPreferredSize(new Dimension(590, 600));
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 255));
            timer = new Timer(1, new TimerListener());
            reverseTimer = new Timer(1, new ReverseTimerListener());
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(midColor);
            g.fillRect(pointX1, pointY1, width, height);
            g.fillRect(pointX2, pointY2, width, height);
        }

        public class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (pointY1 > 30){
                    pointY1-=4;
                    pointX1-=2;
                    pointY2-=4;
                    pointX2-=2;
                    width+=3;
                    height+=8;
                    repaint();
                }else {
                    setLayout(new FlowLayout(FlowLayout.LEFT, 10, 35));
                    getComponent(1).setVisible(true);
                    if (contentPanel.getComponentCount() != 0) {
                        contentPanel.remove(0);
                    }
                    timer.stop();
                }
            }
        }
        public class ReverseTimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (pointY1 < 255){
                    pointY1+=4;
                    pointX1+=2;
                    pointY2+=4;
                    pointX2+=2;
                    width-=3;
                    height-=8;
                    repaint();
                }else {
                    right.getComponent(0).setVisible(true);
                    reverseTimer.stop();
                }
            }
        }
        public void setInputPanelFalse(){this.getComponent(1).setVisible(false);
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 255));}
        public Timer getTimer(){return timer;}
        public Timer getReverseTimer(){return reverseTimer;}

    }

    public void addClientListener(ActionListener l) {clientBtn.addActionListener(l);}
    public void addOrderListener(ActionListener l) {orderBtn.addActionListener(l);}
    public void addProductListener(ActionListener l) {productBtn.addActionListener(l);}
    public void addAddListener(ActionListener l) {addBtn.addActionListener(l);}
    public void addEditListener(ActionListener l) {editBtn.addActionListener(l);}
    public void addDeleteListener(ActionListener l) {deleteBtn.addActionListener(l);}
    public void addFindListener(ActionListener l) {findBtn.addActionListener(l);}
    public void addViewAllListener(ActionListener l) {viewAllBtn.addActionListener(l);}
    public void addBackListener(ActionListener l) {backBtn.addActionListener(l);}
    public void addApplyListener(ActionListener l) {applyBtn.addActionListener(l);}
    public void addBillListener(ActionListener l) {billBtn.addActionListener(l);}
    public LeftPanel getLeftPanel(){return left;}
    public RightPanel getRightPanel(){return right;}
    public void setMainButton(int n){mainButton = n;}
    public int getMainButton(){return mainButton;}
    public int getAction(){return action;}
    public void setAction(int n){action = n;}
    public String getTextField1Text(){return textField1.getText();}
    public String getTextField2Text(){return textField2.getText();}
    public String getTextField3Text(){return textField3.getText();}
    public String getTextField4Text(){return textField4.getText();}
    public String getTextField5Text(){return textField5.getText();}
    public String getTextFieldShortText(){return textFieldShort.getText();}
    public JTable getClientTable(){return clientTable;}
    public JTable getProductTable(){return productTable;}
    public JTable getOrderTable(){return orderTable;}
    public JButton getAddBtn(){return addBtn;}
    public JButton getViewAllBtn(){return viewAllBtn;}
    public int getAnswer(){if (answer == 0){answer = 1; return 0;} return answer;}
    public void showClient(String s){JOptionPane.showMessageDialog(this, s, "Client", JOptionPane.PLAIN_MESSAGE);}
    public void showProduct(String s){JOptionPane.showMessageDialog(this, s, "Product", JOptionPane.PLAIN_MESSAGE);}
    public void showOrder(String s){JOptionPane.showMessageDialog(this, s, "Order", JOptionPane.PLAIN_MESSAGE);}
    public void showBill(String s){JOptionPane.showMessageDialog(this, s, "Bill", JOptionPane.PLAIN_MESSAGE);}
    public void error(String e){JOptionPane.showMessageDialog(this,e,"Error",JOptionPane.ERROR_MESSAGE);}
    public void warning(String e){JOptionPane.showMessageDialog(this,e,"Warning",JOptionPane.WARNING_MESSAGE);}
}

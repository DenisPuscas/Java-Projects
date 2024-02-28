package GraphicalUserInterface;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

public class View extends JFrame{
	private JTextField input1 	 = new JTextField("ex: -x3+2x2-x+10", 25);
    private JTextField input2    = new JTextField(25);
    private JTextField res     	 = new JTextField(25);
    private JButton    addBtn 	 = new JButton("+");
    private JButton    subtBtn 	 = new JButton("-");
    private JButton    multBtn 	 = new JButton("*");
    private JButton    divBtn 	 = new JButton("/");
    private JButton    derivBtn  = new JButton("Deriv");
    private JButton    integrBtn = new JButton("Integr");
    
    private Model model;
    
    public View(Model mod) {
        model = mod;
        res.setEditable(false);
        res.setText(model.getResult());
         
        Font fo1 = new Font("Serif", Font.BOLD, 25);
        Font fo2 = new Font("Serif", Font.BOLD, 20);
        Dimension dim = new Dimension(10, 60);
        Border border = BorderFactory.createEmptyBorder(0, 15, 0, 15);
        
        input1.setFont(fo1);
        input1.setBorder(border);
        input1.setPreferredSize(dim);
        input1.setForeground(Color.gray);
        
        input2.setFont(fo1);
        input2.setBorder(border);
        input2.setPreferredSize(dim);
        
        res.setFont(fo1);
        res.setBorder(border);
        res.setPreferredSize(dim);
        res.setBackground(Color.white);
        
        addBtn.setPreferredSize(dim);
        addBtn.setFont(fo2);
        addBtn.setBackground(new Color(204, 230, 255));
        subtBtn.setFont(fo2);
        subtBtn.setBackground(new Color(204, 230, 255));
        multBtn.setFont(fo2);
        multBtn.setBackground(new Color(204, 230, 255));
        divBtn.setFont(fo2);
        divBtn.setBackground(new Color(204, 230, 255));
        derivBtn.setFont(fo2);
        derivBtn.setBackground(new Color(204, 230, 255));
        integrBtn.setFont(fo2);
        integrBtn.setBackground(new Color(204, 230, 255));
        
        JLabel l1 = new JLabel("   Result:             ");
        JLabel l2 = new JLabel("* Polynomial 1: ");
        JLabel l3 = new JLabel("   Polynomial 2: ");
        l1.setForeground(Color.white);
        l2.setForeground(Color.white);
        l3.setForeground(Color.white);
        l1.setFont(fo2);
        l2.setFont(fo2);
        l3.setFont(fo2);
        
        JPanel label1 = new JPanel();
        label1.setBackground(new Color(38, 38, 38));
        label1.setFont(fo2);
        label1.add(l1);
        label1.add(res);
        
        JPanel label2 = new JPanel();
        label2.setBackground(new Color(38, 38, 38));
        label2.add(l2);
        label2.add(input1);
        
        JPanel label3 = new JPanel();
        label3.setBackground(new Color(38, 38, 38));
        label3.add(l3);
        label3.add(input2);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(38, 38, 38));
        topPanel.add(label1);
        topPanel.add(label2);   
        topPanel.add(label3);      
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 3, 10, 10));
        buttons.setBackground(new Color(38, 38, 38));
        buttons.add(addBtn);
        buttons.add(subtBtn);
        buttons.add(derivBtn);
        buttons.add(multBtn);
        buttons.add(divBtn);
        buttons.add(integrBtn);
        
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(topPanel);
        verticalPanel.add(buttons);
        verticalPanel.setPreferredSize(new Dimension(670, 350));
        
        JPanel content = new JPanel();
        content.add(verticalPanel);
        content.setBackground(new Color(38, 38, 38));
        
        this.setContentPane(content);
        this.setSize(700, 400);
        this.setTitle("Polynomial Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	public void addTextFieldListener(FocusListener l) {
        input1.addFocusListener(l);
    }

    public void addAdditionListener(ActionListener l) {
        addBtn.addActionListener(l);
    }

    public void addSubtractionListener(ActionListener l) {
        subtBtn.addActionListener(l);
    }

    public void addMultiplicationListener(ActionListener l) {
        multBtn.addActionListener(l);
    }

    public void addDivisionListener(ActionListener l) {
        divBtn.addActionListener(l);
    }

    public void addDerivativeListener(ActionListener l) {
        derivBtn.addActionListener(l);
    }

    public void addIntegralListener(ActionListener l) {
        integrBtn.addActionListener(l);
    }
	
	public JTextField getInput1() {
		return input1;
	}
	
	public String getInputText1() {
		return input1.getText();
	}

	public String getInputText2() {
		return input2.getText();
	}

	public void setResText(String res) {
		this.res.setText(res);
	}

    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
}

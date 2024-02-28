package GUI;

import Model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimulationFrame extends JFrame {
    private JTextField textFieldClients    = new JTextField(4);
    private JTextField textFieldQueues     = new JTextField(4);
    private JTextField textFieldSimulation = new JTextField(4);
    private JTextField textFieldArivalMin  = new JTextField(4);
    private JTextField textFieldArivalMax  = new JTextField(4);
    private JTextField textFieldServiceMin = new JTextField(4);
    private JTextField textFieldServiceMax = new JTextField(4);

    private JLabel time     = new JLabel("0");
    private JButton nextBtn =  new JButton("Next");

    private String draw1 = "\n     O\n     /|\\\n     / \\     ";

    JPanel contentSimulation;
    private JPanel[] panels;
    private JLabel[] labels;
    private List<LinkedList<JTextArea>> queues;

    public SimulationFrame(){
        JLabel labelClients    = new JLabel("Number of clients:    ");
        JLabel labelQueues     = new JLabel("Number of queues:   ");
        JLabel labelSimulation = new JLabel("Simulation interval:   ");
        JLabel labelArrival    = new JLabel("Arrival time min:        ");
        JLabel labelService    = new JLabel("Service time min:      ");
        JLabel labelMax1       = new JLabel("    max: ");
        JLabel labelMax2       = new JLabel("    max: ");

        Font fo = new Font(labelClients.getFont().toString(), Font.PLAIN, 20);

        textFieldClients.setFont(fo);
        textFieldQueues.setFont(fo);
        textFieldSimulation.setFont(fo);
        textFieldArivalMin.setFont(fo);
        textFieldArivalMax.setFont(fo);
        textFieldServiceMin.setFont(fo);
        textFieldServiceMax.setFont(fo);
        labelClients.setFont(fo);
        labelQueues.setFont(fo);
        labelSimulation.setFont(fo);
        labelArrival.setFont(fo);
        labelService.setFont(fo);
        labelMax1.setFont(fo);
        labelMax2.setFont(fo);
        nextBtn.setFont(fo);
        nextBtn.setBackground(Color.white);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(labelClients);
        p1.add(textFieldClients);
        p1.setPreferredSize(new Dimension(810, 80 ));

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(labelQueues);
        p2.add(textFieldQueues);
        p2.setPreferredSize(new Dimension(810, 80 ));

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(labelSimulation);
        p3.add(textFieldSimulation);
        p3.setPreferredSize(new Dimension(810, 80 ));

        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(labelArrival);
        p4.add(textFieldArivalMin);
        p4.add(labelMax1);
        p4.add(textFieldArivalMax);
        p4.setPreferredSize(new Dimension(810, 80 ));

        JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(labelService);
        p5.add(textFieldServiceMin);
        p5.add(labelMax2);
        p5.add(textFieldServiceMax);
        p5.setPreferredSize(new Dimension(810, 60 ));

        JPanel inputPanel = new JPanel();
        inputPanel.add(p1);
        inputPanel.add(p2);
        inputPanel.add(p3);
        inputPanel.add(p4);
        inputPanel.add(p5);
        inputPanel.setBorder(new EmptyBorder(30, 40, 0, 0));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextBtn);
        buttonPanel.setPreferredSize(new Dimension(800, 60));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 40));

        JPanel contentInput = new JPanel();
        contentInput.add(inputPanel);
        contentInput.add(buttonPanel);
        contentInput.setLayout(new BoxLayout(contentInput, BoxLayout.Y_AXIS));

        this.setVisible(true);
        this.setContentPane(contentInput);
        this.setSize(850, 550);
        this.setTitle("Input Data");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void createSimulationFrame(int nrQueue){
        String draw2 = "   O\n   /|\\ ╔═ ═╗\n╔═ ═╩═ ═╣\n╚═ ═══ ═╝";

        Font fo1 = new Font("Roboto", Font.PLAIN, 15);
        Font fo2 = new Font("Roboto", Font.PLAIN, 20);
        time.setFont(fo2);
        time.setBackground(new Color(233, 241, 247));

        panels = new JPanel[nrQueue];
        labels = new JLabel[nrQueue];
        queues = new ArrayList<>();

        JPanel counbterPanel = new JPanel();
        counbterPanel.add(time);
        counbterPanel.setBackground(new Color(233, 241, 247));
        counbterPanel.setPreferredSize(new Dimension(60, 40));

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timePanel.add(counbterPanel);
        timePanel.setBackground(new Color(34, 116, 165));
        timePanel.setPreferredSize(new Dimension(800, 50));
        timePanel.setBorder(new EmptyBorder(10, 0, 0, 14));

        JPanel queuePanel = new JPanel();
        queuePanel.setBackground(new Color(34, 116, 165));
        queuePanel.setPreferredSize(new Dimension(800, 850));

        for (int i = 0; i < nrQueue; i++){
            labels[i] = new JLabel("0");
            queues.add(new LinkedList<>());
            panels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panels[i].setBackground(new Color(233, 241, 247));
            panels[i].setPreferredSize(new Dimension(800, 80));

            JTextArea draw = new JTextArea(draw2);
            draw.setEditable(false);
            draw.setFont(fo1);
            draw.setBackground(new Color(233, 241, 247));
            draw.setPreferredSize(new Dimension(80, 80));

            panels[i].add(draw);
            panels[i].add(labels[i]);
            queuePanel.add(panels[i]);
        }

        contentSimulation = new JPanel();
        contentSimulation.add(timePanel);
        contentSimulation.add(queuePanel);
        contentSimulation.setLayout(new BoxLayout(contentSimulation, BoxLayout.Y_AXIS));

//        JScrollPane scrPane = new JScrollPane(contentSimulation);
    }
    public void setTime(int time) {
        String s = "0:00";
        if (time < 10)
            s = "0:0" + time;
        else {
            s = time / 60 + ":" + ((time % 60 < 10) ? "0" : "") + time % 60;
        }
        this.time.setText(s);
    }

    public void addToQueue(int id, Task t){
        JTextArea task = new JTextArea("(" + t.getID() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ")" + draw1);
        task.setFont(new Font(task.getFont().toString(), Font.PLAIN, 15));
        task.setBackground(new Color(233, 241, 247));
        queues.get(id).add(task);
        panels[id].add(queues.get(id).getLast());
    }

    public void removeFromQueue(int id){
        panels[id].remove(queues.get(id).poll());
        panels[id].repaint();
    }

    public void switchContent(){this.setContentPane(contentSimulation); this.setTitle("Simulation");}
    public void addNextListener(ActionListener l) {nextBtn.addActionListener(l);}
    public void error() {JOptionPane.showMessageDialog(this, "Invalid input!");}

    public void setServiceTime(int id, int time){labels[id].setText("" + time);}
    public String getClientsInput(){return textFieldClients.getText();}
    public String getQueueInput(){return textFieldQueues.getText();}
    public String getTimeInput(){return textFieldSimulation.getText();}
    public String getMinArrivalInput(){return textFieldArivalMin.getText();}
    public String getMaxArrivalInput(){return textFieldArivalMax.getText();}
    public String getMinServiceInput(){return textFieldServiceMin.getText();}
    public String getMaxServiceInput(){return textFieldServiceMax.getText();}
}

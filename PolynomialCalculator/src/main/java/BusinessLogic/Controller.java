package BusinessLogic;

import DataModels.Polynomial;
import GraphicalUserInterface.View;
import GraphicalUserInterface.Model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Controller {
	private Model model;
    private View view;
    
    public Controller(Model model, View view) {
        this.model = model;
        this.view  = view;
        
        view.addTextFieldListener(new TextFieldListener());
        view.addAdditionListener(new AdditionListener());
        view.addSubtractionListener(new SubtractionListener());
        view.addMultiplicationListener(new MultiplicationListener());
        view.addDerivativeListener(new DerivativeListener());
        view.addDivisionListener(new DivisionListener());
        view.addIntegralListener(new IntegralListener());
    }
    
    class TextFieldListener implements FocusListener {
		public void focusGained(FocusEvent e) {
			if (view.getInput1().getText().equals("ex: -x3+2x2-x+10")) {
				view.getInput1().setText("");
				view.getInput1().setForeground(Color.black);
			}
		}
		public void focusLost(FocusEvent e) {
			if (view.getInput1().getText().equals("")) {
				view.getInput1().setText("ex: -x3+2x2-x+10");
				view.getInput1().setForeground(Color.gray);
			}
		}	
    }
    
    class AdditionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Polynomial pol1;
            Polynomial pol2;
            try {
            	pol1 = Polynomial.read(view.getInputText1());
            	pol2 = Polynomial.read(view.getInputText2());
                model.setResult(Operations.addition(pol1, pol2).toString());
                view.setResText(model.getResult());   
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }
    
    class SubtractionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Polynomial pol1;
            Polynomial pol2;
            try {
            	pol1 = Polynomial.read(view.getInputText1());
            	pol2 = Polynomial.read(view.getInputText2());
                model.setResult(Operations.subtraction(pol1, pol2).toString());
                view.setResText(model.getResult());
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }
    
    class MultiplicationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Polynomial pol1;
            Polynomial pol2;
            try {
            	pol1 = Polynomial.read(view.getInputText1());
            	pol2 = Polynomial.read(view.getInputText2());
                model.setResult(Operations.multiplication(pol1, pol2).toString());
                view.setResText(model.getResult());
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }
    
    class DivisionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Polynomial pol1;
            Polynomial pol2;
            try {
            	pol1 = Polynomial.read(view.getInputText1());
            	pol2 = Polynomial.read(view.getInputText2());
                model.setResult(Operations.division(pol1, pol2));
                view.setResText(model.getResult());
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }
    
    class DerivativeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Polynomial pol;
            try {
            	pol = Polynomial.read(view.getInputText1());
                model.setResult(Operations.derivative(pol).toString());
                view.setResText(model.getResult());
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }
    
    class IntegralListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Polynomial pol;
            try {
            	pol = Polynomial.read(view.getInputText1());
                model.setResult(Operations.integral(pol).toString());
                view.setResText(model.getResult() + "+C");
            } catch (Exception ex) {
                view.showError(ex.getMessage());
            }
        }
    }
}

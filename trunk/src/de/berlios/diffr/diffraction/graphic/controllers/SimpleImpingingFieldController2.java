package de.berlios.diffr.diffraction.graphic.controllers;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import de.berlios.diffr.diffraction.exceptions.WrongClassException;
import de.berlios.diffr.diffraction.fields.impingingFields.VerySimpleImpingingField;
import de.berlios.diffr.diffraction.graphic.Controllable;

public class SimpleImpingingFieldController2 extends Controller {
  private SimpleImpingingFieldController2() {
  }

  private JTextField text = new JTextField();

  private VerySimpleImpingingField field;
  private double newAngle;

  private JPanel panel;

  private void drawField(Graphics g, double angle, int widht, int height) {
    int x2 = widht / 2;
    int y2 = height / 2;
    int x1 = x2 - (int)(Math.cos(angle) * 150);
    int y1 = y2 - (int)(Math.sin(angle) * 150);
    int sx1 = x2 + (int)(Math.cos(angle + 2.8)*15);
    int sy1 = y2 + (int)(Math.sin(angle + 2.8)*15);
    int sx2 = x2 + (int)(Math.cos(angle - 2.8)*15);
    int sy2 = y2 + (int)(Math.sin(angle - 2.8)*15);
    g.drawLine(x1, y1, x2, y2);
    g.drawLine(sx1, sy1, x2, y2);
    g.drawLine(sx2, sy2, x2, y2);
  }

  public SimpleImpingingFieldController2(Controllable impField) throws WrongClassException {
    try {
      this.field = (VerySimpleImpingingField) impField;
      newAngle = field.getAngle();
      text.setText(Double.toString(newAngle * 180 / Math.PI));
      panel = new JPanel() {
        public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.setColor(new Color(0, 50, 50));
          drawField(g, field.getAngle(), panel.getWidth(), panel.getHeight());
        }
      };
      panel.setBackground(new Color(0,0,0));
      panel.setLayout(new BorderLayout());
      text.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            newAngle = Double.parseDouble(text.getText()) * Math.PI / 180;
            renewObject();
            panel.repaint();
          }
          catch (NumberFormatException ex) {
          }
        }
      });
      panel.add(text, BorderLayout.NORTH);
    }
    catch (Exception ex) {
      throw new WrongClassException();
    }
  }

  public JPanel getPanel() {
    return panel;
  }

  public void renewObject() {
    VerySimpleImpingingField imp = new VerySimpleImpingingField(new Double(newAngle));
    if (super.objectWasChanged(imp)) {
      field = imp;
    }
  }

  public void drawControllable(Graphics g, int width, int height) {
    g.setColor(new Color(0, 50, 50));
    drawField(g, field.getAngle(), width, height);
  }

  public Controllable getControllable() {
    return field;
  }
}

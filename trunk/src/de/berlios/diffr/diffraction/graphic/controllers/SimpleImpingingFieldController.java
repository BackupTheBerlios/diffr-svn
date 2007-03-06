/* 
 * This file is part of the diffr project.
 * Copyright (C) 2006-2007, diffr team
 * All rights reserved.
 * 
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * */
/**
 * Created on 08.12.2006
 * 
 * @author Petr Mikheev
 */

package de.berlios.diffr.diffraction.graphic.controllers;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import de.berlios.diffr.diffraction.exceptions.WrongClassException;
import de.berlios.diffr.diffraction.fields.impingingFields.VerySimpleImpingingField;
import de.berlios.diffr.diffraction.graphic.Controllable;

public class SimpleImpingingFieldController extends Controller {
  private SimpleImpingingFieldController() {
  }

  private JTextField text = new JTextField();

  private VerySimpleImpingingField field;
  private double newAngle;

  private JPanel panel;

  private void drawField(Graphics g, double angle, int widht, int height) {
    int x2 = widht / 2;
    int y2 = height / 2;
    int x1 = x2 - (int)(Math.cos(angle) * 100);
    int y1 = y2 - (int)(Math.sin(angle) * 100);
    int sx1 = x2 + (int)(Math.cos(angle + 2.8)*15);
    int sy1 = y2 + (int)(Math.sin(angle + 2.8)*15);
    int sx2 = x2 + (int)(Math.cos(angle - 2.8)*15);
    int sy2 = y2 + (int)(Math.sin(angle - 2.8)*15);
    g.drawLine(x1, y1, x2, y2);
    g.drawLine(sx1, sy1, x2, y2);
    g.drawLine(sx2, sy2, x2, y2);
  }

  public SimpleImpingingFieldController(Controllable impField) throws WrongClassException {
    try {
      this.field = (VerySimpleImpingingField) impField;
      newAngle = field.getAngle();
      text.setText(Double.toString(newAngle));
      panel = new JPanel() {
        public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.setColor(new Color(150, 150, 150));
          drawField(g, newAngle, panel.getWidth(), panel.getHeight());
          g.setColor(new Color(0, 0, 255));
          drawField(g, field.getAngle(), panel.getWidth(), panel.getHeight());
        }
      };
      panel.setBackground(new Color(0,0,0));
      panel.setLayout(new BorderLayout());
      text.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            newAngle = Double.parseDouble(text.getText());
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
    g.setColor(new Color(0, 0, 255));
    drawField(g, field.getAngle(), width, height);
  }

  public Controllable getControllable() {
    return field;
  }
}

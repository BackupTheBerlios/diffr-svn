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
import javax.swing.*;

import de.berlios.diffr.diffraction.exceptions.WrongClassException;
import de.berlios.diffr.diffraction.fields.reflectedFields.VerySimpleReflectedField;
import de.berlios.diffr.diffraction.graphic.Controllable;

public class SimpleReflectedFieldController extends Controller {
  private SimpleReflectedFieldController() {
  }

  private VerySimpleReflectedField field;
  private boolean fieldExist;

  private JPanel panel = new JPanel() {
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (fieldExist) {
        g.setColor(new Color(255, 255, 0));
        drawField(g, field.getAngle() + Math.PI , panel.getWidth(), panel.getHeight());
      }
    }
  };

  private void drawField(Graphics g, double angle, int widht, int height) {
    int x1 = widht / 2;
    int y1 = height / 2;
    int x2 = x1 + (int)(Math.cos(angle) * 100);
    int y2 = y1 + (int)(Math.sin(angle) * 100);
    int sx1 = x2 + (int)(Math.cos(angle + 2.8)*15);
    int sy1 = y2 + (int)(Math.sin(angle + 2.8)*15);
    int sx2 = x2 + (int)(Math.cos(angle - 2.8)*15);
    int sy2 = y2 + (int)(Math.sin(angle - 2.8)*15);
    g.drawLine(x1, y1, x2, y2);
    g.drawLine(sx1, sy1, x2, y2);
    g.drawLine(sx2, sy2, x2, y2);
  }

  public SimpleReflectedFieldController(Controllable field) throws WrongClassException {
    try {
      this.field = (VerySimpleReflectedField) field;
      if (field != null) {
        panel.setBackground(new Color(0, 0, 0));
        fieldExist = true;
      }
      else
        panel.add(new JLabel("Reflected field isn`t calculate"));
      fieldExist = false;
    }
    catch (Exception ex) {
      throw new WrongClassException();
    }
  }

  public JPanel getPanel() {
    return panel;
  }

  public void drawControllable(Graphics g, int width, int height) {
    if (fieldExist) {
      g.setColor(new Color(255, 255, 0));
      drawField(g, field.getAngle() + Math.PI, width, height);
    }
  }

  public void setControllable(Controllable field) throws WrongClassException {
    try {
      this.field = (VerySimpleReflectedField) field;
      if (field != null) {
        panel.setBackground(new Color(0, 0, 0));
        fieldExist = true;
      } else {
        panel.add(new JLabel("Reflected field isn`t calculate"));
        fieldExist = false;
      }
    }
    catch (Exception ex) {
      throw new WrongClassException();
    }
  }

  public Controllable getControllable() {
    return field;
  }
}

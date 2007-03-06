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

import de.berlios.diffr.diffraction.arguments.*;
import de.berlios.diffr.diffraction.graphic.*;


public class UniversalController extends Controller {
  private UniversalController() {}

  private Controllable object;
  private JPanel panel = new JPanel();
  private JPanel objectPanel = null;
  private Box fields = Box.createVerticalBox();

  public UniversalController(Controllable object) {
    this.object = object;
    makePanel();
  }

  private void makePanel() {
    panel.removeAll();
    panel.setLayout(new BorderLayout());
    if (object == null) {
      panel.add(new JLabel("Object doesn`t exist"), BorderLayout.NORTH);
    } else {
      ArgumentPanelFactory factory = new ArgumentPanelFactory() {
        public void panelValueWasChanged() {
          renewObject();
        }
      };
      objectPanel = factory.getArgumentPanel(new ArgumentType("this",
          object.getClass(), object), object);
      panel.add(objectPanel);
    }
    panel.updateUI();
//    panel.validate();
  }

  private void renewObject() {
    Controllable newObject;
    if (objectPanel != null)
      newObject = (Controllable)((ArgumentPanel)objectPanel).getValue();
    else
      newObject = null;
    if (super.objectWasChanged(newObject)) {
      object = newObject;
    } else {
      makePanel();
    }
  }

  public JPanel getPanel() {
    return panel;
  }

  public void drawControllable(Graphics g, int widht, int height) {}

  public Controllable getControllable() {
    return object;
  }

    public void setControllable(Controllable object) {
    this.object = object;
    makePanel();
  }

}

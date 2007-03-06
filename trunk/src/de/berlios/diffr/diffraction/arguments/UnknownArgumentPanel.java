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

package de.berlios.diffr.diffraction.arguments;

import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

import de.berlios.diffr.diffraction.graphic.*;

public class UnknownArgumentPanel extends ArgumentPanel {
  private UnknownArgumentPanel() {}
  private ArgumentType argument;
  private Constructor constructor;
  private ArrayList argumentPanels;
  private Controllable controllable;
  public UnknownArgumentPanel(ArgumentType p, Object object) {
    this.argument = p;
    controllable = (Controllable) object;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    showInfo();
    showArguments();
  }

  private void showInfo() {
    this.add(new JLabel(argument.getHint() + " : " + argument.getType().getName()));
  }
  public Object getValue() {
    renewObject();
    return controllable;
  }

  private void renewObject() {
    try {
      ArrayList argumentsList = new ArrayList();
      for (int argumentNumber = 0; argumentNumber < argumentPanels.size();
           argumentNumber++) {
        argumentsList.add( ( (ArgumentPanel) argumentPanels.get(
            argumentNumber)).getValue());
      }
      Object[] arguments = argumentsList.toArray();
      Object object = constructor.newInstance(arguments);
      controllable = (Controllable) object;
    }
    catch (InvocationTargetException ex) {
      ex.printStackTrace();
    }
    catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
    catch (InstantiationException ex) {
      ex.printStackTrace();
    }
  }

  private void showArguments() {
    ArgumentPanelFactory factory = new ArgumentPanelFactory() {
      public void panelValueWasChanged() {
        valueWasChanged();
      }
    };
    argumentPanels = new ArrayList();
    ArgumentType[] arguments = controllable.getArguments();
    Class[] argumentClasses = new Class[arguments.length];
    for (int argumentNumber = 0; argumentNumber < arguments.length;
         argumentNumber++) {
      ArgumentPanel panel = factory.getArgumentPanel(arguments[argumentNumber],
          controllable.getArgumentValue(argumentNumber));
      this.add(panel);
      argumentClasses[argumentNumber] = (Class)arguments[argumentNumber].getType();
      argumentPanels.add(panel);
    }
    try {
      constructor = controllable.getClass().getConstructor(argumentClasses);
    }
    catch (SecurityException ex) {
      ex.printStackTrace();
    }
    catch (NoSuchMethodException ex) {
      ex.printStackTrace();
    }
  }

}

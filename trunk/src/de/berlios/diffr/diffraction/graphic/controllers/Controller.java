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

import java.util.*;

import java.awt.*;
import javax.swing.*;

import de.berlios.diffr.diffraction.exceptions.*;
import de.berlios.diffr.diffraction.graphic.*;

public abstract class Controller {
  public Controller() {}
  public Controller(Controllable object) throws WrongClassException {}
  public abstract JPanel getPanel();
  public abstract void drawControllable(Graphics g, int width, int height);
  private ArrayList listeners = new ArrayList();
  public final void addControllObjectChangingListener(ControllObjectChangingListener l) {
    listeners.add(l);
  }
  public final void removeControllObjectChangingListener(ControllObjectChangingListener l) {
    listeners.remove(l);
  }

  protected final boolean objectWasChanged(Controllable object) {
    boolean answer = true;
    for (int number = 0; number < listeners.size(); number++) {
      ControllObjectChangingListener listener = (ControllObjectChangingListener) listeners.get(number);
      if (!listener.controllObjectWasChanged(object)) answer = false;
    }
    if (!answer) {
      DiffractionPanel.showMessage("You can`t set this");
    }
    return answer;
  }

  public final void controllerWasChanged() {
    for (int number = 0; number < listeners.size(); number++) {
      ControllObjectChangingListener listener = (ControllObjectChangingListener) listeners.get(number);
      listener.controllerWasChanged();
    }
  }

  public final ArrayList getListeners() {
    return listeners;
  }

  public void setControllable(Controllable object) throws WrongClassException {}
  public abstract Controllable getControllable();

}

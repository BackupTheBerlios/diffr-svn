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

package de.berlios.diffr.diffraction.graphic;

import de.berlios.diffr.diffraction.exceptions.WrongClassException;
import de.berlios.diffr.diffraction.graphic.controllers.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TaskPart {
  private Controller controller = null;
  private PanelForChoose controllerChooser;
  private JPanel panel = new JPanel();
  private Controllable partOfTask;
  public TaskPart(Controllable partOfTask, Object[] controllers) {
    this.partOfTask = partOfTask;
    panel.setLayout(new BorderLayout());
    makeControllerChooser(controllers);
    if (controllers != null)
      if (controllers.length > 0)
        tryToSetController((ControllerType)controllers[0]);
  }

  private void makeControllerChooser(Object[] controllers) {
    controllerChooser = new PanelForChoose(
      controllers,
      "Controller chooser",
      "There isn`t any controllers",
      true
    );
    controllerChooser.addPanelForChooseListener(controllerChooserListener);
    panel.add(controllerChooser, BorderLayout.EAST);
  }

  PanelForChooseListener controllerChooserListener = new PanelForChooseListener() {
      public void OKButtonPressed(Object choosedControllerClass) {
        if (!tryToSetController((ControllerType)choosedControllerClass))
          DiffractionPanel.showMessage("This controller can`t work with this object");
      }
      public void cancelButtonPressed() {}
    };

  private boolean tryToSetController(ControllerType controllerType) {
    try {
      if (controller != null) {
        partOfTask = controller.getControllable();
      }
      Controller newController = controllerType.getInstance(partOfTask);
      setController(newController);
      return true;
    }
    catch (WrongClassException ex) {
      System.out.println("Controller for " + partOfTask.toString());
      ex.printStackTrace();
      return false;
    }
  }

  public void renewControllers(Object[] controllers) {
    panel.remove(controllerChooser);
    controllerChooser = new PanelForChoose(
      controllers,
      "Controller chooser",
      "There isn`t any controllers",
      true
    );
    controllerChooser.addPanelForChooseListener(controllerChooserListener);
    panel.add(controllerChooser, BorderLayout.EAST);
    if (controllers.length > 0)
      setController((Controller)controllers[0]);
    else {
      if (controllerPanel != null) panel.remove(controllerPanel);
      controllerPanel = null;
    }
  }

  private JPanel controllerPanel = null;

  private void setController(Controller c) {
    if (controllerPanel != null) panel.remove(controllerPanel);
    ArrayList listeners = new ArrayList();
    if (controller != null) {
      listeners = controller.getListeners();
    }
    controller = c;
    if (controller != null) {
      for (int number = 0; number < listeners.size(); number++) {
        ControllObjectChangingListener listener = (ControllObjectChangingListener) listeners.get(number);
        controller.addControllObjectChangingListener(listener);
      }
      controller.controllerWasChanged();
      controllerPanel = controller.getPanel();
      panel.add(controllerPanel, BorderLayout.CENTER);
      panel.validate();
    }
  }

  public Controller getCurrentController() {
    return controller;
  }

  public JPanel getPanel() {
    return panel;
  }

  public void setControllable(Controllable object) {
    try {
      controller.setControllable(object);
    }
    catch (WrongClassException ex) {
      ex.printStackTrace();
    }
    catch (NullPointerException ex) {
      ex.printStackTrace();
    }
  }

  public void addControllObjectChangingListener(ControllObjectChangingListener l) {
    controller.addControllObjectChangingListener(l);
  }
  public void removeControllObjectChangingListener(ControllObjectChangingListener l) {
    controller.removeControllObjectChangingListener(l);
  }

}

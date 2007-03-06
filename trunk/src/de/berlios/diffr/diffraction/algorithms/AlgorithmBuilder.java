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

package de.berlios.diffr.diffraction.algorithms;

import javax.swing.*;
import java.util.*;

import java.lang.reflect.*;

import de.berlios.diffr.diffraction.arguments.*;
import de.berlios.diffr.diffraction.exceptions.*;
import de.berlios.diffr.diffraction.graphic.*;
import de.berlios.diffr.diffraction.tasks.*;

public class AlgorithmBuilder extends JPanel {
  private PanelForChoose panelForChoose;
  private JLabel autor = new JLabel("autor:");
  private Box argumentsBox = Box.createVerticalBox();
  private AlgorithmType algorithmType;
  private Task task;

  public AlgorithmBuilder(Task task, Object[] algorithmTypes) {
    this.task = task;
    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

    showListOfAlgorithmTypes(algorithmTypes);
    this.add(autor);
    this.add(Box.createVerticalStrut(20));
    this.add(argumentsBox);
    panelForChoose.addPanelForChooseListener(new PanelForChooseListener() {
      public void OKButtonPressed(Object type) {
        algorithmType = (AlgorithmType) type;
        showInfo();
        showArguments();
      }
    });

    try {
      algorithmType = (AlgorithmType) algorithmTypes[0];
    }
    catch (Exception ex) {
      algorithmType = null;
    }

    showInfo();
    showArguments();
  }

  private void thereIsNotAnyObjectsError(String errorHint) {
    JLabel message=new JLabel(errorHint);
    this.add(new JScrollPane(message));
  }

  public void showListOfAlgorithmTypes(Object[] objects) {
    panelForChoose = new PanelForChoose(objects, "Algorithm types", "There aren`t any algorithm types", true);
    this.add(panelForChoose);
  }

  public void updateAlgorithm() {
    if (algorithmType != null) {
      if (!algorithmIsTimely) {
        renewAlgorithm();
        algorithmIsTimely = true;
      }
    } else {
      try {
        task.setAlgorithm(null);
      }
      catch (TaskIsSolvingException ex) {
      }
      catch (WrongClassException ex) {
      }
    }
  }

  private void renewAlgorithm() {
    try {
      ArrayList argumentsList = new ArrayList();
      for (int argumentNumber = 0; argumentNumber < argumentPanels.size();
           argumentNumber++) {
        argumentsList.add(((ArgumentPanel)argumentPanels.get(argumentNumber)).getValue());
      }
      Object[] arguments = argumentsList.toArray();
      Constructor constructor = algorithmType.getAlgorithmClass().getConstructors()[0];
      Object algorithm = constructor.newInstance(arguments);
      Algorithm alg = (Algorithm) algorithm;
      task.setAlgorithm(alg);
    }
    catch (SecurityException ex) {
      ex.printStackTrace();
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
    catch (TaskIsSolvingException ex) {
      ex.printStackTrace();
    }
    catch (WrongClassException ex) {
      ex.printStackTrace();
    }
  }

  private void showInfo() {
    if (algorithmType != null) {
      autor.setText("autor: " + algorithmType.getAutor());
    } else {
      autor.setText("autor:");
    }
  }

  private ArrayList argumentPanels = null;

  private boolean algorithmIsTimely = false;

  private void showArguments() {
    if (algorithmType != null) {
      ArgumentPanelFactory factory = new ArgumentPanelFactory() {
        public void panelValueWasChanged() {
          algorithmIsTimely = false;
        }
      };
      argumentPanels = new ArrayList();
      ArgumentType[] arguments = algorithmType.getArguments();
      argumentsBox.removeAll();
      for (int argumentNumber = 0; argumentNumber < arguments.length; argumentNumber++) {
        ArgumentPanel panel = factory.getArgumentPanel(arguments[argumentNumber], arguments[argumentNumber].getInitialValue());
        argumentsBox.add(panel);
        argumentPanels.add(panel);
        algorithmIsTimely = false;
      }
    }
  }

}

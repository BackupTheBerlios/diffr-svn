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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import de.berlios.diffr.diffraction.algorithms.*;
import de.berlios.diffr.diffraction.exceptions.*;
import de.berlios.diffr.diffraction.fields.impingingFields.ImpingingField;
import de.berlios.diffr.diffraction.graphic.controllers.*;
import de.berlios.diffr.diffraction.surfaces.Surface;
import de.berlios.diffr.diffraction.tasks.*;

public class TaskPanel extends JPanel {
  private JTabbedPane tabbedPane = new JTabbedPane();
  private Box buttonsBox = Box.createVerticalBox();
  private JButton startButton = new JButton("Start");
  private JButton stopButton = new JButton("Stop");
  private JLabel stateLabel = new JLabel("State");
  private JTextField state = new JTextField();
  private Task task;
  private Description description;
  public TaskPanel(Task task, Description description, ActionListener startButtonL, ActionListener stopButtonL) {
    this.task = task;
    task.addTaskListener(taskListener);
    startButton.addActionListener(startButtonL);
    stopButton.addActionListener(stopButtonL);
    stopButton.setEnabled(false);
    stateIntialization(task);
    this.description = description;
    makeTaskParts();
    this.setLayout(new BorderLayout());
    tabedPaneInitialization();
    buttonsBoxInitialization();
    this.add(tabbedPane);
    this.add(buttonsBox, BorderLayout.EAST);
    taskImageInitialization();
  }

  private void stateIntialization(Task task) {
    try {
      if (task.getReflectedField() != null)
        state.setText("Task isn calculate");
      else
        state.setText("Task isn`t calculate");
    }
    catch (TaskIsSolvingException ex) {
    }
  }

  private void taskImageInitialization() {
    taskImage.setLayout(new BorderLayout());
    renewTaskImage();
  }

  private void makeTaskParts() {
    algorithm = new AlgorithmBuilder(task, task.getType().getAlgorithmTypes().toArray());
    surface = new TaskPart(
      task.getSurface(),
      task.getType().getSurfaceControllerTypes().toArray()
    );
    surface.addControllObjectChangingListener(new ControllObjectChangingListener() {
      public boolean controllObjectWasChanged(Controllable surface) {
        try {
          task.setSurface( (Surface) surface);
          renewReflectedField();
          renewTaskImage();
          return true;
        }
        catch (TaskIsSolvingException ex) {
          return false;
        }
        catch (WrongClassException ex) {
          return false;
        }
      }
      public void controllerWasChanged() {
        renewTaskImage();
      }
    });
    impingingField = new TaskPart(
      task.getImpingingField(),
      task.getType().getImpingingFieldControllerTypes().toArray()
    );
    impingingField.addControllObjectChangingListener(new ControllObjectChangingListener() {
      public boolean controllObjectWasChanged(Controllable impingingField) {
        try {
          task.setImpingingField( (ImpingingField) impingingField);
          renewReflectedField();
          renewTaskImage();
          return true;
        }
        catch (TaskIsSolvingException ex) {
          return false;
        }
        catch (WrongClassException ex) {
          return false;
        }
      }
      public void controllerWasChanged() {
        renewTaskImage();
      }
    });

    try {
      reflectedField = new TaskPart(
          task.getReflectedField(),
          task.getType().getReflectedFieldControllerTypes().toArray()
      );
    }
    catch (TaskIsSolvingException ex) {
      reflectedField = null;
    }
    reflectedField.addControllObjectChangingListener(new ControllObjectChangingListener() {
      public boolean controllObjectWasChanged(Controllable impingingField) {
        return false;
      }
      public void controllerWasChanged() {
        renewReflectedField();
      }
    });
  }

  private void renewReflectedField() {
    try {
      reflectedField.setControllable(task.getReflectedField());
      renewTaskImage();
    }
    catch (TaskIsSolvingException ex) {
      reflectedField.setControllable(null);
    }
  }

  private void buttonsBoxInitialization() {
    state.setEditable(false);
    state.setMaximumSize(new Dimension(500,20));
    buttonsBox.add(Box.createGlue());
    buttonsBox.add(startButton);
    buttonsBox.add(Box.createGlue());
    buttonsBox.add(stopButton);
    buttonsBox.add(Box.createGlue());
    buttonsBox.add(stateLabel);
    buttonsBox.add(state);
    buttonsBox.add(Box.createGlue());
  }

  private AlgorithmBuilder algorithm;
  private TaskPart surface;
  private TaskPart impingingField;
  private TaskPart reflectedField;

  public void showAllTask() {
    tabbedPane.setSelectedIndex(0);
  }

  public void showSurfacePane() {
    tabbedPane.setSelectedIndex(1);
  }

  public void showImpingingFieldPane() {
    tabbedPane.setSelectedIndex(2);
  }

  public void showReflectedFieldPane() {
    tabbedPane.setSelectedIndex(3);
  }

  public void showAlgorithmPane() {
    tabbedPane.setSelectedIndex(4);
  }

  public void showHelpPane() {
    tabbedPane.setSelectedIndex(5);
  }

  private JPanel taskImage = new JPanel();

  private void renewTaskImage() {
    Controller[] controllers = {
        surface.getCurrentController(),
        impingingField.getCurrentController(),
        reflectedField.getCurrentController()
    };
    taskImage.removeAll();
    taskImage.add(new TaskImage(controllers));
  }

  private void tabedPaneInitialization() {
    tabbedPane.addTab("Task", taskImage);
    tabbedPane.addTab("Surface", surface.getPanel());
    tabbedPane.addTab("Impinging field", impingingField.getPanel());
    tabbedPane.addTab("Reflected field", reflectedField.getPanel());
    tabbedPane.addTab("Algorithm", algorithm);
    tabbedPane.addTab("About programm", description.getAboutProgrammPanel());
  }

  public synchronized boolean startTask() {
    algorithm.updateAlgorithm();
    try {
      task.solve();
      state.setText("Running...");
      startButton.setEnabled(false);
      stopButton.setEnabled(true);
      return true;
    }
    catch (TaskIsSolvingException ex) {
      DiffractionPanel.showMessage("Task already running");
    }
    catch (AlgorithmDoesNotChoosedException ex) {
      DiffractionPanel.showMessage("Algorithm doesn`t choosed");
    }
    return false;
  }

  TaskListener taskListener = new TaskListener() {
    public void solutionIsCalculate() {
      taskIsCalculate();
    }
    public void errorHappened() {
      errorInAlgorithmHappened();
    }
  };

  public void stopTask() {
    try {
      task.stop();
      state.setText("Task isn`t calculate");
      startButton.setEnabled(true);
      stopButton.setEnabled(false);
    }
    catch (TaskIsNotSolvingException ex) {
      DiffractionPanel.showMessage("Task isn`t calculating now");
    }
  }

  private void errorInAlgorithmHappened() {
    state.setText("Error happened");
    startButton.setEnabled(true);
    stopButton.setEnabled(false);
    DiffractionPanel.showMessage("Current algorithm can`t solve it");
  }

  private void taskIsCalculate() {
    startButton.setEnabled(true);
    stopButton.setEnabled(false);
    try {
      reflectedField.setControllable(task.getReflectedField());
    }
    catch (TaskIsSolvingException ex) {
      reflectedField = null;
    }
    state.setText("Task is calculate");
    taskImage.repaint();
    reflectedField.getPanel().repaint();
  }
}

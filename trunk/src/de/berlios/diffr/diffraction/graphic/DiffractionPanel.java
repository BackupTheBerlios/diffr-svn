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

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import de.berlios.diffr.diffraction.manager.*;
import de.berlios.diffr.diffraction.tasks.*;

public class DiffractionPanel {
  private Container cont;
  private boolean isApplet;
  public DiffractionPanel(boolean isApplet) {
    this.isApplet=isApplet;
    JFrame frame=new JFrame("Diffraction");
    cont=frame.getContentPane();
    frame.setSize(640,480);
    addWindowListeners(frame);
    initialization();
    frame.setJMenuBar(menuBar);
    frame.setVisible(true);
  }
  public DiffractionPanel(JApplet applet) {
    isApplet=true;
    cont=applet.getContentPane();
    initialization();
    applet.setJMenuBar(menuBar);
  }

  private Description description = new Description();

  private TaskPanel taskPanel = null;

  private SourceOfTaskTypes sourceOfTaskTypes=null;

  private JMenuBar menuBar;

  private JMenu fileMenu;
  private JMenu editMenu;
  private JMenu runMenu;
  private JMenu algorithmsMenu;
  private JMenu helpMenu;

  private JMenuItem newItem;
  private JMenuItem openItem;
  private JMenuItem saveItem;
  private JMenuItem exitItem;
  private JMenuItem surfaceItem;
  private JMenuItem impingingFieldItem;
  private JMenuItem reflectedFieldItem;
  private JMenuItem algorithmItem;
  private JMenuItem taskImageItem;
  private JMenuItem startItem;
  private JMenuItem stopItem;
  private JMenuItem addAlgorithmItem;
  private JMenuItem removeAlgorithmItem;
  private JMenuItem addAlgorithmToCurrentTaskItem;
  private JMenuItem removeAlgorithmFromCurrentTaskItem;
  private JMenuItem aboutProgrammItem;

  public void setSourceOfTaskTypes(SourceOfTaskTypes s) {
    this.sourceOfTaskTypes=s;
  }

  private void initialization() {
    menuInitialization();
    showWelcome();
  }

  private void showWelcome() {
    lockItemsForTask();
    addAlgorithmToCurrentTaskItem.setEnabled(false);
    removeAlgorithmFromCurrentTaskItem.setEnabled(false);
    cont.removeAll();
    cont.validate();
    cont.repaint();
  }

  private void lockItemsForTask() {
    saveItem.setEnabled(false);
    surfaceItem.setEnabled(false);
    reflectedFieldItem.setEnabled(false);
    impingingFieldItem.setEnabled(false);
    algorithmItem.setEnabled(false);
    taskImageItem.setEnabled(false);
    startItem.setEnabled(false);
    stopItem.setEnabled(false);
  }

  private void menuInitialization() {
    menuBar = new JMenuBar();

    fileMenu = new JMenu("File");
    editMenu = new JMenu("Edit");
    runMenu = new JMenu("Run");
    algorithmsMenu = new JMenu("Algorithms");
    helpMenu = new JMenu("Help");

    newItem = new JMenuItem("New task");
    openItem = new JMenuItem("Open task");
    saveItem = new JMenuItem("Save task");
    exitItem = new JMenuItem("Exit");
    fileMenu.add(newItem);
    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.addSeparator();
    fileMenu.add(exitItem);

    taskImageItem = new JMenuItem("Task");
    surfaceItem = new JMenuItem("Surface");
    impingingFieldItem = new JMenuItem("Impinging field");
    reflectedFieldItem = new JMenuItem("Reflected field");
    algorithmItem = new JMenuItem("Algorithm");
    editMenu.add(taskImageItem);
    editMenu.add(surfaceItem);
    editMenu.add(impingingFieldItem);
    editMenu.add(reflectedFieldItem);
    editMenu.add(algorithmItem);

    startItem = new JMenuItem("Start");
    stopItem = new JMenuItem("Stop");
    runMenu.add(startItem);
    runMenu.add(stopItem);

    addAlgorithmItem = new JMenuItem("Load algorithm");
    removeAlgorithmItem = new JMenuItem("Remove algorithm");
    addAlgorithmToCurrentTaskItem = new JMenuItem("Load algorithm for current task");
    removeAlgorithmFromCurrentTaskItem = new JMenuItem("Remove algorithm from current task");
    algorithmsMenu.add(addAlgorithmItem);
    algorithmsMenu.add(removeAlgorithmItem);
    algorithmsMenu.addSeparator();
    algorithmsMenu.add(addAlgorithmToCurrentTaskItem);
    algorithmsMenu.add(removeAlgorithmFromCurrentTaskItem);

    aboutProgrammItem = new JMenuItem("About Programm");
    helpMenu.add(aboutProgrammItem);

    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    menuBar.add(runMenu);
    menuBar.add(algorithmsMenu);
    menuBar.add(helpMenu);

    addListenersToMenuItems();
  }

  private void addListenersToMenuItems() {
    newItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        makeNewTask();
      }
    });
    openItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tryLoadTask();
      }
    });
    saveItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        trySaveTask();
      }
    });
    exitItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        userWantToExitEvent();
      }
    });
    taskImageItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        taskPanel.showAllTask();
      }
    });
    surfaceItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        taskPanel.showSurfacePane();
      }
    });
    impingingFieldItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        taskPanel.showImpingingFieldPane();
      }
    });
    reflectedFieldItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        taskPanel.showReflectedFieldPane();
      }
    });
    algorithmItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        taskPanel.showAlgorithmPane();
      }
    });
    aboutProgrammItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showAboutProgrammPanel();
      }
    });
    startItem.addActionListener(startTask);
    stopItem.addActionListener(stopTask);
  }

  private ActionListener startTask = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      if (taskPanel.startTask()) {
        startItem.setEnabled(false);
        stopItem.setEnabled(true);
      }
    }
  };
  private ActionListener stopTask = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      taskPanel.stopTask();
      startItem.setEnabled(true);
      stopItem.setEnabled(false);
    }
  };

  private void addWindowListeners(Frame frame) {
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        userWantToExitEvent();
      }
    });
  }

  private ArrayList listeners=new ArrayList();
  public void addDiffractionPanelListener(DiffractionPanelListener listener) {
    listeners.add(listener);
  }
  public void removeDiffractionPanelListener(DiffractionPanelListener listener) {
    listeners.remove(listener);
  }

  private void makeNewCurrentTaskEvent(TaskType type) {
    for (int number = 0; number < listeners.size(); number++) {
      DiffractionPanelListener listener = (DiffractionPanelListener) listeners.get(number);
      listener.makeNewCurrentTask(type);
    }
  }

  private void tryLoadTask() {
    JFileChooser problemChooser = new JFileChooser();
    problemChooser.setFileFilter(new SavedTaskFileFilter());
    problemChooser.showOpenDialog(cont);
    File selectedFile=problemChooser.getSelectedFile();
    if (selectedFile != null) loadTaskEvent(selectedFile);
  }

  private void trySaveTask() {
    JFileChooser problemChooser = new JFileChooser();
    problemChooser.setFileFilter(new SavedTaskFileFilter());
    problemChooser.showSaveDialog(cont);
    File selectedFile=problemChooser.getSelectedFile();
    if (selectedFile != null) {
      StringTokenizer st = new StringTokenizer(selectedFile.getName());
      String lastToken = null;
      while (st.hasMoreTokens()) lastToken = st.nextToken(".");
      if (!lastToken.equalsIgnoreCase("task")) selectedFile =
          new File(selectedFile.getAbsolutePath() + ".task");
      saveTaskEvent(selectedFile);
    }
  }

  private class SavedTaskFileFilter extends FileFilter {
    public boolean accept(File file) {
      if (file.isDirectory()) return true;
      StringTokenizer st = new StringTokenizer(file.getName());
      String lastToken = null;
      while (st.hasMoreTokens()) lastToken = st.nextToken(".");
      return lastToken.equalsIgnoreCase("task");
    }
    public String getDescription() {
      return "Task";
    }
  }

  private void loadTaskEvent(File file) {
    boolean fileIsCorrect = true;
    for (int number = 0; number < listeners.size(); number++) {
      DiffractionPanelListener listener = (DiffractionPanelListener) listeners.get(number);
      if (!listener.loadTask(file)) fileIsCorrect=false;
    }
    if (!fileIsCorrect) {
      showMessage("This file is incorrect.");
    }
  }
  private void saveTaskEvent(File file) {
    boolean fileIsCorrect = true;
    for (int number = 0; number < listeners.size(); number++) {
      DiffractionPanelListener listener = (DiffractionPanelListener) listeners.get(number);
      if (!listener.saveTask(file)) fileIsCorrect=false;
    }
    if (!fileIsCorrect) {
      showMessage("This file is incorrect.");
    }
  }


  private void makeNewTask() {
    cont.removeAll();
    cont.setLayout(new BorderLayout());
    Object[] taskTypes;
    if (sourceOfTaskTypes!=null)
      taskTypes = sourceOfTaskTypes.getTaskTypes();
    else
      taskTypes = null;
    PanelForChoose panelForChoose = new PanelForChoose(
      taskTypes,
      "Choose task type please:",
      "There isn`t any task types",
      false
    );
    panelForChoose.addPanelForChooseListener(new PanelForChooseListener() {
      public void cancelButtonPressed() {
        showWelcome();
      }

      public void OKButtonPressed(Object selectedObject) {
        showWelcome();
        makeNewCurrentTaskEvent((TaskType)selectedObject);
      }
    });
    cont.add(panelForChoose);
    cont.validate();
  }

  public void showTask(Task p) {
    unlockTaskItems();
    addAlgorithmToCurrentTaskItem.setEnabled(true);
    removeAlgorithmFromCurrentTaskItem.setEnabled(true);
    taskPanel = new TaskPanel(p, description, startTask, stopTask);
    p.addTaskListener(new TaskListener() {
      public void errorHappened() {}
      public void solutionIsCalculate() {
        startItem.setEnabled(true);
        stopItem.setEnabled(false);
      }
    });
    cont.removeAll();
    cont.add(taskPanel);
    cont.validate();
  }

  private void unlockTaskItems() {
    saveItem.setEnabled(true);
    surfaceItem.setEnabled(true);
    reflectedFieldItem.setEnabled(true);
    impingingFieldItem.setEnabled(true);
    algorithmItem.setEnabled(true);
    taskImageItem.setEnabled(true);
    startItem.setEnabled(true);
    stopItem.setEnabled(false);
  }

  private void showAboutProgrammPanel() {
    if (taskPanel == null) {
      cont.removeAll();
      cont.setLayout(new BorderLayout());
      cont.add(description.getAboutProgrammPanel());
    } else {
      taskPanel.showHelpPane();
    }
    cont.validate();
    cont.repaint();
  }

  private void userWantToExitEvent() {
    for (int number = 0; number < listeners.size(); number++) {
      DiffractionPanelListener listener = (DiffractionPanelListener) listeners.get(number);
      listener.userWantToExit();
    }
  }

  public static void showMessage(String s) {
    JOptionPane.showMessageDialog(null,s);
  }
}

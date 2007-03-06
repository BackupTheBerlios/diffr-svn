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

package de.berlios.diffr.diffraction.manager;

import de.berlios.diffr.diffraction.algorithms.*;
import de.berlios.diffr.diffraction.arguments.*;
import de.berlios.diffr.diffraction.exceptions.*;
import de.berlios.diffr.diffraction.graphic.*;
import de.berlios.diffr.diffraction.graphic.controllers.ControllerType;
import de.berlios.diffr.diffraction.tasks.*;

import java.util.*;
import java.io.*;

public class Diffraction implements SourceOfTaskTypes {
  private boolean isApplet;
  private Task currentTask=null;
  private ArrayList taskTypes;
  private DiffractionPanel diffractionPanel;
  public Diffraction(DiffractionPanel panel,boolean isApplet) {
    this.isApplet=isApplet;
    this.diffractionPanel = panel;
    diffractionPanel.addDiffractionPanelListener(diffractionPanelListener);
    loadTaskTypes();
  }

  private void loadTaskTypes() {
    taskTypes=new ArrayList();
    try {
      TaskType type1 = new TaskType(
          "Very simple task",
          Class.forName("de.berlios.diffr.diffraction.surfaces.PlaneMirrowSurface"),
          Class.forName("de.berlios.diffr.diffraction.fields.impingingFields.VerySimpleImpingingField"),
          Class.forName("de.berlios.diffr.diffraction.fields.reflectedFields.VerySimpleReflectedField")
      );
      TaskType type2 = new TaskType(
          "Diffraction of plane wave on periodic surface",
          Class.forName("de.berlios.diffr.diffraction.surfaces.periodicSurface.PeriodicSurface"),
          Class.forName("de.berlios.diffr.diffraction.fields.impingingFields.PlaneImpingingWave"),
          Class.forName("de.berlios.diffr.diffraction.fields.reflectedFields.ReflectedFieldOfPlaneWaves")
      );
      ControllerType universalController =
          new ControllerType(
              Class.forName("de.berlios.diffr.diffraction.graphic.controllers.UniversalController"),
              "Universal"
          );
      ControllerType controllerType1 =
          new ControllerType(
              Class.forName("de.berlios.diffr.diffraction.graphic.controllers.PlaneMirrowSurfaceController"),
              "controller1"
          );
      ControllerType controllerType2 =
          new ControllerType(
              Class.forName("de.berlios.diffr.diffraction.graphic.controllers.SimpleImpingingFieldController"),
              "controller2"
          );
      ControllerType controllerType4 =
          new ControllerType(
              Class.forName("de.berlios.diffr.diffraction.graphic.controllers.SimpleImpingingFieldController2"),
              "controller4"
          );
      ControllerType controllerType3 =
          new ControllerType(
              Class.forName("de.berlios.diffr.diffraction.graphic.controllers.SimpleReflectedFieldController"),
              "controller3"
          );
      ArgumentType p1 = new ArgumentType("Time of work", new Long(0).getClass(), new Long(100));
      ArgumentType p2 = new ArgumentType("Deviation", new Double(0).getClass(), new Double(0));
      ArgumentType p3 = new ArgumentType("Δελενθε", new Double(0).getClass(), new Double(2));
      ArgumentType[] arguments1 = {p1, p2};
      ArgumentType[] arguments2 = {p1, p3};
      AlgorithmType a1 = new AlgorithmType(
         "Algorithm1",
         "Petr Mikheev",
         "1.0",
         arguments1,
         Class.forName("de.berlios.diffr.diffraction.algorithms.AlgorithmForVerySimpleTask")
      );
      AlgorithmType a2 = new AlgorithmType(
         "Algorithm2",
         "timka",
         "0.0",
         arguments2,
         Class.forName("de.berlios.diffr.diffraction.algorithms.Algorithm2ForVerySimpleTask")
      );
      AlgorithmType a3 = new AlgorithmType(
         "Small perturbation",
         "Mikheev Andrei",
         "0.01",
         new ArgumentType[0],
         Class.forName("de.berlios.diffr.diffraction.algorithms.SmallPerturbationAlgorithm")
      );

      type1.getSurfaceControllerTypes().add(controllerType1);
      type1.getSurfaceControllerTypes().add(universalController);
      type1.getImpingingFieldControllerTypes().add(controllerType2);
      type1.getImpingingFieldControllerTypes().add(controllerType4);
      type1.getImpingingFieldControllerTypes().add(universalController);
      type1.getReflectedFieldControllerTypes().add(controllerType3);
      type1.getReflectedFieldControllerTypes().add(universalController);
      type1.getAlgorithmTypes().add(a1);
      type1.getAlgorithmTypes().add(a2);
      taskTypes.add(type1);

      type2.getSurfaceControllerTypes().add(universalController);
      type2.getImpingingFieldControllerTypes().add(universalController);
      type2.getReflectedFieldControllerTypes().add(universalController);
      type2.getAlgorithmTypes().add(a3);
      taskTypes.add(type2);
    }
    catch (ClassNotFoundException ex) {
      ex.printStackTrace();
      System.err.println("Very simple task isn`t add");
    }
  }
  private DiffractionPanelListener diffractionPanelListener=
      new DiffractionPanelListener() {
        public void userWantToExit() {
          System.exit(0);
        }
        public void makeNewCurrentTask(TaskType type) {
          currentTask=new Task(type);
          diffractionPanel.showTask(currentTask);
        }
        public boolean loadTask(File file) {
            try {
              ObjectInputStream out = new ObjectInputStream(new
                  FileInputStream(file));
              Task currentTask = (Task)out.readObject();
              return true;
            }
            catch (ClassNotFoundException ex) {
              return false;
            }
            catch (IOException ex) {
              return false;
            }

        }
        public boolean saveTask(File file) {
          try {
            ObjectOutputStream out = new ObjectOutputStream(new
                FileOutputStream(file));
            out.writeObject(currentTask);
            return true;
          }
          catch (IOException ex) {
            return false;
          }
        }
      };

  public Object[] getTaskTypes() {
    return taskTypes.toArray();
  }
}

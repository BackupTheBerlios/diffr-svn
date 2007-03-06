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

package de.berlios.diffr.diffraction.tasks;

import de.berlios.diffr.diffraction.algorithms.*;
import de.berlios.diffr.diffraction.arguments.*;
import de.berlios.diffr.diffraction.exceptions.*;
import de.berlios.diffr.diffraction.fields.impingingFields.ImpingingField;
import de.berlios.diffr.diffraction.fields.reflectedFields.ReflectedField;
import de.berlios.diffr.diffraction.surfaces.Surface;
//import diffraction.graphic.controllers.Controller;
import java.util.*;
import java.io.*;

public class Task implements Serializable {

  private Surface surface = null;
  private ImpingingField impingingField = null;
  private ReflectedField reflectedField = null;
  private Algorithm algorithm = null;
  private double validity=0;
/*
  private Controller surfaceController=null;
  private Controller impingingFieldController=null;
  private Controller reflectedFieldController=null;
*/
  private TaskType type=null;

  private boolean taskIsSolving=false;
  private SolveThread solveThread=null;

  private ArrayList listeners=new ArrayList();

  public void addTaskListener(TaskListener l) {
    listeners.add(l);
  }
  public void removeTaskListener(TaskListener l) {
    listeners.remove(l);
  }

  public Task(TaskType type) {
    this.type=type;
    try {
      surface = (Surface) type.getSurfaceType().newInstance();
    }
    catch (Exception e) {}
    try {
      impingingField = (ImpingingField) type.getImpingingFieldType().newInstance();
    }
    catch (Exception e) {}
  }

  public ReflectedField getReflectedField() throws TaskIsSolvingException {
    if (taskIsSolving) throw new TaskIsSolvingException();
    return reflectedField;
  }

  public ImpingingField getImpingingField() {
    return impingingField;
  }

  public Surface getSurface() {
    return surface;
  }

  public Algorithm getAlgorithm() {
    return algorithm;
  }
  public double getValidity() throws TaskIsSolvingException {
    if (taskIsSolving) throw new TaskIsSolvingException();
    return validity;
  }
  public TaskType getType() {
    return type;
  }
  public void setSurface(Surface surface) throws WrongClassException,TaskIsSolvingException {
    if(taskIsSolving) {
      throw new TaskIsSolvingException();
    } else {
      if (surface.getClass().isAssignableFrom(type.getSurfaceType())) {
        this.surface = surface;
        unmakeResult();
      }
      else {
        throw new WrongClassException();
      }
    }
  }
  public void setImpingingField(ImpingingField impingingField) throws WrongClassException,TaskIsSolvingException {
    if(taskIsSolving) {
      throw new TaskIsSolvingException();
    } else {
      if (impingingField.getClass().isAssignableFrom(type.getImpingingFieldType())) {
        this.impingingField = impingingField;
        unmakeResult();
      }
      else {
        throw new WrongClassException();
      }
    }
  }
  public void setAlgorithm(Algorithm algorithm) throws WrongClassException,TaskIsSolvingException {
    if (taskIsSolving) {
      throw new TaskIsSolvingException();
    }
    else {
      ArrayList algorithmTypes=type.getAlgorithmTypes();
      boolean algorithmIsConform=false;
      for(int number=0;number<algorithmTypes.size();number++) {
        if (((AlgorithmType)algorithmTypes.get(number)).getAlgorithmClass()==algorithm.getClass()) {
          algorithmIsConform=true;
        }
      }
      if (algorithmIsConform) {
        this.algorithm=algorithm;
      } else {
        throw new WrongClassException();
      }
    }
  }
/*
  public void setSurfaceController(Controller surfaceController) throws WrongClassException {
    ArrayList surfaceControllerTypes = type.getSurfaceControllers();
    boolean surfaceControllerIsConform=false;
    for(int number=0;number<surfaceControllerTypes.size();number++) {
      if (surfaceControllerTypes.get(number).getClass()==surfaceController.getClass()) {
        surfaceControllerIsConform=true;
      }
    }
    if (surfaceControllerIsConform) {
      this.surfaceController=surfaceController;
    } else {
      throw new WrongClassException(null,algorithm.getClass());
    }
  }

  public void setImpingingFieldController(Controller impingingFieldController) throws WrongClassException {
    ArrayList impingingFieldControllerTypes=type.getImpingingFieldControllers();
    boolean impingingFieldControllerIsConform=false;
    for(int number=0;number<impingingFieldControllerTypes.size();number++) {
      if (impingingFieldControllerTypes.get(number).getClass()==surfaceController.getClass()) {
        impingingFieldControllerIsConform=true;
      }
    }
    if (impingingFieldControllerIsConform) {
      this.impingingFieldController=impingingFieldController;
    } else {
      throw new WrongClassException(null,algorithm.getClass());
    }
  }

  public void setReflectedFieldController(Controller reflectedFieldController) throws WrongClassException {
    ArrayList reflectedFieldControllerTypes=type.getReflectedFieldControllers();
    boolean reflectedFieldControllerIsConform=false;
    for(int number=0;number<reflectedFieldControllerTypes.size();number++) {
      if (reflectedFieldControllerTypes.get(number).getClass()==surfaceController.getClass()) {
        reflectedFieldControllerIsConform=true;
      }
    }
    if (reflectedFieldControllerIsConform) {
      this.impingingFieldController=impingingFieldController;
    } else {
      throw new WrongClassException(null,algorithm.getClass());
    }
  }

  public Controller getSurfaceController() {
    return surfaceController;
  }

  public Controller getImpingingFieldController() {
    return impingingFieldController;
  }

  public Controller getReflectedFieldController() {
    return reflectedFieldController;
  }
*/
  private void unmakeResult() throws TaskIsSolvingException {
    if (taskIsSolving) throw new TaskIsSolvingException();
    reflectedField=null;
    validity=0;
  }

  public void solve() throws TaskIsSolvingException, AlgorithmDoesNotChoosedException {
    if (taskIsSolving) throw new TaskIsSolvingException();
    if (algorithm == null) throw new AlgorithmDoesNotChoosedException();
    taskIsSolving=true;
    solveThread=new SolveThread();
    solveThread.start();
  }

  public void stop() throws TaskIsNotSolvingException {
    if (!taskIsSolving) throw new TaskIsNotSolvingException();
    solveThread.stop();
    taskIsSolving=false;
    try {
      unmakeResult();
    }
    catch (TaskIsSolvingException ex) {
      ex.printStackTrace();
    }
  }

  private void errorHappened() {
    taskIsSolving = false;
    for(int number=0;number<listeners.size();number++) {
      TaskListener listener=(TaskListener)listeners.get(number);
      listener.errorHappened();
    }
  }
  private void solutionIsCalculate() {
    taskIsSolving = false;
    for(int number=0;number<listeners.size();number++) {
      TaskListener listener=(TaskListener)listeners.get(number);
      listener.solutionIsCalculate();
    }
  }

  private Task This=this;
  private class SolveThread extends Thread {
    public void run() {
      try {
        TaskResults results = algorithm.solve(This);
        reflectedField = results.getReflectedField();
        solutionIsCalculate();
      } catch (ErrorInAlgorithmException e) {
        errorHappened();
      } catch (Exception e) {
        e.printStackTrace();
        errorHappened();
      }
    }
  }
}

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

import de.berlios.diffr.diffraction.exceptions.WrongClassException;
import de.berlios.diffr.diffraction.graphic.Controllable;

import java.lang.reflect.*;

public class ControllerType {
  private Class controllerClass;
  private String name;
  public ControllerType(Class controllerClass, String name) {
    this.controllerClass = controllerClass;
    this.name = name;
  }
  public String toString() {
    return name;
  }

  public Controller getInstance(Controllable object) throws WrongClassException {
    try {
      Class[] argumentTypes = {
          Class.forName("de.berlios.diffr.diffraction.graphic.Controllable")};
      Object[] arguments = {
          object};
      return (Controller) controllerClass.getConstructor(argumentTypes).
          newInstance(arguments);
    }
    catch (SecurityException ex) {
      ex.printStackTrace();
      throw new WrongClassException();
    }
    catch (NoSuchMethodException ex) {
      ex.printStackTrace();
      throw new WrongClassException();
    }
    catch (InvocationTargetException ex) {
      throw new WrongClassException();
    }
    catch (IllegalArgumentException ex) {
      ex.printStackTrace();
      throw new WrongClassException();
    }
    catch (IllegalAccessException ex) {
      ex.printStackTrace();
      throw new WrongClassException();
    }
    catch (InstantiationException ex) {
      ex.printStackTrace();
      throw new WrongClassException();
    }
    catch (ClassNotFoundException ex) {
      ex.printStackTrace();
      throw new WrongClassException();
    }
  }

}

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

import java.util.*;
import java.io.*;
public class TaskType implements Serializable {
  private ArrayList algorithmTypes;
  private String name;
  private Class surfaceType;
  private ArrayList surfaceControllerTypes;
  private Class impingingFieldType;
  private ArrayList impingingFieldControllerTypes;
  private Class reflectedFieldType;
  private ArrayList reflectedFieldControllerTypes;
  public TaskType
      (String name,Class surfaceType,Class impingingFieldType,Class reflectedFieldType) {
    algorithmTypes = new ArrayList();
    this.name = name;
    this.surfaceType = surfaceType;
    surfaceControllerTypes = new ArrayList();
    this.impingingFieldType = impingingFieldType;
    impingingFieldControllerTypes = new ArrayList();
    this.reflectedFieldType = reflectedFieldType;
    reflectedFieldControllerTypes = new ArrayList();
  }
  public String getName() {
    return name;
  }
  public String toString() {
    return name;
  }
  public Class getSurfaceType() {
    return surfaceType;
  }
  public Class getImpingingFieldType() {
    return impingingFieldType;
  }
  public Class getReflectedFieldType() {
    return reflectedFieldType;
  }
  public ArrayList getAlgorithmTypes() {
    return algorithmTypes;
  }
  public ArrayList getImpingingFieldControllerTypes() {
    return impingingFieldControllerTypes;
  }
  public ArrayList getReflectedFieldControllerTypes() {
    return reflectedFieldControllerTypes;
  }
  public ArrayList getSurfaceControllerTypes() {
    return surfaceControllerTypes;
  }

}

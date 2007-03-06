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

import de.berlios.diffr.diffraction.arguments.*;

public class AlgorithmType {
  private String name;
  private String autor;
  private String version;
  private ArgumentType[] arguments;
  private Class algorithm;
  public AlgorithmType(String name, String autor, String version, ArgumentType[] arguments, Class algorithm) {
    this.name = name;
    this.autor = autor;
    this.version = version;
    this.arguments = arguments;
    this.algorithm = algorithm;
  }
  public String getName() {
    return name;
  }
  public String getAutor() {
    return autor;
  }
  public String getVersion() {
    return version;
  }
  public ArgumentType[] getArguments() {
    return arguments;
  }

  public String toString() {
    return name + ' ' + version;
  }

  public Class getAlgorithmClass() {
    return algorithm;
  }
}

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

package de.berlios.diffr.diffraction.surfaces.periodicSurface;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.diffraction.arguments.ArgumentType;
import de.berlios.diffr.diffraction.surfaces.Surface;

public class PeriodicSurface extends Surface {
  public PeriodicSurface() {}
  public PeriodicSurface(SurfaceShape shape, SurfaceConductivity conductivity) {
    this.shape = shape;
    this.conductivity = conductivity;
  }

  private Long conductivityType = new Long(0);

  public PeriodicSurface(SurfaceShape shape, Long conductivityType, Complex conductivityEpsilon) {
    this.shape = shape;
    this.conductivityType = conductivityType;
    switch (conductivityType.intValue()) {
      case 0: conductivity = new PerfectConductivity(); break;
      case 1: conductivity = new HeightConductivity(conductivityEpsilon); break;
      case 2: conductivity = new RealConductivity(conductivityEpsilon); break;
    }
  }

  public ArgumentType[] getArguments() {
    try {
      ArgumentType shapeArgument = new ArgumentType("Shape", shape.getClass(), shape);
      ArgumentType conductivityTypeArgument = new ArgumentType("Conductivity Type \n (0 - pefect, 1 - height, 2 - real)",
          Class.forName("java.lang.Long"), new Long(0));
      ArgumentType conductivityEpsilonArgument = new ArgumentType("Conductivity Epsilon",
          new Complex(1, 0).getClass(), new Complex(1, 0));
      ArgumentType[] arguments = {
          shapeArgument, conductivityTypeArgument, conductivityEpsilonArgument};
      return arguments;
    }
    catch (ClassNotFoundException ex) {
      return null;
    }
  }
  public Object getArgumentValue(int number) {
    switch (number) {
      case 0:return shape;
      case 1:return conductivityType;
      case 2:
        switch (conductivityType.intValue()) {
          case 0:return new Complex(1, 0);
          case 1:return ((HeightConductivity)conductivity).getEpsilon();
          case 2:return ((RealConductivity)conductivity).getEpsilon();
        } break;
    }
    return null;
  }

  public SurfaceShape getShape() {
    return shape;
  }

  public SurfaceConductivity getConductivity() {
    return conductivity;
  }

  private SurfaceShape shape = new SurfaceShape();
  private SurfaceConductivity conductivity = new PerfectConductivity();
}

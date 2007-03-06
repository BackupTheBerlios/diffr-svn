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

import de.berlios.diffr.diffraction.arguments.*;
import de.berlios.diffr.diffraction.graphic.Controllable;

public class SurfaceShape implements Controllable {
  public SurfaceShape() {}
  public SurfaceShape(Double period, Double shift,
                      Long numberOfFourierCoefficients,
                      ArrayListArgument coefficientsOfCosinus,
                      ArrayListArgument coefficientsOfSinus) {
    this.period = period.doubleValue();
    this.shift = shift.doubleValue();
    this.numberOfFourierCoefficients = numberOfFourierCoefficients.intValue();
    this.coefficientsOfCosinus = coefficientsOfCosinus;
    this.coefficientsOfSinus = coefficientsOfSinus;
  }

  public ArgumentType[] getArguments() {
    Class doubleClass = new Double(0).getClass();
    Class longClass = new Long(0).getClass();
    ArgumentType periodArgument = new ArgumentType("Period", doubleClass, new Double(period));
    ArgumentType shiftArgument = new ArgumentType("Shift", doubleClass, new Double(shift));
    ArgumentType numberOfFourierCoefficientsArgument = new ArgumentType("Number Of Fourier Coefficients", longClass, new Long(numberOfFourierCoefficients));
    ArgumentType coefficientsOfCosinusArgument = new ArgumentType("Coefficients Of cosinus", coefficientsOfCosinus.getClass(), coefficientsOfCosinus);
    ArgumentType coefficientsOfSinusArgument = new ArgumentType("Coefficients Of sinus", coefficientsOfSinus.getClass(), coefficientsOfSinus);
    ArgumentType[] arguments = {periodArgument, shiftArgument, numberOfFourierCoefficientsArgument, coefficientsOfCosinusArgument, coefficientsOfSinusArgument};
    return arguments;
  }

  public Object getArgumentValue(int number) {
    switch (number) {
      case 0:return new Double(period);
      case 1:return new Double(shift);
      case 2:return new Long(numberOfFourierCoefficients);
      case 3:return coefficientsOfCosinus;
      case 4:return coefficientsOfSinus;
    }
    return null;
  }

  private double period = 1;
  private double shift = 0;
  private long numberOfFourierCoefficients = 0;
  private ArrayListArgument coefficientsOfCosinus =
      new ArrayListArgument(new ArgumentType("", new Double(0).getClass(), new Double(0)));
  private ArrayListArgument coefficientsOfSinus =
      new ArrayListArgument(new ArgumentType("", new Double(0).getClass(), new Double(0)));

  public double getPeriod() {
    return period;
  }

  public double getShift() {
    return shift;
  }

  public long getNumberOfFourierCoefficients() {
    return numberOfFourierCoefficients;
  }

  public ArrayListArgument getCoefficientsOfCosinus() {
    return coefficientsOfCosinus;
  }

  public ArrayListArgument getCoefficientsOfSinus() {
    return coefficientsOfSinus;
  }

}

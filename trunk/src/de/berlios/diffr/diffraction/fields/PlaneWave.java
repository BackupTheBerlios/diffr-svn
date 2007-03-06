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

package de.berlios.diffr.diffraction.fields;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.diffraction.arguments.ArgumentType;
import de.berlios.diffr.diffraction.graphic.Controllable;

public class PlaneWave implements Controllable {
  public static final boolean polarizationE = false;
  public static final boolean polarizationH = true;

  public PlaneWave() {}
  public PlaneWave(Boolean polarization, Double angle, Double waveLength, Complex amplitude) {
    if (polarization == null) throw new NullPointerException();
    if (amplitude == null) throw new NullPointerException();
    if (angle == null) throw new NullPointerException();
    if (waveLength == null) throw new NullPointerException();
    this.amplitude = amplitude;
    this.angle = angle.doubleValue();
    this.polarization = polarization.booleanValue();
    this.waveLength = waveLength.doubleValue();
  }

  private boolean polarization = PlaneWave.polarizationE;
  private double angle = 1;
  private Complex amplitude = new Complex(1, 1);
  private double waveLength = 10;

  public double getLength() {
    return waveLength;
  }

  public double getAngle() {
    return angle;
  }

  public Complex getAmplitude() {
    return amplitude;
  }

  public boolean getPolarization() {
    return polarization;
  }

  public ArgumentType[] getArguments() {
    Class doubleClass = new Double(0).getClass();
    Class booleanClass = new Boolean(false).getClass();
    ArgumentType polarizationArgument = new ArgumentType("Polarization", booleanClass, new Boolean(polarization));
    ArgumentType angleArgument = new ArgumentType("Angle", doubleClass, new Double(angle));
    ArgumentType waveLengthArgument = new ArgumentType("Wave length", doubleClass, new Double(waveLength));
    ArgumentType amplitudeArgument = new ArgumentType("Amplitude", amplitude.getClass(), amplitude);
    ArgumentType[] arguments = {polarizationArgument, angleArgument, waveLengthArgument, amplitudeArgument};
    return arguments;
  }

  public Object getArgumentValue(int number) {
    switch (number) {
      case 0:return new Boolean(polarization);
      case 1:return new Double(angle);
      case 2:return new Double(waveLength);
      case 3:return amplitude;
    }
    return null;
  }
}

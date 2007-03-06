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

package de.berlios.diffr.diffraction.fields.reflectedFields;

import de.berlios.diffr.diffraction.arguments.*;
import de.berlios.diffr.diffraction.fields.PlaneWave;

public class ReflectedFieldOfPlaneWaves implements ReflectedField {
  private ArrayListArgument waves;
  public ReflectedFieldOfPlaneWaves(ArrayListArgument waves) {
    this.waves = waves;
  }

  public ArrayListArgument getWaves() {
    return (ArrayListArgument)waves.clone();
  }

  public ArgumentType[] getArguments() {
    ArgumentType[] arguments = {new ArgumentType("Waves", waves.getClass(), waves)};
    return arguments;
  }
  public Object getArgumentValue(int number) {
    return waves;
  }
}

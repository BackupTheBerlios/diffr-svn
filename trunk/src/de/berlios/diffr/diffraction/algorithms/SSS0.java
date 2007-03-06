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
 * @author Andrei Mikheev
 */

package de.berlios.diffr.diffraction.algorithms;

import Org.netlib.math.complex.Complex;
import de.berlios.diffr.diffraction.tasks.TaskResults;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SSS0 {

  int IPL;
  int IBC;
  int IALG;
  int IDIM;
  int ICAR;
  long NG;
  double [] RS1;
  double [] RC1;
  double AK1;
  double UGOL;
  Complex EPSLN;

  public SSS0(int IPL, int IBC, int IALG, int IDIM, int ICAR, long NG, double [] RS1, double [] RC1, double AK1, double UGOL, Complex EPSLN) {
        this.IPL = IPL;
        this.IBC = IBC;
        this.IALG = IALG;
        this.IDIM = IDIM;
        this.ICAR = ICAR;
        this.NG = NG;
        this.RS1 = RS1;
        this.RC1 = RC1;
        this.AK1 = AK1;
        this.UGOL = UGOL;
        this.EPSLN = EPSLN;
  }

TaskResults calculate() {
  Complex F[] = new Complex[1000];
  Complex VS[] = new Complex[104];
  System.out.println("SSS0 is being calculated now " + "\n" );

  SPM alg = new  SPM(IPL,IBC,IDIM,ICAR,NG,RS1,RC1,AK1,UGOL,EPSLN,F,VS);
  return alg.calculate();
}

}

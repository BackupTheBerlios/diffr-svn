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

import Org.netlib.math.complex.Complex;


public class Functions {

    public static Complex GMMMA(int n){
    	
    	double S;
		Complex SS;
//        COMMON/DDZ/T,B,AK,PI,EEE
        S=DDZ.AK*DDZ.AK-ALAMB(n)*ALAMB(n);
        
        if (S >= 0) {
        	SS = new Complex (Math.sqrt(S) ,0.0);
        } else {
        	SS = new Complex (0.0 ,Math.sqrt(-S));
        }
        return SS;
    }

    public static double ALAMB(int n) {
//	        COMMON/DDZ/T,B,AK,PI,EEE
	          return DDZ.T/DDZ.B + n;
	}
		
    public static double AAAG(double YP) {
           double S=0;
        for (int i =1; i<=DSV.NG; i++) {
              S=S+i*(DSV.RS[i]*Math.cos(i*YP)-DSV.RC[i]*Math.sin(i*YP));
        }      
           double S1= Math.atan(S);
           return S1;
 }
    
    public static Complex DU0DN(double YP) {
//        COMPLEX EEE,GMMMA,U0
    	Complex S;
//       COMMON/DDZ/T,B,AK,PI,EEE
//       COMMON/ZAZ/NNN
       double G=AAAG(YP);
       S=DDZ.EEE.mul( 
    		   (GMMMA(ZAZ.NNN).mul(new Complex(Math.cos(G),0.0))).add
    		   (  new Complex(ALAMB(ZAZ.NNN)*Math.sin(G),0.0) ) 
    		   ).mul(U0(YP));
    	return S;
}

    public static Complex U0(double YP) {
//        COMPLEX EEE,GMMMA
//      COMMON/DDZ/T,B,AK,PI,EEE
//      COMMON/ZAZ/NNN
    	Complex S = DDZ.EEE.mul( 
    			(GMMMA(ZAZ.NNN).mul(new Complex(XX(YP),0.0))).add(
    				new Complex(ALAMB(ZAZ.NNN)*YP,0.0)
    			    ) 
    			);
    	Complex S0 = CEXP(S); 
//      U0=CEXP(EEE*( GMMMA(NNN)*XX(YP)+ALAMB(NNN)*YP))
		return S0;
	}

	public static Complex CEXP(Complex s) {
		double re = s.re();
		double im = s.im();
		double exp = Math.exp(re);
		double cosinus = Math.cos(im); 
		double sinus = Math.sin(im); 
		return new Complex(exp*cosinus, exp*sinus);
	}

	public static double XX(double YP) {
//        COMMON/DSV/RS(25),RC(25),NG
        double S=0;
//     DO 15 I=1,NG
        for (int i = 1; i<= DSV.NG; i++) {
            S=S+DSV.RS[i]*Math.sin(i*YP)+DSV.RC[i]*Math.cos(i*YP);
        }
//        S=S+RS(I)*SIN(I*YP)+RC(I)*COS(I*YP)
//15      CONTINUE
     return -S;
	}
    
    
}

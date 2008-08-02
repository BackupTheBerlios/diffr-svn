package de.berlios.diffr.math;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

import Org.netlib.math.complex.Complex;

public class ComplexFormula {
	public static void main(String[] args) {
		// example
		Complex a = new Complex(1, 2);
		Complex b = new Complex(5, -1);
		ComplexFormula formula1 = new ComplexFormula("x y", "x*y/(x^y-y)");
		Complex ans1 = formula1.calculate(new Complex[]{a, b}); // x=a  y=b
		Complex ans2 = formula1.calculate(new Complex[]{b, a}); // x=b  y=a
		System.out.println(ans1);
		System.out.println(ans2);
		Complex ans = new ComplexFormula("a", "asin(a)").calculate(new Complex[]{a});
		System.out.println(ans);
		ans = new ComplexFormula("a b", "( exp(a-b)-exp(b) ) / (a+b)").calculate(new Complex[]{a, b});
		System.out.println(ans);
	}
	private ArrayList<Integer> list = new ArrayList<Integer>();
	private TreeMap<String, Integer> map = new TreeMap<String, Integer>();
	private char[] form;
	private int pos = 0;
	private String nextToken() {
		while (pos<form.length && form[pos]==' ') pos++;
		if (pos>=form.length) return null;
		String ans = "";
		if (form[pos]=='(') ans = "(";
		if (form[pos]==')') ans = ")";
		if (form[pos]=='+') ans = "+";
		if (form[pos]=='-') ans = "-";
		if (form[pos]=='*') ans = "*";
		if (form[pos]=='/') ans = "/";
		if (form[pos]=='^') ans = "^";
		if (ans.length()==0) {
			while (pos<form.length && form[pos]!='(' && form[pos]!=')' && form[pos]!='+' &&
					form[pos]!='-' && form[pos]!='*' && form[pos]!='/' && form[pos]!=' ' && form[pos]!='^')
				ans += form[pos++];
		} else {
			pos++;
		}
		return ans;
	}
	private void readSimple() {
		String s = nextToken();
		if (s.equals("(")) {
			readTerm();
			s = nextToken();
			if (!s.equals(")")) System.err.println("Error");
			return;
		}
		Integer i = map.get(s);
		if (i!=null) {
			list.add(i);
			return;
		}
		if (s.equals("acos")) {
			readParam();
			list.add(-7);
			return;
		}
		if (s.equals("acosh")) {
			readParam();
			list.add(-8);
			return;
		}
		if (s.equals("asin")) {
			readParam();
			list.add(-9);
			return;
		}
		if (s.equals("asinh")) {
			readParam();
			list.add(-10);
			return;
		}
		if (s.equals("atan")) {
			readParam();
			list.add(-11);
			return;
		}
		if (s.equals("atanh")) {
			readParam();
			list.add(-12);
			return;
		}
		if (s.equals("conj")) {
			readParam();
			list.add(-13);
			return;
		}
		if (s.equals("cos")) {
			readParam();
			list.add(-14);
			return;
		}
		if (s.equals("cosec")) {
			readParam();
			list.add(-15);
			return;
		}
		if (s.equals("cosh")) {
			readParam();
			list.add(-16);
			return;
		}
		if (s.equals("cot")) {
			readParam();
			list.add(-17);
			return;
		}
		if (s.equals("exp")) {
			readParam();
			list.add(-18);
			return;
		}
		if (s.equals("log")) {
			readParam();
			list.add(-19);
			return;
		}
		if (s.equals("sec")) {
			readParam();
			list.add(-20);
			return;
		}
		if (s.equals("sin")) {
			readParam();
			list.add(-21);
			return;
		}
		if (s.equals("sinh")) {
			readParam();
			list.add(-22);
			return;
		}
		if (s.equals("sqrt")) {
			readParam();
			list.add(-23);
			return;
		}
		if (s.equals("tan")) {
			readParam();
			list.add(-23);
			return;
		}
		if (s.equals("tanh")) {
			readParam();
			list.add(-23);
			return;
		}
		System.err.println("Error");
	}
	
	private void readParam() {
		String s;
		s = nextToken();
		if (!s.equals("(")) System.err.println("Error");
		readTerm();
		s = nextToken();
		if (!s.equals(")")) System.err.println("Error");
	}
	
	private void readMultiplier() {
		int p2 = pos;
		String s = nextToken();
		if (s.equals("-")) {
			readMultiplier();
			list.add(-6);
			return;
		}
		pos = p2;
		readSimple();
		p2 = pos;
		s = nextToken();
		if (s==null || !s.equals("^")) {
			pos = p2;
			return;
		}
		readSimple();
		list.add(-5);
	}
	
	private void readAddend() {
		readMultiplier();
		while (pos<form.length) {
			int p2 = pos;
			String s = nextToken();
			if (s.equals("+") || s.equals("-") || s.equals(")")) {
				pos = p2;
				break;
			}
			readMultiplier();
			if (s.equals("*")) {
				list.add(-3);
				continue;
			}
			if (s.equals("/")) {
				list.add(-4);
				continue;
			}
			System.err.println("Error");
		}		
	}
	private void readTerm() {
		readAddend();
		while (pos<form.length) {
			int p2 = pos;
			String s = nextToken();
			if (s.equals(")")) {
				pos = p2;
				break;
			}
			if (s==null) break;
			if (s==")")
				break;
			readAddend();
			if (s.equals("+")) {
				list.add(-1);
				continue;
			}
			if (s.equals("-")) {
				list.add(-2);
				continue;
			}
			System.err.println("Error");
		}		
	}
	public ComplexFormula(String params, String formula) {
		this.form = formula.toCharArray();
		StringTokenizer st = new StringTokenizer(params);
		int i = 0;
		while (st.hasMoreTokens())
			map.put(st.nextToken(), i++);
		readTerm();
	}
	public Complex calculate(Complex[] params) {
		Stack<Complex> stack = new Stack<Complex>();
		for (int i:list) {
			if (i>=0) {
				stack.push(params[i]);
				continue;
			}
			switch (i) {
			case -1: // +
				stack.push(stack.pop().add(stack.pop()));
				break;
			case -2: // -
				stack.push(stack.pop().sub(stack.pop()));
				break;
			case -3: // *
				stack.push(stack.pop().mul(stack.pop()));
				break;
			case -4: // /
				stack.push(stack.pop().div(stack.pop()));
				break;
			case -5: // ^
				stack.push(stack.pop().pow(stack.pop()));
				break;
			case -6: // unary -
				stack.push(stack.pop().neg());
				break;
			case -7: // acos
				stack.push(stack.pop().acos());
				break;
			case -8: // acosh
				stack.push(stack.pop().acosh());
				break;
			case -9: // asin
				stack.push(stack.pop().asin());
				break;
			case -10: // asinh
				stack.push(stack.pop().asinh());
				break;
			case -11: // atan
				stack.push(stack.pop().atan());
				break;
			case -12: // atanh
				stack.push(stack.pop().atanh());
				break;
			case -13: // conj
				stack.push(stack.pop().conj());
				break;
			case -14: // cos
				stack.push(stack.pop().cos());
				break;
			case -15: // cosec
				stack.push(stack.pop().cosec());
				break;
			case -16: // cosh
				stack.push(stack.pop().cosh());
				break;
			case -17: // cot
				stack.push(stack.pop().cot());
				break;
			case -18: // exp
				stack.push(stack.pop().exp());
				break;
			case -19: // log
				stack.push(stack.pop().log());
				break;
			case -20: // sec
				stack.push(stack.pop().sec());
				break;
			case -21: // sin
				stack.push(stack.pop().sin());
				break;
			case -22: // sinh
				stack.push(stack.pop().sinh());
				break;
			case -23: // sqrt
				stack.push(stack.pop().sqrt());
				break;
			case -24: // tan
				stack.push(stack.pop().tan());
				break;
			case -25: // tanh
				stack.push(stack.pop().tanh());
				break;
			}
		}
		return stack.pop();
	}
}

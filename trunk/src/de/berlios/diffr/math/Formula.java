package de.berlios.diffr.math;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import Org.netlib.math.complex.Complex;

public class Formula {
	
	public static void main(String[] args) {
		System.out.println("Example");
		Object o = new Object() {
				public double a=1;
				public double b=2;
				public Complex c = new Complex(1, 2);
				public Complex m123(Complex x) {
					return x.sub(x.re());
				}
		};
		System.out.println(calculate("(c-a)^b+4", o));
		System.out.println(calculate("i^i", o));
		System.out.println(calculate("sin(a)+cos(b)-log(c)", o));
		System.out.println(calculate("m123 ( sin(a)+cos(b)-log(c) )", o));
	}
	
	public static Complex calculate(String formula, Object params) {
		return new Formula(formula, params).result;
	}
	
	private Formula(String formula, Object params) {
		this.params = params;
		form = formula.toCharArray();
		result = readTerm();
	}

	private Complex readTerm() {
		Complex res = readAddend();
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
			Complex next = readAddend();
			if (s.equals("+")) {
				res = res.add(next);
				continue;
			}
			if (s.equals("-")) {
				res = res.sub(next);
				continue;
			}
			System.err.println("Error in read term :"+pos);
		}
		if (res.isNaN())
			System.err.println("Error in read term (NaN) :"+pos);
		return res;
	}
	
	private Complex readAddend() {
		Complex res = readMultiplier();
		while (pos<form.length) {
			int p2 = pos;
			String s = nextToken();
			if (s.equals("+") || s.equals("-") || s.equals(")")) {
				pos = p2;
				break;
			}
			Complex next = readMultiplier();
			if (s.equals("*")) {
				res = res.mul(next);
				continue;
			}
			if (s.equals("/")) {
				res = res.div(next);
				continue;
			}
			System.err.println("Error in read addend :"+pos);
		}
		if (res.isNaN())
			System.err.println("Error in read addend (NaN) :"+pos);
		return res;
	}
	
	private Complex readMultiplier() {
		int p2 = pos;
		String s = nextToken();
		if (s.equals("-")) {
			Complex res = readMultiplier().neg();
			if (res.isNaN())
				System.err.println("Error in read multiplier (neg)");
			return res;
		}
		pos = p2;
		Complex res = readSimple();
		p2 = pos;
		s = nextToken();
		if (s==null || !s.equals("^")) {
			pos = p2;
			if (res.isNaN())
				System.err.println("Error in read multiplier");
			return res;
		}
		res = res.pow(readSimple());
		if (res.isNaN())
			System.err.println("Error in read multiplier (pow)");
		return res;
	}
	
	private Complex readSimple() {
		String s = nextToken();
		if (s.equals("(")) {
			Complex res = readTerm();
			s = nextToken();
			if (!s.equals(")")) System.err.println("Error read simple ()");
			if (res.isNaN())
				System.err.println("Error in read simple (NaN)");
			return res;
		}
		if (s.equals("i"))
			return Complex.i;
		try {
			Double res = Double.parseDouble(s);
			return new Complex(res);
		} catch (Exception e) {}
		try {
			Field field = params.getClass().getField(s);
			Object o = field.get(params);
			return makeComplex(o);
		} catch (Exception e) {}
		String s2;
		s2 = nextToken();
		if (!s2.equals("(")) System.err.println("Error in read simple "+s+"(");
		Complex pr = readTerm();
		s2 = nextToken();
		if (!s2.equals(")")) System.err.println("Error in read simple )");
		try {
			Method method = Complex.class.getMethod(s, new Class[0]);
			Object o = method.invoke(pr);
			Complex res = makeComplex(o);
			if (res.isNaN())
				System.err.println("Error in read simple (NaN in method)");
			return res;
		} catch (Exception e) {}
		try {
			Method method = params.getClass().getMethod(s, new Class[]{Complex.class});
			Object o = method.invoke(params, new Object[]{pr});
			Complex res = makeComplex(o);
			if (res.isNaN())
				System.err.println("Error in read simple (NaN in method)");
			return res;
		} catch (Exception e) {}
		System.err.println("Error in read simle");
		return null;
	}
	
	private Complex makeComplex(Object o) {
		if (Complex.class.isInstance(o))
			return (Complex)o;
		if (Number.class.isInstance(o))
			return new Complex((Double)o);
		System.err.println("Error");
		return null;
	}
	
	private Object params;
	private Complex result;
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
}

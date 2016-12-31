package com.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * java反射的demo
 * @author wangpeng
 *
 */
public class InvokeTest {
	public static void main(String[] args) {
		String url = "com.model.Person";
		try {
			Class cl = Class.forName(url);
			// 类修饰符
			String modifier = Modifier.toString(cl.getModifiers());
			// 父类
			Class superClass = cl.getSuperclass();
			Method[] methods = cl.getMethods();
			System.out.print(modifier + " " + cl.getName());
			if (superClass != null && superClass != Object.class) {
				System.out.print("extends " + superClass.getName() + " \n");
			}

			System.out.print("{\n");

			printConstructors(cl);
			printMethod(cl);
			printParam(cl);

			System.out.print("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印构造器
	 * 
	 * @param cl
	 */
	@SuppressWarnings("rawtypes")
	private static void printConstructors(Class cl) {
		Constructor[] consu = cl.getDeclaredConstructors();
		for (Constructor c : consu) {
			// 构造器名
			String name = c.getName();
			System.out.print("	");
			String midifiers = Modifier.toString(c.getModifiers());
			if (midifiers.length() > 0) {
				System.out.print(midifiers + "	");
			}
			System.out.print(name + "(");
			Class[] paramType = c.getParameterTypes();
			for (int i = 0; i < paramType.length; i++) {
				Class type = paramType[i];
				if (i > 0) {
					System.out.print("," + type.getName());
				} else {
					System.out.print(type.getName());
				}
			}
			System.out.print(") \n");
		}
	}

	/**
	 * 打印方法
	 * 
	 * @param cl
	 */
	private static void printMethod(Class cl) {
		Method[] ms = cl.getMethods();
		for (Method m : ms) {
			String methodModifier = Modifier.toString(m.getModifiers());
			String resultType = m.getReturnType().getName();
			System.out.print("	" + methodModifier + " " + resultType + " "
					+ m.getName() + "(");
			Class[] paramType = m.getParameterTypes();
			for (int i = 0; i < paramType.length; i++) {
				if (i == 0) {
					System.out.print(paramType[i].getName());
				} else {
					System.out.print("," + paramType[i].getName());
				}
			}
			System.out.print(")\n");
		}
	}

	private static void printParam(Class cl) {
		Field[] fs = cl.getDeclaredFields();
		for (Field f : fs) {
			String fieldModifier = Modifier.toString(f.getModifiers());
			System.out.print("	" + fieldModifier + " " + f.getType() + " "
					+ f.getName() + "\n");
		}
	}
}

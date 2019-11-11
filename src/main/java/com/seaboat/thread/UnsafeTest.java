package com.seaboat.thread;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeTest {
	private int flag = 100;
	private static long offset;
	private static Unsafe unsafe = null;
	static {
		try {
			unsafe = getUnsafeInstance();
			offset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("flag"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		int expect = 100;
		int update = 101;
		UnsafeTest unsafeTest = new UnsafeTest();
		System.out.println("unsafeTest�����flag�ֶεĵ�ַƫ����Ϊ��" + offset);
		unsafeTest.doSwap(offset, expect, update);
		System.out.println("CAS�������flagֵΪ��" + unsafeTest.getFlag());
	}
	private boolean doSwap(long offset, int expect, int update) {
		return unsafe.compareAndSwapInt(this, offset, expect, update);
	}
	public int getFlag() {
		return flag;
	}
	private static Unsafe getUnsafeInstance()
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeInstance.setAccessible(true);
		return (Unsafe) theUnsafeInstance.get(Unsafe.class);
	}
}

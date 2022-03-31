package com;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.time.Duration;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws Exception {

		int hours = 0;
		int minutes = 0;
		int seconds = 100;

		if (args != null) {
			if (args.length > 0 && isNumber(args[0])) {
				hours = Integer.valueOf(args[0]);
			}
			if (args.length > 1 && isNumber(args[1])) {
				minutes = Integer.valueOf(args[1]);
			}
			if (args.length > 2 && isNumber(args[2])) {
				seconds = Integer.valueOf(args[2]);
			}
		}

		double duration = ((hours * 60 * 60) + (minutes * 60) + seconds) * 1000;

		double secondsDouble = Math.floor((duration / 1000) % 60);
		double minutesDouble = Math.floor((duration / (1000 * 60)) % 60);
		double hoursDouble = Math.floor((duration / (1000 * 60 * 60)) % 24);
		System.out.println("Program will run for " + String.format("%.0f", hoursDouble) + " hours "
				+ String.format("%.0f", minutesDouble) + " minutes " + String.format("%.0f", secondsDouble)
				+ " seconds.");
		long time = System.currentTimeMillis();

		Thread robotMouseMover = new Thread(() -> {
			try {
				Robot robot = new Robot();
				double x1 = 0;
				double x2 = 0;
				double y1 = 0;
				double y2 = 0;
				while (System.currentTimeMillis() - time <= duration) {
					Random random = new Random();
					x2 = random.nextInt(1000);
					y2 = random.nextInt(1000);
					mouseGlide(robot, x1, x2, y1, y2, random.nextInt(4));
					x1 = MouseInfo.getPointerInfo().getLocation().x;
					y1 = MouseInfo.getPointerInfo().getLocation().y;
					Thread.sleep(random.nextInt(5) * 1000);
				}
			} catch (Exception e) {
			}
		});

		Thread loading = new Thread(() -> {
			try {
				System.out.print("running    ");
				while (System.currentTimeMillis() - time <= duration) {
					System.out.print("\b\b\b\b");
					System.out.print(".   ");
					Thread.sleep(250);

					System.out.print("\b\b\b\b");
					System.out.print("..  ");
					Thread.sleep(250);

					System.out.print("\b\b\b\b");
					System.out.print("... ");
					Thread.sleep(250);

					System.out.print("\b\b\b\b");
					System.out.print("....");
					Thread.sleep(250);
				}
				System.out.println("\b\b\b\b\b\b\b\b\b\b\b\b");
				System.out.println("Exiting");
				System.exit(0);
			} catch (Exception e) {
			}
		});

		robotMouseMover.start();
		loading.start();
	}

	public static void mouseGlide(Robot r, double x1, double x2, double y1, double y2) throws InterruptedException {

		double xdifference = x2 - x1;
		double ydifference = y2 - y1;

		for (double i = 0.00; i < 1; i += 0.05) {
			Thread.sleep(10);
			double yslope = (java.lang.Math.pow(10, i) / 10);
			r.mouseMove((int) (x1 + (xdifference * i)), (int) (y1 + (ydifference * yslope)));
		}
	}

	public static void mouseGlide(Robot r, double x1, double x2, double y1, double y2, int slope)
			throws InterruptedException {

		double xdifference = x2 - x1;
		double ydifference = y2 - y1;

		for (double i = 0.00; i < 1; i += 0.05) {
			Thread.sleep(10);
			double yslope = (java.lang.Math.pow(10, i) / 10);

			switch (slope) {
			case 0:
				r.mouseMove((int) (x1 + (xdifference * i)), (int) (y1 + (ydifference * i)));
			case 1:
				r.mouseMove((int) (x1 + (xdifference * i)), (int) (y1 + (ydifference * yslope)));
			case 2:
				r.mouseMove((int) (x1 + (xdifference * yslope)), (int) (y1 + (ydifference * i)));
			case 3:
				r.mouseMove((int) (x1 + (xdifference * yslope)), (int) (y1 + (ydifference * i)));
			}
		}
	}

	private static boolean isNumber(String number) {
		try {
			Integer.valueOf(number);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

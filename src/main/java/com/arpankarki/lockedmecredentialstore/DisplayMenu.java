package com.arpankarki.lockedmecredentialstore;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class DisplayMenu {

	private static Logger displayLogger = Logger.getLogger(DisplayMenu.class);

	public void displayWelcomeScreen() {
		System.out.println("************************************************");
		System.out.println("                Locked Me App                   ");
		System.out.println("           Developed By Arpan Karki             ");
		System.out.println("************************************************");
	}

	public void displayMainMenu() {
		System.out.println("************************************************");
		System.out.println("                1) Login                        ");
		System.out.println("                2) Register                     ");
		System.out.println("                3) Logout                       ");
		System.out.println("************************************************");
	}

	public void displayUserNamePrompt() {
		System.out.print("Enter UserName :");
	}

	public void displayPasswordPrompt() {
		System.out.print("Enter Password :");
	}

	public void displayUrlPrompt() {
		System.out.print("Enter URL :");
	}

	public void displayEnterPrompt() {
		System.out.print("Enter your choice :");
	}

	public void displaySuccessfulLoginOptions() {
		System.out.println("************************************************");
		System.out.println("                1) Fetch User Credentials       ");
		System.out.println("                2) Store Credentials            ");
		System.out.println("                3) Logout                       ");
		System.out.println("************************************************");
	}

	public void displayRegistrationPage() {
		System.out.println("************************************************");
		System.out.println("                Registration Page               ");
		System.out.println("************************************************");
	}

}

package com.arpankarki.lockedmecredentialstore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.arpankarki.lockedmecredentialstore.user.*;

public class Application {

	static Logger logger;

	private ArrayList<User> users;

	private String directoryname = "C:\\Users\\karkia\\Documents\\CredentialStore";
	private String userDetailsFileName = directoryname.concat("\\").concat("userDetailsStore.txt");
	private static Scanner inputScanner;
	private static DisplayMenu displayMenu;

	public void init() {
		logger.info("Entering initialization for Application");
		File checkdirectoryEsists = new File(directoryname);
		if (!checkdirectoryEsists.exists()) {
			logger.info("Directory does not exist");
			if (checkdirectoryEsists.mkdir()) {
				logger.info("Directory Created");
			}
		}

		logger.info("Checking if UserDetailsFile is available ");

		File userDetailFile = new File(userDetailsFileName);
		if (!userDetailFile.exists()) {
			try {
				userDetailFile.createNewFile();
			} catch (IOException e) {

				logger.info("File has been created");
				e.printStackTrace();
			}
		}
		initializeUsers(userDetailFile);
	}

	public void initializeUsers(File userDetailsFile) {
		if (users == null) {
			users = new ArrayList<User>();
		} else {
			users.clear();
		}
		try {
			Scanner scanner = new Scanner(userDetailsFile);
			HashMap<String, String> keyValueHashMap = new HashMap<String, String>();
			while (scanner.hasNext()) {
				String user = scanner.nextLine();

				for (String keyValue : user.split(",")) {

					String[] keyValStrings = keyValue.split("=");
					keyValueHashMap.put(keyValStrings[0].trim(), keyValStrings[1].trim());

				}
				users.add(new User(keyValueHashMap.get("userName"), keyValueHashMap.get("password")));
				keyValueHashMap.clear();
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<User> getUsers() {

		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public Application() {
		init();
		inputScanner = new Scanner(System.in);
		displayMenu = new DisplayMenu();

	}

	public boolean handleMainMenu(Scanner inputScanner) {

		String optionString = inputScanner.nextLine();
		boolean userLoggedIn = true;
		if (!optionString.isEmpty()) {
			int option = Integer.parseInt(optionString);
			switch (option) {
			case 1:
				loginUser();
				break;
			case 2:
				registerUser();
				break;
			case 3:
				userLoggedIn = false;
				break;
			default:
				System.out.println("Please select Proper Value");
				break;
			}

		} else {
			System.out.println("Please select Proper Value");
			logger.info("Invalid option selected while handling main menu");
		}
		return userLoggedIn;

	}

	private void registerUser() {
		displayMenu.displayRegistrationPage();
		displayMenu.displayUserNamePrompt();
		String userName = inputScanner.nextLine();
		User userExists = null;
		if (!userName.isEmpty()) {
			userExists = getUser(userName);
			if (userExists != null) {
				System.out.println("UserName already exists");
			} else {
				displayMenu.displayPasswordPrompt();
				String password = inputScanner.nextLine();
				if (!password.isEmpty()) {
					users.add(new User(userName, password));
					updateUserDataStore();
				} else {
					System.out.println("Password cannot be empty");
				}

			}
		} else {
			System.out.println("Username Cannot be empty");
		}

	}

	private void updateUserDataStore() {
		try {
			PrintWriter printWriter = new PrintWriter(new File(userDetailsFileName));
			for (User user : users) {
				printWriter.println(user);
			}
			printWriter.flush();
			printWriter.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	private User getUser(String userName) {
		for (User user : users) {
			if (user.getUserName().equals(userName)) {
				return user;
			}
		}
		return null;
	}

	private void loginUser() {

		displayMenu.displayUserNamePrompt();
		String userName = inputScanner.nextLine();
		User loggedInUser = null;
		if (!userName.isEmpty()) {
			loggedInUser = getUser(userName);
			if (loggedInUser != null) {
				displayMenu.displayPasswordPrompt();
				String password = inputScanner.nextLine();
				if (!password.isEmpty()) {
					if (loggedInUser.getPassword().equals(password)) {
						handleSuccessfulLoginOptions(loggedInUser);
					} else {
						System.out.println("Invalid Password");
					}

				} else {
					System.out.println("Password cannot be empty");
				}
			} else {
				System.out.println("User Does Not Exist");
			}

		} else

		{
			System.out.println("UserName Cannot be empty");
			logger.info("UserName empty");
		}

	}

	private void handleSuccessfulLoginOptions(User user) {

		boolean userLoggedIn = true;
		while (userLoggedIn) {
			displayMenu.displaySuccessfulLoginOptions();
			displayMenu.displayEnterPrompt();
			String choice = inputScanner.nextLine();
			if (!choice.isEmpty()) {
				int options = Integer.parseInt(choice);
				switch (options) {
				case 1:
					user.fetchCredentials();
					break;
				case 2:
					addCredentials(user);
					break;
				case 3:
					userLoggedIn = false;
					break;
				default:
					System.out.println("Please enter a valid choice");
					break;
				}
			} else {
				System.out.println("Please enter a valid choice");
			}
		}

	}

	private void addCredentials(User user) {

		displayMenu.displayUrlPrompt();
		String url;
		String userName;
		String password;
		url = inputScanner.nextLine();

		if (!url.isEmpty()) {
			if (user.checkCredentialsExist(url)) {
				System.out.println("Credentials Exist");
			} else {
				displayMenu.displayUserNamePrompt();
				userName = inputScanner.nextLine();
				if (!userName.isEmpty()) {
					displayMenu.displayPasswordPrompt();
					password = inputScanner.nextLine();
					if (!password.isEmpty()) {
						user.addUserCredential(new UserCredential(url, userName, password));
					} else {
						System.out.println("Password Cannot be Empty");
					}

				} else {
					System.out.println("Username can not be empty");
				}
			}

		} else {
			System.out.println("URL cannot be empty");
		}

	}

	public static void main(String[] args) {

		logger = Logger.getLogger(Application.class);
		Application application = new Application();
		application.init();
		boolean isUserLoggedIn = true;
		while (isUserLoggedIn) {
			displayMenu.displayWelcomeScreen();
			displayMenu.displayMainMenu();
			isUserLoggedIn = application.handleMainMenu(inputScanner);
		}

	}

}

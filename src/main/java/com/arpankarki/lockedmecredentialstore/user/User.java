package com.arpankarki.lockedmecredentialstore.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.arpankarki.lockedmecredentialstore.EncodeDecode;

public class User {
	private String userName;
	private String password;
	private String directoryName = "C:\\Users\\karkia\\Documents\\CredentialStore";
	private String fileExtension = ".txt";
	private String fileName;
	private ArrayList<UserCredential> userCredentials;

	private static Logger fileLogger = Logger.getLogger(User.class);

	public void init() {

		fileLogger.info("Initializing User credentialStoreFile");
		File file = new File(this.fileName);
		if (!file.exists()) {
			fileLogger.info("File does not exist for the user");
			try {
				file.createNewFile();
			} catch (IOException e) {
				fileLogger.info("Error in init " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			readUserCredentials();
		}

	}

	private void readUserCredentials() {
		userCredentials.clear();
		fileLogger.info("Reading UserCredentials");
		try {

			Scanner scanner = new Scanner(new File(fileName));
			HashMap<String, String> userCreds = new HashMap<String, String>();

			while (scanner.hasNext()) {

				String lineRead = scanner.nextLine();
				for (String credsSet : lineRead.split(",")) {
					String[] valueSet = credsSet.split("=");
					userCreds.put(valueSet[0].trim(), valueSet[1].trim());
				}

				userCredentials.add(
						new UserCredential(userCreds.get("url"), userCreds.get("userName"), userCreds.get("password")));
				userCreds.clear();
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			fileLogger.info("Error while reading userCredentials" + e.getMessage());
			e.printStackTrace();
		}

	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.fileName = directoryName.concat("\\").concat(userName).concat(fileExtension);
		this.userCredentials = new ArrayList<UserCredential>();
		init();
	}

	public List<UserCredential> getUserCredentials() {
		return userCredentials;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public String toString() {
		return "userName=" + userName + ", password=" + password + ", fileName=" + fileName;
	}

	public void fetchCredentials() {
		readUserCredentials();
		if (userCredentials.isEmpty()) {
			System.out.println(" No User Credentials Stored ");

		} else {
			System.out.println(userCredentials);
		}

	}

	public boolean checkCredentialsExist(String url) {
		readUserCredentials();
		boolean credentialExists = false;
		for (UserCredential userCredential : userCredentials) {
			if (userCredential.getUrl().equals(url)) {
				credentialExists = true;
			}
		}
		return credentialExists;
	}

	public void addUserCredential(UserCredential addUserCredential) {
		userCredentials.add(addUserCredential);
		try {
			PrintWriter printWriter = new PrintWriter(new File(fileName));
			for (UserCredential userCredential : userCredentials) {
				printWriter.println(userCredential);
			}
			printWriter.flush();
			printWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

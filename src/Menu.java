import com.zubiri.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		File file = new File("src/usersData.txt");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file, true);
			Scanner fileSc = new Scanner(file);
			Users users = new Users();
			ArrayList<User> usersArray = new ArrayList<User>();
			while (fileSc.hasNextLine()) {
				User user = new User();
				String line = fileSc.nextLine();
				String[] data = line.split("::");
				user.setUsername(data[0]);
				user.setPassword(data[1]);
				usersArray.add(user);
			}
			users.setUsers(usersArray);
			boolean repeat = true;
			while (repeat) {
				System.out.println("Press one of the following numbers:");
				System.out.println("[1] Login");
				System.out.println("[2] Register");
				System.out.println("[0] Exit");
				String choice = sc.next().trim();
				sc.nextLine();
				switch (choice) {
				case "0":
					System.out.println("Bye!");
					repeat = false;
					break;
				case "1":
					for (int i = 0; i < 10; ++i)
						System.out.println();
					if (users.getUsers().size()>0) {
						System.out.println("Username:");
						String name = sc.next();
						sc.nextLine();
						System.out.println("Password:");
						String password = sc.nextLine();
						if (users.login(name, password)) {
							System.out.println("Hi " + name + "!");
							System.out.println();
							System.out
									.println("Sorry, there are no options avaiable yet, so you are being logged out.");
							System.out.println("----------------------");
						}else {
							System.out.println("The user or password you've entered is not correct.");
							System.out.println("----------------------");
						}
					} else {
						System.out.println("No user registered yet");
						System.out.println("----------------------");
					}
					break;
				case "2":
					for (int i = 0; i < 10; ++i)
						System.out.println();
					User user = new User();
					boolean askUsername = true;
					while (askUsername) {
						System.out.println("Username:");
						String name = sc.nextLine();
						if (user.verifyUsername(name)) {
							user.setUsername(name);
							askUsername = false;
							boolean askPassword = true;
							while (askPassword) {
								System.out.println("Password:");
								String password = sc.nextLine();
								if (user.verifyPassword(password)) {
									System.out.println("Done");
									System.out.println("----------------------");
									user.setPassword(password);
									askPassword = false;
									users.register(user);
									fw.write(name + "::" + password);
									fw.write(System.getProperty("line.separator"));
									fw.close();
								} else {
									System.out.println("Enter a valid password");
									System.out.println(
											"It has to have at least a letter,  a number, a symbol and 8 or more characters long");
								}
							}
						} else
							System.out.println("Enter a valid username (no numbers or digits)");
					}
					break;
				}
			}
			fileSc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
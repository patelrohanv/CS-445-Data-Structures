/*
Rohan Patel
RVP3
382-7175
*/
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.NullPointerException;
@SuppressWarnings("unchecked")

public class Client implements Serializable{
	static Scanner sKey = new Scanner(System.in); //strings keyboard
	static Scanner iKey = new Scanner(System.in); //int keyboard

	static List<ProfileInterface> profiles = new ArrayList<ProfileInterface>();
	static SimpleStack<ProfileInterface> profileStack = new SimpleStack<ProfileInterface>(20);

	public static void main(String[] args){
		if(args.length != 0 && restore(args[0])){
			System.out.println("Old data read from file");
		}
		else{
				System.out.println("Could not read data from file");
		}

		// try{ //make an array from the profile stack then migrate it to arraylist
		// 	ProfileInterface[] proArr = (ProfileInterface[]) profileStack.topItems(20);
		// 	for(int i = 0; i < proArr.length; i++){
		// 		profiles.add(proArr[i]);
		// 	}
		// }
		// catch (NullPointerException n){
		// 	System.out.println("Could not load profiles from stack");
		// }

		int choice = 0;
		while(choice < 8){
			System.out.println("1. List profiles\n" +
				"2. Create profile\n" +
				"3. Show profile\n" +
				"4. Edit profile\n" +
				"5. Follow\n" +
				"6. Unfollow\n" +
				"7. Suggest a follow\n" +
				"8. Quit");
			choice = iKey.nextInt();
			if (choice == 1){ //list
				listProfiles();
			}
			else if (choice == 2){//create
				create();
			}
			else if (choice == 3){//show
				show();
			}
			else if (choice == 4){//edit
				editProfile();
			}
			else if (choice == 5){//follow
				followProfile();
			}
			else if (choice == 6){//unfollow
				unfollowProfile();
			}
			else if (choice == 7){//sugguest
				suggestProfile();
			}
		}

		for(ProfileInterface p: profiles){
			profileStack.add(p);
		}
		if(args.length != 0){
			saveQuit(args[0]);
		}
		else{
			System.out.println("What do you wish to name the file? \nMust be .bin");
			String saveName = sKey.next();
			saveQuit(saveName);
		}
	}

	static boolean restore(String filename) {
		try {
			ObjectInputStream restoreStream = new ObjectInputStream(new FileInputStream(filename));
			profileStack = (SimpleStack)restoreStream.readObject();
			int stackSize = profileStack.size();

			for(int i =0; i < stackSize; i++){
				profiles.add(profileStack.remove());
			}

			// ProfileInterface[] proArr = (ProfileInterface[]) profileStack.topItems(stackSize);
			// for(int i = 0; i < proArr.length; i++){
			// 	profiles.add(proArr[i]);
			// }
		}
		catch(FileNotFoundException e) {
			System.err.println(filename + " does not exist.");
			return false;
		}
		catch(IOException e) {
			System.err.println("Error resuming from " + filename);
			return false;
		}
		catch(ClassNotFoundException e) {
			System.err.println("Error resuming from " + filename);
			return false;
		}
        return true;
    }

	public static void listProfiles(){ //what does he want us to print?
		int i = 1;
		for(ProfileInterface p: profiles){
			System.out.println("\t" +  i+ ". " +p.getName());
			i++;
		}
	}
	public static void create(){
		System.out.println("What is your name?");
		String name = sKey.nextLine();
		System.out.println("What is your about?");
		String about = sKey.nextLine();
		Profile make = new Profile(name, about);
		profiles.add(make);
	}

	public static void show(){
		try{
			System.out.println("Which profile do you which to see?");
			listProfiles();
			int see = iKey.nextInt() - 1;
			Profile currentProfile = (Profile) profiles.get(see);
			//for debugging
			//System.out.println(see);
			System.out.println(currentProfile);
		}
		catch (NullPointerException n){
			System.out.println("Could not find profile, null pointer");
		}
	}

	public static void editProfile(){
		System.out.println("Which profile do you which to edit?");
		listProfiles();
		int edit = iKey.nextInt()-1;
		System.out.println("What new name do you want?");
		String newName = sKey.nextLine();
		System.out.println("What new about do you want?");
		String newAbout = sKey.nextLine();
		Profile edited = new Profile (newName, newAbout);
		profiles.set(edit, edited);
	}

	public static void followProfile(){
		try{
			listProfiles();
			System.out.println("Which profile do you want to follow?");
			int toFollow = iKey.nextInt() -1;
			System.out.println("Who do you want to follow that profile?");
			int follower = iKey.nextInt() -1;
			if (toFollow == follower){
				System.out.println("Profile cannot follow itself");
			}
			else{
				Profile newFollower = (Profile) profiles.get(follower);
				(profiles.get(toFollow)).follow(newFollower);
				//profiles.get(toFollow).follow(profiles.get(follower));
			}
		}
		catch (NullPointerException n){
			System.out.println("Could not find profile, null pointer");
		}
	}

	public static void unfollowProfile(){
		listProfiles();
		System.out.println("Which profile do you want to remove it's most recent follower");
		int removeProfile = iKey.nextInt() -1;
		profiles.get(removeProfile).unfollow();
	}

	public static void suggestProfile(){
		listProfiles();
		System.out.println("Who do you want to suggest a follower too?");
		int suggestTo = iKey.nextInt()-1;
		Profile suggested = (Profile) profiles.get(suggestTo).recommend();
		System.out.println(suggested.getName());
		System.out.println("Do you want to follow? (yes or no)");
		String responce = sKey.next();
		if(responce.equals("yes")){
			suggested.follow(profiles.get(suggestTo));
		}
	}

    static void saveQuit (String filename) {
        try {
            ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(filename));
            saveStream.writeObject(profileStack);
        }
        catch(IOException e) {
            System.err.println("Something went wrong saving to " + filename);
            e.printStackTrace();
        }
    }
}
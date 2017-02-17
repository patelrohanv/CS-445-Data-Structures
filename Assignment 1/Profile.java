/*
Rohan Patel
RVP3
382-7175
*/
import java.lang.*;
import java.util.Random;
import java.io.Serializable;

public class Profile implements ProfileInterface, Serializable{
	private String name;
	private String about;
    private int numFollowers = 0;
	private SimpleStack<ProfileInterface> followers = new SimpleStack<ProfileInterface>(20);

    public Profile (String name, String about){
    	this.name = name;
    	this.about = about;
    }
    public void setName(String name){ //sets the profile name
    	this.name = name;
    }

    public String getName(){ //gets name
    	return name;
    }

    public void setAbout(String about){ //sets the profile about
    	this.about = about;
    }

    public String getAbout(){ //gets abbout
    	return about;
    }

    public boolean follow(ProfileInterface other){ //follows the profile in the parameters 
    	if (followers.contains(other) || followers.isFull()){
    		return false;
    	}
        numFollowers++;
    	return followers.add(other);
    }

    public ProfileInterface unfollow(){ //unfollows most recent follower
    	numFollowers--;
        return followers.remove();
    }

    public ProfileInterface[] following(int howMany){ //shows howMany of the most recent followers
    	// Object[] arrObj = followers.topItems(howMany);
     //    ProfileInterface[] arr = new ProfileInterface[arrObj.length];
     //    for (int i =0; i < arrObj.length; i++) {
     //        arr[i] = (ProfileInterface) arrObj[i];
     //    }
     //    return arr;
        ProfileInterface[] top = (ProfileInterface[]) followers.topItems(howMany);
        return top;
    }

    public String toString(){
    	StringBuilder s = new StringBuilder();
    	s.append("Name: " + name + "\n" + "About: " + about + "\nRecent Followers:");
        if (! followers.isEmpty()){
            if(numFollowers < 5){
                ProfileInterface[] recent = following(numFollowers);
                for (int i = 0; i < recent.length; i++){
                    s.append("\t\n" + recent[i].getName());
                }
            }
            else{
                ProfileInterface[] recent = following(5);
                for (int i = 0; i < recent.length; i++){
                    s.append("\t\n" + recent[i].getName());
                }
            }
        }  

    	return s.toString();
    }

    /**
     * Recommend a profile for this profile to follow. For now, this can return any arbitrary
     * profile followed by one of this profile's followed profiles. For example, if this profile is
     * Alex, and Alex follows Bart, and Bart follows Crissy, this method might return Crissy. You
     * may implement more intelligent selection (e.g., ensuring that the selection is not already
     * followed), but it is not required.
     * @return The profile to suggest, or null if no suitable profile is found.
     */
    public ProfileInterface recommend(){
        if (followers.size() == 0){
            return null;
        }
        //get a random follower from your followers
        int numFollowers = followers.size();
        Random rand = new Random();
        int randomFollower = rand.nextInt(followers.size());
        ProfileInterface follower = (following(numFollowers))[randomFollower];
       // ProfileInterface follower = .topItems()[randomFollower];

        ProfileInterface newFollower = (follower.following(1))[0];


        return newFollower;
    }

    public boolean equals(ProfileInterface p){
        if ((p.getName()).equals(name) && (p.getAbout()).equals(about)){
            return true;
        }
        return false;
    }
}
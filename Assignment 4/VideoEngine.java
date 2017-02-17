package videoengine;

/**
 * This abstract data type is a predictive engine for video ratings in a streaming video system. It
 * stores a set of users, a set of videos, and a set of ratings that users have assigned to videos.
 *
 * ADD OTHER DETAILS HERE IF NECESSARY
 */
public interface VideoEngine {

    /**
     * The abstract methods below are declared as void methods with no parameters. You need to
     * expand each declaration to specify a return type and parameters, as necessary. You also need
     * to include a detailed comment for each abstract method describing its effect, its return
     * value, any corner cases that the client may need to consider, any exceptions the method may
     * throw (including a description of the circumstances under which this will happen), and so on.
     * You should include enough details that a client could use this data structure without ever
     * being surprised or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new video to the system. This will return true if theVideo was sucessfully
     * added to the system and false if otherwise. A NullPointerException will be thrown if
     * theVideo is null and an IllegalArgumentException will be thrown if theVideo is 
     * already in the system. Assuming Film and TvEpisodes are subinterfaces of Video
     * this add method will also add either of them to the system too.
     * @param theVideo is the video that's being added
     * @return returns true if theVideo was successfully added and false if otherwise
     * @throws NullPointerException if theVideo is null
     * @throws IllegalArgumentException if theVideo is already in the system
     */
    public boolean addVideo(Video theVideo);

    /**
     * Removes an existing video from the system. This method needs to account for the system being empty 
     * and account for the specified video (theVideo) not being found. Assuming Film and TvEpisodes 
     * are subinterfaces of Video this remove method will also remove either of them from
     * the system too. This method will throw an IndexOutOfBoundsException if the system is empty and 
     * has no videos or a NullPointerException if theVideo is null.
     * @param theVideo is the video that's being removed
     * @return returns the Video that was removed from the system or it returns null if theVideo is not found
     * @throws IndexOutOfBoundsException if the system is empty/has no Videos
     * @throws NullPointerException if theVideo is null
     */
    public Video removeVideo(Video theVideo);

    /**
     * Adds an existing television episode to an existing television series. This method
     * will return true if theEpisode was added to theSeries and it will return false if 
     * otherwise. An IllegalArgumentException will be thrown theEpisode is already in
     * theSeries or if it belongs to another series and a NullPointerException will be 
     * thrown if theEpisode or theSeries are null.
     * @param theEpisode is the TvEpisode that's being added to theSeries
     * @param theSeries is the TvSeries that an episode is being added to
     * @return Returns true if theEpisode was added to the Series and false if
     * it was not added for any reason such as theEpisode already belonging to
     * theSeries
     * @throws IllegalArgumentException if theEpisode is already in theSeries
     * or if theEpisode already belonds to another series
     * @throws NullPointerException if either theEpisode or theSeries is null
     */
    public boolean addToSeries(TvEpisode theEpisode, TvSeries theSeries);

    /**
     * Removes a television episode from a television series. This method will return
     * the TvEpisode that was removed if theEpisode was in theSeries and null if 
     * theEpisode is not in theSeries. An IndexOutOfBoundsException will be thrown if
     * theSeries is empty and has no episodes in it and a NullPointerException will be
     * thrown if theEpisode or theSeries are null.
     * @param theEpisode is the the TvEpisode that will be removed from theSeries
     * @param theSeries is the TvSeries that theVideo will be removed from
     * @return Returns theEpisode that was removed from theSeries or it returns
     * null if theEpisode was not found in theSeries
     * @throws IndexOutOfBoundsException if theSeries has no episodes in it
     * @throws NullPointerException if either theEpisode or theSeries is null
     */
    public TvEpisode removeFromSeries(TvEpisode theEpisode, TvSeries theSeries);

    /**
     * Sets a user's rating for a video, as a number of stars from 1 to 5. This
     * method is void because there is no need to return anything, the client already
     * knows what rating theUser gave theVideo. If rating if not between 1 and 5 an
     * IllegalArgumentException will be thrown and if theUser or theVideo are null
     * then a NullPointerException will be thrown.
     * @param theUser is the User that is setting the rating
     * @param theVideo is the Video that theUser is giving a rating to
     * @param rating is the int value from 1 to 5 that theUser will assign theVideo
     * @throws IllegalArgumentException is the rating is not between 1 and 5 or if 
     * theVideo already has a rating
     * @throws NullPointerException if either theUser or theVideo is null
     */
    public void rateVideo(User theUser, Video theVideo, int rating);

    /**
     * Clears a user's rating on a video. If this user has rated this video and the rating has not
     * already been cleared, then the rating is cleared and the state will appear as if the rating
     * was never made. If this user has not rated this video, or if the rating has already been
     * cleared, then this method will throw an IllegalArgumentException.
     * Assuming Film and TvEpisodes are subinterfaces of Video this method will also function
     * the same for both these subinterfaces.
     * @param theUser user whose rating should be cleared
     * @param theVideo video from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a rating on record for
     * the video
     * @throws NullPointerException if either the user or the video is null
     */
    public void clearRating(User theUser, Video theVideo);

    /**
     * Predicts the rating a user will assign to a video that they have not yet rated, as a number
     * of stars from 1 to 5. A NullPointerException will be thrown if theUser or theVideo are
     * null. An IllegalArgumentException will be thrown if theVideo already has a rating.
     * Assuming Film and TvEpisodes are subinterfaces of Video this method will also function
     * the same for both these subinterfaces.
     * @param theUser is the User whose predictated rating will be generated
     * @param theVideo is the Video that will get a predicted rating, this video should not 
     * have been previously rated by theUser
     * @return returns an int between 1 and 5 as theUser's predicted rating
     * @throws IllegalArgumentException if theVideo already has a rating
     * @throws NullPointerException if either theUser or theVideo is null
     */
    public int predictRating(User theUser, Video theVideo);

    /**
     * Suggests a video for a user based on their predicted rating. This method should call 
     * predictRating on theUser and return a Video with a high predicted rating to be 
     * suggested to theUser. A NullPointerException will be thrown if theUser is null.
     * @param theUser is the User who the video will be suggested to
     * @return returns a video (or a subinterface of Video) that is recommended to theUser
     * @throws NullPointerException if theUser is null
     */
   public Video suggestVideo(User theUser);


}


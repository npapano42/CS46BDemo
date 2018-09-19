import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that represents an individual event. Stores the name, date, and time
 * @author Nicholas Papano
 * @version 1.0
 * Date Created: 9/1/18
 */
public class Event implements Comparable<Event>
{
    private String name;
    private LocalDate date;
    private TimeInterval time;
    private static DateTimeFormatter date_format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Default constructor that initializes all instance variables to default values, using LocalDate.MAX as a default date value
     */
    public Event()
    {
        name = "";
        date = LocalDate.MAX;
        time = new TimeInterval();
    }

    /**
     * Constructor that takes in all required information to create an Event
     *
     * @param name the name of the event
     * @param date the date the event takes place
     * @param time the time at which the event takes place
     */
    public Event(String name, LocalDate date, TimeInterval time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    /**
     * Gets the name of the event
     *
     * @return the name of the event
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the date of the event
     *
     * @return the date the event takes place on
     */
    public LocalDate getDate()
    {
        return date;
    }

    /**
     * Gets the time of the event
     *
     * @return the time at which the event occurs
     */
    public TimeInterval getTime()
    {
        return time;
    }

    /**
     * Changes name to the newName String <BR>
     *
     * Postcondition: the name of the event is now changed to the new name
     *
     * @param newName the new name for the event
     *
     */
    public void setName(String newName)
    {
        name = newName;
    }

    /**
     * Changes the date of the event to the new inputted date <BR>
     *
     * Postcondition: the date of the event is now changed to the new date
     *
     * @param newDate the new date for the event
     *
     */
    public void setDate(LocalDate newDate)
    {
        date = newDate;
    }

    /**
     * Changes the time of the event to the new inputted time <BR>
     *
     * Postcondition: the time of the event is now changed to the new time
     *
     * @param newTime the new time for the event
     *
     */
    public void setTime(TimeInterval newTime)
    {
        time = newTime;
    }

    /**
     * Checks if Event d is on the same day as this event
     *
     * @param d the day to check
     * @return True if event is on the same day, false otherwise
     */
    public boolean onDate(LocalDate d)
    {
        return date.equals(d);
    }

    /**
     * Compares one Date Object ot the other, comparing by their starting times
     *
     * @param that the Date to compare to the this date
     * @return negative value if this comes before that, positive value if that comes before this, and 0 if they are exactly equal
     */
    @Override
    public int compareTo(Event that)
    {
        return (getTime().getStartTime().compareTo(that.getTime().getStartTime()));
    }

    /**
     * Provides a nice format for outputting an event, giving the name, date and time of the event
     *
     * @return A string that contains the name, date, and time of the event
     */
    public String toString()
    {
        return name + "\n" + date_format.format(date) + " " + time.toString();
    }

    /**
     * Similar to toString(), but this String omits the date
     *
     * @return a String containing the name and time of the event
     */
    public String toStringTime()
    {
        return name + ": " + time.toString();
    }


}

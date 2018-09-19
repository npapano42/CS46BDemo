import java.time.LocalDateTime;


/**
 * Class that represents an interval of time. Keeps two LocalDateTime objects and assums the event exists
 * @author Nicholas Papano
 * @version 1.0
 * Date Created: 9/1/18
 */
public class TimeInterval
{
    private LocalDateTime startTime, endTime;

    /**
     * Default constructor that initializes the times to default values (LocalDateTime.MAX)
     */
    public TimeInterval()
    {
        startTime = LocalDateTime.MAX;
        endTime = LocalDateTime.MAX;
    }

    /**
     * Constructor taking in two times and initializes instance variables to these values
     *
     * @param start the starting time of the event
     * @param end the ending time of the event
     */
    public TimeInterval(LocalDateTime start, LocalDateTime end)
    {
        startTime = start;
        endTime = end;
    }

    /**
     * Compares two TimeIntervals, looking to see if the inputted TimeInterval overlaps this TimeInterval in any way
     *
     * @param that the TimeInterval to be comparing to this
     * @return True of the events overlap, false otherwise
     */
    public boolean overlap(TimeInterval that)
    {
        if (this.startTime.compareTo(that.startTime) > 0 && this.startTime.compareTo(that.endTime) < 0) // start time exists in event
        {
            return true;
        }
        else if (this.endTime.compareTo(that.startTime) > 0 && this.endTime.compareTo(that.endTime) < 0) // end time exists in event
        {
            return true;
        }
        else if (this.startTime.compareTo(that.startTime) < 0 && this.endTime.compareTo(that.endTime) > 0) // this completely overlaps that
        {
            return true;
        }
        return false;
    }


    /**
     * Gets the starting time of the TimeInterval
     *
     * @return the starting time of the TimeInterval
     */
    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    /**
     * Gets the ending time of the TimeInterval
     *
     * @return the ending time of the TimeInterval
     */
    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    /**
     * Sets the start time to the new inputted start time <BR>
     *
     * Postcondition: the start time is adjusted to the new start time
     *
     * @param t the new starting time for the event
     */
    public void setStartTime(LocalDateTime t)
    {
        startTime = t;
    }

    /**
     * Sets the end time to the new inputted end time <BR>
     *
     * Postcondition: the end time is adjusted to the new end time
     *
     * @param t the new ending time for the event
     */
    public void setEndTime(LocalDateTime t)
    {
        endTime = t;
    }

    /**
     * Provides a format for the time of the event, slicing the date off of the LocalDateTime and printing their respective times
     *
     * @return a string containing the start and end times, separated by a hyphen
     */
    public String toString()
    {
        return startTime.toString().substring(11) + " - " + endTime.toString().substring(11);
    }
}

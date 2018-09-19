import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Class that manages the Calendar, storing events in an ArrayList and supports many basic calendar commands
 * @author Nicholas Papano
 * @version 1.0
 * Date Created: 9/1/18
 */
public class MyCalendar
{
    private static ArrayList<Event> eventList;
    /**
     * Default constructor that initializes the list <BR>
     *
     * Postcondition: MyCalendar object contains an empty list of events
     */
    public MyCalendar()
    {
        eventList = new ArrayList<>();
    }

    /**
     * Grabs events from events.txt and loads them into the list <BR>
     *
     * Precondition: events.txt exists and is full of events in the correct format <BR>
     *
     * Postcondition: eventList contains all events that were in events.txt
     */

    public void load()
    {
        System.out.println("Loading from file...");
        eventList = new ArrayList<>();
        try
        {
            File eventF = new File("src/events.txt");
            FileReader eventFr = new FileReader(eventF);
            BufferedReader eventBr = new BufferedReader(eventFr);
            String ln1, ln2;

            /*
            format of file: 2 line pieces per each event (space delimiter)
            Name_of_Event
            DaysorDate StartTime EndTime
            DaysorDate = SMTWHFA (day/days of week -OR- single date)
            StartTime, EndTime = Time event starts/ends (IN 24HR MILITARY TIME)
            */

            while ((ln1 = eventBr.readLine()) != null && (ln2 = eventBr.readLine()) != null)
            {
                //ln 2 format: days_or_date startTime endTime
                String[] ln2_info = ln2.split(" ");

                if (isNumber(ln2_info[0].charAt(0))) // will be a one time event
                {

                    DateTimeFormatter date_format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");
                    LocalDate date = LocalDate.parse(ln2_info[0], date_format);
                    LocalTime start_time = LocalTime.parse(ln2_info[1], time_format);
                    LocalTime end_time = LocalTime.parse(ln2_info[2], time_format);

                    TimeInterval time = new TimeInterval(LocalDateTime.of(date, start_time) , LocalDateTime.of(date, end_time));


                    Event next = new Event(ln1, date, time);
                    eventList.add(next);
                }

                else // will be a repeated event
                {
                    HashMap<DayOfWeek, Boolean> days = new HashMap<>();


                    for (int i = 0; i < ln2_info[0].length(); i++)
                    {
                        String next = ln2_info[0].substring(i,i+1);
                        switch (next)
                        {
                            case "S":
                                days.put(DayOfWeek.SUNDAY, true);
                                break;
                            case "M":
                                days.put(DayOfWeek.MONDAY, true);
                                break;
                            case "T":
                                days.put(DayOfWeek.TUESDAY, true);
                                break;
                            case "W":
                                days.put(DayOfWeek.WEDNESDAY, true);
                                break;
                            case "H":
                                days.put(DayOfWeek.THURSDAY, true);
                                break;
                            case "F":
                                days.put(DayOfWeek.FRIDAY, true);
                                break;
                            case "A":
                                days.put(DayOfWeek.SATURDAY, true);
                                break;
                        }
                    }

                    // set starting and ending dates for repeated events
                    LocalDate repeat_start = LocalDate.of(2018,8,22);
                    LocalDate repeat_end = LocalDate.of(2019,5,22);

                    DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");

                    LocalTime start_time = LocalTime.parse(ln2_info[1], time_format);
                    LocalTime end_time = LocalTime.parse(ln2_info[2], time_format);



                    while (repeat_start.compareTo(repeat_end) < 0)
                    {
                        if (days.containsKey(repeat_start.getDayOfWeek()) && days.get(repeat_start.getDayOfWeek()))
                        {
                            TimeInterval time = new TimeInterval(LocalDateTime.of(repeat_start, start_time), LocalDateTime.of(repeat_start, end_time));
                            Event next = new Event(ln1, repeat_start, time);
                            eventList.add(next);
                        }
                        repeat_start = repeat_start.plusDays(1);
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Could not get file events.txt");
            e.printStackTrace();
            System.exit(-1);
        }
        catch (IOException e)
        {
            System.out.println("Error reading line from file");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Finished loading from file");
    }

    /**
     * Grabs the month from the LocalDate and prints a formatted calendar <BR>
     *
     * Precondition: LocalDate is a valid date
     *
     * @param c The LocalDate containing the date for the calendar to print
     * @param addBrackets Adds brackets around dates that have events added to the event list on them
     *
     */
    public void printCalendar(LocalDate c, boolean addBrackets)
    {
        // To print a calendar in a specified format.
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
//        System.out.println(formatter.format(c));

        // To figure out the day of week of the 1st day of the given month
        LocalDate x = LocalDate.of(c.getYear(), c.getMonth(), 1);
//        System.out.println(x.getDayOfWeek() + " is the day of " + c.getMonth() + " 1."); // enum value as it is
//        System.out.println(x.getDayOfWeek().getValue() + " is an integer value corresponding "
//                + " to " + x.getDayOfWeek()); // int value corresponding to the enum value

        YearMonth month = YearMonth.of(c.getYear(), c.getMonth());
        int first_day = x.getDayOfWeek().getValue();

        LocalDate current = LocalDate.now();

        System.out.print(c.getMonth() + " ");
        System.out.println(c.getYear());
        System.out.println("Su Mo Tu We Th Fr Sa");

        // offset
        int week = 1;
        for (int i = 0; i < first_day; i++, week++)
            System.out.print("   ");

        // print calendar
        for (int i = 1; i <= month.lengthOfMonth(); i++, week++, x = x.plusDays(1))
        {
            if (i == c.getDayOfMonth() && x.getMonth() == current.getMonth() && x.getYear() == current.getYear() && !addBrackets)
                System.out.print("["+ i + "] ");
            else
            {
                boolean onDay = false;
                for (int q = 0; q < eventList.size(); q++)
                {
                    Event e = eventList.get(q);
                    if (e.onDate(x))
                    {
                        onDay = true;
                        break;
                    }

                }
                if (onDay)
                    System.out.print("{" + i + "} ");
                else
                    System.out.print(i + " ");
            }

            if (i < 10)
                System.out.print(" ");

            if (week%7 == 0)
                System.out.println();
        }
        System.out.println();

    }


    /**
     * Reads the day from the LocalDate and prints all events on that day <BR>
     *
     * Precondition: LocalDate is a valid date
     *
     * @param c The LocalDate containing the date for the month to print
     *
     */
    public void printEventOnDay(LocalDate c)
    {
        System.out.print(c.getMonth() + " ");
        System.out.print(c.getDayOfMonth() + ", ");
        System.out.println(c.getYear() + ":");
        ArrayList<Event> onDay = new ArrayList<>();
        for (int i = 0; i < eventList.size(); i++)
        {
            if (eventList.get(i).onDate(c))
                onDay.add(eventList.get(i));
        }

        sortEventsByTime(onDay);

        for (Event e : onDay)
            System.out.println(e.toStringTime());
    }

    /**
     * Takes in user input and adds an event to the eventList. Checks for overlaps and does not allow an overlapping event to be created <BR>
     *
     * Precondition: User is using console for input and output and inputs valid information <BR>
     *
     * Postcondition: if event does not overlap with other events, a new event is added to the eventList
     */
    public void addEvent()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the name of the event:");
        String name = sc.nextLine();
        System.out.println("Please enter the date of the event in format \"MM/DD/YYYY\", adding 0's if needed:");
        String date_in = sc.nextLine();
        System.out.println("Please enter the starting time of the event as 24 HR time in the format \"HH:MM\", adding 0's if needed:");
        String start_time_in = sc.nextLine();
        System.out.println("Please enter the ending time of the event as 24 HR time in the format \"HH:MM\", adding 0's if needed:");
        String end_time_in = sc.nextLine();

        DateTimeFormatter date_format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter time_format = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate date = LocalDate.parse(date_in, date_format);
        LocalTime start_time = LocalTime.parse(start_time_in, time_format);
        LocalTime end_time = LocalTime.parse(end_time_in, time_format);

        TimeInterval time = new TimeInterval(LocalDateTime.of(date, start_time) , LocalDateTime.of(date, end_time));

        boolean conflict = false;
        Event input_event = new Event(name,date, time);
        Event overlap = input_event;
        for (Event e : eventList)
        {
            if (input_event.getTime().overlap(e.getTime()))
            {
                conflict = true;
                overlap = e;
                break;
            }
        }
        if (conflict)
        {
            System.out.println("Event you attempted to add conflicts with:\n" + overlap.toString());
        }
        else
        {
            System.out.println("Event added:\n" + input_event.toString());
            eventList.add(input_event);
        }

    }


    /**
     * Deletes all events on a day in the eventList, similar to ArrayList.clear() but for a single day <BR>
     *
     * Postcondition: eventsList removes all events on LocalDate c
     *
     * @param c the date on which all events should be deleted
     */
    public void clearEvents(LocalDate c)
    {
        for (int i = 0; i < eventList.size(); i++)
        {
            Event e = eventList.get(i);
            if (e.onDate(c))
            {
                eventList.remove(e);
                i--;
            }
        }
    }

    /**
     * Checks if a character is a number based on its ASCII value
     *
     * @param c the character to be compared
     * @return True if c is the character representation of a number, false otherwise
     */
    private boolean isNumber(char c)
    {
        return (c >= 48 && c <= 57);
    }

    /**
     * Deletes first occurrence of an event with a name that matches the parameter input
     *
     * @param date the date on which the event occurs
     * @param name The name of the event that should be deleted
     *
     * @return True if delete is successful, false if event with name does not exist
     */
    public boolean delete(LocalDate date, String name)
    {
        for (int i =0; i < eventList.size(); i++)
        {
            Event e = eventList.get(i);
            if (name.equals(e.getName()) && date.equals(e.getDate()))
            {
                eventList.remove(e);
                return true;
            }
        }
        return false;
    }

    /**
     * Sorts an ArrayList of Events based on Event's compareTo() <BR>
     *
     * Postcondition: events now contains all the events in sorted order as defined by Event's compareTo()
     *
     * @param events the ArrayList of events that needs sorting
     */
    private void sortEventsByTime(ArrayList<Event> events)
    {
        Collections.sort(events);
    }

    /**
     * Prints all events in order based on Event's compareTo()
     */
    public void printAllEvents()
    {
        sortEventsByTime(eventList);
        for (Event e : eventList)
            System.out.println(e);
        System.out.println("Finished printing all events");
    }
}

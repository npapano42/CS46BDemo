import java.time.LocalDate;
import java.util.Scanner;

/**
 * Class that deals with testing MyCalendar, Event and TimeInterval. Holds the Menu options part of the application
 * @author Nicholas Papano
 * @version 1.0
 *
 */
public class MyCalendarTester
{
    /**
     * Main that hold menu options and shows off the various functions of MyCalendar, Event and TimeInterval
     * @param args arguments to pass into the main when running the app
     */
    public static void main(String[] args)
    {

        String[] date_str;
        int[] date;
        Scanner sc = new Scanner(System.in);
        MyCalendar m = new MyCalendar();
        LocalDate cal = LocalDate.now();
        m.printCalendar(cal, false);

        while (true)
        {
            System.out.println("[L]oad | [V]iew by | [C]reate | [G]o to | [E]vent list | [D]elete | [Q]uit");
            String input = sc.nextLine();
            switch (input.toLowerCase())
            {
                case "l": // load
                    m.load();
                    break;

                case "v": // view
                    System.out.println("[D]ay View | [M]onth view | [G]o back");

                    while (!((input = sc.nextLine().toLowerCase()).equals("g")))
                    {
                        switch (input)
                        {
                            case "d": // day view
                                m.printEventOnDay(cal);
                                System.out.println("[P]revious | [N]ext | [G]o back");
                                while (!((input = sc.nextLine().toLowerCase()).equals("g")))
                                {
                                    switch (input)
                                    {
                                        case "p": // previous day
                                            cal = cal.minusDays(1);
                                            m.printEventOnDay(cal);
                                            break;

                                        case "n": // next day
                                            cal = cal.plusDays(1);
                                            m.printEventOnDay(cal);
                                            break;

                                        default:
                                            System.out.println("Command not recognized");
                                            break;
                                    }
                                    System.out.println("[P]revious | [N]ext | [G]o back");
                                }
                                break;

                            case "m": // month view
                                m.printCalendar(cal, true);
                                System.out.println("[P]revious | [N]ext | [G]o back");
                                while (!((input = sc.nextLine().toLowerCase()).equals("g")))
                                {
                                    switch (input)
                                    {
                                        case "p": // previous month
                                            cal = cal.minusMonths(1);
                                            m.printCalendar(cal, true);
                                            break;

                                        case "n": // next month
                                            cal = cal.plusMonths(1);
                                            m.printCalendar(cal, true);
                                            break;

                                        default:
                                            System.out.println("Command not recognized");
                                            break;
                                    }
                                    System.out.println("[P]revious | [N]ext | [G]o back");
                                }
                                break;

                            default:
                                System.out.println("Command not recognized");
                                break;
                        }

                        System.out.println("[D]ay View | [M]onth view | [G]o back");
                    }
                    break;

                case "c": // create
                    m.addEvent();
                    break;

                case "g": // go to
                    System.out.println("Please enter the date you would like to go to in \"MM/DD/YYYY\":");
                    date_str = sc.nextLine().split("/");
                    date = new int[date_str.length];
                    for (int i = 0; i < date_str.length; i++)
                        date[i] = Integer.parseInt(date_str[i]);
                    m.printEventOnDay(LocalDate.of(date[2], date[0] , date[1]));
                    break;

                case "e": // event list
                    m.printAllEvents();
                    break;

                case "d": // delete
                    System.out.println("Delete: [S]elected | [A]ll | [G]o back");
                    while (!((input = sc.nextLine().toLowerCase()).equals("g")))
                    {
                        switch (input)
                        {
                            case "s":
                                System.out.println("Please enter the date you would like to go to in \"MM/DD/YYYY\":");
                                date_str = sc.nextLine().split("/");
                                date = new int[date_str.length];
                                for (int i = 0; i < date_str.length; i++)
                                    date[i] = Integer.parseInt(date_str[i]);
                                m.printEventOnDay(LocalDate.of(date[2], date[0] , date[1]));

                                System.out.println("What is the name of the event you would like to delete?");
                                String name_in = sc.nextLine();

                                if(!m.delete(LocalDate.of(date[2], date[0] , date[1]), name_in))
                                    System.out.println("Delete failed, no event called: " + name_in);
                                else
                                    System.out.println(name_in + " deleted successfully");
                                break;

                            case "a":
                                System.out.println("Please enter the date you would like to go to in \"MM/DD/YYYY\":");
                                date_str = sc.nextLine().split("/");
                                date = new int[date_str.length];
                                for (int i = 0; i < date_str.length; i++)
                                    date[i] = Integer.parseInt(date_str[i]);
                                m.clearEvents(LocalDate.of(date[2], date[0] , date[1]));
                                System.out.println("All events on inputted day have been deleted");
                                break;

                            default:
                                System.out.println("Command not recognized");
                        }
                        System.out.println("Delete: [S]elected | [A]ll | [G]o back");
                    }
                    break;

                case "q": // quit
                    System.out.println("Good Bye");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }

    }
}

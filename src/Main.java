import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // make a list to hold CallEvent objects
        List<CallEvent> events = new ArrayList<>();
        events.add(new Call("Bob", "Alice", 1711132463));
        events.add(new Call("Carl", "Doug", 1711132465));
        events.add(new Hangup("Alice", "Bob", 1711132467));
        events.add(new Call("Ed", "Frank", 1711132481));
        events.add(new Hangup("Carl", "Doug", 1711132482));
        events.add(new Call("Bob", "Doug", 1711132483));
        events.add(new Hangup("Doug", "Bob", 1711132484));
        events.add(new Hangup("Ed", "Frank", 1711132501));

        // make a function and return a list of all callers who have an average call time of less than 5 seconds
        List<String> highCallers = CallEvent.getHighCallers(events);

        System.out.println(highCallers);
        
    }
}
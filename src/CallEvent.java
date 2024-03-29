import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallEvent {
    private String from;
    private String to;
    private long timestamp;

    public CallEvent(String from, String to, long timestamp) {
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // pass in a CallEvent object and return true if the two CallEvent objects are equal
    public boolean matchCallers(String caller1, String caller2) {
        return (caller1.equals(from) && caller2.equals(to)) || (caller1.equals(to) && caller2.equals(from));
    }

    public static List<String> getHighCallers(List<CallEvent> events) {
        Map<String, ArrayList<Long>> callDurations = new HashMap<>();
        for (CallEvent event : events) {
            // check to see if event is hangup
            if (event instanceof Hangup) {
                // go through list of events and find call for hangup
                for (CallEvent call : events) {
                    // check to see if call is a call
                    if (call instanceof Call) {
                        // check to see if the call and hangup match
                        if (call.matchCallers(event.getFrom(), event.getTo())) {
                            // calculate duration
                            long duration = (event.getTimestamp() - call.getTimestamp());
                            // add call duration to map
                            if (!callDurations.containsKey(call.getFrom())) {
                                callDurations.put(call.getFrom(), new ArrayList<>());
                            }
                            callDurations.get(call.getFrom()).add(duration); 
                        }
                    }
                }
            }
        }

        List<String> highCallers = new ArrayList<>();
        for (String key : callDurations.keySet()) {
            long totalCallTime = 0;
            // calculate total call time
            for (long time : callDurations.get(key)) {
                totalCallTime += time;
            }
            // calculate average call time
            double averageCallTime = totalCallTime / (double) callDurations.get(key).size();
            if (averageCallTime < 5) {
                highCallers.add(key);
            }
        }
        return highCallers;
    }

}

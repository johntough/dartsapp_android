package murrayfield.sportsbar.dartsapp.request;

public interface AsyncResponse {

    enum Endpoint {
        ACHIEVEMENTS, PLAYERS, FIXTURES, RESULTS, TABLES, WEEKS
    }

    void processFinish(String output, Endpoint endpoint);
}
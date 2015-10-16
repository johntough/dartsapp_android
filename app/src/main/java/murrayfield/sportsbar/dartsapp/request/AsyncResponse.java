package murrayfield.sportsbar.dartsapp.request;

public interface AsyncResponse {

    enum Endpoint {
        PLAYERS, FIXTURES, RESULTS, WEEKS
    }

    void processFinish(String output, Endpoint endpoint);
}
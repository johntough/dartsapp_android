package murrayfield.sportsbar.dartsapp.request;

public interface AsyncResponse {

    enum Endpoint {
        PLAYERS, FIXTURES, RESULTS
    }

    void processFinish(String output, Endpoint endpoint);
}
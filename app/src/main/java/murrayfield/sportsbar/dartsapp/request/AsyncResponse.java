package murrayfield.sportsbar.dartsapp.request;

public interface AsyncResponse {

    enum Endpoint {
        BESTLEGS, FIXTURES, HIGHFINISHES, PLAYERS, PLAYER180s, RESULTS, TABLES, WEEKS
    }

    void processFinish(String output, Endpoint endpoint);
}
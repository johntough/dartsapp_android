package murrayfield.sportsbar.dartsapp.request;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import murrayfield.sportsbar.dartsapp.request.AsyncResponse.Endpoint;


public class GetJSONData extends AsyncTask<String, String, String> {

    public AsyncResponse delegate = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    HttpURLConnection urlConnection;
    String currentEndpoint;

    @Override
    protected String doInBackground(String... params) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(params[0]);
            currentEndpoint = params[1];

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        Endpoint endpoint = null;

        switch (Endpoint.valueOf(currentEndpoint)) {
            case FIXTURES:
                endpoint = Endpoint.FIXTURES;
                break;
            case PLAYERS:
                endpoint = Endpoint.PLAYERS;
                break;
            case RESULTS:
                endpoint = Endpoint.RESULTS;
                break;
        }

        delegate.processFinish(result, endpoint);
    }
}
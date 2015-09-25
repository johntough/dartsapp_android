package murrayfield.sportsbar.dartsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import murrayfield.sportsbar.dartsapp.request.AsyncResponse;
import murrayfield.sportsbar.dartsapp.request.GetJSONData;

public class FixtureActivity extends AppCompatActivity implements AsyncResponse {

    GetJSONData asyncTask = new GetJSONData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);
        asyncTask.delegate = this;
        asyncTask.execute("http://nodejs-dartsapp.rhcloud.com/fixtures", "FIXTURES");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fixture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent;
        switch (id) {
            case  R.id.achievements_menu:
                break;
            case R.id.home_menu_text:
                intent = new Intent(this, HomeActivity.class);
                this.startActivity(intent);
                break;
            case R.id.players_menu_text:
                intent = new Intent(this, PlayerActivity.class);
                this.startActivity(intent);
            case R.id.results_menu_text:
                break;
            case R.id.tables_menu_text:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String output, Endpoint endpoint) {

        if (endpoint == Endpoint.FIXTURES) {
            TableLayout table = new TableLayout(this);

            table.setStretchAllColumns(true);
            table.setShrinkAllColumns(true);

            JSONObject jObject;
            JSONArray jArray = null;
            try {
                jObject = new JSONObject(output);
                jArray = jObject.getJSONArray("fixtures");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject objectInArray;
                String playerOne;
                String playerTwo;
                String venue;
                int orderOfPlay;
                String weekDate;
                try {
                    objectInArray = jArray.getJSONObject(i);

                    weekDate = objectInArray.getString("weekDate");
                    playerOne = objectInArray.getString("playerOne");

                    String[] playerNameArray;

                    // This ensures only forename and surname are displayed in the table
                    playerNameArray = playerOne.split("\\s+");
                    if (playerNameArray.length > 2) {
                        playerOne = playerNameArray[0] + " " + playerNameArray[1];
                    }
                    // This ensures only forename and surname are displayed in the table
                    playerTwo = objectInArray.getString("playerTwo");
                    playerNameArray = playerTwo.split("\\s+");
                    if (playerNameArray.length > 2) {
                        playerTwo = playerNameArray[0] + " " + playerNameArray[1];
                    }

                    venue = objectInArray.getString("venue");
                    orderOfPlay = objectInArray.getInt("orderOfPlay");

                    TableRow fixtureRow = new TableRow(this);

                    TextView weekDateLabel = new TextView(this);
                    weekDateLabel.setText(weekDate);

                    TextView playerOneLabel = new TextView(this);
                    playerOneLabel.setText(playerOne);

                    TextView playerTwoLabel = new TextView(this);
                    playerTwoLabel.setText(playerTwo);

                    TextView venueLabel = new TextView(this);
                    venueLabel.setText(venue);

                    TextView orderOfPlayLabel = new TextView(this);
                    orderOfPlayLabel.setText(Integer.toString(orderOfPlay));

                    fixtureRow.addView(weekDateLabel);
                    fixtureRow.addView(playerOneLabel);
                    fixtureRow.addView(playerTwoLabel);
                    fixtureRow.addView(venueLabel);
                    fixtureRow.addView(orderOfPlayLabel);

                    table.addView(fixtureRow);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            RelativeLayout main = (RelativeLayout)findViewById(R.id.fixture_activity);
            main.addView(table);
        }
    }
}
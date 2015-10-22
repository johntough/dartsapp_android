package murrayfield.sportsbar.dartsapp;

import android.content.Intent;
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

public class ResultActivity extends BaseActivity implements AsyncResponse {

    GetJSONData asyncTask = new GetJSONData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!isConnectedToInternet()) {
            // popup to inform user of no internet connection
            onCreateDialogNoInternetConnection().show();
        } else {
            setContentView(R.layout.activity_result);
            asyncTask.delegate = this;
            asyncTask.execute("http://nodejs-dartsapp.rhcloud.com/results", "RESULTS");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.home_menu_text:
                intent = new Intent(this, HomeActivity.class);
                this.startActivity(intent);
                break;
            case R.id.fixtures_menu_text:
                intent = new Intent(this, FixtureActivity.class);
                this.startActivity(intent);
                break;
            case R.id.players_menu_text:
                intent = new Intent(this, PlayerActivity.class);
                this.startActivity(intent);
                break;
            case  R.id.player180_menu:
                intent = new Intent(this, Player180Activity.class);
                this.startActivity(intent);
                break;
            case R.id.tables_menu_text:
                intent = new Intent(this, TableActivity.class);
                this.startActivity(intent);
                break;
            case R.id.weeks_menu_text:
                intent = new Intent(this, WeekActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String output, Endpoint endpoint) {

        if (endpoint == Endpoint.RESULTS) {
            TableLayout table = new TableLayout(this);

            JSONObject jObject;
            JSONArray jArray = null;
            try {
                jObject = new JSONObject(output);
                jArray = jObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null) {

                // converting value from dps to pixels using the display scale factor
                final float scale = this.getResources().getDisplayMetrics().density;
                int dateWidthPx = (int) (250 * scale + 0.5f);
                int groupWidthPx = (int) (100 * scale + 0.5f);
                int playerWidthPx = (int) (140 * scale + 0.5f);
                int legsWonWidthPx = (int) (80 * scale + 0.5f);

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject objectInArray;
                    String weekDate;
                    String group;
                    String playerOne;
                    String playerOneLegsWon;
                    String playerTwo;
                    String playerTwoLegsWon;
                    try {
                        objectInArray = jArray.getJSONObject(i);

                        weekDate = objectInArray.getString("weekDate");
                        group = objectInArray.getString("group");

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

                        playerOneLegsWon = Integer.toString(objectInArray.getInt("playerOneLegsWon"));
                        playerTwoLegsWon = Integer.toString(objectInArray.getInt("playerTwoLegsWon"));

                        TableRow fixtureRow = new TableRow(this);

                        TextView weekDateLabel = new TextView(this);
                        weekDateLabel.setText(weekDate);
                        weekDateLabel.setWidth(dateWidthPx);

                        TextView groupLabel = new TextView(this);
                        groupLabel.setText(group);
                        groupLabel.setWidth(groupWidthPx);

                        TextView playerOneLabel = new TextView(this);
                        playerOneLabel.setText(playerOne);
                        playerOneLabel.setWidth(playerWidthPx);

                        TextView playerOneLegsWonLabel = new TextView(this);
                        playerOneLegsWonLabel.setText(playerOneLegsWon);
                        playerOneLegsWonLabel.setWidth(legsWonWidthPx);

                        TextView playerTwoLabel = new TextView(this);
                        playerTwoLabel.setText(playerTwo);
                        playerTwoLabel.setWidth(playerWidthPx);

                        TextView playerTwoLegsWonLabel = new TextView(this);
                        playerTwoLegsWonLabel.setText(playerTwoLegsWon);
                        playerTwoLegsWonLabel.setWidth(legsWonWidthPx);

                        fixtureRow.addView(weekDateLabel);
                        fixtureRow.addView(groupLabel);
                        fixtureRow.addView(playerOneLabel);
                        fixtureRow.addView(playerOneLegsWonLabel);
                        fixtureRow.addView(playerTwoLabel);
                        fixtureRow.addView(playerTwoLegsWonLabel);

                        table.addView(fixtureRow);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            RelativeLayout main = (RelativeLayout)findViewById(R.id.result_activity);
            main.addView(table);
        }
    }
}
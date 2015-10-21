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

public class PlayerActivity extends BaseActivity implements AsyncResponse {

    GetJSONData asyncTask = new GetJSONData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isConnectedToInternet()) {
            // popup to inform user of no internet connection
            onCreateDialogNoInternetConnection().show();
        } else {
            setContentView(R.layout.activity_player);
            asyncTask.delegate = this;
            asyncTask.execute("http://nodejs-dartsapp.rhcloud.com/players", "PLAYERS");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
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
            case  R.id.achievements_menu:
                intent = new Intent(this, AchievementActivity.class);
                this.startActivity(intent);
                break;
            case R.id.fixtures_menu_text:
                intent = new Intent(this, FixtureActivity.class);
                this.startActivity(intent);
                break;
            case R.id.home_menu_text:
                intent = new Intent(this, HomeActivity.class);
                this.startActivity(intent);
                break;
            case R.id.results_menu_text:
                intent = new Intent(this, ResultActivity.class);
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

        if (endpoint == Endpoint.PLAYERS) {
            TableLayout table = new TableLayout(this);

            JSONObject jObject;
            JSONArray jArray = null;

            try {
                jObject = new JSONObject(output);
                jArray = jObject.getJSONArray("players");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null) {

                // converting value from dps to pixels using the display scale factor
                final float scale = this.getResources().getDisplayMetrics().density;
                int groupWidthPx = (int) (120 * scale + 0.5f);
                int nameWidthPx = (int) (200 * scale + 0.5f);

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject objectInArray;

                    String playerForename;
                    String playerSurname;
                    String group;
                    try {
                        objectInArray = jArray.getJSONObject(i);

                        group = objectInArray.getString("group");

                        playerForename = objectInArray.getString("forename");

                        String[] playerNameArray;

                        // This ensures only forename and surname are displayed in the table
                        playerNameArray = playerForename.split("\\s+");
                        if (playerNameArray.length > 2) {
                            playerForename = playerNameArray[0];
                        }
                        // This ensures only forename and surname are displayed in the table
                        playerSurname = objectInArray.getString("surname");
                        playerNameArray = playerSurname.split("\\s+");
                        if (playerNameArray.length > 2) {
                            playerSurname = playerNameArray[0];
                        }

                        TableRow playerRow = new TableRow(this);

                        TextView groupLabel = new TextView(this);
                        groupLabel.setText(group);
                        groupLabel.setWidth(groupWidthPx);

                        TextView playerLabel = new TextView(this);
                        playerLabel.setText(playerForename + " " + playerSurname);
                        playerLabel.setWidth(nameWidthPx);

                        playerRow.addView(groupLabel);
                        playerRow.addView(playerLabel);
                        table.addView(playerRow);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            RelativeLayout main = (RelativeLayout)findViewById(R.id.player_activity);
            main.addView(table);
        }
    }
}

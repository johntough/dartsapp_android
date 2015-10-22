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

public class HighFinishActivity extends BaseActivity implements AsyncResponse {

    GetJSONData asyncTask = new GetJSONData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isConnectedToInternet()) {
            // popup to inform user of no internet connection
            onCreateDialogNoInternetConnection().show();
        } else {
            setContentView(R.layout.activity_high_finish);
            asyncTask.delegate = this;
            asyncTask.execute("http://nodejs-dartsapp.rhcloud.com/highfinishes", "HIGHFINISHES");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_finish, menu);
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
            case R.id.fixtures_menu_text:
                intent = new Intent(this, FixtureActivity.class);
                this.startActivity(intent);
                break;
            case R.id.home_menu_text:
                intent = new Intent(this, HomeActivity.class);
                this.startActivity(intent);
                break;
            case R.id.players_menu_text:
                intent = new Intent(this, PlayerActivity.class);
                this.startActivity(intent);
                break;
            case  R.id.player180_menu_text:
                intent = new Intent(this, Player180Activity.class);
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

        if (endpoint == Endpoint.HIGHFINISHES) {
            TableLayout table = new TableLayout(this);

            JSONObject jObject;
            JSONArray jArray = null;
            try {
                jObject = new JSONObject(output);
                jArray = jObject.getJSONArray(endpoint.toString().toLowerCase());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null) {

                // converting value from dps to pixels using the display scale factor
                final float scale = this.getResources().getDisplayMetrics().density;
                int columnWidthPx = (int) (200 * scale + 0.5f);

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject objectInArray;

                    String columnOneString;
                    String columnTwoString;

                    try {
                        objectInArray = jArray.getJSONObject(i);

                        columnOneString = objectInArray.getString("player");
                        columnTwoString = objectInArray.getString("checkout");

                        TableRow tableRow = new TableRow(this);

                        TextView columnOneLabel = new TextView(this);
                        columnOneLabel.setWidth(columnWidthPx);
                        columnOneLabel.setText(columnOneString);

                        TextView columnTwoLabel = new TextView(this);
                        columnTwoLabel.setText(columnTwoString);
                        columnTwoLabel.setWidth(columnWidthPx);

                        tableRow.addView(columnOneLabel);
                        tableRow.addView(columnTwoLabel);
                        table.addView(tableRow);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            RelativeLayout main = (RelativeLayout)findViewById(R.id.high_finish_activity);
            main.addView(table);
        }
    }
}

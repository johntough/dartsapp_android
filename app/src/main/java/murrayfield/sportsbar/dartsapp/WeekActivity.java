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

public class WeekActivity extends AppCompatActivity implements AsyncResponse {

    GetJSONData asyncTask = new GetJSONData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        asyncTask.delegate = this;
        asyncTask.execute("http://nodejs-dartsapp.rhcloud.com/weeks", "WEEKS");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_week, menu);
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
                break;
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

        if (endpoint == Endpoint.WEEKS) {
            TableLayout table = new TableLayout(this);

            table.setStretchAllColumns(true);
            table.setShrinkAllColumns(true);

            JSONObject jObject;
            JSONArray jArray = null;
            try {
                jObject = new JSONObject(output);
                jArray = jObject.getJSONArray("weeks");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject objectInArray;

                String name;
                String date;
                try {
                    objectInArray = jArray.getJSONObject(i);

                    name = objectInArray.getString("name");
                    date = objectInArray.getString("date");

                    TableRow weekRow = new TableRow(this);

                    TextView nameLabel = new TextView(this);
                    nameLabel.setText(name);

                    TextView dateLabel = new TextView(this);
                    dateLabel.setText(date);

                    weekRow.addView(nameLabel);
                    weekRow.addView(dateLabel);
                    table.addView(weekRow);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            RelativeLayout main = (RelativeLayout)findViewById(R.id.week_activity);
            main.addView(table);
        }
    }
}
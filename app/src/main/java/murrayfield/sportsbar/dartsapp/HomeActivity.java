package murrayfield.sportsbar.dartsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
            case R.id.players_menu_text:
                intent = new Intent(this, PlayerActivity.class);
                this.startActivity(intent);
                break;
            case  R.id.player180_menu:
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
}
package solange.amor.my_love.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import solange.amor.my_love.R;
import solange.amor.my_love.dao.NewsDao;
import solange.amor.my_love.fragment.AddFragment;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initInstances();

        NewsDao dao = getIntent().getParcelableExtra("dao");

        initFragment(savedInstanceState, dao);
    }

    private void initInstances() {
        getSupportActionBar().setTitle("Añadir foto");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initFragment(Bundle savedInstanceState, NewsDao dao) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, AddFragment.newInstance(dao))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

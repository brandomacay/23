package solange.amor.my_love.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import solange.amor.my_love.R;
import solange.amor.my_love.controller.StartActivity;
import solange.amor.my_love.controller.StartApp;
import solange.amor.my_love.dao.NewsDao;
import solange.amor.my_love.fragment.Casa;
import solange.amor.my_love.fragment.FotosSinInternet;
import solange.amor.my_love.fragment.MainFragment;
import solange.amor.my_love.fragment.Web;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener{

    private final int[] colors = {R.color.white, R.color.white, R.color.white};
    ProgressDialog mProgressDialog;
    private Toolbar toolbar;
    private NoSwipePager viewPager;
    private AHBottomNavigation bottomNavigation;
    private BottomBarAdapter pagerAdapter;

    private boolean notificationVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StartApp startApp = new StartApp(getApplicationContext());
        if (startApp.isFirstTimeLaunch()) {
            finish();
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("­");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        Toast.makeText(MainActivity.this,"Oh genial! Bienvenida de nuevo", Toast.LENGTH_SHORT).show();

        setupViewPager();



        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        setupBottomNavBehaviors();
        setupBottomNavStyle();

       // createFakeNotification();

        addBottomNavigationItems();
        bottomNavigation.setCurrentItem(0);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
//                fragment.updateColor(ContextCompat.getColor(MainActivity.this, colors[position]));

                if (!wasSelected)
                    viewPager.setCurrentItem(position);

                // remove notification badge
                int lastItemPos = bottomNavigation.getItemsCount() - 2;
                if (notificationVisible && position == lastItemPos)
                    bottomNavigation.setNotification(new AHNotification(), lastItemPos);

                return true;
            }
        });

    }



    private void setupViewPager() {
        viewPager = (NoSwipePager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(casa(R.color.white));
        pagerAdapter.addFragments(foto(R.color.white));
        pagerAdapter.addFragments(web(R.color.white));

        viewPager.setAdapter(pagerAdapter);
    }

    @NonNull
    private FotosSinInternet foto(int color) {
        FotosSinInternet fragment = new FotosSinInternet();
        fragment.setArguments(passFragmentArguments(fetchColor(color)));
        return fragment;
    }

    @NonNull
    private Casa casa(int color) {
        Casa fragment = new Casa();
        fragment.setArguments(passFragmentArguments(fetchColor(color)));
        return fragment;
    }

    @NonNull
    private Web web(int color) {
        Web fragment = new Web();
        fragment.setArguments(passFragmentArguments(fetchColor(color)));
        return fragment;
    }

    @NonNull
    private Bundle passFragmentArguments(int color) {
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        return bundle;
    }

    /*private void createFakeNotification() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText("♡")
                        .setBackgroundColor(Color.RED)
                        .setTextColor(Color.WHITE)
                        .build();
                // Adding notification to last item.

                bottomNavigation.setNotification(notification, bottomNavigation.getItemsCount() - 2);

                notificationVisible = true;
            }
        }, 250);
    }*/


    public void setupBottomNavBehaviors() {
//        bottomNavigation.setBehaviorTranslationEnabled(false);

        /*
        Before enabling this. Change MainActivity theme to MyTheme.TranslucentNavigation in
        AndroidManifest.

        Warning: Toolbar Clipping might occur. Solve this by wrapping it in a LinearLayout with a top
        View of 24dp (status bar size) height.
         */
        bottomNavigation.setTranslucentNavigationEnabled(false);
    }

    /**
     * Adds styling properties to {@link AHBottomNavigation}
     */
    private void setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.

        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(fetchColor(R.color.morado));
        bottomNavigation.setAccentColor(fetchColor(R.color.bottomtab_0));
        bottomNavigation.setInactiveColor(fetchColor(R.color.negro));

        // Colors for selected (active) and non-selected items.
        bottomNavigation.setColoredModeColors(fetchColor(R.color.morado),
                fetchColor(R.color.inactivo));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }


    /**
     * Adds (items) {@link AHBottomNavigationItem} to {@link AHBottomNavigation}
     * Also assigns a distinct color to each Bottom Navigation item, used for the color ripple.
     */
    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_home_black_24dp, colors[0]);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.firebase, colors[1]);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.info, colors[2]);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
    }


    /**
     * Simple facade to fetch color resource, so I avoid writing a huge line every time.
     *
     * @param color to fetch
     * @return int color value.
     */
    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }


    @Override
    public void onItemListClicked(NewsDao dao) {
        Intent intent = new Intent(MainActivity.this,AddActivity.class);
        intent.putExtra("dao",dao);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.carta) {
            Intent i = new Intent(this, Carta.class );
            startActivity(i);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
            return true;
        }
        if (id== R.id.version){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inplementar menu en el toolbar
        getMenuInflater().inflate(R.menu.menu_buzon, menu);
        return true;
    }


}

package solange.amor.my_love.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import solange.amor.my_love.R;
import solange.amor.my_love.util.SectionsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragmentFotos extends Fragment{
    ViewPager viewPager;
    TabLayout tabLayout;
    private FragmentActivity myContext;

    public MainFragmentFotos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_mainfragment, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.container);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        setupViewPager();
        return rootView;
    }
    /**
     * Responsible for adding the 2 tabs: FotosSinInternet,MainFragment
     */
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FotosSinInternet()); //index 0
        adapter.addFragment(new MainFragment()); //index 1

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.wifi_off);
        tabLayout.getTabAt(1).setIcon(R.drawable.wifi_on);

    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}

package solange.amor.my_love.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import solange.amor.my_love.R;
import solange.amor.my_love.adapter.NewsAdapter;
import solange.amor.my_love.connection.FirebaseConnection;
import solange.amor.my_love.dao.NewsDao;


/**
 * Created by Brandon_Macay on 3/29/2017 AD.
 */

public class MainFragment extends Fragment {
    ProgressDialog mProgressDialog;

    private ListView listView;
    private List<NewsDao> newsList;
    private NewsAdapter adapter;
    private FirebaseConnection firebase;
    private FloatingActionButton fab;
     ProgressBar pb;

    public MainFragment() {
        super();
    }


    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_foto, container, false);
        initInstances(rootView);
        showData();
        return rootView;
    }

    private void initInstances(View rootView) {
        firebase = new FirebaseConnection();
        listView = rootView.findViewById(R.id.listView);
        pb = rootView.findViewById(R.id.progressBar2);

        newsList = new ArrayList<>();
        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkConnected()) {
                    /*mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setMessage("Verificando coneccion...");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();*/
                    //isWifiConnected();
                    NewsDao dao = new NewsDao();
                    openAddFragment(dao);

                   // FragmentListener listener = (FragmentListener) getActivity();
                //listener.onItemListClicked(dao);
                }else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Acceso denegado")
                            .setCancelable(false)
                            .setMessage("Esta funcion esta bloqueada debido a que no dispones de Internet")
                            .setPositiveButton(R.string.de_acuerdo, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getContext(),"Lo siento", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ).setIcon(R.drawable.wifi_off).show();
                }
            }
        });
    }

    private void showData(){
        Query query = firebase.getDatabase("news");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NewsDao dao = postSnapshot.getValue(NewsDao.class);
                    newsList.add(dao);
                    pb.setVisibility(View.INVISIBLE);

                }
                adapter = new NewsAdapter(newsList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        NewsDao dao = newsList.get(i);

                        //openAddFragment(dao);

                        FragmentListener listener = (FragmentListener) getActivity();
                        listener.onItemListClicked(dao);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //implement interface
    public interface FragmentListener {
        void onItemListClicked(NewsDao dao);
    }

    private void openAddFragment(NewsDao dao){
//        NewsDao dao = new NewsDao();

        FragmentListener listener = (FragmentListener) getActivity();
        listener.onItemListClicked(dao);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }

    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        mProgressDialog.hide();
        Toast.makeText(getContext(),"Genial ahora puedes usar esta funcion", Toast.LENGTH_SHORT).show();
        return networkInfo != null && (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) && networkInfo.isConnected();
    }


}

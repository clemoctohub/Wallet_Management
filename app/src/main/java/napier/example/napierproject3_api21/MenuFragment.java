package napier.example.napierproject3_api21;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }

    BottomNavigationView navigationView;
    FrameLayout frameLayout;

    private HomeFragment homeFragment;
    private GraphFragment graphFragment;
    private ProfilFragment profilFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        navigationView = view.findViewById(R.id.bottomNavigationView);
        frameLayout = view.findViewById(R.id.frameLayout);

        homeFragment = new HomeFragment(getArguments().getString("account_id"));
        graphFragment = new GraphFragment(getArguments().getString("account_id"));
        profilFragment = new ProfilFragment();

        InitializeFragment(homeFragment);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.home_nav:
                        InitializeFragment(homeFragment);
                        return true;
                    case R.id.graph_nav:
                        InitializeFragment(graphFragment);
                        return true;
                    case R.id.profil_nav:
                        InitializeFragment(profilFragment);
                        return true;
                }

                return false;
            }
        });
        return view;
    }

    private void InitializeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
package com.example.myapplication.Fragment;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;

public class BaseFragment extends Fragment {
    public void nextFragment(Fragment fragment, int fl) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(fl, fragment);
        ft.commit();
    }
}

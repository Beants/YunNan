package com.pilab.yunnan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import static android.support.constraint.Constraints.TAG;


public class SearchFragment extends Fragment {
    private android.support.v7.widget.SearchView searchView;
    private Spinner spinner;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        searchView = (android.support.v7.widget.SearchView) view.findViewById(R.id.searchview);
        spinner = view.findViewById(R.id.search_spinner);
        recyclerView = view.findViewById(R.id.search_recycler_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i(TAG, "onQueryTextSubmit: "+s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange: "+s);
                return false;
            }
        });
    }
}

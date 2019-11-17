package com.example.navviewmodel;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navviewmodel.databinding.FragmentDetailBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail, container, false);
        MyViewModel myViewModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail,
                container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(getActivity());
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_detailFragment_to_homeFragment);
            }
        });

        return binding.getRoot();
    }

}

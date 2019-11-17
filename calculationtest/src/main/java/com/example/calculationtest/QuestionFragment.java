package com.example.calculationtest;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.calculationtest.databinding.FragmentQuestionBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {
    private MyViewModel myViewModel;
    private StringBuilder builder;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_question, container, false);
        myViewModel = ViewModelProviders.of(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity()))
                .get(MyViewModel.class);
//        myViewModel.getCurrentScore().setValue(0);
//        myViewModel.generator();
        final FragmentQuestionBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_question, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());

        builder = new StringBuilder();
        String editText = myViewModel.getEditText().getValue();
        if (!TextUtils.isEmpty(editText)) {
            builder.append(editText);
            binding.textView6.setText(builder.toString());
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button0:
                        builder.append("0");
                        break;
                    case R.id.button1:
                        builder.append("1");
                        break;
                    case R.id.button2:
                        builder.append("2");
                        break;
                    case R.id.button3:
                        builder.append("3");
                        break;
                    case R.id.button4:
                        builder.append("4");
                        break;
                    case R.id.button5:
                        builder.append("5");
                        break;
                    case R.id.button6:
                        builder.append("6");
                        break;
                    case R.id.button7:
                        builder.append("7");
                        break;
                    case R.id.button8:
                        builder.append("8");
                        break;
                    case R.id.button9:
                        builder.append("9");
                        break;
                    case R.id.buttonClear:
                        builder.setLength(0);
                        myViewModel.getEditText().setValue("");
                        break;
                }
                if (builder.length() == 0) {
                    binding.textView6.setText(getString(R.string.input_indicator));
                } else {
                    binding.textView6.setText(builder.toString());
                }
            }
        };
        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonClear.setOnClickListener(listener);
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.getEditText().setValue("");
                if (builder.length() == 0) {
                    builder.append("-1");
                }
                //noinspection ConstantConditions
                if (Integer.valueOf(builder.toString()).intValue() == myViewModel.getAnswer().getValue()) {
                    myViewModel.answerCorrect();
                    binding.textView6.setText(R.string.answer_correct_message);
                } else {
                    NavController navController = Navigation.findNavController(v);
                    if (myViewModel.win_flat) {
                        navController.navigate(R.id.action_questionFragment_to_winFragment);
                        myViewModel.win_flat = false;
                        myViewModel.save();
                    } else {
                        navController.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                }
                builder.setLength(0);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        String editText = builder.toString();
        if (!TextUtils.isEmpty(editText)) {
            myViewModel.getEditText().setValue(editText);
        }
        super.onPause();
    }
}

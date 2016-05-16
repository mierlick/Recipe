package com.captech.merlick.recipe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RecipeDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button yesButton;
    private Button noButton;
    private RecipeFragmentListener recipeFragmentListener;

    public RecipeDialogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_dialog, container);

        yesButton = (Button) view.findViewById(R.id.yesButton);
        noButton = (Button) view.findViewById(R.id.noButton);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        recipeFragmentListener.dialogButtonClick(view.getId());
        dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        recipeFragmentListener = (RecipeFragmentListener) activity;
    }

    public interface RecipeFragmentListener {
        public void dialogButtonClick(int buttonId);
    }
}

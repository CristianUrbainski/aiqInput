package com.aiqfome.aiqinput.sheets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiqfome.aiqinput.R;
import com.aiqfome.aiqinput.adapters.CommonSearchableAdapter;
import com.aiqfome.aiqinput.databinding.LayoutBottomSheetSearchableListBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SearchableListBottomSheet extends BottomSheetDialogFragment {

    LayoutBottomSheetSearchableListBinding binding;

    private String title;
    private CommonSearchableAdapter adapter;
    private BottomSheetDialog dialog;

    public SearchableListBottomSheet(String title, CommonSearchableAdapter adapter) {
        this.title = title;
        this.adapter = adapter;

        this.setStyle(STYLE_NORMAL, R.style.aiq_BottomSheet);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = LayoutBottomSheetSearchableListBinding.inflate(inflater);
        binding.title.setText(title);

        binding.rvItems.setAdapter(adapter);
        binding.rvItems.setNestedScrollingEnabled(true);

        binding.rvItems.setLayoutManager(
                new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,
                false)
        );

        binding.svSearch.setOnSearchClickListener(v -> {
            BottomSheetDialog d = dialog;

            FrameLayout bottomSheet = d.findViewById
                    (com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null)
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        binding.svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        binding.executePendingBindings();
        return binding.getRoot();
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Context context = getContext();

        if (context != null) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null && this.getView() != null)
                imm.hideSoftInputFromWindow
                        (this.getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        super.onDismiss(dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

}

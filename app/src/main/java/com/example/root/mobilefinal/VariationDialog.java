package com.example.root.mobilefinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class VariationDialog extends DialogFragment implements View.OnClickListener {

    private Callback callback;
    EditText editText_color;
    EditText editText_size;

    static VariationDialog newInstance() {
        return new VariationDialog();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.variation_dialog, container, false);
        ImageButton close = view.findViewById(R.id.fullscreen_dialog_close);
        ImageButton action = view.findViewById(R.id.fullscreen_dialog_action);

        editText_color = view.findViewById(R.id.editText_colorAttribute);
        editText_size = view.findViewById(R.id.editText_sizeAttribute);

        close.setOnClickListener(this);
        action.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.fullscreen_dialog_close:
                dismiss();
                break;

            case R.id.fullscreen_dialog_action:
                callback.onActionClick(editText_color.getText().toString(), editText_size.getText().toString());
                dismiss();
                break;

        }

    }

    public interface Callback {

        void onActionClick(String color, String size);

    }

}
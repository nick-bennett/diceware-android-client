package edu.cnm.deepdive.diceware.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.diceware.R;
import edu.cnm.deepdive.diceware.model.Passphrase;
import java.util.Arrays;

public class PassphraseFragment extends DialogFragment {

  private OnCompleteListener listener;
  private Passphrase passphrase;

  public static PassphraseFragment newInstance() {
    return newInstance(null);
  }

  public static PassphraseFragment newInstance(Passphrase passphrase) {
    PassphraseFragment fragment = new PassphraseFragment();
    Bundle args = new Bundle();
    if (passphrase != null) {
      args.putSerializable("passphrase", passphrase);
    }
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    Passphrase temp = (Passphrase) getArguments().getSerializable("passphrase");
    Passphrase passphrase = (temp != null) ? temp : new Passphrase();
    View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_passphrase, null);
    EditText passphraseKey = view.findViewById(R.id.passphrase_key);
    EditText passphraseWords = view.findViewById(R.id.passphrase_words);
    if (savedInstanceState == null) {
      if (passphrase.getKey() != null) {
        passphraseKey.setText(passphrase.getKey());
      }
      if (passphrase.getWords() != null) {
        passphraseWords.setText(passphrase.getWords().toString()
            .replaceAll("^\\[|\\]$", "")
            .trim()
            .replaceAll("\\s*,\\s*", " "));
      }
    }
    return new AlertDialog.Builder(getContext())
        .setTitle("Passphrase Details")
        .setView(view)
        .setNegativeButton("Cancel", (dialog, button) -> {
        })
        .setPositiveButton("Ok", (dialog, button) -> {
          passphrase.setKey(passphraseKey.getText().toString().trim());
          String words = passphraseWords.getText().toString().trim();
          if (!words.isEmpty()) {
            passphrase.setWords(Arrays.asList(words.trim().split("\\s+")));
          } else {
            passphrase.setWords(null);
          }
          ((OnCompleteListener) getActivity()).complete(passphrase);
        })
        .create();
  }

  @FunctionalInterface
  public interface OnCompleteListener {

    void complete(Passphrase passphrase);

  }

}

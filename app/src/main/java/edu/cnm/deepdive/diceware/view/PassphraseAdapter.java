/*
 *  Copyright 2019 Nicholas Bennett & Deep Dive Coding/CNM Ingenuity
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cnm.deepdive.diceware.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.diceware.R;
import edu.cnm.deepdive.diceware.model.Passphrase;
import edu.cnm.deepdive.diceware.view.PassphraseAdapter.Holder;
import java.util.List;

/**
 *
 *
 * @author Nicholas Bennett, Todd Nordquist, Brian Bleck, Deep Dive Coding Java + Android Cohort 8
 */
public class PassphraseAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Passphrase> passphrases;
  private final OnClickListener clickListener;
  private final OnContextListener contextListener;

  /**
   *
   * @param context
   * @param passphrases
   * @param clickListener
   * @param contextListener
   */
  public PassphraseAdapter(Context context, List<Passphrase> passphrases,
      OnClickListener clickListener, OnContextListener contextListener) {
    this.context = context;
    this.passphrases = passphrases;
    this.clickListener = clickListener;
    this.contextListener = contextListener;
  }

  /**
   *
   * @param parent
   * @param viewType
   * @return
   */
  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.passphrase_item, parent, false);
    return new Holder(view);
  }

  /**
   *
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    Passphrase passphrase = passphrases.get(position);
    holder.bind(position, passphrase);
  }

  /**
   *
   * @return
   */
  @Override
  public int getItemCount() {
    return passphrases.size();
  }

  /**
   *
   */
  @FunctionalInterface
  public interface OnClickListener {

    /**
     *
     * @param view
     * @param position
     * @param passphrase
     */
    void onClick(View view, int position, Passphrase passphrase);

  }

  /**
   *
   */
  @FunctionalInterface
  public interface OnContextListener {

    /**
     *
     * @param menu
     * @param position
     * @param passphrase
     */
    void onLongPress(Menu menu, int position, Passphrase passphrase);

  }

  class Holder extends RecyclerView.ViewHolder {

    private final View view;

    private Holder(@NonNull View itemView) {
      super(itemView);
      view = itemView;
    }

    private void bind(int position, Passphrase passphrase) {
      ((TextView) view).setText(passphrase.getKey());
      if (clickListener != null) {
        view.setOnClickListener((v) -> clickListener.onClick(v, position, passphrase));
      }
      if (contextListener != null) {
        view.setOnCreateContextMenuListener((menu, v, menuInfo) ->
            contextListener.onLongPress(menu, position, passphrase));
      }
    }

  }

}

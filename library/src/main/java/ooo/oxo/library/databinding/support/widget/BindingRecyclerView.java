/*
 * Moments - To the best Instagram client
 * Copyright (C) 2015  XiNGRZ <xxx@oxo.ooo>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program;  if not, see <http://www.gnu.org/licenses/>.
 */

package ooo.oxo.library.databinding.support.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class BindingRecyclerView {

    public static abstract class ListAdapter<T, VH extends ViewHolder> extends RecyclerView.Adapter<VH> {

        protected final LayoutInflater inflater;
        protected final ObservableList<T> data;

        public ListAdapter(Context context, ObservableList<T> data) {
            this.inflater = LayoutInflater.from(context);
            this.data = data;

            data.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
                @Override
                public void onChanged(ObservableList<T> sender) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<T> sender,
                                               int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList<T> sender,
                                                int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(ObservableList<T> sender,
                                             int fromPosition, int toPosition, int itemCount) {
                    for (int i = 0; i < itemCount; i++) {
                        notifyItemMoved(fromPosition + i, toPosition + i);
                    }
                }

                @Override
                public void onItemRangeRemoved(ObservableList<T> sender,
                                               int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }

    public static class ViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

        public final V binding;

        public ViewHolder(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup parent) {
            this(DataBindingUtil.<V>inflate(inflater, layoutId, parent, false));
        }

        public ViewHolder(V binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}

package com.tdr.firstlook.partsneeded;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PartsAdapter extends ArrayAdapter {
    private List<String> partList;
    private Context context;
    private int itemLayout;

    private ListFilter listFilter = new ListFilter();
    private List<String> partsListAllItems;

    public PartsAdapter(Context context, int resource, List<String> partsList) {
        super(context, resource, partsList);
        this.partList = partsList;
        this.context = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        return partList.size();
    }

    @Override
    public String getItem(int position) {
        return partList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView partName = view.findViewById(R.id.part_suggestion);
        partName.setText(getItem(position));
        return view;
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private final Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (partsListAllItems == null) {
                synchronized (lock) {
                    partsListAllItems = new ArrayList<>(partList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = partsListAllItems;
                    results.count = partsListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<String> matchValues = new ArrayList<>();

                for (String part : partsListAllItems) {
                    if (part.toLowerCase().contains(searchStrLowerCase)) {
                        matchValues.add(part);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                partList = (ArrayList<String>) results.values;
            } else {
                partList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}

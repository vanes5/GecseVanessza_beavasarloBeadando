package com.example.beavasarlasbeadando;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class TermekekAdapter extends BaseAdapter {

    private List<Termekek> termekekList;
    private Context context;

    public TermekekAdapter(List<Termekek> termekekList, Context context) {
        this.termekekList = termekekList;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.d("Adapter", "Adapter size: " + termekekList.size());
        return termekekList.size();
    }

    @Override
    public Object getItem(int i) {
        return termekekList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.card, viewGroup, false);

        TextView nev = view.findViewById(R.id.nevTextView);
        TextView egysegrar = view.findViewById(R.id.egysegarTextView);
        TextView mennyiseg = view.findViewById(R.id.mennyisegTextView);
        TextView mertekegyseg = view.findViewById(R.id.mertekegysegTextView);
        TextView bruttoAr = view.findViewById(R.id.bruttoarTextView);

        Termekek termek = termekekList.get(i);
        nev.setText(termek.getNev());
        egysegrar.setText(String.valueOf(termek.getEgysegAr()));
        mennyiseg.setText(String.valueOf(termek.getMennyiseg()));
        mertekegyseg.setText(String.valueOf(termek.getMertekegyseg()));
        bruttoAr.setText(String.valueOf(termek.getBruttoAr()));

        return view;
    }
}


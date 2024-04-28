package com.example.smartspend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smartspend.model.Transaction;
import com.example.smartspend.navigation.NavBar;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public ArrayList<Transaction> transactions;
    Context context;

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions){
        super(context, R.layout.transaction_card, transactions);
        this.transactions = transactions;
        this.context = context;
    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return transactions.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra. Naravno mozemo iskoristiti i
     * jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Ova metoda popunjava pojedinacan element ListView-a podacima.
     * Ako adapter cuva listu od n elemenata, adapter ce u petlji ici
     * onoliko puta koliko getCount() vrati. Prilikom svake iteracije
     * uzece java objekat sa odredjene poziciuje (model) koji cuva podatke,
     * i layout koji treba da prikaze te podatke (view) npr R.layout.AccommodationListing_card.
     * Kada adapter ima model i view, prosto ce uzeti podatke iz modela,
     * popuniti view podacima i poslati listview da prikaze, i nastavice
     * sledecu iteraciju.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Transaction transaction = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_card,
                    parent, false);
        }
        TextView name = convertView.findViewById(R.id.name);
        TextView date = convertView.findViewById(R.id.date);
        TextView type = convertView.findViewById(R.id.type);
        TextView value = convertView.findViewById(R.id.value);
        ImageView image = convertView.findViewById(R.id.icon);

        name.setText(transaction.getName());
        date.setText(transaction.getDate());
        type.setText(transaction.getType());
        value.setText(transaction.getValue() + " " + transaction.getCurrency());

        if(transaction != null){
            if(transaction.getType().equals("TRAVEL")) {
                image.setImageResource(R.drawable.airplane);
            } else if(transaction.getType().equals("GROCERIES")) {
                image.setImageResource(R.drawable.groceries);
            }else if(transaction.getType().equals("FUN")) {
                image.setImageResource(R.drawable.fun);
            }else if(transaction.getType().equals("OTHER")) {
                image.setImageResource(R.drawable.other);
            }else if(transaction.getType().equals("BILLS")) {
                image.setImageResource(R.drawable.bills);
            }else if(transaction.getType().equals("TRANSPORT")) {
                image.setImageResource(R.drawable.car);
            }
        }

        return convertView;
    }
}

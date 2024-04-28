package com.example.smartspend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.smartspend.model.Transaction;
import com.example.smartspend.navigation.NavBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private TransactionAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientUtils.setClientUtils(getSharedPreferences("sharedPref", MODE_PRIVATE));
        NavBar.setNavigationBar(findViewById(R.id.bottom_navigaiton), this, R.id.navigation_home);
        ClientUtils.setClientUtils(getSharedPreferences("sharedPref", MODE_PRIVATE));

        transactions = new ArrayList<>();
        adapter = new TransactionAdapter(this, transactions, this.getPreferences(Context.MODE_PRIVATE));
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = findViewById(R.id.root);
        transactions = new ArrayList<>();
        prepareTransactionList(transactions);
        listView = root.findViewById(R.id.list);
        adapter = new TransactionAdapter(this, transactions, this.getPreferences(Context.MODE_PRIVATE));
        listView.setAdapter(adapter);

        return root;
    }

    private void prepareTransactionList(ArrayList<Transaction> products){
        products.clear();   //in case it's not initialization but searching
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<List<Transaction>> transactions = ClientUtils.service.getAll();
        try{
            Response<List<Transaction>> response = transactions.execute();
            ArrayList<Transaction> listings = (ArrayList<Transaction>) response.body();
            for(Transaction t : listings) {
                products.add(t);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING TRANSACTIONS");
            ex.printStackTrace();
        }
    }

}
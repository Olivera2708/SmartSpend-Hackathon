package com.example.smartspend;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.example.smartspend.model.HomeCard;
import com.example.smartspend.model.Transaction;
import com.example.smartspend.navigation.NavBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private TransactionAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavBar.setNavigationBar(findViewById(R.id.bottom_navigaiton), this, R.id.navigation_home);
        ClientUtils.setClientUtils(getSharedPreferences("sharedPref", MODE_PRIVATE));

        transactions = new ArrayList<>();
        adapter = new TransactionAdapter(this, transactions);

        View root = findViewById(R.id.root);
        transactions = new ArrayList<>();
        prepareTransactionList(root);
        listView = root.findViewById(R.id.list);
        adapter = new TransactionAdapter(this, transactions);
        listView.setAdapter(adapter);

        FloatingActionButton button = findViewById(R.id.add_new_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_new);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        EditText name = dialog.findViewById(R.id.name);
        EditText amount = dialog.findViewById(R.id.amount);
        SwitchMaterial toggle = dialog.findViewById(R.id.toggle);
        Button add = dialog.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction transactionNew = new Transaction();
                transactionNew.setName(name.getText().toString());
                transactionNew.setCurrency("eur");
                if (toggle.isChecked())
                    transactionNew.setValue(Integer.parseInt(amount.getText().toString()) * -1);
                else{
                    transactionNew.setValue(Integer.parseInt(amount.getText().toString()));
                }
                transactionNew.setType("");
                transactionNew.setDate(getDateNow());


                Call<Transaction> call = ClientUtils.service.add(transactionNew);
                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        View root = findViewById(R.id.root);
                        transactions = new ArrayList<>();
                        prepareTransactionList(root);
                        dialog.cancel();
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        Log.d("Error123", t.getMessage());
                    }
                });
            }
        });


        dialog.show();
    }

    public String getDateNow(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyy-MM-dd");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    private void prepareTransactionList(View root){
        transactions.clear();   //in case it's not initialization but searching

        Call<HomeCard> call = ClientUtils.service.getAll();
        call.enqueue(new Callback<HomeCard>() {
            @Override
            public void onResponse(Call<HomeCard> call, Response<HomeCard> response) {
                HomeCard listings = response.body();
                Log.d("Error1234", response.body().toString());
                for(Transaction t : listings.getValue()) {
                    transactions.add(t);
                }
                listView = root.findViewById(R.id.list);
                adapter = new TransactionAdapter(getApplicationContext(), transactions);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<HomeCard> call, Throwable t) {
                Log.d("Error123", t.getMessage());
            }
        });
    }

}
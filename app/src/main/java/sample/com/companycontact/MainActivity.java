package sample.com.companycontact;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // json array response url
    private String urlJsonArry = "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json";
    // temporary string to show the parsed response
    private String jsonResponse;

    private static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private ArrayList<Company> data;
    private CompanyAdapter adapter;
    private ProgressBar progressIndicator;
    private TextView errorMessageTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorMessageTextView = (TextView) findViewById(R.id.tv_errorMessage);
        progressIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        new AsyncFetch().execute();
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            initViews();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressIndicator.setVisibility(View.INVISIBLE);
            if (isNetworkAvailable()) {
                showJsonDataView();
            }
//            } else if ((!isNetworkAvailable()) && (databaseHelper.getCompanyCount() == 0)) {
//
            else {
                Toast.makeText(getApplicationContext(), "No internet connection or cache available", Toast.LENGTH_LONG).show();
                showErrorMessage();
            }
        }


        private void initViews() {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            loadJSON();
        }

        private void loadJSON() {
            if (isNetworkAvailable() == true) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://sandbox.bottlerocketapps.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestInterface request = retrofit.create(RequestInterface.class);
                Call<JSONResponse> call = request.getJSON();
                call.enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, retrofit2.Response<JSONResponse> response) {

                        JSONResponse jsonResponse = response.body();
                        data = new ArrayList<>(Arrays.asList(jsonResponse.getStores()));
                        createAndSaveJSONFile(data);
                        //take the data from the JSON url and put it into the recyclerview
                        configureAdapter(data);
                    }

                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
        }

        private void showJsonDataView() {
            recyclerView.setVisibility(View.VISIBLE);
            errorMessageTextView.setVisibility(View.INVISIBLE);
        }

        private void showErrorMessage() {
            recyclerView.setVisibility(View.INVISIBLE);
            errorMessageTextView.setVisibility(View.VISIBLE);
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        private void configureAdapter(ArrayList<Company> data) {
            adapter = new CompanyAdapter(getApplicationContext(), data);
            recyclerView.setAdapter(adapter);
            adapter.setListener(new CompanyAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    int current = position;
                    Intent intent = new Intent(MainActivity.this, CompanyDetailsActivity.class);
                    intent.putExtra(CompanyDetailsActivity.TAG_pos, current);
                    startActivity(intent);
                }
            });
        }
    }

    public void createAndSaveJSONFile(ArrayList<Company> data) {
        String json = data.toString();

        try {
            FileWriter file = new FileWriter("/data/data/" + getApplicationContext().getPackageName() + "/" + "json");
            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "JSON file saved", Toast.LENGTH_LONG).show();
    }

}
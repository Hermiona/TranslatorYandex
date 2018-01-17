package com.example.retrofittranslator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofittranslator.http.Api;
import com.example.retrofittranslator.http.ApiClient;
import com.example.retrofittranslator.models.GetLangsResponse;
import com.example.retrofittranslator.models.LangItem;
import com.example.retrofittranslator.models.TranslateResponse;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txtOrigin) EditText txtOrigin;
    @BindView(R.id.txtResult)    TextView txtResult;
    @BindView(R.id.txtError)    TextView txtError;
    @BindView(R.id.listFromLangs)    Spinner listFromLangs;
    @BindView(R.id.listToLangs)    Spinner listToLangs;

    final String TAG="retrofittranslator";

    String[] items = new String[]{"1", "2", "three"};
    private SpinAdapter adapter;
    private ArrayList<LangItem> langsList;

    int positionFromLang, positionToLang;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG, "started");

        langsList=new ArrayList<>();

        getLangs();





    }

    @OnItemSelected({R.id.listFromLangs, R.id.listToLangs})
    public void langSelected(Spinner spinner, int position){
        switch(spinner.getId()){
            case R.id.listFromLangs:
                if(positionFromLang != position && position==positionToLang){
                    listToLangs.setSelection(positionFromLang);
                    positionToLang=positionFromLang;
                }
                positionFromLang=position;

                break;
            case R.id.listToLangs:
                if(positionToLang != position && position==positionFromLang){
                    listFromLangs.setSelection(positionToLang);
                    positionFromLang=positionToLang;
                }
                positionToLang=position;

                break;
        }
    }


    @OnClick(R.id.translate)
    public void translate(){
        txtError.setVisibility(View.INVISIBLE);

        Api apiService= ApiClient.getClient().create(Api.class);
        String langTranslation=langsList.get((int)listFromLangs.getSelectedItemId()).getAbbr() +
                "-"+ langsList.get((int)listToLangs.getSelectedItemId()).getAbbr();
        Call<TranslateResponse> call=apiService.translate(langTranslation, 1, txtOrigin.getText().toString());
        call.enqueue(new Callback<TranslateResponse>() {

            @Override
            public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                   String[] res=response.body().getText();
                    Map<String, String> detectedLangMap=response.body().getDetected();
                    compareDetectedLangToOrigin(detectedLangMap);
                    String result= TextUtils.join(", ", res);
                    txtResult.setText(result);
                    Log.d(TAG, result);

            }

            @Override
            public void onFailure(Call<TranslateResponse> call, Throwable t) {

            }
        });
    }

    public void getLangs(){
        Log.d(TAG, "clicked");
        Api apiService= ApiClient.getClient().create(Api.class);
        Call<GetLangsResponse> call=apiService.getLangs("ru");
        call.enqueue(new Callback<GetLangsResponse>() {

            @Override
            public void onResponse(Call<GetLangsResponse> call, Response<GetLangsResponse> response) {
                Map<String, String> res=response.body().getLangs();

                langsList=convertMapToArray(res);

                adapter=new SpinAdapter(MainActivity.this,R.layout.spinner_item, langsList);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                listFromLangs.setAdapter(adapter);
                listToLangs.setAdapter(adapter);

                positionFromLang=getPositionByLangAbbr("ru");
                positionToLang=getPositionByLangAbbr("en");

                listFromLangs.setSelection(positionFromLang);
                listToLangs.setSelection(positionToLang);
                Log.d(TAG, res.get("am"));

            }

            @Override
            public void onFailure(Call<GetLangsResponse> call, Throwable t) {

            }
        });
    }

    public ArrayList<LangItem> convertMapToArray(Map<String, String> map){
        LangItem item;
        ArrayList<LangItem> items=new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            item=new LangItem(entry.getKey(),entry.getValue() );
            items.add(item);
        }

        return items;
    }

    private void compareDetectedLangToOrigin(Map<String, String> detectedLangMap){
        // Get the first entry that the iterator returns
        Map.Entry<String, String> entry = detectedLangMap.entrySet().iterator().next();
        String detectedLangAbbr=entry.getValue();
        if(!langsList.get((int)listFromLangs.getSelectedItemId()).getAbbr().equals(detectedLangAbbr)){
            txtError.setText(getString(R.string.detectedLangError)+" "+ getDescriptionByAbbr(detectedLangAbbr));
            txtError.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Язык оригинала: " + detectedLangAbbr, Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, entry.getKey() + "+" + entry.getValue());
    }

    private int getPositionByLangAbbr(String abbr){
       return langsList.indexOf(new LangItem(abbr, "Русский"));
    }

    private String getDescriptionByAbbr(String abbr){
        int index= langsList.indexOf(new LangItem(abbr, ""));
        if(index>-1){
            return langsList.get(index).getDescription();
        }
        return abbr;
    }
}

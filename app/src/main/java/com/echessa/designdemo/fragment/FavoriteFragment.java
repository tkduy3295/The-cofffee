package com.echessa.designdemo.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.echessa.designdemo.Adapter.CustomFavoriteAdapter;
import com.echessa.designdemo.DBUtils.Favorite;
import com.echessa.designdemo.MainActivity;
import com.echessa.designdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    GridView gvFavorite;

    List<Favorite> favoriteList;

    CustomFavoriteAdapter customTab1Adapter;

    Button btnQuatityFavorite;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);

        //setup font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        gvFavorite = (GridView) view.findViewById(R.id.gvFavorite);

        toast("truoc");

        toast("sau");

        new readJsonFavorite().execute("https://cappuccino-hello.herokuapp.com/api/menu/favorite/");

//        btnQuatityFavorite = (Button) view.findViewById(R.id.btnQuatityFavorite);










        return view;
    }

    private class readJsonFavorite extends AsyncTask<String, Void, String>{

        StringBuilder content = new StringBuilder();
        @Override
        protected String doInBackground(String... strings) {

            try {


                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";

                while((line=bufferedReader.readLine()) != null){
                    content.append(line);
                }

                bufferedReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            toast(s);

            favoriteList = new ArrayList<Favorite>();

            try {
                JSONObject jsonObjectFavorite = new JSONObject(s);

                JSONArray response = jsonObjectFavorite.getJSONArray("response");

                for (int i = 0 ; i < response.length() ; i++){
                    JSONObject objectItem = response.getJSONObject(i);

                    String id = objectItem.getString("id");
                    String name = objectItem.getString("name");
                    int price = objectItem.getInt("price");
                    int createAt = objectItem.getInt("createAt");
                    String urlImage = objectItem.getString("urlImage");
                    int totalOrder = objectItem.getInt("totalOrder");

                    Favorite favorite = new Favorite(id,name,price,createAt,urlImage,totalOrder);

                    favoriteList.add(favorite);
                }


                customTab1Adapter = new CustomFavoriteAdapter(getActivity(), favoriteList);

                gvFavorite.setAdapter(customTab1Adapter);

                gvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(view.getContext(),favoriteList.get(position).getName(),Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public void toast(String msg){
        Toast.makeText(this.getActivity(),msg,Toast.LENGTH_LONG).show();
    }

}

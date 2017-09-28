package com.echessa.designdemo.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.echessa.designdemo.Adapter.CustomFavoriteAdapter;
import com.echessa.designdemo.DBUtils.Favorite;
import com.echessa.designdemo.DBUtils.Ordered;
import com.echessa.designdemo.MainActivity;
import com.echessa.designdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

    CustomFavoriteAdapter customFavoriteAdapter;

    private TextView tvQualityFavorite;

    private int quatityOrderCurrent=1;




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

        new readJsonFavorite(view).execute("https://cappuccino-hello.herokuapp.com/api/menu/favorite/");


        return view;
    }

    private class readJsonFavorite extends AsyncTask<String, Void, String>{

        private View mView;



        public readJsonFavorite(View view){
            this.mView = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
                    int quatity = 0;

                    Favorite favorite = new Favorite(id,name,price,createAt,urlImage,totalOrder,quatity);

                    favoriteList.add(favorite);
                }


                customFavoriteAdapter = new CustomFavoriteAdapter(getActivity(), favoriteList);

                gvFavorite.setAdapter(customFavoriteAdapter);


                final List<Integer> listPositionOrder = new ArrayList<Integer>();


                gvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = getActivity().getIntent();
                        final Bundle bundleTable = intent.getBundleExtra("bundleTable");
                        final List<Ordered> orderedListTable = (List<Ordered>) bundleTable.getSerializable("orderedListTable");
                        if(bundleTable!=null){

                            bundleTable.putString("statusTable","1");
                            /*String name = viewHolder.tvNameFavorite.getText().toString();
                            String price = viewHolder.tvPriceFavorite.getText().toString();
                            int quatity = Integer.parseInt(viewHolder.tvQualityFavorite.getText().toString());
                            orderedListTable.add(new Ordered(name,price, quatity));*/

                           /* Log.v("AAA",""+name+" - "+price+" - "+ quatity +" - "+orderedListTable.size());
*/

                        }else{
                            Log.v("AAA","null roi!!!");
                        }


                        /*quatityOrderCurrent = favoriteList.get(position).getQuatity()+1;
                        favoriteList.get(position).setQuatity(quatityOrderCurrent);
                        favoriteList.set(position,favoriteList.get(position));
                        customFavoriteAdapter.notifyDataSetChanged();

                        if(checkPosition(position, listPositionOrder) == true){

                            listPositionOrder.add(position);

                        }
                        Log.v("AAA",""+listPositionOrder.size());
*/

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public boolean checkPosition(int positionCurrent, List<Integer> listPositionOrder){
        for (int k=0;k<listPositionOrder.size();k++){
            if(positionCurrent==listPositionOrder.get(k)) {
                return false;
            }
        }
        return true;
    }

    public void toast(String msg){
        Toast.makeText(this.getActivity(),msg,Toast.LENGTH_LONG).show();
    }

}

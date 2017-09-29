package com.echessa.designdemo.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.echessa.designdemo.DBUtils.Categories;
import com.echessa.designdemo.Adapter.CustomCategoriesAdapter;
import com.echessa.designdemo.DBUtils.Position;
import com.echessa.designdemo.DBUtils.SizeTable;
import com.echessa.designdemo.DBUtils.Table;
import com.echessa.designdemo.MainActivity;
import com.echessa.designdemo.MenuTabsActivity;
import com.echessa.designdemo.PaymentActivity;
import com.echessa.designdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private ListView lvCategories;

    private List<Categories> categoriesList;

    private CustomCategoriesAdapter categoriesAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_categories, container, false);

        lvCategories = (ListView) view.findViewById(R.id.lvCategories);
        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {




                Bundle bundleIdCategories = new Bundle();
                bundleIdCategories.putString("idCategories","ahihi");


                Fragment menuOfCategoriesFragment = new MenuOfCategoriesFragment();

                menuOfCategoriesFragment.setArguments(bundleIdCategories);
                menuOfCategoriesFragment.setArguments(bundleIdCategories);




                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.frame_root, new MenuOfCategoriesFragment());
//                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                trans.addToBackStack(null);

                trans.commit();

            }
        });





        new ReadJsonCategories().execute("https://cappuccino-hello.herokuapp.com/api/menu/category/");




        return view;
    }

    private class ReadJsonCategories extends AsyncTask<String,Void,String> {

        StringBuilder content = new StringBuilder();

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);

                String line="";
                while((line = bufferedReader.readLine())!= null){
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

            try {

                JSONObject jsonObjectTable = new JSONObject(s);

                categoriesList = new ArrayList<Categories>();


                JSONArray response = jsonObjectTable.getJSONArray("response");


                for (int i = 0 ; i < response.length() ; i++){
                    JSONObject objectItem = response.getJSONObject(i);

                    String id = objectItem.getString("id");
                    String name = objectItem.getString("name");
                    int createAt = objectItem.getInt("createAt");
                    String urlImage = objectItem.getString("urlImage");


                    Categories categories = new Categories(id,name,createAt,urlImage,null);
                    categoriesList.add(categories);
                }

                categoriesAdapter = new CustomCategoriesAdapter(getActivity(),categoriesList);

                lvCategories.setAdapter(categoriesAdapter);





            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void toast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }



}

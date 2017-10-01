package com.echessa.designdemo;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.echessa.designdemo.DBUtils.Ordered;
import com.echessa.designdemo.DBUtils.Receipt;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReceiptActivity extends AppCompatActivity {

    private String urlReceiptById;

    private List<Receipt> listReceipt;

    private List<Ordered> listOrdered;

    LinearLayout llReceipt;

    TextView tvToTalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        urlReceiptById = "https://cappuccino-hello.herokuapp.com/api/receipt/59cfdc66eff96700044aec50";

        listReceipt = new ArrayList<Receipt>();

        listOrdered = new ArrayList<Ordered>();

        tvToTalMoney = (TextView) findViewById(R.id.tvToTalMoney);

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, urlReceiptById, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("response");
                    String id = jsonObject.getString("id");
                    String tableId = jsonObject.getString("tableId");
                    int totalPrice = jsonObject.getInt("totalPrice");
                    int createAt = jsonObject.getInt("createAt");
                    int payAt = jsonObject.getInt("payAt");



                    JSONArray items = jsonObject.getJSONArray("items");

                    for (int i=0;i<items.length();i++){
                        JSONObject item = items.getJSONObject(i);
                        String itemId = item.getString("itemId");
                        int srcImage = R.drawable.sinhtodau;
                        String name = item.getString("name");
                        int price = item.getInt("price");
                        int quantity = item.getInt("quantity");
                        listOrdered.add(new Ordered(itemId,srcImage,name,price,quantity));

                    }


                    listReceipt.add(new Receipt(id,tableId,totalPrice,createAt,payAt,listOrdered));


                    llReceipt = (LinearLayout) findViewById(R.id.llReceipt);


                    for(int i = 0 ; i < listOrdered.size() ; i++){

                        View v = getView(i, listOrdered);
                        llReceipt.addView(v);
                    }

                    tvToTalMoney.setText(listReceipt.get(0).getTotalPrice()+"");




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);


        /*getReceipt(urlReceiptById);*/



        /*llReceipt = (LinearLayout) findViewById(R.id.llReceipt);


        for(int i = 0 ; i < listReceipt.size() ; i++){

            View v = getView(i, listOrdered);
            llReceipt.addView(v);
        }*/




    }

    private class ViewHolder{
        ImageView imageReceipt;
        TextView tvNameReceipt;
        TextView tvPriceReceipt;
        Button btnQualityReceipt;
        TextView tvMoneyReceipt;
    }

    public View getView(int position, List<Ordered> listOrdered) {

        ViewHolder viewHolder;

        View view = null ;



        viewHolder = new ViewHolder();



        view = LayoutInflater.from(this).inflate(R.layout.layout_listview_receipt, null);

        viewHolder.imageReceipt = (ImageView) view.findViewById(R.id.imageReceipt);

        viewHolder.tvNameReceipt = (TextView) view.findViewById(R.id.tvNameReceipt);

        viewHolder.tvPriceReceipt = (TextView) view.findViewById(R.id.tvPriceReceipt);

        viewHolder.btnQualityReceipt = (Button) view.findViewById(R.id.btnQualityReceipt);

        viewHolder.tvMoneyReceipt = (TextView) view.findViewById(R.id.tvMoneyReceipt);

        view.setTag(viewHolder);


//        Payment payment = (Payment) paymentList.get(position);

        Typeface mFont = Typeface.createFromAsset(this.getAssets(),"Roboto-Bold.ttf");

        for(int i = 0;i<listOrdered.size();i++){

            Ordered ordered = listOrdered.get(i);

            viewHolder.imageReceipt.setImageResource(ordered.getSrcImage());


            viewHolder.tvNameReceipt.setText(ordered.getName());
            viewHolder.tvNameReceipt.setTypeface(mFont);

            String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(ordered.getPrice());

            viewHolder.tvPriceReceipt.setText(""+price+" đ");

            viewHolder.btnQualityReceipt.setText(""+ordered.getQuantity());

            int caculMoney = ordered.getPrice()*ordered.getQuantity();

            String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(caculMoney);

            viewHolder.tvMoneyReceipt.setText(""+money+" đ");
        }



        return view;
    }



    /*private void getReceipt(String url){


        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("response");
                    String id = jsonObject.getString("id");
                    String tableId = jsonObject.getString("tableId");
                    int totalPrice = jsonObject.getInt("totalPrice");
                    int createAt = jsonObject.getInt("createAt");
                    int payAt = jsonObject.getInt("payAt");



                    JSONArray items = jsonObject.getJSONArray("items");

                    for (int i=0;i<items.length();i++){
                        JSONObject item = items.getJSONObject(i);
                        String itemId = item.getString("itemId");
                        int srcImage = R.drawable.sinhtodau;
                        String name = item.getString("name");
                        int price = item.getInt("price");
                        int quantity = item.getInt("quantity");
                        listOrdered.add(new Ordered(itemId,srcImage,name,price,quantity));

                    }


                    listReceipt.add(new Receipt(id,tableId,totalPrice,createAt,payAt,listOrdered));






                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }*/

    // event icon back
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
//            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
            this.finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }
}

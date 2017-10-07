package com.echessa.designdemo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReceiptActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private String urlReceiptById;

    private List<Ordered> listOrdered;

    LinearLayout llReceipt;

    TextView tvToTalMoney;

    private int totalMoney =0;

    private Button btnChooseMenuReceipt;

    private Receipt receipt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // set Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        urlReceiptById = "https://cappuccino-hello.herokuapp.com/api/receipt/"+getIntent().getStringExtra("getReceiptId");

        listOrdered = new ArrayList<Ordered>();

        tvToTalMoney = (TextView) findViewById(R.id.tvToTalMoney);



        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, urlReceiptById, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resp = response.getJSONObject("response");
                    String id = resp.getString("id");
                    String tableId = resp.getString("tableId");
                    int totalPrice = resp.getInt("totalPrice");

                    JSONArray items= resp.getJSONArray("items");


                    for (int i=0;i<items.length();i++){
                        JSONObject item = items.getJSONObject(i);
                        String itemId = item.getString("itemId");
                        String srcImage = item.getString("urlImage");
                        String name = item.getString("name");
                        int price = item.getInt("price");
                        int quantity = item.getInt("quantity");
                        totalMoney+=price*quantity;
                        listOrdered.add(new Ordered(itemId,srcImage,name,price,quantity));
                    }


                    receipt = new Receipt(id,tableId,totalPrice,listOrdered);


                    llReceipt = (LinearLayout) findViewById(R.id.llReceipt);


                    for(int i = 0 ; i < listOrdered.size() ; i++){

                        View v = getView(i, listOrdered.get(i));
                        llReceipt.addView(v);
                    }

                    tvToTalMoney.setText(receipt.getTotalPrice()+"");
                    String total = NumberFormat.getNumberInstance(Locale.GERMAN).format(totalMoney);
                    tvToTalMoney.setText(total);


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

        btnChooseMenuReceipt = (Button) findViewById(R.id.btnChooseMenuReceipt);

        btnChooseMenuReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiptActivity.this, MenuTabsActivity.class);

                String checkFavoriteOrMenuOfCategory = "1";
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundleListOrdered", (Serializable) listOrdered);
                intent.putExtra("listOrdered", bundle);
                intent.putExtra("checkFavoriteOrMenuOfCategory",checkFavoriteOrMenuOfCategory);
                intent.putExtra("getReceiptIdByTable", receipt.getId());
                startActivity(intent);
            }
        });

    }

    private class ViewHolder{
        ImageView imageReceipt;
        TextView tvNameReceipt;
        TextView tvPriceReceipt;
        Button btnQualityReceipt;
        TextView tvMoneyReceipt;
    }

    public View getView(int position, Ordered ordered) {

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


        Typeface mFont = Typeface.createFromAsset(this.getAssets(),"Roboto-Bold.ttf");


        Picasso.with(getBaseContext()).load(ordered.getSrcImage()).into(viewHolder.imageReceipt);


        viewHolder.tvNameReceipt.setText(ordered.getName());
        viewHolder.tvNameReceipt.setTypeface(mFont);

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(ordered.getPrice());

        viewHolder.tvPriceReceipt.setText(""+price+" đ");

        viewHolder.btnQualityReceipt.setText(""+ordered.getQuantity());

        int caculMoney = ordered.getPrice()*ordered.getQuantity();

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(caculMoney);

        viewHolder.tvMoneyReceipt.setText(""+money+" đ");



        return view;
    }

    // event icon back
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ReceiptActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }
}

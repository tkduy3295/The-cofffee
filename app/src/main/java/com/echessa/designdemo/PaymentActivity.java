package com.echessa.designdemo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.echessa.designdemo.Adapter.PaymentAdapter;
import com.echessa.designdemo.DBUtils.Payment;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    //ListView lvPayment;

    List<Payment> paymentList;

    PaymentAdapter paymentAdapter;

    Button btnPayment,btnChooseMenuPayment;

    LinearLayout llPayment;

    TextView tvToTalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //lvPayment = (ListView) findViewById(R.id.lvPayment);

        paymentList = new ArrayList<Payment>();

        /*Payment payment1 = new Payment(1, R.drawable.cafedenda, "Cà Phê Đen Đá", 25000, 2, 50000);

        Payment payment2 = new Payment(2, R.drawable.cafesuada, "Cà Phê Sữa Đá", 25000, 2, 50000);

        Payment payment3 = new Payment(3, R.drawable.sinhtobo, "Sinh Tố Bơ", 25000, 2, 50000);

        Payment payment4 = new Payment(4, R.drawable.tradao, "Trà Đào", 25000, 2, 50000);

        Payment payment5 = new Payment(5, R.drawable.mixaotrung, "Mì Xào Trứng", 25000, 2, 50000);

        Payment payment6 = new Payment(6, R.drawable.tralipton, "Trà Líp Ton", 25000, 2, 50000);

        paymentList.add(payment1);

        paymentList.add(payment2);

        paymentList.add(payment3);


        paymentList.add(payment4);

        paymentList.add(payment5);

        paymentList.add(payment6);*/

        paymentAdapter = new PaymentAdapter(this, paymentList);

        //lvPayment.setAdapter(paymentAdapter);

        llPayment = (LinearLayout) findViewById(R.id.llPayment);

        for(int i = 0 ; i < paymentList.size() ; i++){

            View v = getView(i, paymentList);
            llPayment.addView(v);
        }

        btnPayment = (Button) findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(this);

        btnChooseMenuPayment = (Button) findViewById(R.id.btnChooseMenuPayment);
        btnChooseMenuPayment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int btnChoose = v.getId();
        if(btnChoose == R.id.btnPayment){
            Toast.makeText(getBaseContext(),"Thanh toan",Toast.LENGTH_LONG).show();
        }else if(btnChoose == R.id.btnChooseMenuPayment){
            Intent intent = new Intent(this, MenuTabsActivity.class);
            startActivity(intent);
        }
    }

    private class ViewHolder{
        ImageView imagePayment;
        TextView tvNamePayment;
        TextView tvPricePayment;
        Button btnQualityPayment;
        TextView tvMoneyPayment;
    }

    public View getView(int position, List<Payment> paymentList) {

        ViewHolder viewHolder;

        View view = null ;



            viewHolder = new ViewHolder();



            view = LayoutInflater.from(this).inflate(R.layout.layout_listview_payment, null);

            viewHolder.imagePayment = (ImageView) view.findViewById(R.id.imagePayment);

            viewHolder.tvNamePayment = (TextView) view.findViewById(R.id.tvNamePayment);

            viewHolder.tvPricePayment = (TextView) view.findViewById(R.id.tvPricePayment);

            viewHolder.btnQualityPayment = (Button) view.findViewById(R.id.btnQualityPayment);

            viewHolder.tvMoneyPayment = (TextView) view.findViewById(R.id.tvMoneyPayment);

            view.setTag(viewHolder);


        Payment payment = (Payment) paymentList.get(position);
        Typeface mFont = Typeface.createFromAsset(this.getAssets(),"Roboto-Bold.ttf");

        viewHolder.imagePayment.setImageResource(payment.getImage());

        viewHolder.tvNamePayment.setText(payment.getName());
        viewHolder.tvNamePayment.setTypeface(mFont);

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(payment.getPrice());

        viewHolder.tvPricePayment.setText(""+price+" đ");

        viewHolder.btnQualityPayment.setText(""+payment.getQuantity());

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(payment.getMoney());

        viewHolder.tvMoneyPayment.setText(""+money+" đ");

        return view;
    }

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



}

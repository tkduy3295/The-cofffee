package com.echessa.designdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.echessa.designdemo.DBUtils.Payment;
import com.echessa.designdemo.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by sung on 20/08/2017.
 */

public class PaymentAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Payment> paymentList;

    public PaymentAdapter(Context context, List<Payment> paymentList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.paymentList = paymentList;
    }

    @Override
    public int getCount() {
        return paymentList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imagePayment;
        TextView tvNamePayment;
        TextView tvPricePayment;
        Button btnQualityPayment;
        TextView tvMoneyPayment;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if(view == null){

            viewHolder = new ViewHolder();

            view = layoutInflater.inflate(R.layout.layout_listview_payment,null);

            viewHolder.imagePayment = (ImageView) view.findViewById(R.id.imagePayment);

            viewHolder.tvNamePayment = (TextView) view.findViewById(R.id.tvNamePayment);

            viewHolder.tvPricePayment = (TextView) view.findViewById(R.id.tvPricePayment);

            viewHolder.btnQualityPayment = (Button) view.findViewById(R.id.btnQualityPayment);

            viewHolder.tvMoneyPayment = (TextView) view.findViewById(R.id.tvMoneyPayment);

            view.setTag(viewHolder);

        }else{

            viewHolder =(ViewHolder) view.getTag();

        }

        Payment payment = (Payment) paymentList.get(position);

        viewHolder.imagePayment.setImageResource(payment.getImage());

        viewHolder.tvNamePayment.setText(payment.getName());

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(payment.getPrice());

        viewHolder.tvPricePayment.setText(""+price+" đ");

        viewHolder.btnQualityPayment.setText(""+payment.getQuantity());

        String money = NumberFormat.getNumberInstance(Locale.GERMAN).format(payment.getMoney());

        viewHolder.tvMoneyPayment.setText(""+money+" đ");

        return view;
    }
}

package sample.com.companycontact;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Marco on 5/25/2017.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {
    private ArrayList<Company> company;
    private Context context;

    private Listener listener;

    public static interface Listener {
        public void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public CompanyAdapter(Context context, ArrayList<Company> company) {
        this.context = context;
        this.company = company;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView view = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        CardView cardView = viewHolder.cardView;

        Picasso.with(context).load(company.get(position).getLogo()).resize(120, 60).into(viewHolder.logo);
        viewHolder.phone.setText(company.get(position).getPhone());
        viewHolder.address.setText(company.get(position).getAddress());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return company.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        private TextView phone, address;
        private ImageView logo;

        public ViewHolder(CardView view) {
            super(view);

            logo = (ImageView) view.findViewById(R.id.company_logo);
            phone = (TextView) view.findViewById(R.id.company_phone);
            address = (TextView) view.findViewById(R.id.company_address);

            cardView = view;
        }

    }

}
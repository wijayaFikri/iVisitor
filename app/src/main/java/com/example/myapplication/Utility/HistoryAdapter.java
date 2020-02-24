package com.example.myapplication.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.Visitor;
import com.example.myapplication.R;
import com.example.myapplication.config.Config;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<Visitor> {
    private int resourceLayout;
    private Context mContext;

    public HistoryAdapter(@NonNull Context context, int resource, List<Visitor> listItem) {
        super(context, resource, listItem);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        final Visitor visitor = getItem(position);

        TextView visitorNameTv = v.findViewById(R.id.history_visitor_name);
        TextView visitorReasonTv = v.findViewById(R.id.history_visitor_reason);
        TextView visitorDateTv = v.findViewById(R.id.history_visitor_date);
        TextView visitorIsAcceptedTv = v.findViewById(R.id.history_visitor_is_accepted);
        TextView visitorClockInAndOutTv = v.findViewById(R.id.history_visitor_clock_in_and_out);
        final ImageView visitorKtpIv = v.findViewById(R.id.history_visitor_ktp);

        visitorNameTv.setText(visitor.getVisitorName());
        visitorReasonTv.setText(visitor.getVisitingReason());
        visitorDateTv.setText(visitor.getVisitDate());
        if (visitor.getIsAccepted().equals("false")){
            visitorIsAcceptedTv.setText("Rejected");
        } else {
            visitorIsAcceptedTv.setText("Accepted");
        }

        if (visitor.getClockIn() != null && visitor.getClockOut() != null){
            String sessionDuration = visitor.getClockIn().substring(0, 5)
                    + " - "
                    + visitor.getClockOut().substring(0, 5);
            visitorClockInAndOutTv.setText(sessionDuration);
        }


        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                visitorKtpIv.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                // Get image from data Notification
                Picasso.get()
                        .load(Config.BASE_URL + "/media/" + visitor.getVisitorKtp())
                        .into(target);
            }
        });
        return v;

    }
}

package com.goodhopes.poovam.projectgoodhopes.aboutus;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.NetworkConnection;
import com.goodhopes.poovam.projectgoodhopes.interfaces.ResponseHandler;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by poovam on 3/12/16.
 * this fragment tells the detail about developer
 */

public class AboutUsFragment extends Fragment {
    int count = 0;
    int nameCount = 0;
    String responseURL = "https://docs.google.com/forms/d/e/1FAIpQLSc_QV0rnodrm22A4aV2KW-Jgc5xOxBpv7n6Rzmo2zt1fELXLw/formResponse";
    final String NAME_PARAM = "entry.1953821525";
    final String FEEDBACK_PARAM = "entry.839568076";

    @BindView(R.id.logo) ImageView logo;
    @BindView(R.id.sender_name) EditText name;
    @BindView(R.id.feedback) EditText feedback;
    @OnClick(R.id.rate_us_text)void onRateUsClick(){
     rateUs();
    }
    @OnClick(R.id.play_store_logo)void onRateUsImageClick(){
        rateUs();
    }
    @OnClick(R.id.sender_name)void onNameClick(){
        nameCount = nameCount + 1;
        if(nameCount == 10){
            Toast.makeText(getContext(),"Thanks to Chandrasekar T T",Toast.LENGTH_SHORT).show();
            nameCount = 0;
        }
    }
    @OnClick(R.id.submit)void onSubmitClick(){
        if(!feedback.getText().toString().equals("")) {
            HashMap<String, String> params = new HashMap<>();
            params.put(NAME_PARAM, "");
            params.put(FEEDBACK_PARAM, name.getText().toString() + ": " + feedback.getText().toString());
            new NetworkConnection(getActivity()).sendFeedBack(responseURL, params, new ResponseHandler() {
                @Override
                public void parse(String response) {
                    Toast.makeText(getContext(), "Feedback form submitted successfully", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    feedback.setText("");
                }

                @Override
                public void error(String error) {
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(),"Please enter your feedback",Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.logo) void onClick(){
        count = count + 1;
        if(count==8){
            Toast.makeText(getActivity(),"Hi "+("\uD83D\uDE09"),Toast.LENGTH_LONG).show();
            count = 0;
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.about_us_framgent,container,false);
        ButterKnife.bind(this,root);
        setCircularLogo();
        return root;
    }

    private void setCircularLogo(){
        Resources res = getActivity().getResources();
        Bitmap src = BitmapFactory.decodeResource(res, R.drawable.logo);
        RoundedBitmapDrawable dr =
                RoundedBitmapDrawableFactory.create(res, src);
        dr.setCircular(true);
        logo.setImageDrawable(dr);
    }
    private void rateUs(){
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
        }
    }
}

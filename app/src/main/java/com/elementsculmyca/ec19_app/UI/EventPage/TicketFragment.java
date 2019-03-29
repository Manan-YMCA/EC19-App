package com.elementsculmyca.ec19_app.UI.EventPage;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.Util.TicketsGenerator;

public class TicketFragment extends Fragment {
    String qrcode;
    ImageView qrCodeImage;
    TextView registerButton;
    SharedPreferences sharedPreferences;
    String phoneNumber;
    TextView phone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_after_registration, container, false);
        phone = view.findViewById(R.id.tv_phone);
        sharedPreferences = getActivity().getSharedPreferences("login_details",0);
        phoneNumber = sharedPreferences.getString("UserPhone","");
        phone.setText(phoneNumber);
        qrcode = getArguments().getString("qrcode");
        qrCodeImage = view.findViewById(R.id.qrcode);
        registerButton = getActivity().findViewById(R.id.register);
        registerButton.setText("Show Details");
        TicketsGenerator ticketsGenerator = new TicketsGenerator();
        Bitmap qrTicket = ticketsGenerator.GenerateClick(qrcode, getActivity(), (int) getResources().getDimension(R.dimen.threefifty), (int) getResources().getDimension(R.dimen.twoforty), 30, 30);
        qrCodeImage.setImageBitmap(qrTicket);
        return view;
    }
}

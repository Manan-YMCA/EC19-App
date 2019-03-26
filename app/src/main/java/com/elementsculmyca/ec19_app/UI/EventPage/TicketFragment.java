package com.elementsculmyca.ec19_app.UI.EventPage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elementsculmyca.ec19_app.R;
import com.elementsculmyca.ec19_app.Util.TicketsGenerator;

public class TicketFragment extends Fragment {
    String qrcode;
    ImageView qrCodeImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_after_registration, container, false);
        qrcode = getArguments().getString("qrcode");
        qrCodeImage = view.findViewById(R.id.qrcode);
        TicketsGenerator ticketsGenerator = new TicketsGenerator();
        Bitmap qrTicket = ticketsGenerator.GenerateClick(qrcode, getActivity(), (int) getResources().getDimension(R.dimen.threefifty), (int) getResources().getDimension(R.dimen.twoforty), 120, 120);
        qrCodeImage.setImageBitmap(qrTicket);
        return view;
    }
}

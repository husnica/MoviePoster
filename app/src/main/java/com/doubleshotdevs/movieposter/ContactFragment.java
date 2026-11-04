package com.doubleshotdevs.movieposter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    // Creates the Contact Us page and tells it which layout to show
    public ContactFragment() {
        super(R.layout.fragment_contact);
    }

    // Sets up the email row and the About Me button once the page is on screen
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.layoutEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmailApp(getString(R.string.contact_email));
            }
        });

        // Tapping About Me replaces the description with my personal introduction
        TextView textIntro = view.findViewById(R.id.textIntro);
        view.findViewById(R.id.buttonShowIntro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textIntro.setText(R.string.my_intro);
            }
        });
    }

    // Opens the phone's email app with the address filled in, or shows a message if there is none
    private void openEmailApp(String emailaddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailaddress));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), R.string.no_email_app, Toast.LENGTH_SHORT).show();
        }
    }
}
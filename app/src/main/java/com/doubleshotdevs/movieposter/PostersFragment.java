package com.doubleshotdevs.movieposter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostersFragment extends Fragment implements posterListener {

    private Button buttonAddToWatchlist;

    public PostersFragment() {
        super(R.layout.fragment_posters);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView posterRecyclerView = view.findViewById(R.id.postersRecyclerView);
        buttonAddToWatchlist = view.findViewById(R.id.buttonAddToWatchlist);

        List<Posters> posters = new ArrayList<>();

        Posters theAvengers = new Posters();
        theAvengers.image = R.drawable.poster1;
        theAvengers.name = "Avengers: Infinity War";
        theAvengers.rating = 5f;
        theAvengers.createdBy = "Anthony Russo, Joe Russo";
        theAvengers.story = "The Avengers unite to stop Thanos from collecting all six Infinity Stones and wiping out half of all life.";
        posters.add(theAvengers);

        Posters TokyoDrift = new Posters();
        TokyoDrift.image = R.drawable.poster2;
        TokyoDrift.name = "The Fast and the Furious: Tokyo Drift";
        TokyoDrift.rating = 5f;
        TokyoDrift.createdBy = "Justin Lin";
        TokyoDrift.story = "A troubled teen is sent to live with his father in Tokyo, where he gets pulled into the dangerous world of underground drift racing.";
        posters.add(TokyoDrift);

        Posters theBatman = new Posters();
        theBatman.image = R.drawable.poster3;
        theBatman.name = "The Batman";
        theBatman.rating = 5f;
        theBatman.createdBy = "Matt Reeves";
        theBatman.story = "In his second year fighting crime, Batman hunts the Riddler, a serial killer targeting Gotham's elite, and uncovers the city's hidden corruption.";
        posters.add(theBatman);

        Posters Goal = new Posters();
        Goal.image = R.drawable.poster4;
        Goal.name = "Goal! The Dream Begins";
        Goal.rating = 5f;
        Goal.createdBy = "Danny Cannon";
        Goal.story = "A young Mexican immigrant in Los Angeles gets the chance to try out for Newcastle United and chase his dream of playing professional football.";
        posters.add(Goal);

        Posters peakyBlinders = new Posters();
        peakyBlinders.image = R.drawable.poster5;
        peakyBlinders.name = "Peaky Blinders: The Immortal Man";
        peakyBlinders.rating = 5f;
        peakyBlinders.createdBy = "Tom Harper";
        peakyBlinders.story = "During World War II, Tommy Shelby is drawn back to Birmingham to face a new threat to his family and the city.";
        posters.add(peakyBlinders);

        //connect the list to adapter
        final PostersAdapter postersAdapter = new PostersAdapter(posters, (posterListener) this);
        posterRecyclerView.setAdapter(postersAdapter);

        //show selected names
        buttonAddToWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Posters> selectPosters = postersAdapter.getSelectedPosters();

                StringBuilder names = new StringBuilder();
                for (int i = 0; i < selectPosters.size(); i++) {
                    if (i > 0) {
                        names.append("\n");
                    }
                    names.append(selectPosters.get(i).name);
                }
                Toast.makeText(requireContext(), names.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void OnPosterAction(Boolean isSelected) {
        if(isSelected){
            buttonAddToWatchlist.setVisibility(View.VISIBLE);
        }else{
            buttonAddToWatchlist.setVisibility(View.GONE);

        }

    }
}

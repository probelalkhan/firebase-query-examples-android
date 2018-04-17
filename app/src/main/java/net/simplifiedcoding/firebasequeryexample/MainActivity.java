package net.simplifiedcoding.firebasequeryexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArtistsAdapter adapter;
    private List<Artist> artistList;

    DatabaseReference dbArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistList = new ArrayList<>();
        adapter = new ArtistsAdapter(this, artistList);
        recyclerView.setAdapter(adapter);

        //1. SELECT * FROM Artists
        dbArtists = FirebaseDatabase.getInstance().getReference("Artists");

        //2. SELECT * FROM Artists WHERE id = "-LAJ7xKNj4UdBjaYr8Ju"
        Query query = FirebaseDatabase.getInstance().getReference("Artists")
                .orderByChild("id")
                .equalTo("-LAJ7xKNj4UdBjaYr8Ju");

        //3. SELECT * FROM Artists WHERE country = "India"
        Query query3 = FirebaseDatabase.getInstance().getReference("Artists")
                .orderByChild("country")
                .equalTo("India");

        //4. SELECT * FROM Artists LIMIT 2
        Query query4 = FirebaseDatabase.getInstance().getReference("Artists").limitToFirst(2);


        //5. SELECT * FROM Artists WHERE age < 30
        Query query5 = FirebaseDatabase.getInstance().getReference("Artists")
                .orderByChild("age")
                .endAt(29);


        //6. SELECT * FROM Artists WHERE name = "A%"
        Query query6 = FirebaseDatabase.getInstance().getReference("Artists")
                .orderByChild("name")
                .startAt("A")
                .endAt("A\uf8ff");

        ;
        /*
        * You just need to attach the value event listener to read the values
        * for example
        * query6.addListenerForSingleValueEvent(valueEventListener)
        * */
    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            artistList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Artist artist = snapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}

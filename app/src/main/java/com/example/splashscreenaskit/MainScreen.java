package com.example.splashscreenaskit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity
{
    private ImageButton logo;
    private Spinner spinner;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private ArrayList<Question> questionsList;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Setting the logo to open the asking questions screen
        logo = (ImageButton) findViewById(R.id.searchButton);
        logo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openAskScreen();
            }
        });

        //Activating toolbar ( incomplete)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creating a spinner that would allow the users to sort by most voted or frequently asked
        spinner = (Spinner) findViewById(R.id.sort_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sorts, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //specify the interface implementation
        // spinner.setOnItemSelectedListener(this);

        //Setting up firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Question");
        reference.addListenerForSingleValueEvent(valueEventListener);

        //Working with recycle view
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        questionsList = new ArrayList<>();


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

    }

    //TODO: Display questions in listview( or recycle view)
    ValueEventListener valueEventListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
            {
                Question student = postSnapshot.getValue(Question.class);
                questionsList.add(student);
            }
            mAdapter = new MyAdapter(MainScreen.this, questionsList);
            recyclerView.setAdapter(mAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {

        }

    };
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG);

            //Checks what the user has chosen for sorting the questions
            if (text.equals("FAQ")) //If the user chooses to sort by frequently asked questions
            {
                sortByFAQ();
            } else if (text.equals("Most Voted")) //If the user chooses to sort by most vote questions
            {
                sortByMostVoted();
            }
        }

        private void sortByMostVoted()
        {
            // System.out.println( "most voted");
        }

        private void sortByFAQ()
        {
            //System.out.println( "FAQ");
        }

        public void openAskScreen()
        {
            Intent intent;
            intent = new Intent(this, AskingQuestionsScreen.class);
            startActivity(intent);
        }

}








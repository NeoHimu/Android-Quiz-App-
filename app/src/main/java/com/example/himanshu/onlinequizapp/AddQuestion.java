package com.example.himanshu.onlinequizapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.himanshu.onlinequizapp.Model.HandshakedUsers;
import com.example.himanshu.onlinequizapp.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by himanshu on 3/3/18.
 */

public class AddQuestion extends Activity {
    Button btAddNewQues, btBackButton;
    EditText etQ,etC1,etC2,etC3,etC4,etCC;
    FirebaseDatabase database;
    DatabaseReference questions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question_layout);
        btBackButton = (Button)findViewById(R.id.addNewQuesBack);
        btAddNewQues = (Button)findViewById(R.id.addNewQues);
        etQ = (EditText)findViewById(R.id.etNewQuestion);
        etC1 = (EditText)findViewById(R.id.etChoice1);
        etC2 = (EditText)findViewById(R.id.etChoice2);
        etC3 = (EditText)findViewById(R.id.etChoice3);
        etC4 = (EditText)findViewById(R.id.etChoice4);
        etCC = (EditText)findViewById(R.id.etCorrectChoice);

        //Firebase
        database = FirebaseDatabase.getInstance();
        questions = database.getReference();

        btAddNewQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Question question = new Question(etQ.getText().toString(),
                        etC1.getText().toString(), etC2.getText().toString(),
                        etC3.getText().toString(), etC4.getText().toString(),
                        etCC.getText().toString());

                questions.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        questions.child("Questions").push().setValue(question);
                        Toast.makeText(AddQuestion.this, "Question Added Successful1y!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        btBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}


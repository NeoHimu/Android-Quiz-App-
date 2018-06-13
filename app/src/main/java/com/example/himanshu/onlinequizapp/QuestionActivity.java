package com.example.himanshu.onlinequizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.himanshu.onlinequizapp.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by himanshu on 2/3/18.
 */

public class QuestionActivity extends Activity {

    Map<String, Object> questions;
    Button prev,next;
    int ques_no=0;
    TextView q;
    RadioButton ch1,ch2,ch3,ch4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout);

        prev = (Button)findViewById(R.id.prev);
        next = (Button)findViewById(R.id.next);
        q = (TextView)findViewById(R.id.tvQuestion);
        ch1 = (RadioButton)findViewById(R.id.rbChoice1);
        ch2 = (RadioButton)findViewById(R.id.rbChoice2);
        ch3 = (RadioButton)findViewById(R.id.rbChoice3);
        ch4 = (RadioButton)findViewById(R.id.rbChoice4);

        //Get datasnapshot at your "handshakedUsers" root node
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Questions");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        questions =(Map<String,Object>) dataSnapshot.getValue();
                        int i=0;
                        //iterate through each question, ignoring their UID
                        //Set the first question on the UI
                        for (Map.Entry<String, Object> entry : questions.entrySet()) {
                            if(i==0){
                                //Get user map
                                Map ques = (Map) entry.getValue();
                                //Get ques and choices in ques
                                q.setText(ques.get("question").toString());
                                ch1.setText(ques.get("choice1").toString());
                                ch2.setText(ques.get("choice2").toString());
                                ch3.setText(ques.get("choice3").toString());
                                ch4.setText(ques.get("choice4").toString());

                            }
                            i+=1;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ques_no>0){
                    int i=0;
                    ques_no -= 1;
                    //iterate through each question, ignoring their UID
                    for (Map.Entry<String, Object> entry : questions.entrySet()) {
                        if(i==ques_no){
                            //Get user map
                            Map ques = (Map) entry.getValue();
                            //Get ques and choices in ques
                            q.setText(ques.get("question").toString());
                            ch1.setText(ques.get("choice1").toString());
                            ch2.setText(ques.get("choice2").toString());
                            ch3.setText(ques.get("choice3").toString());
                            ch4.setText(ques.get("choice4").toString());

                        }
                        i+=1;
                    }
                }
                else if(ques_no==0){
                    finish();
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ques_no < questions.size()-1){

                    int i=0;
                    ques_no += 1;
                    //iterate through each question, ignoring their UID
                    for (Map.Entry<String, Object> entry : questions.entrySet()) {
                        if(i==ques_no){
                            //Get user map
                            Map ques = (Map) entry.getValue();
                            //Get ques and choices in ques
                            q.setText(ques.get("question").toString());
                            ch1.setText(ques.get("choice1").toString());
                            ch2.setText(ques.get("choice2").toString());
                            ch3.setText(ques.get("choice3").toString());
                            ch4.setText(ques.get("choice4").toString());

                        }
                        i+=1;
                    }
                }
                else {
                    Intent i =new Intent(QuestionActivity.this, Handshake.class);
                    startActivity(i);
                }

            }
        });
    }
}

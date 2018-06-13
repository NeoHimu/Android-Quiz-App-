package com.example.himanshu.onlinequizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.himanshu.onlinequizapp.Model.HandshakedUsers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by himanshu on 2/3/18.
 */

public class FacultyActivity extends Activity{
    FirebaseDatabase database;
    DatabaseReference handshakedUsers;
    Button btBackToLogin, btGoToQuiz, btGetAttendance, btAddEdge,btAddQues;
    TextView test;
    EditText stud1,stud2;
    int numberOfStudents=20;

    //Make the graph using adjacency matrix
    int[][] graph = new int[numberOfStudents][numberOfStudents];
    boolean []visited = new boolean[numberOfStudents];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_layout);
        btBackToLogin = (Button)findViewById(R.id.btBackToLogin);
        btGoToQuiz = (Button)findViewById(R.id.btGoToQuiz);
        btGetAttendance = (Button)findViewById(R.id.btGetAttendance);
        btAddEdge = (Button)findViewById(R.id.addEdgeButton);
        btAddQues = (Button)findViewById(R.id.addQues);
        stud1 = (EditText)findViewById(R.id.student1);
        stud2 = (EditText)findViewById(R.id.student2);
        test = (TextView)findViewById(R.id.testTextView);
        // Firebase
        database = FirebaseDatabase.getInstance();
        handshakedUsers = database.getReference();


        btAddQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FacultyActivity.this, AddQuestion.class);
                startActivity(i);
            }
        });
        btBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btGoToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FacultyActivity.this, QuestionActivity.class);
                startActivity(i);
                finish();
            }
        });

        btAddEdge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HandshakedUsers hskdUsers = new HandshakedUsers(stud1.getText().toString(),
                        stud2.getText().toString());

                handshakedUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            handshakedUsers.child("handshakedUsers").push().setValue(hskdUsers);
                            Toast.makeText(FacultyActivity.this, "Edge Addition Successful1!", Toast.LENGTH_SHORT).show();
                        }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        });

        btGetAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get datasnapshot at your "handshakedUsers" root node
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("handshakedUsers");
                ref.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Get map of users in datasnapshot
                                getConnectedUsers((Map<String,Object>) dataSnapshot.getValue());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });
            }
        });
    }

    private void getConnectedUsers(Map<String, Object> edges) {
        String[] stud1_String = new String[numberOfStudents];
        String[] stud2_String = new String[numberOfStudents];
        int[] stud1_int = new int[numberOfStudents];
        int[] stud2_int = new int[numberOfStudents];
        //each index contains unique student name
        String[] verticesString = new String[numberOfStudents];

        Map<String, Integer> map = new HashMap<String, Integer>();

        int i=0;
        int j=0;
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : edges.entrySet()){
            //Get user map
            Map edge = (Map) entry.getValue();
            //Get sender and receiver in edge
            if(!map.containsKey(edge.get("sender").toString())){
                map.put(edge.get("sender").toString(),i);
                verticesString[i] = edge.get("sender").toString();
                i += 1;
            }
            if(!map.containsKey(edge.get("receiver").toString())){
                map.put(edge.get("receiver").toString(),i);
                verticesString[i] = edge.get("receiver").toString();
                i += 1;
            }
            stud1_String[j] = edge.get("sender").toString();
            stud2_String[j] = edge.get("receiver").toString();
            j+=1;
        }


        //Initialize the graph with 0
        for(int m=0;m<numberOfStudents;m++){
            for(int n=0;n<numberOfStudents;n++)
                graph[m][n]=0;
        }
        //add edges in the graph
        for(int l=0;l<j;l++){
            stud1_int[l] = map.get(stud1_String[l]);
            stud2_int[l] = map.get(stud2_String[l]);
            graph[stud1_int[l]][stud2_int[l]] = 1;
            graph[stud2_int[l]][stud1_int[l]] = 1;
        }

        String temp= "";
        for(int m=0;m<numberOfStudents;m++){
            for(int n=0;n<numberOfStudents;n++)
                temp += ""+graph[m][n];
        }

        //Apply DFS
        Set<String> total_result_of_dfs = new HashSet<String>();
        for(int v=0;v<numberOfStudents;v++){
            DFS(v);
            String visited_nodes = "";
            for(int vv=0;vv<numberOfStudents;vv++) {
                if (visited[vv] == true)
                {
                    visited_nodes += verticesString[vv] + " ";
                    visited[vv] = false;
                }
            }

            if(!visited_nodes.equals(null) && !total_result_of_dfs.contains(visited_nodes))
                total_result_of_dfs.add(visited_nodes);
        }

        test.setText(total_result_of_dfs.toString());

    }
    void DFS(int u){
            visited[u]=true;
            for(int v=0;v<numberOfStudents;v++){
                if(!visited[v] && graph[u][v]==1)
                    DFS(v);
            }
    }
}
package com.app.gaurav.photoupload;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.net.URI;

public class AddMemberActivity extends AppCompatActivity {

    private Button addMember;
    private TextView memberNameText;
    private Button galleryButton;
    private Button cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);


        final DataBaseHandler db = new DataBaseHandler(this);

        addMember = (Button) findViewById(R.id.addMember);
        memberNameText = (EditText) findViewById(R.id.memberNameText);
        galleryButton = (Button) findViewById(R.id.galleryButton);
        cameraButton = (Button) findViewById(R.id.cameraButton);

        cameraButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                Intent intent1 = getIntent();
                final String groupName = intent1.getStringExtra("groupName");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                final String fileName = "fname_" +  String.valueOf(System.currentTimeMillis()) + ".jpg";
                Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 0);

                addMember.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        db.addRecords(new SqlEntry(memberNameText.getText().toString(), fileName, groupName));
                    }
                });

            }
        });

        galleryButton.setOnClickListener((new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = getIntent();
                final String groupName = intent1.getStringExtra("groupName");
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                final String fileName = "fname_" +  String.valueOf(System.currentTimeMillis()) + ".jpg";
                File file = new File(Environment.getExternalStorageDirectory(), fileName);
                Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 0);

                addMember.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        db.addRecords(new SqlEntry(memberNameText.getText().toString(), fileName, groupName));
                    }
                });

            }
        }));


    }

}

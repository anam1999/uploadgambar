package com.example.myapplication98;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.AndroidRuntimeException;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class uploadvideo extends AppCompatActivity {
    ImageView imageView;
    ProgressBar progressBar;
    Button btnDownload;
    TextView errorMsg;

    // initializing video variable
    private static final int SELECT_VIDEO = 3;

    // Declaring connection variables
    Connection con;
    String un,password,db,ip;

    // Byte Stream Buffer for converting Video
    ByteArrayOutputStream byteBuffer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadvideo);
        // Initializing the layouts on oncreate
        imageView = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorMsg = (TextView) findViewById(R.id.errorMsg);
        btnDownload = (Button) findViewById(R.id.button);

        //Stopping progressbar first
        progressBar.setVisibility(View.GONE);

        // Initializing Connection Variables
        ip = ""; //Change this ip with your Ip, don't forget to put port at the end
        db = ""; //Change this Database name with yours
        un = ""; // Change this username with your database username
        password = ""; // Change this password with your database password

    }

    // function to download image from the server
    public void uploadVideo(View view)
    {
        // opening the gallery to select video from there to be uploaded to the server
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&&
                !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING))
        {
            //Setting type to video
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
        }
        else
        {
            // if the gallery is not found or some error occurs
            errorMsg.setText("No Gallery Found!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // If the video that has been selected
        System.gc();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK && null != data)
        {
            // If we got success, there is no need put this check because the check has already been putted there
            if (requestCode == SELECT_VIDEO)
            {
                // Getting the path of the video to be converted in to base64
                Uri selectedVideoUri = data.getData();
                String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
                Cursor cursor = managedQuery(selectedVideoUri, projection, null, null, null);
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                errorMsg.setText("File name : " + filePath);
                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);
                // Setting the thumbnail of the video in to the image view
                imageView.setImageBitmap(thumb);
                InputStream inputStream = null;
                // Converting the video in to the bytes
                try
                {
                    inputStream = getContentResolver().openInputStream(selectedVideoUri);
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                byteBuffer = new ByteArrayOutputStream();
                int len = 0;
                try
                {
                    while ((len = inputStream.read(buffer)) != -1)
                    {
                        byteBuffer.write(buffer, 0, len);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                System.out.println("converted!");

                // Confirming if you want to upload the video to the server or not
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Options");
                alertDialog.setMessage("Are You Sure To Upload Video?");
                alertDialog.setButton("Upload", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Add your code for the button here.
                        // Setting an Async Task for uploading video in the background thread so that main thread does not through exception
                        UploadVideo doin = new UploadVideo();
                        doin.execute();
                    }
                });
                alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Auto-generated method stub
                        errorMsg.setText("Cancelled uploading video");
                    }
                });
                // Set the Icon for the Dialog
                alertDialog.setIcon(R.drawable.ic_launcher_background);
                alertDialog.show();
            }
        }
    }

    // Async task ; a background method
    private class UploadVideo extends AsyncTask<String, Void, String>
    {
        String videoData="";
        String msg =  "";

        @Override
        protected void onPreExecute()
        {
            errorMsg.setText("Uploading Please Wait...");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params)
        {
            videoData = Base64.encodeToString(byteBuffer.toByteArray(), Base64.DEFAULT);
            //Connecting to the database server using connection helper library that we included
            con = ConnectionHelper(un, password, db, ip);
            if(con == null)
            {
                msg = "Check your Internet! Try Again!";
            }
            else
            {
                // query to insert the video in to the database you need to modify
                // this query according to your own need
                String command = "INSERT INTO tableVideo(videoData) VALUES('"+videoData+"')";
                try
                {
                    // running the query so that the video that has been selected would be
                    // uploaded to the server
                    PreparedStatement preStmt = con.prepareStatement(command);
                    preStmt.executeUpdate();
                    msg = "Inserted Successfully";
                }
                // If there is any errors then catch them and show them in to the message text view
                catch (SQLException ex)
                {
                    msg = "Error Occurred! Try Again";
                    Log.d("Error no 1", ex.getMessage().toString());
                }
                catch (IOError ex)
                {
                    msg = "Error Occurred! Try Again";
                    Log.d("Error no 2", ex.getMessage().toString());
                }
                catch (AndroidRuntimeException ex)
                {
                    msg = "Error Occurred! Try Again";
                    Log.d("Error no 3", ex.getMessage().toString());
                }
                catch (NullPointerException ex)
                {
                    msg = "Error Occurred! Try Again";
                    Log.d("Error no 4", ex.getMessage().toString());
                }
                catch (Exception ex)
                {
                    msg = "Error Uploading the Video to the Server";
                    Log.d("Error no 5", ex.getMessage().toString());
                }
            }
            // Returning the message so that we will be able to know
            // the results we got, wither successfully uploaded the video or not
            return msg;
        }

        @Override
        protected void onPostExecute(String resultSet)
        {
            //Stopping the progress bar and showing the message
            progressBar.setVisibility(View.GONE);
            errorMsg.setText(msg);
        }
    }

    // This function is used to connect to the database. It uses the library that we included
    @SuppressLint("NewApi")
    public Connection ConnectionHelper(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server
                    + database + ";user=" + user
                    + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("ERROR 1", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR 2", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR 3", e.getMessage());
        }
        return connection;
    }

    // Function to check if connected to any network
    public Boolean isInternetAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

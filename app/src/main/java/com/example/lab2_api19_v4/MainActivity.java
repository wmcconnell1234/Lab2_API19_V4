//Camera app code provided by Tejinder
//Modified by WM. Search for WM for changes

package com.example.lab2_api19_v4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList; //WM
import java.util.List; //WM

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;

    //Values associated with saving the caption. WM
    String currentFileName = null; //similar to mCurrentPhotoPath, but the filename only. WM
    List captionList = new ArrayList(); //contains all the captions. WM
    List filenameList = new ArrayList(); //contains all the filenames. WM
    // These two lists should always be the same size. WM
    int currentElement = 0; //The element number of the current image. WM

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void takePicture(View v) {   //changed from private to public WM ----------------
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.lab2_api19_v4.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public File createImageFile() throws IOException { //changed to public WM -------------------------------
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg",storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        currentFileName = image.getName(); //Added WM to get the filename
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView mImageView = (ImageView) findViewById(R.id.ivGallery);
            mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
            //Add the image to the list immediately with the default caption "Enter Caption". WM
            filenameList.add(currentFileName);
            captionList.add("Enter Caption");
            //Change the current element to the new image. WM
            currentElement = filenameList.size() - 1;
            //Display the caption for the current image. WM
            TextView textView = (TextView) findViewById(R.id.editTextCaption);
            textView.setText((CharSequence) captionList.get(currentElement));
        }
    }

    //Saves the caption. WM
    //Currently the captions are not saved when the program closes.
    public void saveCaption(View view){

        //Other commands that may be useful
        //captionListSize = captionList.size();
        //testString2 = captionList.get(0).toString();
        //ImageView currentImage = (ImageView) findViewById(R.id.ivGallery);

        int j; //for testing only.

        //Algorithm 3 for saving captions:
        //1. See if there is an image in the imageView.
        //   I will do this indirectly by seeing if currentFileName is not null.
        //2. If not, do nothing.
        //3. If yes, change the caption for the image.

        //Algorithm 3
        if(currentFileName != null)
        {
            //int elementNumber = filenameList.indexOf(currentFileName);
            TextView textView = (TextView) findViewById(R.id.editTextCaption);
            String caption = textView.getText().toString();
            captionList.set(currentElement, caption);
        }

        j = 1;
    }//end method

    //Move to the newer image. WM
    public void Left(View view)
    {
        //Other commands that might be useful
        //textView.onCommitCompletion();

        //Algorithm:
        //1. See if the number of images is greater than 1.
        //2. If not, do nothing.
        //3. If yes, continue with the next steps.
        //4. See if the current image is an older image.
        //   I will do this by checking the currentElement.
        //5. If not, do nothing.
        //6. If yes, continue with the next steps.
        //7. Save the caption.
        //8. Set the current image to the newer image.

        int j; //for testing only.

        //See if the number of images is greater than 1.
        int filenameListSize = filenameList.size();
        if(filenameListSize > 1)
        {
            //See if the current image is an older image.
            //The current image is an older image if currentElement is not the last element number.
            if(currentElement != (filenameListSize - 1))
            {
                //Algorithm 3 for saving captions:
                //1. See if there is an image in the imageView.
                //   I will do this indirectly by seeing if currentFileName is not null.
                //2. If not, do nothing.
                //3. If yes, change the caption for the image.

                j = 1;

                //Get a handle to the newer image
                String newerImageName = filenameList.get(currentElement + 1).toString();
                String temporaryCopy = newerImageName;
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                newerImageName = storageDir + "/" + newerImageName;
                j = 5;
                //Set the current image to the newer image.
                mCurrentPhotoPath = newerImageName;
                currentFileName = temporaryCopy;
                currentElement++;
                //Draw the current image.
                ImageView mImageView = (ImageView) findViewById(R.id.ivGallery);
                mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                //Display the caption for the current image.
                TextView textView = (TextView) findViewById(R.id.editTextCaption);
                textView.setText((CharSequence) captionList.get(currentElement));
            }
        }
        j = 1;
    }//end method

    //Display the older image. WM
    public void Right(View view)
    {
        //Algorithm:
        //1. See if the number of images is greater than 1.
        //2. If not, do nothing.
        //3. If yes, continue with the next steps.
        //4. See if the current image is a newer image.
        //   I will do this by checking the currentElement.
        //5. If not, do nothing.
        //6. If yes, continue with the next steps.
        //7. Set the current image to the more recently taken image.

        int j = 1; //for testing only.

        //See if the number of images is greater than 1.
        int filenameListSize = filenameList.size();
        if(filenameListSize > 1)
        {
            //See if the current image is a newer image.
            //The current image is a newer image if currentElement is not the first element number.
            if(currentElement != 0)
            {
                //Get a handle to the older image
                String olderImageName = filenameList.get(currentElement - 1).toString();
                String temporaryCopy = olderImageName;
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                olderImageName = storageDir + "/" + olderImageName;
                j = 5;
                //Set the current image to the older image.
                mCurrentPhotoPath = olderImageName;
                currentFileName = temporaryCopy;
                currentElement--;
                //Draw the current image.
                ImageView mImageView = (ImageView) findViewById(R.id.ivGallery);
                mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                //Display the caption for the current image.
                TextView textView = (TextView) findViewById(R.id.editTextCaption);
                textView.setText((CharSequence) captionList.get(currentElement));
            }
        }
        j = 2;
    }//end method
}


package com.example.group_0571.gamecentre.slidingTiles.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.group_0571.gamecentre.slidingTiles.SlidingTilesImage;
import com.example.group_0571.gamecentre.utils.PreferenceHandler;
import com.example.group_0571.gamecentre.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Settings for the Sliding Tiles game.
 */
public class SlidingTilesSettingsActivity extends AppCompatActivity {
    /**
     * The user's username.
     */
    private String username;
    /**
     * An image from the user's device.
     */
    private Uri imageUri;

    /**
     * Object to handle interactions with SharedPreferences
     */
    private PreferenceHandler preferenceHandler;

    /**
     * Class variables for layout objects that are repeatedly accessed.
     */
    private Button getImageButton;
    private RadioButton numericLimitRadioButton;
    private EditText numericLimitEditText;
    private EditText urlField;

    private List<RadioButton> radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tiles_settings);
        setTitle(R.string.settings);
        username = getIntent().getStringExtra("user");
        preferenceHandler = new PreferenceHandler(
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()), getBaseContext());
        // setup views from saved values
        radioButtons = new ArrayList<>();
        initializeViewVariables();
        checkImageTypeFromSavedPreferences();
        setRadioGroupStatus(false, urlField, getImageButton);
        setViewsFromSavedPreferences();
        // setup listeners
        addImageRadioGroupListener();
        addUndoLimitRadioGroupListener();
    }

    /**
     * Initialize view class variables.
     */
    private void initializeViewVariables() {
        radioButtons.add((RadioButton) findViewById(R.id.sliding_tiles_default_image));
        radioButtons.add((RadioButton) findViewById(R.id.sliding_tiles_image_1));
        radioButtons.add((RadioButton) findViewById(R.id.sliding_tiles_image_2));
        radioButtons.add((RadioButton) findViewById(R.id.sliding_tiles_image_3));
        radioButtons.add((RadioButton) findViewById(R.id.sliding_tiles_image_file));
        radioButtons.add((RadioButton) findViewById(R.id.sliding_tiles_url_image));
        urlField = findViewById(R.id.sliding_tiles_image_url);
        getImageButton = findViewById(R.id.sliding_tiles_get_image);
    }

    /**
     * Setup undo limit layout views from saved values.
     */
    private void setViewsFromSavedPreferences() {
        RadioButton unlimitedLimitRadioButton = findViewById(R.id.undo_limit_set_unlimited);
        numericLimitRadioButton = findViewById(R.id.undo_limit_set_numeric);
        numericLimitEditText = findViewById(R.id.undo_limit_edit);
        int undoLimit = preferenceHandler.getUndoLimit();
        if (undoLimit == -1) { // unlimited undo limit
            numericLimitRadioButton.setChecked(false);
            unlimitedLimitRadioButton.setChecked(true);
            setRadioGroupStatus(false, numericLimitEditText, null);
            undoLimit = getResources().getInteger(R.integer.undo_limit); // default undo limit
        } else {
            setRadioGroupStatus(true, numericLimitEditText, null);
        }
        numericLimitEditText.setText(String.valueOf(undoLimit));
    }

    /**
     * Add a click listener for the Tile Background Radio Button Group.
     */
    private void addUndoLimitRadioGroupListener() {
        RadioGroup radioGroup = findViewById(R.id.undo_limit_radios);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == numericLimitRadioButton.getId()) {
                    setRadioGroupStatus(true, numericLimitEditText, null);
                } else {
                    setRadioGroupStatus(false, numericLimitEditText, null);
                }
            }
        });
    }

    /**
     * Check the image type from saved preferences.
     */
    private void checkImageTypeFromSavedPreferences() {
        int type = preferenceHandler.getImageType();
        RadioButton radioButton = type < radioButtons.size() ? radioButtons.get(type) : radioButtons.get(0);
        radioButton.setChecked(true);
        if (type == SlidingTilesImage.IMAGE_FROM_URL) {
            urlField.setText(preferenceHandler.getImageURL());
        }
    }

    /**
     * Save the image type to saved preferences.
     */
    private void saveImageTypeToSavedPreferences() {
        int imageType = 4; // placeholder value that isn't valid
        for (int i = 0; i < radioButtons.size(); i++) {
            if (radioButtons.get(i).isChecked()) {
                imageType = i;
                if (imageType == SlidingTilesImage.IMAGE_FROM_GALLERY && !getImageFromGallery()) {
                    imageType = SlidingTilesImage.DEFAULT_TILES;
                } else if (imageType == SlidingTilesImage.IMAGE_FROM_URL && !getImageFromUrl()) {
                    imageType = SlidingTilesImage.DEFAULT_TILES;
                }
            }
        }
        preferenceHandler.setImageType(imageType);
    }

    /**
     * Get the image from gallery.
     *
     * @return false if could not obtain image from gallery
     */
    private boolean getImageFromGallery() {
        if (imageUri != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                preferenceHandler.setEncodedImage(SlidingTilesImage.getInstance().encodeImage(image));
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Get the image from url
     *
     * @return false if could not obtain url.
     */
    private boolean getImageFromUrl() {
        try {
            Bitmap image = new ImageDownloadTask().execute(new URL(urlField.getText().toString())).get();
            if (image != null) {
                preferenceHandler.setEncodedImage(SlidingTilesImage.getInstance().encodeImage(image),
                        urlField.getText().toString());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * OnClick method for get image button
     *
     * @param v View clicked
     */
    public void getImageButtonOnClick(View v) {
        EditText urlField = findViewById(R.id.sliding_tiles_image_url);
        try {
            URL url = new URL(urlField.getText().toString());
            new ImageDownloadTask().execute(url);
            Toast.makeText(SlidingTilesSettingsActivity.this,
                    R.string.image_loaded, Toast.LENGTH_SHORT).show();
        } catch (MalformedURLException e) {
            Toast.makeText(SlidingTilesSettingsActivity.this,
                    R.string.invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Add a click listener for the Tile Background Image Radio Button Group.
     */
    private void addImageRadioGroupListener() {
        final RadioButton imageRadioButton = findViewById(R.id.sliding_tiles_url_image);
        final RadioButton fromFileRadioButton = findViewById(R.id.sliding_tiles_image_file);
        RadioGroup radioGroup = findViewById(R.id.sliding_tiles_image_radios);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == imageRadioButton.getId()) {
                    setRadioGroupStatus(true, urlField, getImageButton);
                } else {
                    setRadioGroupStatus(false, urlField, getImageButton);
                }
                if (checkedId == fromFileRadioButton.getId()) {
                    Intent getFile = new Intent(Intent.ACTION_PICK);
                    getFile.setType("image/*");
                    startActivityForResult(getFile, 1);
                }
            }
        });
    }

    /**
     * Set whether the EditText and Button group are enabled or not.
     *
     * @param status   boolean value of status to set
     * @param editText EditText object to enable/disable
     * @param button   Button object to enable/disable (can be null)
     */
    private void setRadioGroupStatus(boolean status, EditText editText, Button button) {
        editText.setEnabled(status);
        editText.setTextIsSelectable(status);
        if (button != null) { // button can be null
            button.setClickable(status);
            button.setEnabled(status);
        }
    }

    /**
     * OnClick method for save button
     *
     * @param v View clicked
     */
    public void saveButtonOnClick(View v) {
        int newUndoLimit = -1;
        if (numericLimitRadioButton.isChecked()) {
            newUndoLimit = Integer.parseInt(numericLimitEditText.getText().toString());
        }
        preferenceHandler.setUndoLimit(newUndoLimit);

        Toast.makeText(SlidingTilesSettingsActivity.this, R.string.settings_saved,
                Toast.LENGTH_SHORT).show();
        returnToSlidingTilesActivity(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageUri = data.getData();
        } else {
            radioButtons.get(SlidingTilesImage.DEFAULT_TILES).setChecked(true);
            Toast.makeText(this, R.string.no_image_chosen, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        returnToSlidingTilesActivity(false);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            returnToSlidingTilesActivity(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Send the settings back to the Sliding Tiles activity.
     *
     * @param saved whether the save button was clicked
     */
    private void returnToSlidingTilesActivity(boolean saved) {
        Intent intent = new Intent();
        intent.putExtra("user", username);
        if (saved) {
            saveImageTypeToSavedPreferences();
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Download images in the background.
     */
    private class ImageDownloadTask extends AsyncTask<URL, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(URL... urls) {
            try {
                return SlidingTilesImage.getInstance().downloadImage(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (bitmap != null) {
                ImageView imageView = findViewById(R.id.sliding_tiles_image);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}

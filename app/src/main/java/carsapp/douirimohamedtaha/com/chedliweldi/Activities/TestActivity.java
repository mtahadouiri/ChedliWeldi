package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

/**
 * Created by oussama_2 on 11/27/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.InfoFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.MyOffersFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.OffersFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.PhotosFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.SettingsFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.TabsFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import me.gujun.android.taggroup.TagGroup;

public class TestActivity extends AppCompatActivity implements ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader  {

    private static final String TAG = "MainActivity";
    ImageGalleryFragment fragment;
   LinearLayoutCompat linear ;

    private PaletteColorType paletteColorType;
    TagGroup tags;
    RelativeLayout infoTab ;
Button btn;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        btn=(Button) findViewById(R.id.testBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TestActivity.this,ProfilActivity.class);
                //   JSONObject jsonObject  =new JSONObject();
                // i.putExtra("json",jsonObject);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(TestActivity.this, "M_CH_ID");

                notificationBuilder.setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(1)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("Hearty365")
                        //     .setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                        .setContentTitle("Default notification")
                        .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                        .setContentInfo("Info");

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notificationBuilder.build());

                Log.i("MyFirebaseMsgService","sone");

            }
        });
        /*
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, new MyOffersFragment(), "")
                .commit();

        /*

        btn=(Button) findViewById(R.id.testBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TestActivity.this,ProfilActivity.class);
             //   JSONObject jsonObject  =new JSONObject();
               // i.putExtra("json",jsonObject);
                startActivity(i);

            }
        });


/*
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, new TabsFragment(), "")
                .commit();
/*
linear = (LinearLayoutCompat) findViewById(R.id.fragmentLayout);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, new TabsFragment(), "")
                .commit();


        /*
        setContentView(R.layout.about);
        tags = (TagGroup) findViewById(R.id.tags);
        tags.setTags(new String[]{"good listener","friendly","good looking","amazing dancer","teach music"});

/*
        String[] images = null;

        ArrayList<String> i = new ArrayList();
        i.add("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
        i.add("https://cdn.pixabay.com/photo/2016/11/03/04/02/ancient-1793421_960_720.jpg");


        ImageGalleryActivity.setImageThumbnailLoader(this);
        ImageGalleryFragment.setImageThumbnailLoader(this);

        FullScreenImageGalleryActivity.setFullScreenImageLoader(this);

        // optionally set background color using Palette for full screen images
        paletteColorType = PaletteColorType.VIBRANT;

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, i);



       fragment = ImageGalleryFragment.newInstance(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.testLayout, fragment, "")
                .commit();

        getSupportFragmentManager().executePendingTransactions();


*/



    }

    @Override
    public void loadFullScreenImage(final ImageView iv, String imageUrl, int width, final LinearLayout bglinearLayout) {
        Picasso.with(iv.getContext())
                .load(imageUrl)
                .resize(width, 0)
                .into(iv);
      //  Glide.with(iv.getContext()).load(imageUrl).into(iv);

    }


    public void showDialog(View vIew){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = this.getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setView(view)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null);



        AlertDialog dialog = builder.create();
        dialog.show();
    }
    LinearLayout tmp;
    @Override
    public void loadImageThumbnail(ImageView iv, String imageUrl, int dimension) {
     if(tmp==null){
         tmp = (LinearLayout) fragment.getView();

         tmp.setBackgroundColor(Color.RED);
     }

        Picasso.with(iv.getContext())
                .load(imageUrl)
                .resize(dimension, dimension)
                .centerCrop()
                .into(iv); Glide.with(iv.getContext()).load(imageUrl).into(iv); Glide.with(iv.getContext()).load(imageUrl).into(iv);


    }


    // region Helper Methods
    private void applyPalette(Palette palette, ViewGroup viewGroup){
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            viewGroup.setBackgroundColor(bgColor);
    }

    private void applyPalette(Palette palette, View view){
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            view.setBackgroundColor(bgColor);
    }

    private int getBackgroundColor(Palette palette) {
        int bgColor = -1;

        int vibrantColor = palette.getVibrantColor(0x000000);
        int lightVibrantColor = palette.getLightVibrantColor(0x000000);
        int darkVibrantColor = palette.getDarkVibrantColor(0x000000);

        int mutedColor = palette.getMutedColor(0x000000);
        int lightMutedColor = palette.getLightMutedColor(0x000000);
        int darkMutedColor = palette.getDarkMutedColor(0x000000);

        if (paletteColorType != null) {
            switch (paletteColorType) {
                case VIBRANT:
                    if (vibrantColor != 0) { // primary option
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) { // fallback options
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case LIGHT_VIBRANT:
                    if (lightVibrantColor != 0) { // primary option
                        bgColor = lightVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case DARK_VIBRANT:
                    if (darkVibrantColor != 0) { // primary option
                        bgColor = darkVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case MUTED:
                    if (mutedColor != 0) { // primary option
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) { // fallback options
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case LIGHT_MUTED:
                    if (lightMutedColor != 0) { // primary option
                        bgColor = lightMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case DARK_MUTED:
                    if (darkMutedColor != 0) { // primary option
                        bgColor = darkMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                default:
                    break;
            }
        }

        return bgColor;
    }




}
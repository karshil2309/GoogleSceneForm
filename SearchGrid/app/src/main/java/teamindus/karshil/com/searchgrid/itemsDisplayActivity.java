package teamindus.karshil.com.searchgrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class itemsDisplayActivity extends AppCompatActivity {


    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_display);



    ItemsModel itemsModel;



        imageView = findViewById(R.id.imageViewItem);
        textView  = findViewById(R.id.tvNameItem);


        Intent intent = getIntent();

        if(intent.getExtras() != null){

            itemsModel = (ItemsModel) intent.getSerializableExtra("item");
            imageView.setImageResource(itemsModel.getImage());
            textView.setText(itemsModel.getName());

        }

    }
}


package bhumi.arrapp.com;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;



public class MainGridActivity extends AppCompatActivity {

    int images[] = {R.drawable.armchair,R.drawable.bed,R.drawable.clock,R.drawable.lamp_thumb,R.drawable.sofa1,R.drawable.armchair,R.drawable.armchair,R.drawable.armchair};
    String names[] = {"Apple","Banana","Oranges","Kiwi","Watermelon","Apple","Apple","Watermelon"};
    String desc[] = {"This is apple","This is banana","This is an orange","This is kiwi","This is watermelon","This is watermelon","This is watermelon","This is watermelon"};

    List<ItemsModel> itemsList = new ArrayList<>();

    GridView gridView;
    FloatingActionButton flb;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maingrid);

        gridView = findViewById(R.id.gridView);

        flb=findViewById(R.id.floating_action_button);
        for(int i=0; i<names.length; i++){

            ItemsModel itemsModel = new ItemsModel(names[i],desc[i],images[i]);
            itemsList.add(itemsModel);

        }


        customAdapter = new CustomAdapter(itemsList,this);

        gridView.setAdapter(customAdapter);
        flb= (FloatingActionButton) findViewById(R.id.floating_action_button);
        flb.setOnClickListener(view -> {
            Intent intent=new Intent(MainGridActivity.this,MainArFragmentActivity.class);
            startActivity(intent);

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);


        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);

                return true;
            }
        });



        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.search_view){
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends BaseAdapter implements Filterable, ListAdapter {


        private List<ItemsModel> itemsModelList;
        private List<ItemsModel> itemsModelListFiltered;
        private Context context;
        private int position;
        private View convertView;
        private ViewGroup parent;

        public CustomAdapter(List<ItemsModel> itemsModelList, Context context) {
            this.itemsModelList = itemsModelList;
            this.itemsModelListFiltered = itemsModelList;
            this.context = context;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            this.position = position;
            this.convertView = convertView;
            this.parent = parent;

            View view = getLayoutInflater().inflate(R.layout.rows_item,null);

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView tvNames = view.findViewById(R.id.tvName);
            TextView tvDesc = view.findViewById(R.id.tvDesc);

            imageView.setImageResource(itemsModelListFiltered.get(position).getImage());
            tvNames.setText(itemsModelListFiltered.get(position).getName());
            tvDesc.setText(itemsModelListFiltered.get(position).getDesc());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainGridActivity.this,itemsDisplayActivity.class).putExtra("item",itemsModelListFiltered.get(position)));

                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();

                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = itemsModelList.size();
                        filterResults.values = itemsModelList;

                    }else{

                        String searchStr = constraint.toString().toLowerCase();
                        List<ItemsModel> resultData = new ArrayList<>();

                        for(ItemsModel itemsModel:itemsModelList){

                            if(itemsModel.getName().contains(searchStr) || itemsModel.getDesc().contains(searchStr)){
                                resultData.add(itemsModel);
                            }

                            filterResults.count = resultData.size();
                            filterResults.values = resultData;

                        }


                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    itemsModelListFiltered = (List<ItemsModel>) results.values;
                    notifyDataSetChanged();

                }
            };

            return filter;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int i) {
            return false;
        }
    }


}

package com.example.zomebie.miniproject01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;

public class FourthFragment extends Fragment {

    private static MainActivity mainActivity2;

    int[] images = new int[]{R.drawable.maps, R.drawable.shapeofyou, R.drawable.ladyantebellum, R.drawable.showman,
            R.drawable.showman, R.drawable.showman, R.drawable.showman};
    String[] names = new String[]{"Maroon5", "Ed Sheeran", "Lady Antevellum", "Keala Settle",
            "Roren Alled", "Hue Jackman", "Zack Efron, Zandaya"};
    String[] descriptions = new String[]{"Maps", "Shape of you", "Need You Now", "This Is Me",
            "Never Enough", "From Now On", "Rewrite The Stars"};


    public static FourthFragment newInstance() {

        Bundle args = new Bundle();

        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fourth_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView lv =  view.findViewById(R.id.listView1);
        SearchView sv =  view.findViewById(R.id.searchView1);

        final ListAdapter adapter2 = new ListAdapter(getContext(), getItems());
        lv.setAdapter(adapter2);

        // 아이템 클릭 시 실행
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {


                switch (pos) {

                    case 0:
                        Intent intent=new Intent(getContext(),FiveFragment.class);
                        startActivity(intent);
                        break;

                    case 1:
                            Intent intent1=new Intent(getContext(),ShapeOfYou.class);
                            startActivity(intent1);

                        break;

                    case 2:
                        break;

                    default:
                        break;

                } // switch

            }
        });

        // 서치뷰
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter2.getFilter().filter(newText);
                return false;
            }
        });
    }



    // 아이템 가져오기
    private ArrayList<Item> getItems()
    {
        ArrayList<Item> items  = new ArrayList<>();
        Item t;

        for(int i=0; i < names.length; i++)
        {
            t = new Item(images[i],names[i],descriptions[i]);
            items.add(t);
        }
        return items;
    }

}

package com.cfish.rvb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cfish.rvb.adapter.ItemClickListener;
import com.cfish.rvb.adapter.SectionedExpandableLayoutHelper;
import com.cfish.rvb.bean.Item;
import com.cfish.rvb.bean.Section;

import java.util.ArrayList;

/**
 * Created by dfish on 2016/11/19.
 */
public class GroupListActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        //setting the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this,
                mRecyclerView, this, 3);

        //random data
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(new Item("iPhone", 0));
        arrayList.add(new Item("iPad", 1));
        arrayList.add(new Item("iPod", 2));
        arrayList.add(new Item("iMac", 3));
        sectionedExpandableLayoutHelper.addSection("Apple Products", arrayList);
        arrayList = new ArrayList<>();
        arrayList.add(new Item("LG", 0));
        arrayList.add(new Item("Apple", 1));
        arrayList.add(new Item("Samsung", 2));
        arrayList.add(new Item("Motorola", 3));
        arrayList.add(new Item("Sony", 4));
        arrayList.add(new Item("Nokia", 5));
        sectionedExpandableLayoutHelper.addSection("Companies", arrayList);
        arrayList = new ArrayList<>();
        arrayList.add(new Item("Chocolate", 0));
        arrayList.add(new Item("Strawberry", 1));
        arrayList.add(new Item("Vanilla", 2));
        arrayList.add(new Item("Butterscotch", 3));
        arrayList.add(new Item("Grape", 4));
        sectionedExpandableLayoutHelper.addSection("Ice cream", arrayList);

        sectionedExpandableLayoutHelper.notifyDataSetChanged();

        //checking if adding single item works
        sectionedExpandableLayoutHelper.addItem("Ice cream", new Item("Tutti frutti",5));
        sectionedExpandableLayoutHelper.notifyDataSetChanged();
    }



    @Override
    public void itemClicked(Item item) {
        Toast.makeText(this, "Item: " + item.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClicked(Section section) {
        Toast.makeText(this, "Section: " + section.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }
}


package com.doublej.mathquizmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    private static final String SUMMARY = "com.doublej.mathquizmobile.summary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        final String[] summaryArray = getIntent().getStringArrayExtra(SUMMARY);

        final RecyclerView summaryRecyclerView = findViewById(R.id.summaryRecyclerView);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter adapter = new SummaryAdapter(summaryArray,getApplicationContext());
        summaryRecyclerView.setAdapter(adapter);
    }

    public static Intent newIntent(Context packageContext, String[] summaryArray) {
        Intent i = new Intent(packageContext, SummaryActivity.class);
        i.putExtra(SUMMARY, summaryArray);
        return i;
    }

    private class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

        String[] summary;

        public SummaryAdapter(String[] summary, Context context) {
            this.summary = summary;
        }

        @Override
        public SummaryAdapter.SummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_row, parent, false);
            return new SummaryViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SummaryAdapter.SummaryViewHolder holder, int position) {
            if(!summary[position].contains("A"))
                holder.image.setImageResource(R.drawable.tick);
            else
                holder.image.setImageResource(R.drawable.wrongsymbol);

            holder.text.setText(summary[position]);
        }

        @Override
        public int getItemCount() {
            return summary.length;
        }

        class SummaryViewHolder extends RecyclerView.ViewHolder {

            protected ImageView image;
            protected TextView text;

            public SummaryViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image_id);
                text = itemView.findViewById(R.id.text_id);
            }
        }

    }


}

package in.junctiontech.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // startActivity(new Intent(this, STEP2.class));
        finish();
        return true;
    }

    public void onClick(View v)
    {
       if( v.getId()==R.id.projects_tasks_receipts)
       {
           startActivity(new Intent(DetailActivity.this, ReceiptActivity.class).putExtras(this.getIntent()));
       }
       else if( v.getId()==R.id.projects_tasks_expenses)
       {
           startActivity(new Intent(DetailActivity.this, ExpenseActivity.class).putExtras(this.getIntent()));
       }
       else if( v.getId()==R.id.projects_tasks_images)
       {
           startActivity(new Intent(DetailActivity.this, ImageSelectionActivity.class).putExtras(this.getIntent()));
       }
    }
}

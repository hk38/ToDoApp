package android.lifeistech.com.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import io.realm.Realm;

public class selectActivity extends AppCompatActivity {
    public Realm realm;
    public FloatingActionButton fab;
    public TextView selectTT;
    public TextView selectCT;
    public CheckBox selectCB;
    public Memo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        fab = findViewById(R.id.fab);
        selectTT = findViewById(R.id.selectTT);
        selectCT = findViewById(R.id.selectCT);
        selectCB = findViewById(R.id.selectCB);

        realm = Realm.getDefaultInstance();

        memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();

        selectTT.setText(memo.title);
        selectCT.setText(memo.content);
        selectCB.setChecked(memo.checked);

        selectCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        memo.checked = selectCB.isChecked();
                    }
                });

            }
        });
    }

    public void edit(View v){
        Intent intent = new Intent(selectActivity.this, DetailActivity.class);
        intent.putExtra("updateDate", memo.updateDate);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        selectTT.setText(memo.title);
        selectCT.setText(memo.content);
        selectCB.setChecked(memo.checked);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

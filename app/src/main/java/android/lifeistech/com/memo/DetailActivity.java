package android.lifeistech.com.memo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    public Realm realm;

    public EditText titleText;
    public EditText contentText;
    public CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();

        titleText = (EditText) findViewById(R.id.titleEditText);
        contentText = (EditText) findViewById(R.id.contentEditText);
        checkBox = (CheckBox) findViewById(R.id.detailCheckBox);

        showData();
    }

    public void showData(){
        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();

        titleText.setText(memo.title);
        contentText.setText(memo.content);
        checkBox.setChecked(memo.checked);
    }

    public void update(View v){
        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();

        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                memo.title = titleText.getText().toString();
                memo.content = contentText.getText().toString();
                memo.checked = checkBox.isChecked();
            }
        });

        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public void onPause(){
        super.onPause();

        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();

        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                memo.checked = checkBox.isChecked();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
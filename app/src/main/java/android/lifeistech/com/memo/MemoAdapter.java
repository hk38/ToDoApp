package android.lifeistech.com.memo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

public class MemoAdapter extends ArrayAdapter<Memo> {

    private LayoutInflater layoutinflater;
    public Realm realm;

    MemoAdapter(Context context, int textViewResourceId, List<Memo> objects) {
        super(context, textViewResourceId, objects);
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        CheckBox checkBox;

        public ViewHolder(View view){
            checkBox = (CheckBox) view.findViewById(R.id.itemCheckBox);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Memo memo = getItem(position);

        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.layout_item_memo, null);
            viewHolder = new ViewHolder(convertView);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            Log.d("tag",convertView.getTag().toString());
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                realm = Realm.getDefaultInstance();

                final Memo toDoCheck = realm.where(Memo.class).equalTo("updateDate", memo.updateDate).findFirst();

                realm.executeTransaction(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        toDoCheck.checked = isChecked;
                    }
                });
                realm.close();
            }
        });



        TextView titleText = (TextView) convertView.findViewById(R.id.titleText);
        TextView contentText = (TextView) convertView.findViewById(R.id.contentText);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

        titleText.setText(memo.title);
        contentText.setText(memo.content);
        checkBox.setChecked(memo.checked);

        return convertView;
    }
}

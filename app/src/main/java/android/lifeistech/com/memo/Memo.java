package android.lifeistech.com.memo;

import io.realm.Realm;
import io.realm.RealmObject;

public class Memo extends RealmObject {
    public String title;
    public String updateDate;
    public String content;

}

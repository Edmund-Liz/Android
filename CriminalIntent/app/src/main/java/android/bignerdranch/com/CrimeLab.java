package android.bignerdranch.com;

import static android.bignerdranch.com.database.CrimeDbSchema.CrimeTable;

import android.bignerdranch.com.database.CrimeBaseHelper;
import android.bignerdranch.com.database.CrimeCursorWrapper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private final Context mContext;
    private final SQLiteDatabase mDatabase;

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();

    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {//若是第一次调用此函数（即sCrime为null）则：
            sCrimeLab = new CrimeLab(context);//使sCrimeLab为一个mCrimes==下方数组的CrimeLab对象
        }                                     //即不必通过任何CrimeLab实例就可以得到一个sCrimeLab数组
        return sCrimeLab;
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT,crime.getSuspect());

        return values;
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void deleteCrime(Crime c){
        ContentValues values=getContentValues(c);
        mDatabase.delete(CrimeTable.NAME,CrimeTable.Cols.UUID
        +" = ?",new String[]{c.getId().toString()});
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while ((!cursor.isAfterLast())) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return crimes;
    }

    //查找单个crime
    public Crime getCrime(UUID id) {
        //遍历crime数组
        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID
                        + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount()==0){
                return null;
            }
        cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }
}

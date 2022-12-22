package com.example.crimeactivity;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    public static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;//存储Crime对象的列表

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //返回数组列表
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    //根据UUID读取单个Crime对象
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;//若传入的UUID无匹配对象，返回null
    }
}

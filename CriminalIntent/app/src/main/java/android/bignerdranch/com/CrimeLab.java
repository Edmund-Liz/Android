package android.bignerdranch.com;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {//若是第一次调用此函数（即sCrime为null）则：
            sCrimeLab = new CrimeLab();//使sCrimeLab为一个mCrimes==下方数组的CrimeLab对象
        }                                     //即不必通过任何CrimeLab实例就可以得到一个sCrimeLab数组
        return sCrimeLab;
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    private CrimeLab() {
        mCrimes=new ArrayList<>();
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    //查找单个crime
    public Crime getCrime(UUID id) {
        //遍历crime数组
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        //否则返回null
        return null;
    }
}

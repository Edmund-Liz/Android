package android.bignerdranch.com;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
                                    //第一个参数为生成的视图（此处为一个RecyclerView），第二个参数为该视图的父布局，第三个参数为是否将该视图添加到父布局
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);//此处为fragment_crime_list的id
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());//使crimeLab中的mCrimes为含有一百个crime对象的List
        List<Crime> crimes = crimeLab.getCrimes();//使crimes为含有一百个crime对象的List

        mAdapter = new CrimeAdapter(crimes);//令mAdapter管理这100个Crime对象
        mCrimeRecyclerView.setAdapter(mAdapter);//使mAdapter成为mCrimeRecyclerView的Adapter
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateView;

        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));//给当前holder设置视图
            itemView.setOnClickListener(this);//给当前holder设置监听器

            mTitleTextView=(TextView) itemView.findViewById(R.id.crime_title);
            mDateView=(TextView) itemView.findViewById(R.id.crime_date);
        }

        public int getItemViewType(int position){
            if (mCrime.isRequiresPolice()){
                return 1;
            }
            else return 0;
        }

        @Override
        public void onClick(View view){
            Toast.makeText(getActivity(),
                    mCrime.getTitle()+"clicked!",Toast.LENGTH_SHORT)
                    .show();
        }

        public void bind(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateView.setText(mCrime.getDate().toString());
        }


    }

    private class PoliceHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateView;
        private Button mPoliceButton;

        private Crime mCrime;

        public PoliceHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime2, parent, false));//给当前holder设置视图
            itemView.setOnClickListener(this);//给当前holder设置监听器

            mTitleTextView=(TextView) itemView.findViewById(R.id.crime_title);
            mDateView=(TextView) itemView.findViewById(R.id.crime_date);
            mPoliceButton=(Button) itemView.findViewById(R.id.police_button);
        }

        public int getItemViewType(int position){
            if (mCrime.isRequiresPolice()){
                return 1;
            }
            else return 0;
        }

        @Override
        public void onClick(View view){
            Toast.makeText(getActivity(),
                            mCrime.getTitle()+"clicked!",Toast.LENGTH_SHORT)
                    .show();
        }

        public void bind(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateView.setText(mCrime.getDate().toString());
        }


    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public int getItemViewType(int position){
                if (mCrimes.get(position).isRequiresPolice()){
                    return 1;
                }
                else return 0;
            }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            RecyclerView.ViewHolder aHolder=null;
            if (viewType==0)
                aHolder=new CrimeHolder(layoutInflater,parent);
            else
                aHolder=new PoliceHolder(layoutInflater,parent);

            return aHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Crime crime=mCrimes.get(position);
            if (holder.getItemViewType()==0) {
                CrimeHolder crimeHolder = (CrimeHolder) holder;
                crimeHolder.bind(crime);//使此holder的Title与Date词条成为当前position的Crime对象的对应词条
            }
            else {
                PoliceHolder policeHolder=(PoliceHolder) holder;
                policeHolder.bind(crime);
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}

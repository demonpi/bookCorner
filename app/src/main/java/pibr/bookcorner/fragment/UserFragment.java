package pibr.bookcorner.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pibr.bookcorner.R;

/**
 * 首页“我的”列表页
 * 作者：pishao
 * 时间：2016.07.02
 */
public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        TextView title_tv = (TextView) view.findViewById(R.id.title);

        return view;
    }

}

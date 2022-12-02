package com.example.adminpanel.admin_bottom_navigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adminpanel.R;
import com.example.adminpanel.adapters.SessionManager;
import com.example.adminpanel.admin_login;
import com.example.adminpanel.users_manage.admin_admin_update;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProfileFragment extends Fragment {
    TextView name,email,mobile,gender,nic,address,edit;
    CircleImageView img;
    Button logout;

    AdminAdminFragment adminAdminFragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProfileFragment newInstance(String param1, String param2) {
        AdminProfileFragment fragment = new AdminProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_profile, container, false);

        name = view.findViewById(R.id.admn_pro_textName);
        email = view.findViewById(R.id.admn_pro_textEmail);
        mobile = view.findViewById(R.id.admn_pro_textMobile);
        gender = view.findViewById(R.id.admn_pro_textGender);
        nic = view.findViewById(R.id.admn_pro_textNIC);
        address = view.findViewById(R.id.admn_pro_address);
        img = view.findViewById(R.id.admn_pro_img1);
        edit = view.findViewById(R.id.edit_pro);
        logout = view.findViewById(R.id.btn_logout);

        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();

        String Aname = userDetails.get(SessionManager.KEY_NAME);
        String Aemail = userDetails.get(SessionManager.KEY_EMAIL);
        String Amobile = userDetails.get(SessionManager.KEY_MOBILE);
        String Agender = userDetails.get(SessionManager.KEY_GENDER);
        String Anic = userDetails.get(SessionManager.KEY_NIC);
        String Aaddress = userDetails.get(SessionManager.KE_ADDRESS);
        String image = userDetails.get(SessionManager.KE_url);

        name.setText(Aname);
        email.setText(Aemail);
        mobile.setText(Amobile);
        gender.setText(Agender);
        nic.setText(Anic);
        address.setText(Aaddress);
        Picasso.get().load(image).centerCrop().fit().into(img);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_admin_update adminUpdate = new admin_admin_update(sessionManager.get_admin_details());
                adminUpdate.show(getChildFragmentManager(),"profile1");
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), admin_login.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
package com.petworld_madebysocialworld;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


/**
 * Vista para los leads del CRM
 */
public class LeadsFragment extends Fragment {

    ListView mLeadsList;
    ArrayAdapter<Lead> mLeadsAdapter;

    public LeadsFragment() {
        // Required empty public constructor
    }

    public static LeadsFragment newInstance(/*parámetros*/) {
        LeadsFragment fragment = new LeadsFragment();
        // Setup parámetros
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Gets parámetros
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leads, container, false);


        // Inicializar el adaptador con la fuente de datos.
        List<Lead> repo = LeadsRepository.getInstance().getLeads();
        //es buit
        mLeadsAdapter = new LeadsAdapter(getActivity(), repo);

        // Instancia del ListView.
        mLeadsList = (ListView) root.findViewById(R.id.leads_list);

        //Relacionando la lista con el adaptador
        mLeadsList.setAdapter(mLeadsAdapter);

        mLeadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lead selItem = (Lead) parent.getAdapter().getItem(position);
                String passId = selItem.getId();
                Intent i = new Intent(LeadsFragment.this.getActivity(), CreateWalkActivity.class);
                i.putExtra("itemId",passId);
                startActivity(i);
            }
        });
        return root;
    }
}

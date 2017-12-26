package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.List;

import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Babysitter;
import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Dialog;
import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Message;
import carsapp.douirimohamedtaha.com.chedliweldi.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Chat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Chat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Chat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Dialog dialog;
    private Message message;
    private OnFragmentInteractionListener mListener;
    private DialogsList dialogsListView;
    private List<Dialog> dialogs;
    private List<Message> messages;
    private ArrayList<Babysitter> babysitters;

    public Chat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chat.
     */
    // TODO: Rename and change types and number of parameters
    public static Chat newInstance(String param1, String param2) {
        Chat fragment = new Chat();
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
        View v = inflater.inflate(R.layout.fragment_messages, container, false);
        dialogs=new ArrayList<>();
        message = new Message("0",new Babysitter("Taha", "Douiri", "https://scontent.ftun5-1.fna.fbcdn.net/v/t1.0-9/16425772_10210635995528840_4542581271681303557_n.jpg?oh=412d980a5d28bff28ba8436d778df384&oe=5AC2463A", "tsaassou@gmail.com", "", null, 0,0, 5),"Hellp");

        babysitters=new ArrayList<>();
        dialogs.add(new Dialog("0","Test","https://scontent.ftun5-1.fna.fbcdn.net/v/t1.0-9/16425772_10210635995528840_4542581271681303557_n.jpg?oh=412d980a5d28bff28ba8436d778df384&oe=5AC2463A",babysitters,message,1));
        dialogs.add(new Dialog("0","Test","https://scontent.ftun5-1.fna.fbcdn.net/v/t1.0-9/16425772_10210635995528840_4542581271681303557_n.jpg?oh=412d980a5d28bff28ba8436d778df384&oe=5AC2463A",babysitters,message,5));
        dialogsListView = (DialogsList)v.findViewById(R.id.dialogsList);
        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter(new com.stfalcon.chatkit.commons.ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(getContext()).load(url).into(imageView);

            }
        });
        dialogsListAdapter.setItems(dialogs);
        dialogsListView.setAdapter(dialogsListAdapter);



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

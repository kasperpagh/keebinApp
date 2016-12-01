package kasper.pagh.keebin;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class UsersLoyaltyCards extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public UsersLoyaltyCards() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UsersLoyaltyCards newInstance() {
        UsersLoyaltyCards fragment = new UsersLoyaltyCards();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.loyaltycardrow, container, false);

        //Her finder vi drawable billede dynamisk!
        String coffeeBrand = mParam1;
        String number = "1";
        String combined = coffeeBrand+number;


        int thisCard = getResources().getIdentifier(combined, "drawable", "com.ebookfrenzy.fragmenter");
        //Find billede dynamisk slut.

        Integer[] testArray = {thisCard, R.drawable.baresso2, R.drawable.baresso3, R.drawable.baresso4, R.drawable.baresso5,
                R.drawable.baresso6, R.drawable.baresso7, R.drawable.baresso8, R.drawable.baresso9,
                R.drawable.baresso10};

        for (Integer testCard : testArray)
        {

//        loop som kører igennem array




            LinearLayout rl = (LinearLayout) view.findViewById(R.id.LinearLayout_LoyaltyCards);
            ImageView iv = new ImageView(getContext());

            iv.setImageDrawable(getContext().getDrawable(testCard));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600);
            lp.setMargins(0, 40, 0, 0);
            iv.setLayoutParams(lp);
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            rl.addView(iv);

        }


        return view;
    }





//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}

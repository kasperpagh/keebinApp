package kasper.pagh.keebin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import entity.User;

/**
 * Created by pelle on 12/6/2016.
 */


public class YourInfoFragment extends Fragment implements AsyncResponse{
    EditText firstName;
    EditText lastName;
    TextView email;
    EditText birthday;
    Spinner dropdown;

    public static YourInfoFragment newInstance()
    {

        String name;
        Bundle args = new Bundle();
        YourInfoFragment fragment = new YourInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d("her er vores user ", MainActivity.currentUser.toString());
        final View view = inflater.inflate(R.layout.your_info, container, false);

        email = (TextView) view.findViewById(R.id.email);
        birthday = (EditText) view.findViewById(R.id.birthday);
        lastName = (EditText) view.findViewById(R.id.lastName);
        firstName = (EditText) view.findViewById(R.id.firstName);

        firstName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                MainActivity.currentUser.setFirstName(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        lastName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                MainActivity.currentUser.setLastName(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        birthday.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                MainActivity.currentUser.setBirthday(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        firstName.setText(MainActivity.currentUser.getFirstName());
        lastName.setText(MainActivity.currentUser.getLastName());
        email.setText(MainActivity.currentUser.getEmail());
        birthday.setText(MainActivity.currentUser.getBirthday());

        dropdown = (Spinner) view.findViewById(R.id.Køn);
        String[] items = new String[]{"male", "female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        return view;
    }


    @Override
    public void processFinished(String output)
    {
        Gson gson = new Gson();
        User user = gson.fromJson(output, User.class);
        MainActivity.currentUser = user;

    }

}

package app.delchat.heaven.zion.delchat;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Zion on 26/09/15.
 */

public class InboxFragment extends ListFragment {

    protected List<ParseObject>  mMessages;
    public static final String TAG =InboxFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());

        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messages, ParseException e) {
                if (e == null) {
                    mMessages = messages;
                    String[] username = new String[mMessages.size()];
                    int i = 0;
                    for (ParseObject message : mMessages) {
                        username[i] = message.getString(ParseConstants.KEY_USERNAME);
                        i++;
                    }
                    MessageAdapter adapter = new MessageAdapter(
                            getListView().getContext(),
                            mMessages
                    );
                    setListAdapter(adapter);
                }
            }
        });
    }


}

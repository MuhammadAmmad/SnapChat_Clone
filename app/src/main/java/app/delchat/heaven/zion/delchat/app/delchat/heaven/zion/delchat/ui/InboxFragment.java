package app.delchat.heaven.zion.delchat.app.delchat.heaven.zion.delchat.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import app.delchat.heaven.zion.delchat.R;
import app.delchat.heaven.zion.delchat.adapters.MessageAdapter;
import app.delchat.heaven.zion.delchat.utilities.ParseConstants;

/**
 * Created by Zion on 26/09/15.
 */

public class InboxFragment extends ListFragment {

    protected List<ParseObject> mMessages;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String TAG = InboxFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListner);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        retriveMessages();
    }

    private void retriveMessages() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());

        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messages, ParseException e) {

                if (mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (e == null) {
                    mMessages = messages;
                    String[] username = new String[mMessages.size()];
                    int i = 0;
                    for (ParseObject message : mMessages) {
                        username[i] = message.getString(ParseConstants.KEY_USERNAME);
                        i++;
                    }
                    if (getListView().getAdapter() == null) {
                        MessageAdapter adapter = new MessageAdapter(
                                getListView().getContext(),
                                mMessages);
                        setListAdapter(adapter);
                    } else {
                        //refill the adapter
                        ((MessageAdapter) getListView().getAdapter()).refill(mMessages);
                    }
                }
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ParseObject message = mMessages.get(position);
        String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
        ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
        Uri fileUri = Uri.parse(file.getUrl());
        if (messageType.equals(ParseConstants.TYPE_IMAGE)) {

            //Show Image
            Intent intent = new Intent(getActivity(), ViewImage.class);
            intent.setData(fileUri);
            startActivity(intent);
        } else {
            // Show Video
            Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
            intent.setDataAndType(fileUri, "video/*");
            startActivity(intent);
        }
        List<String> ids = message.getList(ParseConstants.KEY_RECIPIENT_IDS);
        if (ids.size() == 1) {
            //Delete Whole Thing
            message.deleteInBackground();
        } else {
            //Delete Specific
            ArrayList<String> idToRemove = new ArrayList<String>();
            idToRemove.add(ParseUser.getCurrentUser().getObjectId());
            message.removeAll(ParseConstants.KEY_RECIPIENT_IDS, idToRemove);

            message.saveInBackground();
        }
    }

    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListner = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //Toast.makeText(getActivity(), "We're Refreshing", Toast.LENGTH_SHORT).show();
            retriveMessages();
        }
    };
}

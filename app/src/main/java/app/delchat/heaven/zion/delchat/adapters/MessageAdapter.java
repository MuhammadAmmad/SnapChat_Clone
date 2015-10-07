package app.delchat.heaven.zion.delchat.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

import app.delchat.heaven.zion.delchat.R;
import app.delchat.heaven.zion.delchat.utilities.ParseConstants;

/**
 * Created by Zion on 03/10/15.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mMessages;

    public MessageAdapter(Context context, List<ParseObject> messages) {
        super(context, R.layout.message_layout, messages);
        mContext = context;
        mMessages = messages;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_layout, null);
            holder = new ViewHolder();

            holder.iconImageView = (ImageView)convertView.findViewById(R.id.messageIcon);
            holder.nameLabel = (TextView)convertView.findViewById(R.id.senderLabel1);
            holder.timeLabel = (TextView)convertView.findViewById(R.id.timeLabel);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        ParseObject message = mMessages.get(position);
        Date createdAt = message.getCreatedAt();
        long now = new Date().getTime();

        String convertedDate = DateUtils.getRelativeTimeSpanString(createdAt.getTime(),
                now,
                DateUtils.SECOND_IN_MILLIS ).toString();
        holder.timeLabel.setText(convertedDate);

        if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {

            holder.iconImageView.setImageResource(R.drawable.ic_image);
        }
        else {
            holder.iconImageView.setImageResource(R.drawable.ic_video);
        }

        holder.nameLabel.setText(message.getString(ParseConstants.KEY_USERNAME));

        return convertView;
    }
    private static class ViewHolder{
        ImageView iconImageView;
        TextView nameLabel;
        TextView timeLabel;
    }

    public void refill(List<ParseObject> messages){
        mMessages.clear();
        mMessages.addAll(messages);
         notifyDataSetChanged();

    }
}



package pl.admund.MVM_Client.Message;

import java.util.ArrayList;
import java.util.List;
import pl.admund.MVM_Client.R;
import pl.admund.MVM_Client.XMLUtils.HTTPGetAsyncTask;
import pl.admund.MVM_Client.XMLUtils.XMLCreator;
import pl.admund.MVM_Client.XMLUtils.XMLType;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MessageListAdapter extends ArrayAdapter<MessageClnt>
{
	private Context context = null;
	private ArrayList<MessageClnt> messageList = new ArrayList<MessageClnt>();

	public MessageListAdapter(Context context, int resource, int textViewResourceId, List<MessageClnt> objects) 
	{
		super(context, resource, textViewResourceId, objects);
		
		this.context = context;
		this.messageList = (ArrayList<MessageClnt>) objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		if(rowView == null) 
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.msg_message_row, null);
		}
		
		final MessageClnt tmpMessage = messageList.get(position);
		//if(position%2 == 0)
		//	rowView.setBackgroundResource(R.drawable.bar_msg_1);
		//else
		//	rowView.setBackgroundResource(R.drawable.bar_msg_2);

		TextView dateTextView = (TextView)rowView.findViewById(R.id.message_row_date_text);
		String tab[] = tmpMessage.getDate().split(" ");
		dateTextView.setText(tab[0] + "\n" + tab[1]);
		
		TextView senderTextView = (TextView)rowView.findViewById(R.id.message_row_sender_text);
		senderTextView.setText(tmpMessage.getSender());
		senderTextView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				((Activity)context).showDialog(99);//WAIT_DIALOG = 99;
				
				String url = XMLCreator.createGetPlayerProfileURL(tmpMessage.getSenderId());
		    	new HTTPGetAsyncTask(XMLType.GET_PROFILE_HTTP).execute(url);
			}
		});
		
		TextView msgTextView = (TextView)rowView.findViewById(R.id.message_row_msg_text);
		addMsgText(tmpMessage, msgTextView);
		msgTextView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Bundle bundle = new Bundle();
				bundle.putString("MSG", tmpMessage.getMsg());
				bundle.putInt("MSG_TYPE", tmpMessage.getMsgType());
				bundle.putString("SENDER", tmpMessage.getSender());
				if(tmpMessage.getMsgType() == MessageClnt.MSG_TYPE_MATCH_REQ)
				{
					String[] strTab = tmpMessage.getMsg().split("#");
					//bundle.putString("MSG", "MSG REQ MATCH_ID " + strTab[0] + " OPPONENT_ID " + strTab[1]);
					bundle.putInt("SENDER_ID", Integer.parseInt(strTab[1]));
					bundle.putInt("PROP_CNT", 1);
					
					((Activity)context).showDialog(4, bundle);//MSG_REQ_DIALOG = 4;
				}
				else if(tmpMessage.getMsgType() == MessageClnt.MSG_TYPE_MATCH_PROP)
				{
					bundle.putInt("SENDER_ID", tmpMessage.getSenderId());
					
					((Activity)context).showDialog(5, bundle);//MSG_PROP_DIALOG = 5;
				}
				else if(tmpMessage.getMsgType() == MessageClnt.MSG_TYPE_MATCH_ACCEPT)
				{
					bundle.putInt("SENDER_ID", tmpMessage.getSenderId());
					
					((Activity)context).showDialog(6, bundle);//MSG_ACCEPT_DIALOG = 6;
				}
				else if(tmpMessage.getMsgType() == MessageClnt.MSG_TYPE_MATCH)
				{
					bundle.putInt("SENDER_ID", tmpMessage.getSenderId());
					
					((Activity)context).showDialog(7, bundle);//MSG_MATCH_DIALOG = 7;
				}
				else
				{
					bundle.putInt("UID", tmpMessage.getSenderId());
					bundle.putString("SENDER", tmpMessage.getSender());
					
					((Activity)context).showDialog(3, bundle);//MSG_NORMAL_DIALOG = 3;
				}
			}
		});
		
		/*
		Button replyBtn = (Button)rowView.findViewById(R.id.message_row_reply_button);
		replyBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Bundle bundle = new Bundle();
				
				int uid = tmpMessage.getSenderId();
				bundle.putInt("UID", uid);
				int mid = tmpMessage.getMsgId();
				bundle.putInt("MID", mid);
				String senderName = tmpMessage.getSender();
				bundle.putString("SENDER", senderName);
				
				((Activity)context).showDialog(MessagePanelActivity.SEND_MESSAGE_DIALOG, bundle);
			}
		});
		*/
		
		Button deleteBtn = (Button)rowView.findViewById(R.id.message_row_delete_button);
		deleteBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Bundle bundle = new Bundle();
				
				int mid = tmpMessage.getMsgId();
				bundle.putInt("MID", mid);
				
				((Activity)context).showDialog(MessagePanelActivity.DELETE_MESSAGE_DIALOG, bundle);
			}
		});
		
		return rowView;
	}
	
	private void addMsgText(MessageClnt msg, TextView textView)
	{
		String text;
		String[] strTab = msg.getMsg().split("#");
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND
		if(msg.getMsgType() == MessageClnt.MSG_TYPE_MATCH_REQ)
		{
			text = "TERMIN! Wybierz termin meczu z " + strTab[2] + " w lidze " + strTab[3] + " sezon " + strTab[4] + " runda " + strTab[5] + "!!!";
		}
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND # PROP_CNT # TERM1 # TERM2 # TERM3
		else if(msg.getMsgType() == MessageClnt.MSG_TYPE_MATCH_PROP)
		{
			text = "PROPOZYCJA TERMINU! Gracz " + strTab[2] + " zaproponowal nastepujace terminy.";
		}
		//FORMAT:  MATCH_ID # OPPONENT_ID # OPPONENT_NAME # LEAGUE_NAME # SEASON # ROUND # TERM
		else if(msg.getMsgType() == MessageClnt.MSG_TYPE_MATCH_ACCEPT)
		{
			text = "TERMIN USTALONY! Termin meczu z graczem " + strTab[2] + " zostal ustalony.";
		}
		else
			text = msg.getMsg();
		
		textView.setText(text);
	}
}

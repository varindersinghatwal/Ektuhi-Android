package com.jeifbell.ektuhi;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	
	// Keep all Images in array
	
	public ArrayList<String> mThumbIds = new ArrayList<String>();
	// Constructor
	public ImageAdapter(Context c){
		mContext = c;
	}

	@Override
	public int getCount() {
		return mThumbIds.size();
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		/*ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(mThumbIds.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(90, 90));
        return imageView;*/
		ViewHolderItem viewHolder;

		if (convertView == null) {

			// inflate the layout

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.gridv, parent,false);
			viewHolder = new ViewHolderItem();

			viewHolder.commentTxt = (TextView) convertView
					.findViewById(R.id.gridtxt);

			// store the holder with the view.

			// viewHolder.durationView = (TextView) convertView
			// .findViewById(R.id.event_duration);

			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolderItem) convertView.getTag();

		}
		viewHolder.commentTxt.setText(mThumbIds.get(position));
		
		return convertView;
	}

	static class ViewHolderItem {

		TextView commentTxt;

	}
	
}

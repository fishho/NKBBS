package com.cfish.rvb.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.widget.TextView;

import com.cfish.rvb.R;


/**
 * The ImageGetterUtil
 * @author ThachNguyen
 * @version 1.0 
 * 			No support for multiscreensize. 
 * */
public class ImageGetterUtil implements ImageGetter { 
	// The context of activity
	Context mContext;
	// The textview control
	TextView mContentText;
	
	/**
	 * The contructor
	 * @param c
	 * 		The context of activity

	 */
	public ImageGetterUtil(Context c){
		this.mContext = c;
	}
	
	/**
	 * @return the context
	 */
	public Context getContext(){
		return mContext;
	}
	
	/** 
	 * @param source
	 * 			the link of tag <img>
	 * @return drawable
	 */
	@Override
	public Drawable getDrawable(String source) {
		// TODO Auto-generated method stub
		
		LevelListDrawable d = new LevelListDrawable();
		Drawable empty = getContext().getResources().getDrawable(R.mipmap.ic_launcher);
		 d.addLevel(0, 0, empty);
	     d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
	     //d.setBounds(0, 0, 600, 480);
	     new ImageGetterAsync(mContext,mContentText,d).execute(source);
	     return d;
	}
	

	/**
	 * 
	 * @author ThachNguyen
	 *
	 */
	public static class ImageGetterAsync extends AsyncTask<Object, Void, Bitmap>{

		LevelListDrawable listDrawable;
		Context context;
		TextView textView;
		
		public ImageGetterAsync(Context ctx, TextView tv, LevelListDrawable lv) {
			// TODO Auto-generated constructor stub
			this.context = ctx;
			this.textView = tv;
			this.listDrawable = lv;
		}
		@Override
		protected Bitmap doInBackground(Object... params) {
			// TODO Auto-generated method stub

			String source = (String) params[0];
			if (!source.contains("http://")){
				source = "http://bbs.nankai.edu.cn/"+source;
			}
			
			Bitmap bitmap = null;
			
			try {
				InputStream inputStream = new URL(source).openStream();
				bitmap = BitmapFactory.decodeStream(inputStream);
				return bitmap;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null){				
				listDrawable.addLevel(1, 1, new BitmapDrawable(context.getResources(), result));
                Log.d("Dfish","resultwidht"+result.getWidth());
				if (result.getWidth()>600) {
					listDrawable.setBounds(20, 0, 600, result.getHeight() * 600 / result.getWidth());
				}
				listDrawable.setBounds(20, 0, result.getWidth(), result.getHeight());
				listDrawable.setLevel(1);
				
				//textView.invalidate();
				//textView.postInvalidate();
				
				// update view
//				CharSequence text = textView.getText();
//                textView.setText(text);
               
			}
		}
	}

}
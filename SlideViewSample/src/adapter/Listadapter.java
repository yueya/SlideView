package adapter;

import java.util.ArrayList;
import java.util.List;

import widget.SlideView;
import widget.SlideView.OnSlideListener;

import com.example.mytest.R;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Listadapter extends BaseAdapter implements OnSlideListener{
	private List<String> list;
	private List<SlideView> itemList=new ArrayList<SlideView>();
	@Override
	public int getCount() {
		return list.size();
	}
	public void setData(List<String> list){
		this.list=list;
	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public SlideView getItemView(int position){
		return itemList.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SlideView itemView;
		if(convertView==null){
			View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
			LayoutParams params=new LayoutParams(getScreenWidth(parent.getContext()), LayoutParams.WRAP_CONTENT);
			view.setLayoutParams(params);
			ViewHolder viewHolder=new ViewHolder(view);
			itemView=new SlideView(parent.getContext());
			itemView.setOnSlideListener(this);
			itemView.setContentView(view);
			itemView.setTag(viewHolder);
			view.findViewById(R.id.msg).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(v.getContext(), "asd", 0).show();
				}
			});
		}else{
			itemView=(SlideView)convertView;
		}
		itemList.add(position, itemView);
		return itemView;
	}
	 private class ViewHolder {        
		 public TextView msg;        
		 public ViewGroup deleteHolder;  
		 public ViewHolder(View view) {            
			 msg = (TextView) view.findViewById(R.id.msg);            
			 deleteHolder = (ViewGroup) view.findViewById(R.id.holder);        
		}    
	}
	 //关闭其他打开的删除item
	 @Override
	public void onSlide(View view, int status) {
		 for(SlideView item:itemList){
			 if(view!=item){
				item.shrink();
			 }
		 }
	}
	 public int getScreenWidth(Context context){
			DisplayMetrics dm = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
			return dm.widthPixels;
		}
}

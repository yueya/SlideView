package widget;

import java.util.ArrayList;
import java.util.List;

import com.example.mytest.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class SlideView extends LinearLayout{


	private static final String TAG="SlideView";
	private Context context;
	private LinearLayout mViewContent;
	private LinearLayout mHolder;
	private Scroller mScroller;
	private List<OnSlideListener> mOnSlideListener;
	private int mHolderWidth=120;
	private int mLastX;
	private int mLastY;
	private boolean isMove=false;
	private static int TAN=2;// 用来控制滑动角度，仅当角度a满足如下条件才进行滑动：tan a = deltaX / deltaY > 2
	
	public SlideView(Context context) {
		super(context);
		mOnSlideListener=new ArrayList<SlideView.OnSlideListener>();
		initView();
	}
	public SlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	private void initView() {
		context=getContext();
		mScroller=new Scroller(context);
		View.inflate(context, R.layout.slide_view_merge, this);
		mViewContent=(LinearLayout)findViewById(R.id.view_content);
		mHolderWidth=Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHolderWidth	, getResources().getDisplayMetrics()));
	}
	public void setContentView(View view){
		mViewContent.addView(view);
	}
	public void setOnSlideListener(OnSlideListener listener){
		this.mOnSlideListener.add(listener);
	}
	public void shrink(){
		if(getScrollX()!=0){
			this.smoothScrollTo(0, 0);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x=(int)event.getX();
		int y=(int)event.getY();
		Log.d(TAG, "x="+x+",y="+y);
		int deltaX=x-mLastX;
		int deltaY=y-mLastY;
		System.out.println(deltaX);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN://手指按下，开始滑动
			mLastX=x;
			mLastY=y;
			if(!mScroller.isFinished()){
				mScroller.abortAnimation();//停止滑动
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(Math.abs(deltaX)<Math.abs(deltaY)*TAN){ //检查是是否符合横向滑动
				break;
			}else{
				isMove=true;
			}
			int newScrollX=0;
			if(Math.abs(deltaX)>ViewConfiguration.getTouchSlop()){
				if(deltaX<0){
					newScrollX=mHolderWidth;
				}else if(newScrollX>10){
					newScrollX=0;
				}
				System.out.println(newScrollX+"--move");
			this.smoothScrollTo(newScrollX, 0);
			}
			break;
		case MotionEvent.ACTION_UP:
			if(mOnSlideListener!=null && isMove){
				for(OnSlideListener item:mOnSlideListener){
					item.onSlide(this, OnSlideListener.SLIDE_STATUS_START_SCROLL);
				}
			}
			break;
		default:
			break;
		}
		return true;
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int x=(int)ev.getX();
		int y=(int)ev.getY();
		mLastX=x;
		mLastY=y;
		boolean intercept=false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			intercept=false;
			break;
		case MotionEvent.ACTION_MOVE:
			System.out.println("intre");
			intercept=true;
			break;
		case MotionEvent.ACTION_UP:
			intercept=false;
			break;

		default:
			break;
		}
		return intercept;
	}


	public interface OnSlideListener{
		// SlideView的三种状态：开始滑动，打开，关闭       
		public static final int SLIDE_STATUS_OFF = 0;        
		public static final int SLIDE_STATUS_START_SCROLL = 1;        
		public static final int SLIDE_STATUS_ON = 2;        
		/**         
		 * 
		 * @param view         
		 * *  current SlideView         
		 * * @param status         
		 * *  SLIDE_STATUS_ON, SLIDE_STATUS_OFF or         
		 * *  SLIDE_STATUS_START_SCROLL         
		 * */        
		public void onSlide(View view, int status);
	}
	
	private void smoothScrollTo(int destX, int destY) {        
		// 缓慢滚动到指定位置        
		int scrollX = getScrollX();        
		int delta = destX - scrollX;        
		// 以三倍时长滑向destX，效果就是慢慢滑动       
		mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);        
		invalidate();    
		}    
	
	@Override    
	public void computeScroll() {        
		if (mScroller.computeScrollOffset()) {            
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());            
			postInvalidate();        
			}    
		}
}

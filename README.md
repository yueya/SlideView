# SlideView


   一个类似于qq侧滑删除的smple工程，使用一个自定义的SlideView，SlideView是独立的，不需要依赖listview或者在adapter里设置什么，
只要adapter里返回的itemView是SlideView就行，具体使用请看sample
  注意：在listview使用后，listview的onItemClick等事件监听将会失效，目前这是这个view不足的地方，暂时还没解决这个问题。
  我的解决办法是把这些监听放在adapter里。
  

package com.example.whoslucky.app.adapter_listener;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whoslucky.app.gamedto.DTO;
import com.example.whoslucky.app.R;

import java.util.List;

/**
 * Created by Saurav on 7/16/2014.
 */
public class Adapter extends BaseAdapter {

    private Context context;
    private  int layout ;
    List<DTO> imageList;

    public Adapter(Context context, int layout, List<DTO> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView1 = (ImageView) view.findViewById(R.id.imageView1);
            viewHolder.imageView2 = (ImageView) view.findViewById(R.id.imageView2);
            viewHolder.textView = (TextView) view.findViewById(R.id.cardid);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView1.setImageResource(imageList.get(i).getImage());
        viewHolder.textView.setText(""+imageList.get(i).getId());
        Log.i("Check:::",""+imageList.get(i).getId());

        return view;
    }
    public class ViewHolder{
        ImageView imageView1,imageView2;
        TextView textView;

    }
}

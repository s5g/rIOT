package templar.riotandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Devin on 6/6/2017.
 */

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseAdapterViewHolder> {
    private Context mContext;
    private static final int VIEW_TYPE_NORMAL = 0;
    private final String[] mHouseAreas = {"Kitchen"};

    HouseAdapter(@NonNull Context context){
        mContext = context;
    }

    @Override
    public HouseAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        int layoutId = R.layout.house_list_item;
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);
        return new HouseAdapterViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final HouseAdapterViewHolder houseAdapterViewHolder, final int position){
        houseAdapterViewHolder.setHouseItem(position);
    }

    @Override
    public int getItemCount(){
        return mHouseAreas.length;
    }

    @Override
    public int getItemViewType(int position){
        return VIEW_TYPE_NORMAL;
    }

    class HouseAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mHouseLocation;

       private HouseAdapterViewHolder(View view, int viewType){
            super(view);
           mHouseLocation = (TextView) view.findViewById(R.id.houseLocation);
            view.setOnClickListener(this);
        }

        void setHouseItem(int position){
            mHouseLocation.setText(mHouseAreas[position]);
        }

        @Override
        public void onClick(View v){
            //TODO
        }
    }
}

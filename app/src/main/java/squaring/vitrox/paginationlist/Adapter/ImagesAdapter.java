package squaring.vitrox.paginationlist.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import squaring.vitrox.paginationlist.Model.ApiImage;
import squaring.vitrox.paginationlist.R;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private List<ApiImage> mDataSet;
    private Context mContext;
    private final OnItemClickListener mlistener;

    public ImagesAdapter(Context context,OnItemClickListener clickListener) {
        mDataSet = new ArrayList<>();
        mContext = context;
        mlistener=clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mTitleView;
        private ImageView mThumbImageView;
        private TextView mIdView;

        public ViewHolder(View view) {
            super(view);
            mView=view;
            mThumbImageView = (ImageView) view.findViewById(R.id.thumbnail);
            mTitleView = (TextView) view.findViewById(R.id.image_title);
            mIdView = (TextView) view.findViewById(R.id.image_id);
        }

        public void bind(final ApiImage image) {
            mTitleView.setText(image.getTitle());
            mIdView.setText(image.getId());
            String urlImage = image.getDisplaySizes().get(0).getUri();
            Glide.with(mContext).load(urlImage)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                    .fitCenter().into(mThumbImageView);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.onItemClick(image.getCaption());
                }
            });
        }
    }


    public void addMoreData(List<ApiImage> data) {
        mDataSet.addAll(data);
        notifyDataSetChanged();
    }

    public void resetData() {
        mDataSet.clear();
        notifyDataSetChanged();
    }

    public List<ApiImage> getAllDataList() {
        return mDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.bind(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        }
        return mDataSet.size();
    }
}
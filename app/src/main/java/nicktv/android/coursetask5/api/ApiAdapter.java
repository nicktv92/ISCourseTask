package nicktv.android.coursetask5.api;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nicktv.android.coursetask5.R;
import nicktv.android.coursetask5.weather.WeatherActivity;


public class ApiAdapter  extends RecyclerView.Adapter<ApiAdapter.ViewHolder> {

    private List<CityList> cityLists;
    private Context context;

    public ApiAdapter(List<CityList> cityLists, Context context) {
        this.cityLists = cityLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CityList cityList = cityLists.get(position);
        holder.title.setText(cityList.getTitle());
        holder.type.setText(cityList.getLocation_type());
        holder.location.setText(cityList.getLatt_long());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WeatherActivity.class);
                intent.putExtra("woeid", cityList.getWoeid());
                intent.putExtra("title", cityList.getTitle());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView title;
        public TextView type;
        public TextView location;
        public View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            title = (TextView) itemView.findViewById(R.id.cityTitleTxt);
            type = (TextView) itemView.findViewById(R.id.cityTypeTxt);
            location = (TextView) itemView.findViewById(R.id.cityLatLongTxt);
        }
    }
}

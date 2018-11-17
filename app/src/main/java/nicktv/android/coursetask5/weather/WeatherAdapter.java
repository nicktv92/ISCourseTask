package nicktv.android.coursetask5.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import nicktv.android.coursetask5.R;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<Weather> weatherListsLists;
    private Context context;
    private final static String IMG_URL = "https://www.metaweather.com/static/img/weather/png/64/";
    private final static String IMG_TYPE = ".png";

    public WeatherAdapter(List<Weather> cityLists, Context context) {
        this.weatherListsLists = cityLists;
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather, parent, false);
        return new WeatherAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, final int position) {
        final Weather weatherListsList = weatherListsLists.get(position);
        holder.weather.setText(oneChar(weatherListsList.getWeather()));
        holder.weatherMin.setText(oneChar(weatherListsList.getWeatherMin()));
        holder.weatherMax.setText(oneChar(weatherListsList.getWeatherMax()));
        holder.weatherDate.setText(weatherListsList.getWeatherDate());

        String url = IMG_URL + weatherListsList.getWeather_state_abbr() + IMG_TYPE;
        Picasso.with(context).load(url).into(holder.weatherCloud);
    }

    private String oneChar(String in) {
        DecimalFormat df = new DecimalFormat("#.#");
        return in == null ? in : df.format(Float.valueOf(in));
    }

    @Override
    public int getItemCount() {
        return weatherListsLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView weather, weatherMin, weatherMax, weatherDate;
        public ImageView weatherCloud;

        ViewHolder(View itemView) {
            super(itemView);
            weather = (TextView) itemView.findViewById(R.id.weatherTxt);
            weatherMin = (TextView) itemView.findViewById(R.id.weatherMinTxt);
            weatherMax = (TextView) itemView.findViewById(R.id.weatherMaxTxt);
            weatherDate = (TextView) itemView.findViewById(R.id.weatherDateTxt);
            weatherCloud = (ImageView) itemView.findViewById(R.id.weatherImg);
        }
    }
}

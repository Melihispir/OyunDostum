package com.something.oyundostum.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.something.oyundostum.R;
import com.something.oyundostum.models.Games;
import java.util.List;

public class GameAdapter extends PagerAdapter {

  private List<Games> models;
  private LayoutInflater layoutInflater;
  private Context context;

  public GameAdapter(List<Games> models, Context context) {
    this.models = models;
    this.context = context;
  }

  @Override
  public int getCount() {
    return models.size();
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view.equals(object);
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(R.layout.item_trend, container, false);

    ImageView imageView;
    TextView name;

    imageView = view.findViewById(R.id.imgItem);
    name = view.findViewById(R.id.name);

    imageView.setImageResource(models.get(position).getImage());
    name.setText(models.get(position).getName());

    container.addView(view, 0);
    return view;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }
}

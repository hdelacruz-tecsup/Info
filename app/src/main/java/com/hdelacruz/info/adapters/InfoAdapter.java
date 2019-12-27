package com.hdelacruz.info.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hdelacruz.info.R;
import com.hdelacruz.info.models.Info;
import com.hdelacruz.info.services.ApiService;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private static final String TAG = InfoAdapter.class.getSimpleName();
    private List<Info> infos;

    public InfoAdapter(){
        this.infos = new ArrayList<>();
    }

    public void setInfos(List<Info> infos){
        this.infos = infos;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoImage;
        TextView nombreText;
        TextView ariaText;

        ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.foto_image);
            nombreText = itemView.findViewById(R.id.nombre_text);
            ariaText = itemView.findViewById(R.id.aria_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Context context = viewHolder.itemView.getContext();

        Info info = this.infos.get(position);
        viewHolder.nombreText.setText(info.getTrabajador_if());
        viewHolder.ariaText.setText(info.getArea_if());

        String url = ApiService.API_BASE_URL + "/info/images/" + info.getImagen();
        Picasso.with(context).load(url).into(viewHolder.fotoImage);

    }

    @Override
    public int getItemCount() {
        return this.infos.size();
    }

}




















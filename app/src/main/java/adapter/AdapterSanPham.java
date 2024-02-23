package adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

import model.Nhacungcap;
import model.Sanpham;
import vn.edu.stu.doangk.ChiTietSPActivity;
import vn.edu.stu.doangk.EditNhacuncapActivity;
import vn.edu.stu.doangk.R;

public class AdapterSanPham extends ArrayAdapter<Sanpham> {
    String DB_NAME = "dbgk.db";
    Activity context;
    int resource;
    List<Sanpham> objects;
    SQLiteDatabase database;
    public AdapterSanPham(@NonNull Activity context, int resource, @NonNull List<Sanpham> objects,SQLiteDatabase db) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        database=db;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        LayoutInflater inflater = this.context.getLayoutInflater();

        view = inflater.inflate(R.layout.sanpham_custom, null);

        Button btnXoa = view.findViewById(R.id.btnXoa);


        ImageView img = view.findViewById(R.id.imageviewHinh);

        TextView tvMa = view.findViewById(R.id.textviewMa);
        TextView tvTen = view.findViewById(R.id.textviewTen);
        TextView tvNcc = view.findViewById(R.id.textviewLoai);


        Sanpham SP = this.objects.get(position);

        tvMa.setText(SP.getId()+"");
        tvTen.setText(SP.getTen());
        tvNcc.setText(SP.getNcc());

        Bitmap bitmapImg = BitmapFactory.decodeByteArray(SP.getHinh(), 0, SP.getHinh().length);
        img.setImageBitmap(bitmapImg);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        context,
                        ChiTietSPActivity.class
                );
                Sanpham sp = objects.get(position);
                intent.putExtra("SANPHAM", sp);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        objects.remove(position);
                        database.delete("sanpham",  "id" + " = " +SP.getId() , null);
                        AdapterSanPham.this.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

}

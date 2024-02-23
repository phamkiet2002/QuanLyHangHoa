package adapter;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import model.Nhacungcap;
import model.Sanpham;
import util.DBConfig;
import vn.edu.stu.doangk.EditNhacuncapActivity;
import vn.edu.stu.doangk.NhaCungCapActivity;
import vn.edu.stu.doangk.R;

public class AdapterNcc extends ArrayAdapter<Nhacungcap> {
    String DB_NAME = "dbgk.db";
    Activity context;
    int resource;
    List<Nhacungcap> objects;
    SQLiteDatabase database;
    List<Sanpham> sanphams;

    public AdapterNcc(@NonNull Activity context, int resource, @NonNull List<Nhacungcap> objects, SQLiteDatabase db) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.database = db;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = this.context.getLayoutInflater();

        view = inflater.inflate(R.layout.nhacungcap_ten, null);

        Button btnXoa = view.findViewById(R.id.btnXoa);


        TextView tvTen = view.findViewById(R.id.tvTenncc);
        Nhacungcap ncc = this.objects.get(position);
        tvTen.setText(ncc.getTenncc());

        tvTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        context,
                        EditNhacuncapActivity.class
                );

                Nhacungcap nhacungcap = objects.get(position);

                intent.putExtra("NHACUNGCAP", nhacungcap);
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
                        Cursor cursor = database.query(
                                "sanpham",
                                new String[]{"ncc"},
                                "ncc" + " = ?",
                                new String[]{ncc.getTenncc()},
                                null,
                                null,
                                null
                        );

                        if (cursor.getCount()>0) {
                            // The cursor is positioned on the first entry, meaning there is a record
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Đã tồn tại sản phẩm không thể xóa");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog1, int which) {
                                    Intent intent1 = new Intent(context, NhaCungCapActivity.class);
                                    context.startActivity(intent1);
                                }
                            });
                            AlertDialog dialog1 = builder.create();
                            dialog1.show();
                            dialog.dismiss();
                        } else {
                            // The cursor is empty, meaning there is no record
                            objects.remove(position);
                            database.delete("nhacungcap", "idncc" + " = " + ncc.getIdncc(), null);
                            AdapterNcc.this.notifyDataSetChanged();
                        }

                        cursor.close();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }


}

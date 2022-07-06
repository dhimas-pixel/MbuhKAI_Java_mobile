package com.stephanusdhimas.UAS_MbuhKAI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    private EditText etTanggal;
    private DatePickerDialog dpTanggal;
    int jmlDewasa, jmlAnak;
    int hargaDewasa, hargaAnak;
    int hargaTotalDewasa, hargaTotalAnak, hargaTotal;
    public String sWaktu, sAsal, sTujuan, sTanggal, sDewasa, sAnak, name;
    Spinner spinAsal, spinTujuan, spinDewasa, spinAnak, spinWaktu;
    Calendar newCalendar = Calendar.getInstance();
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    int pageWidth = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        name = getIntent().getStringExtra("name");

        final String[] asal = {"Jakarta", "Bandung", "Purwokerto", "Yogyakarta", "Surabaya"};
        final String[] tujuan = {"Jakarta", "Bandung", "Purwokerto", "Yogyakarta", "Surabaya"};
        final String[] dewasa = {"0", "1", "2", "3", "4", "5"};
        final String[] anak = {"0", "1", "2", "3", "4", "5"};
        final String[] waktu = {"09.00", "12.00", "15.00", "18.00"};

        spinAsal = findViewById(R.id.asal);
        spinTujuan = findViewById(R.id.tujuan);
        spinWaktu = findViewById(R.id.waktu);
        spinDewasa = findViewById(R.id.dewasa);
        spinAnak = findViewById(R.id.anak);

        ArrayAdapter<CharSequence> adapterAsal = new ArrayAdapter<CharSequence>(this, R.layout.select_item, asal);
        adapterAsal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAsal.setAdapter(adapterAsal);

        ArrayAdapter<CharSequence> adapterTujuan = new ArrayAdapter<CharSequence>(this, R.layout.select_item, tujuan);
        adapterTujuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTujuan.setAdapter(adapterTujuan);

        ArrayAdapter<CharSequence> adapterWaktu = new ArrayAdapter<CharSequence>(this, R.layout.select_item, waktu);
        adapterWaktu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWaktu.setAdapter(adapterWaktu);

        ArrayAdapter<CharSequence> adapterDewasa = new ArrayAdapter<CharSequence>(this, R.layout.select_item, dewasa);
        adapterDewasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDewasa.setAdapter(adapterDewasa);

        ArrayAdapter<CharSequence> adapterAnak = new ArrayAdapter<CharSequence>(this, R.layout.select_item, anak);
        adapterAnak.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAnak.setAdapter(adapterAnak);

        spinAsal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sAsal = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinTujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTujuan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinWaktu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sWaktu = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinDewasa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sDewasa = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinAnak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sAnak = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Tanggal
        etTanggal = findViewById(R.id.tanggal_berangkat);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();
        setDateTimeField();

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnBook = findViewById(R.id.book);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perhitunganHarga();
                if (sAsal != null && sTujuan != null && sTanggal != null && sDewasa != null) {
                    if ((sAsal.equalsIgnoreCase("jakarta") && sTujuan.equalsIgnoreCase("jakarta"))
                            || (sAsal.equalsIgnoreCase("bandung") && sTujuan.equalsIgnoreCase("bandung"))
                            || (sAsal.equalsIgnoreCase("purwokerto") && sTujuan.equalsIgnoreCase("purwokerto"))
                            || (sAsal.equalsIgnoreCase("yogyakarta") && sTujuan.equalsIgnoreCase("yogyakarta"))
                            || (sAsal.equalsIgnoreCase("surabaya") && sTujuan.equalsIgnoreCase("surabaya"))) {
                        Toast.makeText(BookingActivity.this, "Asal dan Tujuan tidak boleh sama !", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog dialog = new AlertDialog.Builder(BookingActivity.this)
                                .setTitle("Ingin booking kereta sekarang?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        rootNode = FirebaseDatabase.getInstance();
                                        reference = rootNode.getReference();
                                        String getTanggal = etTanggal.getText().toString();
                                        String total = Integer.toString(hargaTotal);
                                        try {
                                            BookingHelper helper = new BookingHelper(name, sAsal, sTujuan, getTanggal, sWaktu, sDewasa, sAnak, total);
                                            reference.child("booking").child(name).push().setValue(helper);
                                            Toast.makeText(BookingActivity.this, "Booking berhasil", Toast.LENGTH_LONG).show();
//                                            createPdf();
                                            finish();
                                        } catch (Exception e) {
                                            Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("Tidak", null)
                                .create();
                        dialog.show();
                    }
                } else {
                    Toast.makeText(BookingActivity.this, "Mohon lengkapi data pemesanan!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createPdf() {
        String getTanggal = etTanggal.getText().toString();
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(70);
        canvas.drawText("Tiket KRL", pageWidth / 2, 270, titlePaint);

        myPaint.setColor(Color.rgb(0, 113, 188));
        myPaint.setTextSize(30f);
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Call : 0831-6762-8073", 1160, 40, myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(35f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Nama Pembeli : " + name, 20, 590, myPaint);

        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Tanggal : " + getTanggal, pageWidth - 20, 640, myPaint);
        canvas.drawText("Waktu : " + sWaktu, pageWidth - 20, 640, myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Asal : " + sAsal, 200, 830, myPaint);
        canvas.drawText("Tujuan : " + sTujuan, 600, 830, myPaint);
        canvas.drawText("Dewasa : " + sDewasa, 300, 900, myPaint);
        canvas.drawText("Anak : " + sAnak, 300, 950, myPaint);

        myPaint.setColor(Color.rgb(247, 147, 30));
        canvas.drawRect(680, 1350, pageWidth-20, 1450, myPaint);

        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(50f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Semoga liburan Menyenangkan", 700, 1415, myPaint);


        myPdfDocument.finishPage(myPage1);
        File file = new File(this.getExternalFilesDir("/"), "Tiket.pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(BookingActivity.this, "PDF Sudah Ada", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        myPdfDocument.close();
    }

    private void setDateTimeField() {
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpTanggal.show();
            }
        });

        dpTanggal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                        "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                sTanggal = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
                etTanggal.setText(sTanggal);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void perhitunganHarga() {
        if (sAsal.equalsIgnoreCase("jakarta") && sTujuan.equalsIgnoreCase("bandung")) {
            hargaDewasa = 100000;
            hargaAnak = 70000;
        } else if (sAsal.equalsIgnoreCase("jakarta") && sTujuan.equalsIgnoreCase("surabaya")) {
            hargaDewasa = 200000;
            hargaAnak = 150000;
        } else if (sAsal.equalsIgnoreCase("jakarta") && sTujuan.equalsIgnoreCase("purwokerto")) {
            hargaDewasa = 150000;
            hargaAnak = 120000;
        } else if (sAsal.equalsIgnoreCase("jakarta") && sTujuan.equalsIgnoreCase("yogyakarta")) {
            hargaDewasa = 180000;
            hargaAnak = 140000;
        } else if (sAsal.equalsIgnoreCase("bandung") && sTujuan.equalsIgnoreCase("jakarta")) {
            hargaDewasa = 100000;
            hargaAnak = 70000;
        } else if (sAsal.equalsIgnoreCase("bandung") && sTujuan.equalsIgnoreCase("surabaya")) {
            hargaDewasa = 120000;
            hargaAnak = 100000;
        } else if (sAsal.equalsIgnoreCase("bandung") && sTujuan.equalsIgnoreCase("purwokerto")) {
            hargaDewasa = 120000;
            hargaAnak = 90000;
        } else if (sAsal.equalsIgnoreCase("bandung") && sTujuan.equalsIgnoreCase("yogyakarta")) {
            hargaDewasa = 190000;
            hargaAnak = 160000;
        } else if (sAsal.equalsIgnoreCase("surabaya") && sTujuan.equalsIgnoreCase("jakarta")) {
            hargaDewasa = 200000;
            hargaAnak = 150000;
        } else if (sAsal.equalsIgnoreCase("surabaya") && sTujuan.equalsIgnoreCase("bandung")) {
            hargaDewasa = 120000;
            hargaAnak = 100000;
        } else if (sAsal.equalsIgnoreCase("surabaya") && sTujuan.equalsIgnoreCase("purwokerto")) {
            hargaDewasa = 170000;
            hargaAnak = 130000;
        } else if (sAsal.equalsIgnoreCase("surabaya") && sTujuan.equalsIgnoreCase("yogyakarta")) {
            hargaDewasa = 180000;
            hargaAnak = 150000;
        } else if (sAsal.equalsIgnoreCase("purwokerto") && sTujuan.equalsIgnoreCase("jakarta")) {
            hargaDewasa = 150000;
            hargaAnak = 120000;
        } else if (sAsal.equalsIgnoreCase("purwokerto") && sTujuan.equalsIgnoreCase("bandung")) {
            hargaDewasa = 120000;
            hargaAnak = 90000;
        } else if (sAsal.equalsIgnoreCase("purwokerto") && sTujuan.equalsIgnoreCase("yogyakarta")) {
            hargaDewasa = 80000;
            hargaAnak = 40000;
        } else if (sAsal.equalsIgnoreCase("purwokerto") && sTujuan.equalsIgnoreCase("surabaya")) {
            hargaDewasa = 170000;
            hargaAnak = 130000;
        } else if (sAsal.equalsIgnoreCase("yogyakarta") && sTujuan.equalsIgnoreCase("jakarta")) {
            hargaDewasa = 180000;
            hargaAnak = 140000;
        } else if (sAsal.equalsIgnoreCase("yogyakarta") && sTujuan.equalsIgnoreCase("bandung")) {
            hargaDewasa = 190000;
            hargaAnak = 160000;
        } else if (sAsal.equalsIgnoreCase("yogyakarta") && sTujuan.equalsIgnoreCase("purwokerto")) {
            hargaDewasa = 80000;
            hargaAnak = 40000;
        } else if (sAsal.equalsIgnoreCase("yogyakarta") && sTujuan.equalsIgnoreCase("surabaya")) {
            hargaDewasa = 180000;
            hargaAnak = 150000;
        }

        jmlDewasa = Integer.parseInt(sDewasa);
        jmlAnak = Integer.parseInt(sAnak);

        hargaTotalDewasa = jmlDewasa * hargaDewasa;
        hargaTotalAnak = jmlAnak * hargaAnak;
        hargaTotal = hargaTotalDewasa + hargaTotalAnak;
    }
}
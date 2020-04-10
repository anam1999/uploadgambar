package com.example.myapplication98;

public class HitungTukarHadiah {
    //kalo butuh string buat diakses semua pake taroh disini aja
    public String status = "";

    public double poinSementara;

    public String hadiah1(double poinsaya, double hadiah1) {
        if (poinsaya >= hadiah1) {
            //ini data nya dapet dr yg tadi
            poinSementara = poinsaya - hadiah1;
            status = "Hadiah berhasil ditukar";
            //setelah perhitungan data hasilnya di masukin lagi ke data global biar ke ganti
            ModelpPoinHadiah.poin = poinSementara;

        } else {
            status = "Poin anda tidak cukup";
        }
        return (status);
    }

//    public String hadiah2(double poinsaya, double hadiah2) {
//
//        if (poinsaya >= hadiah2) {
//            poinSementara = poinsaya - hadiah2;
//            status = "Hadiah berhasil ditukar";
//           ModelpPoinHadiah.poin = poinSementara;
//        } else {
//            status = "Poin anda tidak cukup";
//        }
//        return (status);
//    }

}


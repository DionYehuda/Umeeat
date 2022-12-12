package id.ac.umn.umeeat;

import java.io.Serializable;

public class User implements Serializable {
    private String email, pass, nama, year, jurusan, uname, desc, gender, key;

    public User()
    {

    }

    public User(String email, String pass, String nama, String year, String jurusan, String uname, String desc, String gender) {
        this.email = email;
        this.pass = pass;
        this.nama = nama;
        this.year = year;
        this.jurusan = jurusan;
        this.uname = uname;
        this.desc = desc;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

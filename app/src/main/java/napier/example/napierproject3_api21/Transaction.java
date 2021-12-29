package napier.example.napierproject3_api21;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Transaction implements Parcelable, Serializable {
    private String amount, type, date, commentary,id,idt,id_account;
    private int iconId;

    public String getAmount() { return this.amount; }
    public String getType() { return this.type; }
    public String getDate() { return this.date; }
    public String getCommentary() { return this.commentary; }
    public int getIconId() { return this.iconId; }
    public String getId() { return this.id; }
    public String getIdt(){ return this.idt; }

    public String getId_account() {
        return id_account;
    }

    public void setAmount(String a) { this.amount = a; }
    public void setType(String t) { this.type = t; }
    public void setDate(String d) { this.date = d; }
    public void setCommentary(String c) { this.commentary = c; }
    public void setIconId(int i) { this.iconId = i; }
    public void setId(String id) { this.id = id; }
    public void setIdt(String idt) { this.idt = idt; }

    public void setId_account(String id_account) {
        this.id_account = id_account;
    }

    public Transaction(String a, String t, String d, String c, int i, String iD, String it, String id_account) {
        setAmount(a);
        setType(t);
        setDate(d);
        setCommentary(c);
        setIconId(i);
        setId(iD);
        setIdt(it);
        setId_account(id_account);
    }

    public Transaction(Transaction p) {
        setAmount(p.amount);
        setType(p.type);
        setDate(p.date);
        setCommentary(p.commentary);
        setIconId(p.iconId);
        setId(p.id);
        setIdt(p.idt);
        setId_account(p.id_account);
    }

    public Transaction(Parcel in) {
        this.amount = in.readString();
        this.type = in.readString();
        this.date = in.readString();
        this.commentary = in.readString();
        this.iconId = in.readInt();
        this.id = in.readString();
        this.idt = in.readString();
        this.id_account = in.readString();
    }

    public static final Parcelable.Creator<Transaction> CREATOR =
            new Parcelable.Creator<Transaction>(){
                public Transaction createFromParcel(Parcel in) {
                    return new Transaction(in);
                }

                public Transaction[] newArray(int size) {
                    return new Transaction[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeString(this.type);
        dest.writeString(this.date);
        dest.writeString(this.commentary);
        dest.writeInt(this.iconId);
        dest.writeString(this.id);
        dest.writeString(this.idt);
        dest.writeString(this.id_account);
    }

}
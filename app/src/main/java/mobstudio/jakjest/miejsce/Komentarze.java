package mobstudio.jakjest.miejsce;

/**
 * Created by Michał on 2016-07-27.
 */
public class Komentarze {

    private int idK;
    private String user;
    private String tekst;
    private String czas;
    private String idUserFB;
    private String gwiazda;

    public int getIdK() {
        return idK;
    }

    public void setIdK(int idK) {
        this.idK = idK;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getCzas() {
        return czas;
    }

    public void setCzas(String czas) {
        this.czas = czas;
    }

    public String getIdUserFB() {
        return idUserFB;
    }

    public void setIdUserFB(String idUserFB) {
        this.idUserFB = idUserFB;
    }

    public String getGwiazda() {
        return gwiazda;
    }

    public void setGwiazda(String gwiazda) {
        this.gwiazda = gwiazda;
    }

    public String getParkiet() {
        return parkiet;
    }

    public void setParkiet(String parkiet) {
        this.parkiet = parkiet;
    }

    public String getTlok() {
        return tlok;
    }

    public void setTlok(String tlok) {
        this.tlok = tlok;
    }

    public String getInKlub() {
        return inKlub;
    }

    public void setInKlub(String inKlub) {
        this.inKlub = inKlub;
    }

    private String parkiet;
    private String tlok;
    private String inKlub;

}

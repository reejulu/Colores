package a.bb.colores;

public class User {
    public String getPulsado() {
        return pulsado;
    }

    @Override
    public String toString() {
        return "User{" +
                "pulsado='" + pulsado + '\'' +
                '}';
    }

    public User(String pulsado) {
        this.pulsado = pulsado;
    }

    public void setPulsado(String pulsado) {
        this.pulsado = pulsado;
    }

    String pulsado;



}

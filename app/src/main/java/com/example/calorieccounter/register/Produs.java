package com.example.calorieccounter.register;

public class Produs {
    private String Nume;
    private int Calorii;

    public Produs(){

    }

    public String getNume(){
        return Nume;
    }
    public void setNume (String nume)
    {
        Nume=nume;
    }
    public int getCalorii(){
        return Calorii;
    }
    public void setCalorii (int calorii)
    {
        Calorii=calorii;
    }
    public String toString()
    {
        return Nume + " " +  Calorii;
    }
}

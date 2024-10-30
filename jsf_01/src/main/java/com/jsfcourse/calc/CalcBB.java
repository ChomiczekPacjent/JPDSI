package com.jsfcourse.calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
public class CalcBB {
    private String x;  // Kwota kredytu w PLN
    private String y;  // Liczba rat (miesiące)
    private String z;  // Oprocentowanie (%)
    private Double result;

    @Inject
    FacesContext ctx;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
    
    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    // Metoda sprawdzająca poprawność danych i wykonująca obliczenia
    public boolean doTheMath() {
        try {
            double x = Double.parseDouble(this.x);
            double y = Double.parseDouble(this.y);
            double z = Double.parseDouble(this.z);

            // Walidacja kwoty kredytu (min. 1000 PLN)
            if (x < 1000) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota musi wynosić co najmniej 1000 PLN.", null));
                return false;
            }

            // Walidacja liczby rat (min. 6 miesięcy)
            if (y < 6) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Liczba rat musi wynosić co najmniej 6 miesięcy.", null));
                return false;
            }

            // Walidacja oprocentowania (min. 1%, max. 100%)
            if (z < 1 || z > 100) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Oprocentowanie musi wynosić od 1% do 100%.", null));
                return false;
            }

            // Obliczenia
            result = x + (x * (z / 100)) / y;

            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
            return true;
        } catch (NumberFormatException e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów. Upewnij się, że wprowadzono poprawne wartości liczbowe.", null));
            return false;
        }
    }

    // Go to "showresult" if ok
    public String calc() {
        if (doTheMath()) {
            return "showresult";
        }
        return null;
    }

    public String info() {
        return "info";
    }
}

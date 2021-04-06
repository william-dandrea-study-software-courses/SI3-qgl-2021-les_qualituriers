package fr.unice.polytech.si3.qgl.qualituriers.utils.helpers;

public class MySquared2DMat {
    private final double a, b, c, d;

    public MySquared2DMat(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public MyColumn2DMat multiply(MyColumn2DMat mat) {
        return new MyColumn2DMat(a * mat.getA() + b * mat.getB(), c * mat.getA() + d * mat.getB());
    }

    public double determinant() {
        return a * d - c * b;
    }

    public MySquared2DMat scalar(double s) {
        return new MySquared2DMat(s * a, s * b, s * c, s * d);
    }

    public MySquared2DMat inverse() throws Exception {
        var det = determinant();
        if(det == 0) throw new Exception("Not inversible");
        else {
            return new MySquared2DMat(d, -b, -c, a).scalar(1 / det);
        }
    }
}

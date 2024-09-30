import java.io.File;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,1,2,5};
        int[] e1 = {5, 0, 10, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {6, -1, -2, -5, 4};
        int[] e2 = {5, 0, 10, 3, 2};
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(2));
        for (int i = 0; i < s.coefficients.length; i++){
            System.out.println(s.coefficients[i] + ", " + s.exponents[i]);
        }
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        double[] tc2 = {1, 1};
        double[] tc1 = {-1, -2};
        int[] te2 = {1, 2};
        int[] te1 = {1, 2};

        Polynomial t = new Polynomial(tc1, te1);
        Polynomial t2 = new Polynomial(tc2, te2);
    
        Polynomial m = t.multiply(t2);
        for (int i = 0; i < m.coefficients.length; i++){
            System.out.println(m.coefficients[i] + "x^" + m.exponents[i]);
        }

        m.saveToFile("testFile.txt");

        File file_name = new File("testFile.txt");
        Polynomial fily = new Polynomial(file_name);
        for (int i = 0; i < fily.coefficients.length; i++){
            System.out.println("this one" + fily.coefficients[i] + ", " + fily.exponents[i]);
        }
    }

}
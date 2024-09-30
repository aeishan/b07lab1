import java.util.HashMap;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Polynomial{
    double[] coefficients;
    int[] exponents;

    public Polynomial (){
        coefficients = new double[0];
        exponents = new int[0];
    }

    public Polynomial(double[] arr, int[] arr2){
        coefficients = arr;
        exponents = arr2;
    }

    public Polynomial(File name){
        HashMap<Integer, Integer> hmap = new HashMap<>();

        try{
            Scanner input = new Scanner(name);
            String line = input.next();
            int count = 0;

            for (int i = 0; i < line.length(); i++){
                if (line.charAt(i) == '-' && i == 0){
                    hmap.put(count, count);
                }
                else if (line.charAt(i) == '-'){
                    count++;
                    hmap.put(count, count);
                }
                else if (line.charAt(i) == '+'){
                    count++;
                }
            }

            String[] terms = line.split("\\+|\\-");

            int temp_size = 0;
            if (terms[0] == ""){
                temp_size = terms.length - 1;
            }
            else{
                temp_size = terms.length;
            }

            coefficients = new double[temp_size];
            exponents = new int[temp_size];
            int temp_count = 0;


            for (int i = 0; i < terms.length; i++){
                if (i ==0 && terms[i]==""){
                    terms[1] = "-" + terms[1];
                    temp_count = 1;
                    continue;
                }

                if ((hmap.containsKey(i-1) && temp_count == 1 && i != 1) || (hmap.containsKey(i) && temp_count == 0)){
                    terms[i] = "-" + terms[i];
                }

                String[] values = terms[i].split("x");

                if (terms[0] == ""){
                    if (values.length == 1){
                        coefficients[i-1] = Double.parseDouble(values[0]);
                        exponents[i-1] = 0;
                    }
                    else{
                        coefficients[i-1] = Double.parseDouble(values[0]);
                        exponents[i-1] = Integer.parseInt(values[1]);
                    }
                }
                else{
                    if (values.length == 1){
                        coefficients[i] = Double.parseDouble(values[0]);
                        exponents[i] = 0;
                    }
                    else{
                        coefficients[i] = Double.parseDouble(values[0]);
                        exponents[i] = Integer.parseInt(values[1]);
                    }
                }
            }
            input.close();
        }
        catch(FileNotFoundException e){  // e is the error
            System.out.println("File not found: " + e.getMessage());  // this gets the error message
        }
    }

    public Polynomial add(Polynomial polyy){
        HashMap<Integer, Double> new_poly = new HashMap<>();

        for (int i = 0; i < this.coefficients.length; i++){
            new_poly.put(this.exponents[i], this.coefficients[i]);
        } 

        for (int j = 0; j < polyy.coefficients.length; j++){
            if (new_poly.containsKey(polyy.exponents[j])){
                double new_num = new_poly.get(polyy.exponents[j]) + polyy.coefficients[j];

                if (new_num == 0){
                    new_poly.remove(polyy.exponents[j]);
                }
                else{
                    new_poly.put(polyy.exponents[j], new_num);
                }
            }
            else{
                 new_poly.put(polyy.exponents[j], polyy.coefficients[j]);
            }
        }

        int size = new_poly.size();
        double[] final_coef = new double[size];
        int[] final_exp = new int[size];
        int index = 0;

        for (Integer key : new_poly.keySet()){
            final_exp[index] = key;
            final_coef[index] = new_poly.get(key);
            index++;
        }

        Polynomial final_poly = new Polynomial(final_coef, final_exp);
        return final_poly;
    }

    public double evaluate(double x){
        double sum = 0;

        for (int i = 0; i < this.coefficients.length; i++){
            double exponent = Math.pow(x, this.exponents[i]);
            sum = sum + (this.coefficients[i] * exponent);
        }

        return sum;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial poly){
        if (poly.coefficients.length == 0){
            return this;
        }
        else if (this.coefficients.length == 0){
            return poly;
        }

        HashMap<Integer, Double> new_poly = new HashMap<>();

        for (int j = 0; j < poly.coefficients.length; j++){  // go thru polynomials and multiply their coeffieicients and exponents
            for (int e = 0; e < this.coefficients.length; e++){
                int new_exp = 0;
                if (poly.exponents[j] == 0){
                    new_exp = this.exponents[e];
                }
                else if (this.exponents[e] == 0){
                    new_exp = poly.exponents[j];
                }
                else{
                    new_exp = poly.exponents[j] + this.exponents[e];
                }
                double new_coef = poly.coefficients[j] * this.coefficients[e];  // coefficients WILL NEVER be 0

                if (new_poly.containsKey(new_exp)){  // if the exponent is already in the hashmap, collect like terms
                    double new_new_coef = new_coef + new_poly.get(new_exp);
                    new_poly.put(new_exp, new_new_coef);
                }
                else{
                    new_poly.put(new_exp, new_coef);
                }
            }
        }

        int size = new_poly.size();
        double[] final_coef = new double[size];
        int[] final_exp = new int[size];
        int index = 0;

        for (Integer key : new_poly.keySet()){ // create final polynomial
            final_exp[index] = key;
            final_coef[index] = new_poly.get(key);
            index++;
        }

        Polynomial final_poly = new Polynomial(final_coef, final_exp);
        return final_poly;
    }

    public void saveToFile(String name){
        String final_string = "";
        File file_name = new File(name);

        for (int i = 0; i < this.coefficients.length; i++){
            if (i == 0){
                final_string = this.coefficients[i] + "x" + this.exponents[i];
            }
            else if (this.coefficients[i] > 0){
                final_string = final_string + "+" + this.coefficients[i] + "x" + this.exponents[i]; 
            }
            else{
                final_string = final_string + this.coefficients[i] + "x" + this.exponents[i]; 
            }
        }

        try{
            PrintStream output = new PrintStream(file_name);
            output.printf("%s",final_string);
            output.close();
        }
        catch(FileNotFoundException e){  // e is the error
            System.out.println("File not found: " + e.getMessage());  // this gets the error message
        }
    }

}


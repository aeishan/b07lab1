public class Polynomial{
    double[] coefficients;

    public Polynomial (){
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double[] arr){
        coefficients = new double[arr.length];

        for (int i = 0; i < arr.length; i++){
            coefficients[i] = arr[i];
        }
    }

    public Polynomial add(Polynomial polyy){
        if (polyy.coefficients.length >= this.coefficients.length){
            double[] new_nums = new double[polyy.coefficients.length];

            for (int i = 0; i < this.coefficients.length; i++){
                new_nums[i] = this.coefficients[i] + polyy.coefficients[i];
            }

            for (int j = this.coefficients.length; j < polyy.coefficients.length; j++){
                new_nums[j] = polyy.coefficients[j];
            }

            Polynomial new_one = new Polynomial(new_nums);
            return new_one;
        }

        else{
           double[] new_nums = new double[this.coefficients.length];

            for (int i = 0; i < polyy.coefficients.length; i++){
                new_nums[i] = this.coefficients[i] + polyy.coefficients[i];
            }

            for (int j = polyy.coefficients.length; j < this.coefficients.length; j++){
                new_nums[j] = this.coefficients[j];
            }
            
            Polynomial new_one = new Polynomial(new_nums);
            return new_one;
        }
    }

    public double evaluate(double x){
        double sum = 0;

        for (int i = 0; i < this.coefficients.length; i++){
            double exponent = Math.pow(x, i);
            sum = sum + (this.coefficients[i] * exponent);
        }

        return sum;
    }

    public boolean hasRoot(double x){
        if (evaluate(x) == 0){
            return true;
        }

        return false;
    }

}


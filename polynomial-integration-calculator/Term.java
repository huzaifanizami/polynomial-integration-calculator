public class Term implements Comparable<Term> {
    
    int coefficient = 0;
    int exponent = 0;
    int coefficientDenom = 1;
    
    boolean isTrig = false;
    String trigType = null;  
    int trigCoefficient = 1;  
    
    
    public Term(){}
    
    public Term(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }
    
    // accessors and mutators for isTrig, trigType, trigCoefficient
    
    public void setIsTrig(boolean isTrig){
    this.isTrig = isTrig;
    }
    public boolean getIsTrig(){
        return isTrig;
    }
    
    public void setTrigType(String trigType){
        this.trigType = trigType;
    }
    public String getTrigType(){
        return trigType;
    }
    
    public void setTrigCoefficient(int trigCoefficient){
        this.trigCoefficient = trigCoefficient;
    }
    public int getTrigCoefficient(){
        return trigCoefficient;
    }
    
    
    // accessors and mutators for coefficient
    public void setCoefficient(int coefficient){
        this.coefficient = coefficient;
    }
    public int getCoefficient(){
        return coefficient;
    }
    
    // accessors and mutators for coefficient denominator
    public void setCoefficientDenom(int coefficientDenom){
        this.coefficientDenom = coefficientDenom;
    }
    public int getCoefficientDenom(){
        return coefficientDenom;
    }
    
    // accessors and mutators for exponent
    public void setExponent(int exponent){
        this.exponent = exponent;
    }
    public int getExponent(){
        return exponent;
    }
    
    // calculates the antiderivative of the term 
    public void antiderivative() {
        if (isTrig){
            antiderivativeTrig();
        }
        else{
            int newExp = this.exponent + 1;
            this.exponent = newExp;
            this.coefficientDenom = newExp;
            
            if (this.coefficientDenom < 0) {
                this.coefficient = -this.coefficient;
                this.coefficientDenom = -this.coefficientDenom;
            }
        }
    }
    
    public void antiderivativeTrig(){
        // derivates respective to sin and cos 
        if (trigType.equals("sin")) {
            trigType = "cos";
            coefficient = -coefficient;  // 
        }
        else if (trigType.equals("cos")) {
            trigType = "sin";
        }
        
        // Denominator becomes the inner coefficient
        coefficientDenom = trigCoefficient;
    }
    
    // convert the data to string 
    @Override 
    public String toString() {
        
        if (isTrig) {
            return toStringTrig();
        }
        
        // fraction simplification
        int gcd = gcd(Math.abs(coefficient), Math.abs(coefficientDenom));
        int simpCoef = coefficient / gcd;
        int simpDenom = coefficientDenom / gcd;
        
        String result = "";
        
        // Handle coefficient and fraction
        if (simpDenom != 1) {
            result = result + "(" + Math.abs(simpCoef) + "/" + simpDenom + ")";
        } 
        else {
            // Constant term, always show coefficient
            if (exponent == 0) {
                result = result + Math.abs(simpCoef);
            } else if (Math.abs(simpCoef) != 1) {
                result = result + Math.abs(simpCoef);
            }
        }
        
        // Handle x and exponent
        if (exponent != 0) {
            result = result + "x";
            if (exponent != 1) {
                result = result + "^" + exponent;
            }
        }
        
        return result;
    }
    
    private String toStringTrig() {
        int gcd = gcd(Math.abs(coefficient), Math.abs(coefficientDenom));
        int simpCoef = coefficient / gcd;
        int simpDenom = coefficientDenom / gcd;
        
        String result = "";
        
        // Handle fraction/coefficient
        if (simpDenom != 1) {
            result = "(" + simpCoef + "/" + simpDenom + ")";
        } else if (Math.abs(simpCoef) != 1) {
            result = simpCoef + "";
        }

        // adding a space
        result += trigType + " ";
        
        // Add inner coefficient if not 1
        if (trigCoefficient != 1) {
            result += trigCoefficient;
        }
        
        // Add x
        result += "x";
        
        return result;
    }
    
    
    // GCD helper method
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    
    public int compareTo(Term compareTerm){
        // conditional statements make sure every time a trig term is greater than any other term so it is pushed to the max index or position 
        if (this.isTrig && !compareTerm.isTrig) {
            return -1;
        }
        
        if (!this.isTrig && compareTerm.isTrig) {
            return 1;
        }
        
        if (this.isTrig && compareTerm.isTrig) {
            int typeCompare = this.trigType.compareTo(compareTerm.trigType);
            if (typeCompare != 0) return typeCompare;
            return this.trigCoefficient - compareTerm.trigCoefficient;
        }
    
        
        return exponent - compareTerm.getExponent();
    }
    
    
      

}   


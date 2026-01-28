// Huzaifa Ahmad Nizami
// HXN230018
import java.util.*;
import java.io.*;

public class Main {
    
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Scanner inFS = null;
        Integer lowerLimit = null, upperLimit = null;
        // opening the file and reading lines from the file  
        String fileName = scnr.next();
        
        try {
            FileInputStream fis = new FileInputStream(fileName);
            inFS = new Scanner(fis);
        } catch (FileNotFoundException e) {
            System.out.println("Error! File not found: " + fileName);
            return;
        }
        
        while (inFS.hasNextLine()) {
            BinTree<Term> polynomialTree = new BinTree<>();
            
            String line = inFS.nextLine();
            
            // Skip empty lines
            if (line == null || line.trim().isEmpty()) {
                continue;  
            }
            
            // Find the first space after '|' and "dx"
            int firstSpace = line.indexOf(' ');
            int dxIndex = line.indexOf("dx");
            
            try{
                // Extract bounds, if definite integral
                String boundsSection = line.substring(0, firstSpace);
                boundsSection = boundsSection.replace(" ","");
                
                if (boundsSection.charAt(0) != '|') {
                    // Definite integral 
                    String[] bounds = boundsSection.split("\\|");
                    lowerLimit = Integer.parseInt(bounds[0].trim());
                    upperLimit = Integer.parseInt(bounds[1].trim());
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Error: Invalid boundary values in line: " + line);
                // Skip this line
                continue;  
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Malformed boundary format in line: " + line);
                // Skip this line
                continue;  
            }

            // Extract the expression part
            String expression = line.substring(firstSpace + 1, dxIndex).trim();
            
            // Remove ALL spaces from the expression
            expression = expression.replace(" ", "");
            
            
            // parse the expression 
            int start = 0;
            while (start < expression.length()) {
                int termEnd = start + 1;
                
                // Find the end of this term
                while (termEnd < expression.length()) {
                    char c = expression.charAt(termEnd);
                    
                    // If we find + or -, check if it's part of an exponent
                    if (c == '+' || c == '-') {
                        // Check if previous character is '^' 
                        if (termEnd > 0 && expression.charAt(termEnd - 1) != '^') {
                            // break if it is a real term separator, not part of the exponent
                            break;
                        }
                    }
                    termEnd++;
                }
                
                // Extract the term from the expression 
                String termStr = expression.substring(start, termEnd);
                
                // Parse this term
                Term newTerm = parseTerm(termStr);
                polynomialTree.insert(new Node<>(newTerm));
                
                // Move to the next term
                start = termEnd;
            }
            ArrayList<Term> terms = new ArrayList<>();
            polynomialTree.inOrderTraversal(terms);
        
            displayAntiDerivative(terms);
            if (lowerLimit == null && upperLimit == null){
                System.out.println(" + C");
            }
            else{
                System.out.println(", " + lowerLimit + "|" + upperLimit + " = " + 
                String.format("%.3f", evaluateIntegral(terms, upperLimit, lowerLimit)));            
            }
        }
        
        inFS.close();
        scnr.close();
    }
    
    // function to parse a single term into exponents and coefficients
    public static Term parseTerm(String termStr) {
        Term term = new Term();
        int coefficient = 1;
        int exponent = 1;
        
        if (termStr.contains("sin") || termStr.contains("cos")) {
            return parseTrigTerm(termStr);
        }
        
        try{
            // Find where 'x' is (if it exists)
            int xIndex = termStr.indexOf('x');
            
            if (xIndex == -1) {
                // No 'x' means it's a constant term
                coefficient = Integer.parseInt(termStr);
                exponent = 0;
            } else {
                
                // Get coefficient 
                if (xIndex == 0) {
                    // Term starts with x
                    coefficient = 1;
                } else if (xIndex == 1 && (termStr.charAt(0) == '+' || termStr.charAt(0) == '-')) {
                    // Term is like +x or -x
                    coefficient = termStr.charAt(0) == '-' ? -1 : 1;
                } else {
                    // Term has explicit coefficient 
                    String coeffStr = termStr.substring(0, xIndex);
                    coefficient = Integer.parseInt(coeffStr);
                }
                
                // Find where ^ is, if it exists
                int caretIndex = termStr.indexOf('^');
                
                if (caretIndex == -1) {
                    // No ^ means exponent is 1 
                    exponent = 1;
                } else {
                    // Get exponent part 
                    String expStr = termStr.substring(caretIndex + 1);
                    exponent = Integer.parseInt(expStr);
                }
            }
            
            term.setCoefficient(coefficient);
            term.setExponent(exponent);
            term.setCoefficientDenom(1);
        } 
        catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in term: " + termStr);
            return null;
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Error: Malformed term: " + termStr);
            return null;
        }
        
        return term;
    }
    
    public static Term parseTrigTerm(String termStr) {
        Term term = new Term();
        term.setIsTrig(true);
        
        // Find sin or cos
        int sinIndex = termStr.indexOf("sin");
        int cosIndex = termStr.indexOf("cos");
        
        if (sinIndex != -1) {
            term.setTrigType("sin");
            
            // Parse outer coefficient 
            if (sinIndex == 0) {
                term.setCoefficient(1);
            } else if (sinIndex == 1 && termStr.charAt(0) == '-') {
                term.setCoefficient(-1);
            } else if (sinIndex == 1 && termStr.charAt(0) == '+') {
                term.setCoefficient(1);
            } else {
                String coeffStr = termStr.substring(0, sinIndex);
                term.setCoefficient(Integer.parseInt(coeffStr));
            }
            
            // Parse inner coefficient
            String innerPart = termStr.substring(sinIndex + 3); 
            innerPart = innerPart.replace("x", "");
            
            if (innerPart.isEmpty()) {
                term.setTrigCoefficient(1);
            } else {
                term.setTrigCoefficient(Integer.parseInt(innerPart));
            }
            
        } else if (cosIndex != -1) {
            term.setTrigType("cos");
            
            // Parse outer coefficient 
            if (cosIndex == 0) {
                term.setCoefficient(1);
            } else if (cosIndex == 1 && termStr.charAt(0) == '-') {
                term.setCoefficient(-1);
            } else if (cosIndex == 1 && termStr.charAt(0) == '+') {
                term.setCoefficient(1);
            } else {
                String coeffStr = termStr.substring(0, cosIndex);
                term.setCoefficient(Integer.parseInt(coeffStr));
            }
            
            // Parse inner coefficient 
            String innerPart = termStr.substring(cosIndex + 3); 
            innerPart = innerPart.replace("x", "");
            
            if (innerPart.isEmpty()) {
                term.setTrigCoefficient(1);
            } else {
                term.setTrigCoefficient(Integer.parseInt(innerPart));
            }
        }
        
        term.setCoefficientDenom(1);
        // Trig terms don't have exponent
        term.setExponent(0); 
        
        return term;
    }
    
    
    // calculates the term with the limit substituted 
    public static double evaluateTerm(Term term, int limit){
        return (term.getCoefficient() * Math.pow(limit, term.getExponent())) / term.getCoefficientDenom();
    }
   
    // calculates the definite integral with the upper and lower limits and subtract lower from the upper to return the answer
    public static double evaluateIntegral(ArrayList<Term> terms, int upperLimit, int lowerLimit){
        double totalUpper = 0;
        double totalLower = 0;
        for (Term term:terms){
            totalUpper += evaluateTerm(term, upperLimit);
        }
        for (Term term:terms){
            totalLower += evaluateTerm(term, lowerLimit);
        }
        return (totalUpper - totalLower);
    }
    
    public static void displayAntiDerivative(ArrayList<Term> terms) {
        
        // calculate the antiderivative for each of the term  
        for (Term term : terms) {
            term.antiderivative();
        }
        
        String output = "";
        boolean first = true;
        
        // Printing out the final expression
        
        for (int i = terms.size() - 1; i >= 0; i--) {
            Term term = terms.get(i);
            
            // 0 coefficient encountered
            if (term.getCoefficient() == 0 && !first){
                continue;
            }
            
            // 0 coefficient encountered in the first term             
            if (term.getCoefficient() == 0 && first) {
                output = output + "0";
                first = false;
                continue;
            }
            
            // new term to prevent manipulation with the original term so the calculations for the definite integral are secure 
            int displayCoeff = term.getCoefficient();
            
            // create a string which has a the integrated equation 
            if (!first) {
                if (term.getCoefficient() > 0) {
                    output = output + " + ";
                } else {
                    output = output + " - ";
                        displayCoeff = -displayCoeff;  
                }
            } else {
                if (displayCoeff == 0){
                    
                }
                if (displayCoeff < 0 && term.getCoefficientDenom() == 1) {
                    output = output + "-";
                    displayCoeff = -displayCoeff;
                }
                first = false;
            }
            
            Term displayTerm = new Term(displayCoeff, term.getExponent());
            displayTerm.setCoefficientDenom(term.getCoefficientDenom());
            
            if (term.getIsTrig()) {
                displayTerm.setIsTrig(true);
                displayTerm.setTrigType(term.getTrigType());
                displayTerm.setTrigCoefficient(term.getTrigCoefficient());
            }
            
            output = output + displayTerm.toString();
        }
        
        System.out.print(output);
    }
}
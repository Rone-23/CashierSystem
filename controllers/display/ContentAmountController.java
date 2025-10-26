package controllers.display;

public class ContentAmountController {
    private final StringBuilder content = new StringBuilder("0");

    public String getContent() {
        try {
            float contentNumber = Float.parseFloat(content.toString());
            return String.format("%.2f",contentNumber/100);
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    public void appendContent(String text) {
        if(content.length()>6){
            throw new ArithmeticException("Maximal allowed digit is 7.");
        }
        content.append(text);
    }

    public void clearContent() {
        content.setLength(0);
    }

    public void removeLast(){
        if(!content.isEmpty()){
            content.setLength(content.length()-1);
        }
    }

}

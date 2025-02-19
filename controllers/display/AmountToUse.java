package controllers.display;

public class AmountToUse {
    private final StringBuilder content = new StringBuilder();

    public String getContent() {
        return content.toString();
    }

    public void appendContent(String text) {
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

package patterns.visitor.node;

import patterns.visitor.visitor.Visitor;

public class TextNode implements Node{

    private StringBuilder text = new StringBuilder();

    public TextNode(String initialText) {
        text.append(initialText);
    }

    public String getText() {
        return text.toString();
    }

    public void appendText(String s) {
        text.append(s);
    }

    @Override
    public void accept(Visitor v) {
        v.visitTextNode(this);
    }
}

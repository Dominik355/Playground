package patterns.visitor.visitor;

import patterns.visitor.node.*;

public class WordCountVisitor implements Visitor {

    int wordCount = 0;

    @Override
    public void visitTextNode(TextNode textNode) {
        wordCount += wordCount(textNode.getText());
    }

    public int getWordCount() {
        return wordCount;
    }

    private int wordCount(String text) {
        if (text == null || (text = text.trim()).length() == 0)
            return 0;
        // Replace all non-space chars with nothing;
        // add one because "hello word" has one space, is two words.
        return text.trim().replaceAll("[^\\s]", "").length() + 1;
    }

    @Override
    public void visitImageNode(ImageNode imageNode) {
        // You might say there's nothing to do, but let's try this:
        if (imageNode.getCaption() != null) {
            visitTextNode(imageNode.getCaption());
        }
    }

}
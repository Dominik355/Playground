package patterns.visitor.visitor;

import patterns.visitor.node.*;

public interface Visitor {

    void visitTextNode(TextNode textNode);
    void visitImageNode(ImageNode textNode);

}

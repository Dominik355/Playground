package patterns.visitor.node;

import patterns.visitor.visitor.Visitor;

public interface Node {

    void accept(Visitor v);

}

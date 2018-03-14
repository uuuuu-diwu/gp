package Parser;

import java.util.ArrayList;

public class Node {
    private ArrayList<Node>     children_;
    private ArrayList<Vocab>    vocabs_;
    private String              title_;
    private Node                 parent_;
    private int                 myIndex_;

    public Node(String title) {
        this.children_ = new ArrayList<Node>();
        this.vocabs_ = new ArrayList<Vocab>();
        this.title_ = new String(title);
        parent_ = null;
        this.myIndex_ = -1 ;
    }

    public void insertChild(Node child) {
        this.children_.add(child);
        child.setMyIndex(children_.size() - 1);
        child.setParent(this);
    }

    public ArrayList<Node> getChildren() {
        return children_;
    }

    public void insertVocab(Vocab v) {
        vocabs_.add(v);
        v.parent = this;
    }

    public ArrayList<Vocab> getVocabs() {
        return vocabs_;
    }

    public void setMyIndex(int index) {
        this.myIndex_ = index;
    }
    public void setParent(Node parent) {
        this.parent_ = parent;
    }
    public int getIndex() {
        return myIndex_;
    }
    public String getTitle() {
        return title_;
    }
    public Node getParent() {
        return parent_;
    }
}

class Test {
    public static void  main(String[] argv) {
        Node tree = new Node("病");
        Node n1 = new Node("概述");
        Node n2 = new Node("临床表现");
        Node n3 = new Node("诊断要点");
        Node n4 = new Node("治疗方案");
        tree.insertChild(n1);
        tree.insertChild(n2);
        tree.insertChild(n3);
        tree.insertChild(n4);

    }
}

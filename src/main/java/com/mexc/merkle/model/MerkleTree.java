package com.mexc.merkle.model;

import java.util.List;


public class MerkleTree {

    private MerkleSelfNode self;

    private List<MerklePathNode> pathList;

    public MerkleSelfNode getSelf() {
        return self;
    }

    public void setSelf(MerkleSelfNode self) {
        this.self = self;
    }

    public List<MerklePathNode> getPathList() {
        return pathList;
    }

    public void setPathList(List<MerklePathNode> pathList) {
        this.pathList = pathList;
    }
}

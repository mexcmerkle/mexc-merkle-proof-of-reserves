package com.mexc.merkle.util;

import com.mexc.merkle.model.MerklePathNode;
import com.mexc.merkle.model.MerkleSelfNode;
import com.mexc.merkle.model.MerkleTree;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MerkleProofValidator {

    public static final int LEFT_TYPE = 1;
    public static final int RIGHT_TYPE = 2;
    public static final int ROOT_TYPE = 3;

    public static boolean validate(MerkleTree merkleTree) {
        if (Objects.isNull(merkleTree)) {
            throw new IllegalArgumentException("merkleTree not null");
        }
        List<MerklePathNode> pathNodeList = merkleTree.getPathList();
        if (Objects.isNull(merkleTree.getSelf()) || Objects.isNull(pathNodeList) || pathNodeList.isEmpty()) {
            throw new IllegalArgumentException("invalid merkleTree content");
        }

        if (!merkleTree.getSelf().validate()) {
            System.out.println("self validate failure");
            return false;
        }

        if (!pathValidate(merkleTree.getPathList(), merkleTree.getSelf())) {
            System.out.println("path validate failure");
            return false;
        }
        return true;
    }

    private static boolean pathValidate(List<MerklePathNode> pathNodeList, MerkleSelfNode selfNode) {
        MerklePathNode rootNode = pathNodeList.get(pathNodeList.size() - 1);

        int depth = rootNode.getLevel();
        Pair<MerklePathNode, MerklePathNode> nodePair = null;
        //key=level，value= pathNode
        Map<Integer, MerklePathNode> levelNodeMap = pathNodeList.stream().collect(Collectors.toMap(MerklePathNode::getLevel, merklePathNode -> merklePathNode));
        MerklePathNode clcInnerNode = selfNode;
        for (int i = 0; i < depth; i++) {
            MerklePathNode node = levelNodeMap.get(i);
            if (Objects.isNull(node)) {
                node = createEmptyNode(clcInnerNode.getType() == LEFT_TYPE ? RIGHT_TYPE : LEFT_TYPE, clcInnerNode.getLevel());
            }
            nodePair = decideNodePair(clcInnerNode, node);
            int parentLevel = i + 1;
            clcInnerNode = createInnerNode(nodePair.getLeft(), nodePair.getRight(), getParentNodeType(levelNodeMap.get(parentLevel)), parentLevel);
        }
        if (Objects.isNull(nodePair)) {
            System.out.println("validate error");
            return false;
        }
        MerklePathNode clcRootNode = createInnerNode(nodePair.getLeft(), nodePair.getRight(), ROOT_TYPE, rootNode.getLevel());
        return clcRootNode.getHash().equals(rootNode.getHash());
    }

    private static Pair<MerklePathNode, MerklePathNode> decideNodePair(MerklePathNode currentNode, MerklePathNode brotherNode) {
        return currentNode.getType() == LEFT_TYPE ? Pair.of(currentNode, brotherNode) : Pair.of(brotherNode, currentNode);
    }

    private static int getParentNodeType(MerklePathNode parentNodeBrother) {
        if (Objects.isNull(parentNodeBrother)) {
            return LEFT_TYPE;
        } else {
            return parentNodeBrother.getType() == LEFT_TYPE ? RIGHT_TYPE : LEFT_TYPE;
        }
    }

    /***
     * calculate internal parent nodes through left and right nodes
     */
    public static MerklePathNode createInnerNode(MerklePathNode leftNode, MerklePathNode rightNode, int type, int level) {
        MerklePathNode merklePathNode = new MerklePathNode();
        merklePathNode.setBalances(new TreeMap<>());
        merklePathNode.mergeBalance(leftNode);
        if (StringUtils.isNotBlank(rightNode.getHash())) {
            initBalanceData(merklePathNode);
            merklePathNode.mergeBalance(rightNode);
        }
        merklePathNode.setLevel(level);
        merklePathNode.setType(type);
        merklePathNode.calcHashId(leftNode.getHash(), rightNode.getHash());
        return merklePathNode;
    }

    public static MerklePathNode createEmptyNode(int type, int level) {
        MerklePathNode merklePathNode = new MerklePathNode();
        merklePathNode.setBalances(new TreeMap<>());
        merklePathNode.setLevel(level);
        merklePathNode.setType(type);
        merklePathNode.setHash("");
        return merklePathNode;
    }

    private static void initBalanceData(MerklePathNode merklePathNode) {
        merklePathNode.getBalances().putIfAbsent("BTC", BigDecimal.ZERO.toPlainString());
        merklePathNode.getBalances().putIfAbsent("USDT", BigDecimal.ZERO.toPlainString());
        merklePathNode.getBalances().putIfAbsent("ETH", BigDecimal.ZERO.toPlainString());
        merklePathNode.getBalances().putIfAbsent("USDC", BigDecimal.ZERO.toPlainString());
    }
}

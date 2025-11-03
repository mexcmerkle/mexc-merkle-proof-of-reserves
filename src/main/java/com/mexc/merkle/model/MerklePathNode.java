package com.mexc.merkle.model;

import com.mexc.merkle.util.BigDecimalUtils;
import com.mexc.merkle.util.SignatureUtil;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.SortedMap;

public class MerklePathNode {

    private Integer level;
    /**
     * node type: 1/leftNode 2/rigthNode 3/rootNode
     */
    private Integer type;
    /**
     * node hash
     */
    private String hash;
    /**
     * asset：key=currency value= amount
     */
    private SortedMap<String, String> balances;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public SortedMap<String, String> getBalances() {
        return balances;
    }

    public void setBalances(SortedMap<String, String> balances) {
        this.balances = balances;
    }

    public void mergeBalance(MerklePathNode merklePathNode) {
        if (Objects.isNull(merklePathNode) || Objects.isNull(merklePathNode.getBalances()) || merklePathNode.getBalances().size() == 0) {
            return;
        }
        merklePathNode.getBalances().forEach((key, value) -> {
            String existValue = this.balances.get(key);
            BigDecimal sumBigDecimal = BigDecimalUtils.safeSumBigDecimal(BigDecimalUtils.parseString(existValue), BigDecimalUtils.parseString(value));
            this.balances.put(key, sumBigDecimal.toPlainString());
        });
    }

    /**
     * calculate node hashId
     *
     * @param leftHash:  left node hash
     * @param rightHash: right nodehash
     */
    public String calcHashId(String leftHash, String rightHash) {
        StringBuilder balanceSb = new StringBuilder();
        this.getBalances().forEach((token, balance) -> balanceSb.append(token).append(":").append(BigDecimalUtils.getBigDecimalPlainStr(balance)).append(","));
        if (balanceSb.length() > 1) {
            balanceSb.setLength(balanceSb.length() - 1);
        }
        String content = leftHash + "," + rightHash + "," + balanceSb;
        String hashId = SignatureUtil.genHashId(content);
        this.setHash(hashId);
        return hashId;
    }

}

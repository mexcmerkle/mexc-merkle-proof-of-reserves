package com.mexc.merkle.model;

import com.mexc.merkle.util.SignatureUtil;

import java.util.Objects;

public class MerkleSelfNode extends MerklePathNode {

    private String proofId;

    private String accountId;

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean validate() {
        if (Objects.isNull(this.getBalances())) {
            System.out.println("invalid balance data");
            return false;
        }
        StringBuilder balanceSb = new StringBuilder();
        this.getBalances().forEach((token, balance) -> balanceSb.append(token).append(":").append(balance).append(","));
        if (balanceSb.length() > 1) {
            balanceSb.setLength(balanceSb.length() - 1);
        }
        String content = this.getProofId() + "," + this.getAccountId() + "," + balanceSb;
        return SignatureUtil.genHashId(content).equals(this.getHash());
    }

}

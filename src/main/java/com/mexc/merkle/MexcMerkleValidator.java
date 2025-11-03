package com.mexc.merkle;

import com.alibaba.fastjson.JSON;
import com.mexc.merkle.model.MerkleTree;
import com.mexc.merkle.util.MerkleProofValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MexcMerkleValidator {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("param error");
        }
        String jsonFile = args[0];
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get(jsonFile));
        } catch (IOException e) {
            throw new RuntimeException("can not found json file: " + jsonFile);
        }
        String pathJson = new String(bytes);
        MerkleTree merkleTree = null;
        try {
            merkleTree = JSON.parseObject(pathJson, MerkleTree.class);
        } catch (Exception e) {
            throw new RuntimeException("json content is error");
        }
        boolean validate = MerkleProofValidator.validate(merkleTree);
        System.out.println("validate result is : " + validate);
    }
}

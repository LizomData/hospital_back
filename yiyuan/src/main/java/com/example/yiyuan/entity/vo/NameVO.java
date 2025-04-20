package com.example.yiyuan.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class NameVO {
    private List<Node> nodes;
    private List<Link> links;

    @Data
    public static class Node {
        private String name;

        // 显式添加无参构造函数
        public Node() {}

        // 也可以添加一个带参数的构造函数
        public Node(String name) {
            this.name = name;
        }
    }

    @Data
    public static class Link {
        private String source;
        private String target;
        private int value;

        // 显式添加无参构造函数
        public Link() {}

        // 也可以添加一个带参数的构造函数
        public Link(String source, String target, int value) {
            this.source = source;
            this.target = target;
            this.value = value;
        }
    }
}
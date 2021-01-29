package com.github.fansq.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation
 */
@Data
public class TestMessage implements Serializable {
    private String message;
}

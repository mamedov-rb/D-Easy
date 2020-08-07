package com.rmamedov.deasy.goodsservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodClickMessage {

    private String category;

    private String goodId;

    private String userAgent;

    private String clientIp;

}

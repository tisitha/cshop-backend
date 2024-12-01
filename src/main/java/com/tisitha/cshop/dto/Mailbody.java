package com.tisitha.cshop.dto;

import lombok.Builder;

@Builder
public record Mailbody(String to,String subject, String text) {
}

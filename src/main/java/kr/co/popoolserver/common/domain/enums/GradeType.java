package kr.co.popoolserver.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GradeType {

    Diamond("다이아"),
    Platinum("플레티넘"),
    GOLD("골드"),
    SILVER("실버"),
    WHITE("미정");

    String grade;

    public static GradeType of(String grade){
        return Arrays.stream(GradeType.values())
                .filter(g -> g.toString().equalsIgnoreCase(grade))
                .findAny().orElseThrow(() -> new RuntimeException("해당 등급은 존재하지 않습니다."));
    }
}

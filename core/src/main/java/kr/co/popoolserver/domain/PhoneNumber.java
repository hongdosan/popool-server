package kr.co.popoolserver.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class PhoneNumber {

    private static final String DELIMITER = "-";
    private static final String EMPTY = "";

    @ApiModelProperty(example = "010-XXXX-XXXX")
    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber toCompactNumber(){
        return new PhoneNumber(phoneNumber.replaceAll(DELIMITER, EMPTY));
    }

    public PhoneNumber toFullNumber(){
        if(phoneNumber.contains(DELIMITER)) return this;
        StringBuilder stringBuilder = new StringBuilder(phoneNumber);

        final int len = phoneNumber.length();
        int frontIndex = frontIndexCheck(len);
        int backIndex = backIndexCheck(len);

        stringBuilder.insert(frontIndex, DELIMITER);
        stringBuilder.insert(backIndex, DELIMITER);

        return new PhoneNumber(stringBuilder.toString());
    }

    public int frontIndexCheck(int len){
        if(len == 11) return 3;
        else return 2;
    }

    public int backIndexCheck(int len){
        if(len == 11) return 8;
        else return 7;
    }
}

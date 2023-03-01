package kr.co.popoolserver.persistence.entity.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Address {

    @ApiModelProperty(example = "12345")
    @Column(name = "zipcode")
    private String zipcode;

    @ApiModelProperty(example = "XX특별시 XX구 XX동 00")
    @Column(name = "address1")
    private String address1;

    @ApiModelProperty(example = "101동 1101호")
    @Column(name = "address2")
    private String address2;

    @Builder
    public Address(String zipcode,
                   String address1,
                   String address2) {
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
    }
}

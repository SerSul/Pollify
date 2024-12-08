package ru.coursework.pollify.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coursework.pollify.annotations.Meta;

import java.text.MessageFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "sh_contact_phone_data" )
@Meta( title = "Персональный телефонный номер" )
public class ContactPhoneData extends BaseEntity {

    @Meta(title = "Международный код страны")
    @Column(name = "country_code_p")
    private String countryCode;

    @Meta(title = "Код оператора")
    @Column(name = "operator_code_p")
    private String operatorCode;

    @Meta(title = "Номер телефона")
    @Column(name = "number_p")
    private String number;

    @Meta(title = "Полный номер свободным вводом")
    @Column(name = "value_p")
    private String value;

    @Transient
    public String buildNumber() {
        if (!isFilled()) return "-";
        if (StringUtils.isNotBlank(getValue())) return value;
        return MessageFormat.format("+{0} ({1}) {2}", getCountryCode(), getOperatorCode(), getNumber());
    }

    @Transient
    public boolean isFilled() {
        return !StringUtils.isAnyBlank(getCountryCode(), getOperatorCode(), getNumber()) || StringUtils.isNotBlank(getValue());
    }

    @Override
    public String toString() {
        return buildNumber();
    }

    public ContactPhoneData copy() {
        var copiedPhone = new ContactPhoneData();
        copiedPhone.setCountryCode(this.getCountryCode());
        copiedPhone.setOperatorCode(this.getOperatorCode());
        copiedPhone.setNumber(this.getNumber());
        copiedPhone.setValue(this.getValue());

        return copiedPhone;
    }
}


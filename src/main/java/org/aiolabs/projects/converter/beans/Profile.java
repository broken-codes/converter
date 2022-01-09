package org.aiolabs.projects.converter.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @NotNull(message = "First name cannot be null.")
    private String firstName;

    private String lastName;

    private String companyName;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String phone1;

    private String phone2;

    @Email(message = "Email address is not valid.")
    private String email;

    private String web;

    @Override
    public String toString() {
        return new StringJoiner(",")
                .add(firstName)
                .add(lastName)
                .add(companyName)
                .add(address)
                .add(city)
                .add(state)
                .add(zip)
                .add(phone1)
                .add(phone2)
                .add(email)
                .add(web)
                .toString();
    }

    /**
     * This is lazy but works. Instead of creating custom hashCode implementation (which is hard), the current
     * implementation relies on SDK provided hashCode() implementation of {@link String} class. This could be a bit
     * slower compared to a custom implementation but probably more accurate.
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

package org.aiolabs.projects.converter.beans;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    @Test
    public void tesProfile() {
        PojoClass profileClass = PojoClassFactory.getPojoClass(Profile.class);
        Validator validation = ValidatorBuilder.create()
                .with(new SetterTester(), new GetterTester())
                .build();
        validation.validate(profileClass);

        Profile profile = Profile.builder()
                .firstName("firstName")
                .lastName("lastName")
                .address("address")
                .city("city")
                .companyName("companyName")
                .email("email")
                .phone1("phone1")
                .phone2("phone2")
                .state("state")
                .zip("zip")
                .web("web")
                .build();
        assertThat(profile.toString()).isNotNull();
        assertThat(profile.toString()).isEqualTo("firstName,lastName,companyName,address,city,state,zip,phone1,phone2,email,web");

        Profile profile2 = Profile.builder()
                .firstName("firstName")
                .lastName("lastName")
                .address("address")
                .city("city")
                .companyName("companyName")
                .email("email")
                .phone1("phone1")
                .phone2("phone2")
                .state("state")
                .zip("zip")
                .web("web")
                .build();
        assertThat(profile.hashCode()).isEqualTo(profile2.hashCode());

        Profile profile3 = Profile.builder()
                .firstName("firstName1")
                .lastName("lastName")
                .address("address")
                .city("city")
                .companyName("companyName")
                .email("email")
                .phone1("phone1")
                .phone2("phone2")
                .state("state")
                .zip("zip")
                .web("web")
                .build();
        assertThat(profile.hashCode()).isNotEqualTo(profile3.hashCode());
    }
}
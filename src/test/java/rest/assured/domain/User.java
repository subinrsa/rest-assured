package rest.assured.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data //@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonAlias("first_name")
    private String name;
    private String job;
    private String email;
    @Setter
    private String password;

    @JsonAlias("last_name")
    private String lastName;

//    public String getName() {
//        return name;
//    }
//
//    public String getJob() {
//        return job;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public User() {
//
//    }

//    public User(String name, String job, String email) {
//        this.name = name;
//        this.job = job;
//        this.email = email;
//    }
}

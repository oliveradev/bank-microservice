package bootcamp.customerservice.customerservice.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Customer {
    @Id
    private String id;
    @Field(name="name")

    private String name;
    @Indexed(unique = true)

    @Field(name = "customerIdentityType")
    private String customerIdentityType;

    @Field(name = "customerIdentityNumber")
    @Indexed(unique = true)
    private String customerIdentityNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();

    private CustomerType customerType;
}

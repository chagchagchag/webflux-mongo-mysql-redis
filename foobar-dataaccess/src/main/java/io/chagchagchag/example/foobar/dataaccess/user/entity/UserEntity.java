package io.chagchagchag.example.foobar.dataaccess.user.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("USER")
@AllArgsConstructor(staticName = "ofAll")
public class UserEntity {
  @Id
  private Long id;

  private final String name;
  private final Integer age;
  private final String profileImageId;
  private final String password;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @PersistenceCreator
  public UserEntity(
      Long id, String name, Integer age,
      String profileImageId, String password
  ){
    this.id = id;
    this.name = name;
    this.age = age;
    this.profileImageId = profileImageId;
    this.password = password;
  }

  @Builder(builderClassName = "CreateUserBuilder", builderMethodName = "createBuilder")
  public UserEntity(
      String name, Integer age, String profileImageId, String password
  ){
    this(null, name, age, profileImageId, password);
  }

}

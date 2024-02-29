package io.chagchagchag.example.foobar.dataaccess.user.entity;


import java.time.LocalDateTime;
import java.util.List;
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
  private final String roles;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @PersistenceCreator
  public UserEntity(
      Long id, String name, Integer age,
      String profileImageId,
      String password,
      String roles
  ){
    this.id = id;
    this.name = name;
    this.age = age;
    this.profileImageId = profileImageId;
    this.password = password;
    this.roles = roles;
  }

  @Builder(builderClassName = "CreateUserBuilder", builderMethodName = "createBuilder")
  public UserEntity(
      String name, Integer age, String profileImageId, String password, String roles
  ){
    this(null, name, age, profileImageId, password, roles);
  }

  // 관용적으로 getRoleList()라고 쓰는 편이지만, Lombok 과 혼동될 소지가 있어서 parse** 으로 지정
  public List<String> parseRoleList(){
    if(roles.length() > 0)
      return List.of(
          roles.split(",")
      );
    else
      return List.of();
  }
}

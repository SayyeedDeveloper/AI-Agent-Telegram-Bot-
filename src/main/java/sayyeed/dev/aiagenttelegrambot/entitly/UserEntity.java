package sayyeed.dev.aiagenttelegrambot.entitly;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column()
    private String userName;


    @Column(unique = true)
    private Long chatId;

    @OneToMany(mappedBy = "user")
    private List<UserAiLogsEntity> userAiLogsEntityList;


    /**
     * Getter & Setter
     **/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public List<UserAiLogsEntity> getUserAiLogsList() {
        return userAiLogsEntityList;
    }

    public void setUserAiLogsList(List<UserAiLogsEntity> userAiLogsEntityList) {
        this.userAiLogsEntityList = userAiLogsEntityList;
    }

}

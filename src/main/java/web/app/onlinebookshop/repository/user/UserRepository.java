package web.app.onlinebookshop.repository.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import web.app.onlinebookshop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

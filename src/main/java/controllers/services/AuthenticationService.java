package controllers.services;

import DAO.DAOFactory;
import DAO.UserDAO;
import domain.model.User;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AuthenticationService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class.getName());
  private User mUser;
  private UserDAO dao = DAOFactory.getUserDAO();

  public synchronized boolean login(@NonNull String username, @NonNull String password) {
    try {
      mUser = dao.getUser(username);
      String storedHash = mUser.getHash();
      return storedHash != null && PasswordHash.validatePassword(password, storedHash);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      LOGGER.error("Can't validate password", e.getCause());
      return false;
    }
  }

  public synchronized boolean register(@NonNull String username, @NonNull String password) {
    System.out.println(username);
    String hash;
    if (mUser == null) {
      try {
        hash = PasswordHash.createHash(password);
        User user = new User();
        user.setUserName(username);
        user.setHash(hash);
        dao.addUser(user);
      } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        e.printStackTrace();
      }
      return true;
    } else {
      hash = this.mUser.getHash();
      try {
        return PasswordHash.validatePassword(password, hash);
      } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}